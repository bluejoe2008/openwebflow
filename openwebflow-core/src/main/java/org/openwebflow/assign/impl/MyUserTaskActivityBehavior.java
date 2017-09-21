package org.openwebflow.assign.impl;

import java.util.List;
import java.util.Set;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.task.TaskDefinition;
import org.openwebflow.assign.TaskAssignmentHandler;
import org.openwebflow.assign.TaskAssignmentHandlerChain;

public class MyUserTaskActivityBehavior extends UserTaskActivityBehavior
{
	List<TaskAssignmentHandler> _handlers;

	public MyUserTaskActivityBehavior(List<TaskAssignmentHandler> handlers, String userTaskId, TaskDefinition taskDefinition)
	{
		super(userTaskId, taskDefinition);
		_handlers = handlers;
	}

	protected TaskAssignmentHandlerChainImpl createHandlerChain()
	{
		TaskAssignmentHandlerChainImpl handlerChain = new TaskAssignmentHandlerChainImpl();
		final MyUserTaskActivityBehavior myUserTaskActivityBehavior = this;
		handlerChain.addHandler(new TaskAssignmentHandler()
		{
			@Override
			public void handleAssignment(TaskAssignmentHandlerChain chain, Expression assigneeExpression, Expression ownerExpression, Set<Expression> candidateUserExpressions,
				      Set<Expression> candidateGroupExpressions, TaskEntity task, ActivityExecution execution)
			{
				myUserTaskActivityBehavior.superHandleAssignments(assigneeExpression, ownerExpression, candidateUserExpressions, 
				        candidateGroupExpressions, task, execution);
			}
		});

		handlerChain.addHandlers(_handlers);
		return handlerChain;
	}

	@Override
	protected void handleAssignments(Expression assigneeExpression, Expression ownerExpression, Set<Expression> candidateUserExpressions,
		      Set<Expression> candidateGroupExpressions, TaskEntity task, ActivityExecution execution)
	{
		createHandlerChain().resume(assigneeExpression, ownerExpression, candidateUserExpressions, 
		        candidateGroupExpressions, task, execution);
	}

	protected void superHandleAssignments(Expression assigneeExpression, Expression ownerExpression, Set<Expression> candidateUserExpressions,
		      Set<Expression> candidateGroupExpressions, TaskEntity task, ActivityExecution execution)
	{
		super.handleAssignments(assigneeExpression, ownerExpression, candidateUserExpressions, 
		        candidateGroupExpressions, task, execution);
	}
}
