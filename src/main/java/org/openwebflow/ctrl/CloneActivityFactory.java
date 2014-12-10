package org.openwebflow.ctrl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.TaskActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.openwebflow.util.CloneUtils;
import org.openwebflow.util.ProcessDefinitionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

public class CloneActivityFactory
{
	private ProcessDefinitionEntity _processDefinition;

	private ProcessEngine _processEngine;

	public CloneActivityFactory(ProcessEngine processEngine, ProcessDefinitionEntity processDefinition)
	{
		_processDefinition = processDefinition;
		_processEngine = processEngine;
	}

	public ActivityImpl createActivity(ActivityImpl prototypeActivity, String cloneActivityId, String assignee)
	{
		ActivityImpl clone = cloneActivity(prototypeActivity, cloneActivityId, "executionListeners", "properties");

		//设置assignee
		UserTaskActivityBehavior activityBehavior = (UserTaskActivityBehavior) (prototypeActivity.getActivityBehavior());

		TaskDefinition taskDefinition = cloneTaskDefinition(activityBehavior.getTaskDefinition());
		taskDefinition.setKey(cloneActivityId);
		if (assignee != null)
		{
			taskDefinition.setAssigneeExpression(new FixedValue(assignee));
		}

		UserTaskActivityBehavior cloneActivityBehavior = ((ProcessEngineConfigurationImpl) _processEngine
				.getProcessEngineConfiguration()).getActivityBehaviorFactory().createUserTaskActivityBehavior(null,
			taskDefinition);
		clone.setActivityBehavior(cloneActivityBehavior);

		return clone;
	}

	private TaskDefinition cloneTaskDefinition(TaskDefinition taskDefinition)
	{
		TaskDefinition newTaskDefinition = new TaskDefinition(taskDefinition.getTaskFormHandler());
		BeanUtils.copyProperties(taskDefinition, newTaskDefinition);
		return newTaskDefinition;
	}

	public ActivityImpl createActivity(String prototypeActivityId, String cloneActivityId, String assignee)
	{
		ActivityImpl prototypeActivity = ProcessDefinitionUtils.getActivity(_processEngine, _processDefinition.getId(),
			prototypeActivityId);

		return createActivity(prototypeActivity, cloneActivityId, assignee);
	}

	public ActivityImpl createMultiInstanceActivity(String prototypeActivityId, String cloneActivityId,
			boolean isSequential, String... assignee)
	{
		ActivityImpl prototypeActivity = ProcessDefinitionUtils.getActivity(_processEngine, _processDefinition.getId(),
			prototypeActivityId);

		//拷贝listener，executionListeners会激活历史记录的保存
		ActivityImpl clone = cloneActivity(prototypeActivity, cloneActivityId, "executionListeners", "properties");
		//拷贝所有后向链接
		for (PvmTransition trans : prototypeActivity.getOutgoingTransitions())
		{
			clone.createOutgoingTransition(trans.getId()).setDestination((ActivityImpl) trans.getDestination());
		}

		MultiInstanceActivityBehavior multiInstanceBehavior = isSequential ? new SequentialMultiInstanceBehavior(clone,
				(TaskActivityBehavior) prototypeActivity.getActivityBehavior()) : new ParallelMultiInstanceBehavior(
				clone, (TaskActivityBehavior) prototypeActivity.getActivityBehavior());

		clone.setActivityBehavior(multiInstanceBehavior);

		clone.setScope(true);
		clone.setProperty("multiInstance", isSequential ? "sequential" : "parallel");

		//设置多实例节点属性
		multiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(assignee.length));
		multiInstanceBehavior.setCollectionExpression(new FixedValue(CollectionUtils.arrayToList(assignee)));
		return clone;
	}

	private ActivityImpl cloneActivity(ActivityImpl prototypeActivity, String newActivityId, String... fieldNames)
	{
		ActivityImpl clone = _processDefinition.createActivity(newActivityId);
		CloneUtils.copyFields(prototypeActivity, clone, fieldNames);

		return clone;
	}
}
