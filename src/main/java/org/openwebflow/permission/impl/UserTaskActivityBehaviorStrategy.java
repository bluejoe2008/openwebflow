package org.openwebflow.permission.impl;

import java.util.List;

import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.task.TaskDefinition;
import org.openwebflow.permission.AccessControlStrategy;
import org.openwebflow.permission.AccessControlStrategyChain;

public class UserTaskActivityBehaviorStrategy extends UserTaskActivityBehavior implements AccessControlStrategy
{
	List<AccessControlStrategy> _accessControlStrategies;

	public UserTaskActivityBehaviorStrategy(List<AccessControlStrategy> accessControlStrategies,
			TaskDefinition taskDefinition)
	{
		super(taskDefinition);
		_accessControlStrategies = accessControlStrategies;
	}

	@Override
	public void apply(AccessControlStrategyChain chain, TaskEntity task, ActivityExecution execution)
	{
		super.handleAssignments(task, execution);
	}

	protected AccessControlStrategyChainImpl createHandlerChain()
	{
		AccessControlStrategyChainImpl handlerChain = new AccessControlStrategyChainImpl();
		handlerChain.addStrategy(this);
		handlerChain.addStrategies(_accessControlStrategies);
		return handlerChain;
	}

	@Override
	protected void handleAssignments(TaskEntity task, ActivityExecution execution)
	{
		createHandlerChain().resume(task, execution);
	}
}
