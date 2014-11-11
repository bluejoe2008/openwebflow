package org.openwebflow.permission.impl;

import java.util.List;
import java.util.Stack;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.openwebflow.permission.TaskAssignmentHandlerChain;
import org.openwebflow.permission.TaskAssignmentHandler;

public class TaskAssignmentHandlerChainImpl implements TaskAssignmentHandlerChain
{
	static TaskAssignmentHandler NULL_HANDLER = new TaskAssignmentHandler()
	{
		@Override
		public void handleAssignment(TaskAssignmentHandlerChain chain, TaskEntity task, ActivityExecution execution)
		{
		}
	};

	Stack<TaskAssignmentHandler> _handlers = new Stack<TaskAssignmentHandler>();

	public void addHandlers(List<TaskAssignmentHandler> handlers)
	{
		for (TaskAssignmentHandler handler : handlers)
		{
			_handlers.push(handler);
		}
	}

	public void addHandler(TaskAssignmentHandler handler)
	{
		_handlers.push(handler);
	}

	public TaskAssignmentHandler next()
	{
		if (_handlers.isEmpty())
		{
			return NULL_HANDLER;
		}

		return _handlers.pop();
	}

	@Override
	public void resume(TaskEntity task, ActivityExecution execution)
	{
		next().handleAssignment(this, task, execution);
	}
}
