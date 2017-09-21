package org.openwebflow.assign;

import java.util.Set;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public interface TaskAssignmentHandler
{
	void handleAssignment(TaskAssignmentHandlerChain chain, Expression assigneeExpression, Expression ownerExpression, Set<Expression> candidateUserExpressions,
		      Set<Expression> candidateGroupExpressions, TaskEntity task, ActivityExecution execution);
}
