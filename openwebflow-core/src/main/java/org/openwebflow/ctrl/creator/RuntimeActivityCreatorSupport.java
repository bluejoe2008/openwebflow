package org.openwebflow.ctrl.creator;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.openwebflow.util.CloneUtils;
import org.openwebflow.util.ProcessDefinitionUtils;
import org.springframework.beans.BeanUtils;

public abstract class RuntimeActivityCreatorSupport
{
	private static int SEQUNCE_NUMBER = 0;

	protected ActivityImpl cloneActivity(ProcessDefinitionEntity processDefinition, ActivityImpl prototypeActivity,
			String newActivityId, String... fieldNames)
	{
		ActivityImpl clone = processDefinition.createActivity(newActivityId);
		CloneUtils.copyFields(prototypeActivity, clone, fieldNames);

		return clone;
	}

	protected TaskDefinition cloneTaskDefinition(TaskDefinition taskDefinition)
	{
		TaskDefinition newTaskDefinition = new TaskDefinition(taskDefinition.getTaskFormHandler());
		BeanUtils.copyProperties(taskDefinition, newTaskDefinition);
		return newTaskDefinition;
	}

	protected ActivityImpl createActivity(ProcessEngine processEngine, ProcessDefinitionEntity processDefinition,
			ActivityImpl prototypeActivity, String cloneActivityId, String assignee)
	{
		ActivityImpl clone = cloneActivity(processDefinition, prototypeActivity, cloneActivityId, "executionListeners",
			"properties");

		//设置assignee
		UserTaskActivityBehavior activityBehavior = (UserTaskActivityBehavior) (prototypeActivity.getActivityBehavior());

		TaskDefinition taskDefinition = cloneTaskDefinition(activityBehavior.getTaskDefinition());
		taskDefinition.setKey(cloneActivityId);
		if (assignee != null)
		{
			taskDefinition.setAssigneeExpression(new FixedValue(assignee));
		}

		UserTaskActivityBehavior cloneActivityBehavior = new UserTaskActivityBehavior(prototypeActivity.getId(), taskDefinition);
		clone.setActivityBehavior(cloneActivityBehavior);

		return clone;
	}

	protected ActivityImpl createActivity(ProcessEngine processEngine, ProcessDefinitionEntity processDefinition,
			String prototypeActivityId, String cloneActivityId, String assignee)
	{
		ActivityImpl prototypeActivity = ProcessDefinitionUtils.getActivity(processEngine, processDefinition.getId(),
			prototypeActivityId);

		return createActivity(processEngine, processDefinition, prototypeActivity, cloneActivityId, assignee);
	}

	protected void createActivityChain(List<ActivityImpl> activities, ActivityImpl nextActivity)
	{
		for (int i = 0; i < activities.size(); i++)
		{
			//设置各活动的下线
			activities.get(i).getOutgoingTransitions().clear();
			activities.get(i).createOutgoingTransition("flow" + (i + 1))
					.setDestination(i == activities.size() - 1 ? nextActivity : activities.get(i + 1));
		}
	}

	protected String createUniqueActivityId(String processInstanceId, String prototypeActivityId)
	{
		return processInstanceId + ":" + prototypeActivityId + ":" + System.currentTimeMillis() + "-"
				+ (SEQUNCE_NUMBER++);
	}
}
