package org.openwebflow.ctrl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.openwebflow.util.ActivityUtils;

public class TaskFlowControlService
{
	ProcessEngine _processEngine;

	private String _processId;

	public TaskFlowControlService(ProcessEngine processEngine, String processId)
	{
		_processEngine = processEngine;
		_processId = processId;
	}

	/**
	 * 跳转至指定活动节点
	 * 
	 * @param targetTaskDefinitionKey
	 * @throws Exception
	 */
	public void jump(String targetTaskDefinitionKey) throws Exception
	{
		TaskEntity currentTask = (TaskEntity) _processEngine.getTaskService().createTaskQuery()
				.processInstanceId(_processId).singleResult();
		jump(currentTask, targetTaskDefinitionKey);
	}

	/**
	 * 
	 * @param currentTaskEntity 当前任务节点
	 * @param targetTaskDefinitionKey 目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	private void jump(final TaskEntity currentTaskEntity, String targetTaskDefinitionKey) throws Exception
	{
		final ActivityImpl activity = ActivityUtils.getActivity(_processEngine,
			currentTaskEntity.getProcessDefinitionId(), targetTaskDefinitionKey);

		final ExecutionEntity execution = (ExecutionEntity) _processEngine.getRuntimeService().createExecutionQuery()
				.executionId(currentTaskEntity.getExecutionId()).singleResult();

		//包装一个Command对象
		((RuntimeServiceImpl) _processEngine.getRuntimeService()).getCommandExecutor().execute(
			new Command<java.lang.Void>()
			{
				@Override
				public Void execute(CommandContext commandContext)
				{
					//创建新任务
					execution.setActivity(activity);
					execution.executeActivity(activity);
					
					//删除当前的任务
					//不能删除当前正在执行的任务，所以要先清除掉关联
					currentTaskEntity.setExecutionId(null);
					_processEngine.getTaskService().saveTask(currentTaskEntity);
					_processEngine.getTaskService().deleteTask(currentTaskEntity.getId(), true);

					return null;
				}
			});
	}
}
