package org.openwebflow.permission.impl;

import java.util.List;
import java.util.Stack;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.openwebflow.permission.AccessControlStrategyChain;
import org.openwebflow.permission.AccessControlStrategy;

public class AccessControlStrategyChainImpl implements AccessControlStrategyChain
{
	static AccessControlStrategy NULL_HANDLER = new AccessControlStrategy()
	{
		@Override
		public void apply(AccessControlStrategyChain chain, TaskEntity task, ActivityExecution execution)
		{
		}
	};

	Stack<AccessControlStrategy> _strategies = new Stack<AccessControlStrategy>();

	public void addStrategies(List<AccessControlStrategy> handlers)
	{
		for (AccessControlStrategy handler : handlers)
		{
			_strategies.push(handler);
		}
	}

	public void addStrategy(AccessControlStrategy handler)
	{
		_strategies.push(handler);
	}

	public AccessControlStrategy next()
	{
		if (_strategies.isEmpty())
		{
			return NULL_HANDLER;
		}

		return _strategies.pop();
	}

	@Override
	public void resume(TaskEntity task, ActivityExecution execution)
	{
		next().apply(this, task, execution);
	}
}
