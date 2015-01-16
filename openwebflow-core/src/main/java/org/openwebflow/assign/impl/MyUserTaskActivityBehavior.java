package org.openwebflow.assign.impl;

import java.util.List;

import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.task.TaskDefinition;
import org.openwebflow.assign.TaskAssignmentHandler;
import org.openwebflow.assign.TaskAssignmentHandlerChain;

public class MyUserTaskActivityBehavior extends UserTaskActivityBehavior
{
	List<TaskAssignmentHandler> _handlers;

	public MyUserTaskActivityBehavior(List<TaskAssignmentHandler> handlers, TaskDefinition taskDefinition)
	{
		super(taskDefinition);
		_handlers = handlers;
	}

	protected TaskAssignmentHandlerChainImpl createHandlerChain()
	{
		TaskAssignmentHandlerChainImpl handlerChain = new TaskAssignmentHandlerChainImpl();
		final MyUserTaskActivityBehavior myUserTaskActivityBehavior = this;
		handlerChain.addHandler(new TaskAssignmentHandler()
		{
			@Override
			public void handleAssignment(TaskAssignmentHandlerChain chain, TaskEntity task, ActivityExecution execution)
			{
				myUserTaskActivityBehavior.superHandleAssignments(task, execution);
			}
		});

		handlerChain.addHandlers(_handlers);
		return handlerChain;
	}

	@Override
	protected void handleAssignments(TaskEntity task, ActivityExecution execution)
	{
		createHandlerChain().resume(task, execution);
	}

	protected void superHandleAssignments(TaskEntity task, ActivityExecution execution)
	{
		super.handleAssignments(task, execution);
	}
}
