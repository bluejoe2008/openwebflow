package org.openwebflow.permission.impl;

import java.util.List;

import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.task.TaskDefinition;
import org.openwebflow.permission.AccessControlStrategy;

public class MyActivityBehaviorFactory extends ActivityBehaviorFactoryDelegate implements ActivityBehaviorFactory
{
	List<AccessControlStrategy> _accessControlStrategies;

	public MyActivityBehaviorFactory(ActivityBehaviorFactory source,
			List<AccessControlStrategy> accessControlStrategies)
	{
		super(source);
		_accessControlStrategies = accessControlStrategies;
	}

	@Override
	public UserTaskActivityBehavior createUserTaskActivityBehavior(UserTask userTask, TaskDefinition taskDefinition)
	{
		return new UserTaskActivityBehaviorStrategy(_accessControlStrategies, taskDefinition);
	}
}
