package org.openwebflow.assign.impl;

import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.openwebflow.assign.TaskAssignmentHandler;
import org.openwebflow.assign.TaskAssignmentHandlerChain;

public class TaskAssignmentHandlerChainImpl implements TaskAssignmentHandlerChain
{
	static TaskAssignmentHandler NULL_HANDLER = new TaskAssignmentHandler()
	{
		@Override
		public void handleAssignment(TaskAssignmentHandlerChain chain, Expression assigneeExpression, Expression ownerExpression, Set<Expression> candidateUserExpressions,
			      Set<Expression> candidateGroupExpressions, TaskEntity task, ActivityExecution execution)
		{
		}
	};

	Stack<TaskAssignmentHandler> _handlers = new Stack<TaskAssignmentHandler>();

	public void addHandler(TaskAssignmentHandler handler)
	{
		_handlers.push(handler);
	}

	public void addHandlers(List<TaskAssignmentHandler> handlers)
	{
		for (TaskAssignmentHandler handler : handlers)
		{
			_handlers.push(handler);
		}
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
	public void resume(Expression assigneeExpression, Expression ownerExpression, Set<Expression> candidateUserExpressions,
		      Set<Expression> candidateGroupExpressions, TaskEntity task, ActivityExecution execution)
	{
		next().handleAssignment(this, assigneeExpression, ownerExpression, candidateUserExpressions, 
		        candidateGroupExpressions, task, execution);
	}
}
