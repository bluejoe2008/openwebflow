package org.openwebflow.assign;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public interface TaskAssignmentHandler
{
	void handleAssignment(TaskAssignmentHandlerChain chain, TaskEntity task, ActivityExecution execution);
}
