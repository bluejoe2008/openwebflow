package org.openwebflow.ctrl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.openwebflow.util.ProcessDefinitionUtils;

public class CloneActivitiesCreator extends AbstractActivitiesCreator implements ActivitiesCreator
{
	@Override
	public ActivityImpl[] createActivities(ProcessEngine processEngine, ProcessDefinitionEntity processDefinition,
			ActivitiesCreationEntity info)
	{
		return createActivities(processEngine, processDefinition, info.getProcessInstanceId(),
			info.getPrototypeActivityId(), info.getNextActivityId(), info.getAssignees(), info.getCloneActivityIds());
	}

	private ActivityImpl[] createActivities(ProcessEngine processEngine, ProcessDefinitionEntity processDefinition,
			String processInstanceId, String prototypeActivityId, String nextActivityId, String[] assignees,
			String activityIds[])
	{
		ActivityImpl prototypeActivity = ProcessDefinitionUtils.getActivity(processEngine, processDefinition.getId(),
			prototypeActivityId);

		List<ActivityImpl> activities = new ArrayList<ActivityImpl>();
		for (int i = 0; i < assignees.length; i++)
		{
			if (activityIds[i] == null)
			{
				String activityId = createUniqueActivityId(processInstanceId, prototypeActivityId);
				activityIds[i] = activityId;
			}

			ActivityImpl clone = createActivity(processEngine, processDefinition, prototypeActivity, activityIds[i],
				assignees[i]);
			activities.add(clone);
		}

		ActivityImpl nextActivity = ProcessDefinitionUtils.getActivity(processEngine, processDefinition.getId(),
			nextActivityId);
		createActivityChain(activities, nextActivity);

		return activities.toArray(new ActivityImpl[0]);
	}
}
