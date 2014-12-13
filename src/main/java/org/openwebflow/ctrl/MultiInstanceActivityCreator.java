package org.openwebflow.ctrl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.TaskActivityBehavior;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.openwebflow.util.ProcessDefinitionUtils;
import org.springframework.util.CollectionUtils;

public class MultiInstanceActivityCreator extends AbstractActivitiesCreator implements ActivitiesCreator
{
	@Override
	public ActivityImpl[] createActivities(ProcessEngine processEngine, ProcessDefinitionEntity processDefinition,
			ActivitiesCreationEntity info)
	{
		if (info.getCloneActivityId() == null)
		{
			String cloneActivityId = createUniqueActivityId(info.getProcessInstanceId(), info.getPrototypeActivityId());
			info.setCloneActivityId(cloneActivityId);
		}

		return new ActivityImpl[] { createMultiInstanceActivity(processEngine, processDefinition,
			info.getProcessInstanceId(), info.getPrototypeActivityId(), info.getCloneActivityId(),
			info.getSequential(), info.getAssignees()) };
	}

	private ActivityImpl createMultiInstanceActivity(ProcessEngine processEngine,
			ProcessDefinitionEntity processDefinition, String processInstanceId, String prototypeActivityId,
			String cloneActivityId, boolean isSequential, String... assignee)
	{
		ActivityImpl prototypeActivity = ProcessDefinitionUtils.getActivity(processEngine, processDefinition.getId(),
			prototypeActivityId);

		//拷贝listener，executionListeners会激活历史记录的保存
		ActivityImpl clone = cloneActivity(processDefinition, prototypeActivity, cloneActivityId, "executionListeners",
			"properties");
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
}
