package org.openwebflow.ctrl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.openwebflow.util.ProcessDefinitionUtils;
import org.springframework.util.CollectionUtils;

public class TaskFlowControlService
{
	ProcessDefinitionEntity _processDefinition;

	ProcessEngine _processEngine;

	private String _processInstanceId;

	CloneActivityFactory _cloneActivityFactory;

	private static int SEQUNCE_NUMBER = 0;

	public TaskFlowControlService(ProcessEngine processEngine, String processId)
	{
		_processEngine = processEngine;
		_processInstanceId = processId;

		String processDefId = _processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(_processInstanceId).singleResult().getProcessDefinitionId();

		_processDefinition = ProcessDefinitionUtils.getProcessDefinition(_processEngine, processDefId);
		_cloneActivityFactory = new CloneActivityFactory(_processEngine, _processDefinition);
	}

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 * 
	 * @param targetTaskDefinitionKey
	 * @throws Exception
	 */
	public void moveTo(String targetTaskDefinitionKey) throws Exception
	{
		moveTo(getCurrentTask(), targetTaskDefinitionKey);
	}

	private TaskEntity getCurrentTask()
	{
		return (TaskEntity) _processEngine.getTaskService().createTaskQuery().processInstanceId(_processInstanceId)
				.active().singleResult();
	}

	public void moveTo(String currentTaskId, String targetTaskDefinitionKey) throws Exception
	{
		moveTo(getTaskById(currentTaskId), targetTaskDefinitionKey);
	}

	private TaskEntity getTaskById(String taskId)
	{
		return (TaskEntity) _processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
	}

	/**
	 * 
	 * @param currentTaskEntity
	 *            当前任务节点
	 * @param targetTaskDefinitionKey
	 *            目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	public void moveTo(TaskEntity currentTaskEntity, String targetTaskDefinitionKey) throws Exception
	{
		ActivityImpl activity = ProcessDefinitionUtils.getActivity(_processEngine,
			currentTaskEntity.getProcessDefinitionId(), targetTaskDefinitionKey);

		moveTo(currentTaskEntity, activity);
	}

	private void moveTo(TaskEntity currentTaskEntity, ActivityImpl activity)
	{
		executeCommand(new StartActivityCmd(currentTaskEntity.getExecutionId(), activity));
		executeCommand(new DeleteRunningTaskCmd(currentTaskEntity));
	}

	public void moveForward(TaskEntity currentTaskEntity) throws Exception
	{
		ActivityImpl activity = (ActivityImpl) ProcessDefinitionUtils
				.getActivity(_processEngine, currentTaskEntity.getProcessDefinitionId(),
					currentTaskEntity.getTaskDefinitionKey()).getOutgoingTransitions().get(0).getDestination();

		moveTo(currentTaskEntity, activity);
	}

	public void moveForward() throws Exception
	{
		moveForward(getCurrentTask());
	}

	public void moveBack(TaskEntity currentTaskEntity) throws Exception
	{
		ActivityImpl activity = (ActivityImpl) ProcessDefinitionUtils
				.getActivity(_processEngine, currentTaskEntity.getProcessDefinitionId(),
					currentTaskEntity.getTaskDefinitionKey()).getIncomingTransitions().get(0).getSource();

		moveTo(currentTaskEntity, activity);
	}

	public void moveBack() throws Exception
	{
		moveBack(getCurrentTask());
	}

	/**
	 * 前加签
	 */
	public ActivityImpl[] insertTasksBefore(String targetTaskDefinitionKey, String... assignee) throws Exception
	{
		ActivityImpl prototypeActivity = ProcessDefinitionUtils.getActivity(_processEngine, _processDefinition.getId(),
			targetTaskDefinitionKey);

		List<ActivityImpl> activities = new ArrayList<ActivityImpl>();
		for (String userId : assignee)
		{
			ActivityImpl clone = _cloneActivityFactory.createActivity(prototypeActivity,
				createUniqueActivityId(targetTaskDefinitionKey), userId);
			activities.add(clone);
		}

		createActivityChain(activities, prototypeActivity);
		moveTo(activities.get(0).getId());
		return activities.toArray(new ActivityImpl[0]);
	}

	/**
	 * 后加签
	 */
	public ActivityImpl[] insertTasksAfter(String targetTaskDefinitionKey, String... assignee) throws Exception
	{
		ActivityImpl prototypeActivity = ProcessDefinitionUtils.getActivity(_processEngine, _processDefinition.getId(),
			targetTaskDefinitionKey);

		List<ActivityImpl> activities = new ArrayList<ActivityImpl>();
		List<String> assigneeList = new ArrayList<String>();
		assigneeList.add(Authentication.getAuthenticatedUserId());
		assigneeList.addAll(CollectionUtils.arrayToList(assignee));

		for (String userId : assigneeList)
		{
			ActivityImpl clone = _cloneActivityFactory.createActivity(prototypeActivity,
				createUniqueActivityId(targetTaskDefinitionKey), userId);
			activities.add(clone);
		}

		ActivityImpl nextActivity = (ActivityImpl) prototypeActivity.getOutgoingTransitions().get(0).getDestination();
		createActivityChain(activities, nextActivity);
		moveTo(activities.get(0).getId());

		return activities.toArray(new ActivityImpl[0]);
	}

	protected void createActivityChain(List<ActivityImpl> activities, ActivityImpl tailActivity)
	{
		for (int i = 0; i < activities.size(); i++)
		{
			//设置各活动的下线
			activities.get(i).getOutgoingTransitions().clear();
			activities.get(i).createOutgoingTransition("flow" + (i + 1))
					.setDestination(i == activities.size() - 1 ? tailActivity : activities.get(i + 1));
		}
	}

	protected String createUniqueActivityId(String targetTaskDefinitionKey)
	{
		return this._processInstanceId + ":" + targetTaskDefinitionKey + ":" + System.currentTimeMillis() + "-"
				+ (SEQUNCE_NUMBER++);
	}

	public ActivityImpl split(String targetTaskDefinitionKey, String... assignee)
	{
		return split(targetTaskDefinitionKey, true, assignee);
	}

	/**
	 * 分裂某节点为多实例节点
	 * 
	 * @param targetTaskDefinitionKey
	 * @param assignee
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public ActivityImpl split(String targetTaskDefinitionKey, boolean isSequential, String... assignee)
	{
		ActivityImpl clone = _cloneActivityFactory.createMultiInstanceActivity(targetTaskDefinitionKey,
			createUniqueActivityId(targetTaskDefinitionKey), isSequential, assignee);

		TaskEntity currentTaskEntity = getCurrentTask();
		executeCommand(new CreateAndTakeTransitionCmd(currentTaskEntity.getExecutionId(), clone));
		executeCommand(new DeleteRunningTaskCmd(currentTaskEntity));

		return clone;
	}

	private void executeCommand(Command<java.lang.Void> command)
	{
		((RuntimeServiceImpl) _processEngine.getRuntimeService()).getCommandExecutor().execute(command);
	}

}
