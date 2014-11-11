package org.openwebflow.permission;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public interface TaskAssignmentHandlerChain
{
	public void resume(TaskEntity task, ActivityExecution execution);
}
