package org.openwebflow.assign.impl;

import java.util.List;

import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.task.TaskDefinition;
import org.openwebflow.assign.TaskAssignmentHandler;

public class MyActivityBehaviorFactory extends ActivityBehaviorFactoryDelegate implements ActivityBehaviorFactory
{
	List<TaskAssignmentHandler> _handlers;

	public MyActivityBehaviorFactory(ActivityBehaviorFactory source, List<TaskAssignmentHandler> handlers)
	{
		super(source);
		_handlers = handlers;
	}

	@Override
	public UserTaskActivityBehavior createUserTaskActivityBehavior(UserTask userTask, TaskDefinition taskDefinition)
	{
		String userTaskId = userTask.getId();
		return new MyUserTaskActivityBehavior(_handlers, userTaskId, taskDefinition);
	}
}
