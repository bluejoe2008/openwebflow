package org.openwebflow.permission;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public interface AccessControlStrategy
{
	void apply(AccessControlStrategyChain chain, TaskEntity task, ActivityExecution execution);
}
