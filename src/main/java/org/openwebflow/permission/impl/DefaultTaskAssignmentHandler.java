package org.openwebflow.permission.impl;

import java.util.List;

import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.task.TaskDefinition;
import org.openwebflow.permission.TaskAssignmentHandler;
import org.openwebflow.permission.TaskAssignmentHandlerChain;

public class DefaultTaskAssignmentHandler extends UserTaskActivityBehavior implements TaskAssignmentHandler
{
	List<TaskAssignmentHandler> _accessControlStrategies;

	public DefaultTaskAssignmentHandler(List<TaskAssignmentHandler> accessControlStrategies,
			TaskDefinition taskDefinition)
	{
		super(taskDefinition);
		_accessControlStrategies = accessControlStrategies;
	}

	@Override
	public void handleAssignment(TaskAssignmentHandlerChain chain, TaskEntity task, ActivityExecution execution)
	{
		super.handleAssignments(task, execution);
	}

	protected TaskAssignmentHandlerChainImpl createHandlerChain()
	{
		TaskAssignmentHandlerChainImpl handlerChain = new TaskAssignmentHandlerChainImpl();
		handlerChain.addHandler(this);
		handlerChain.addHandlers(_accessControlStrategies);
		return handlerChain;
	}

	@Override
	protected void handleAssignments(TaskEntity task, ActivityExecution execution)
	{
		createHandlerChain().resume(task, execution);
	}
}
