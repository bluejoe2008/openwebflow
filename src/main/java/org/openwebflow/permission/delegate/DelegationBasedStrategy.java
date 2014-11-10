package org.openwebflow.permission.delegate;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.task.IdentityLink;
import org.openwebflow.permission.AccessControlStrategy;
import org.openwebflow.permission.AccessControlStrategyChain;

public class DelegationBasedStrategy implements AccessControlStrategy
{
	DelegationDetailsService _delegationDetailsService;

	public DelegationDetailsService getDelegationDetailsService()
	{
		return _delegationDetailsService;
	}

	public void setDelegationDetailsService(DelegationDetailsService delegationDetailsService)
	{
		_delegationDetailsService = delegationDetailsService;
	}

	boolean _hideDelegated = false;

	public boolean isHideDelegated()
	{
		return _hideDelegated;
	}

	public void setHideDelegated(boolean hideDelegated)
	{
		_hideDelegated = hideDelegated;
	}

	@Override
	public void apply(AccessControlStrategyChain chain, TaskEntity task, ActivityExecution execution)
	{
		//先执行其它规则
		chain.resume(task, execution);
		String assignee = task.getAssignee();

		//受理人是否被代理
		if (assignee != null)
		{
			String[] delegates = _delegationDetailsService.getDelegates(assignee);
			if (delegates != null && delegates.length > 0)
			{
				task.setAssignee(delegates[0]);
			}
		}

		//候选人是否被代理
		Map<String, Object> userIdMap = getCandidateUserIds(task);
		for (String userId : userIdMap.keySet())
		{
			String[] delegates = _delegationDetailsService.getDelegates(userId);
			if (delegates != null && delegates.length > 0)
			{
				if (_hideDelegated)
				{
					//删除掉受理人的权限
					task.deleteCandidateUser(userId);
				}

				for (String delegate : delegates)
				{
					if (!userIdMap.containsKey(delegate))
					{
						task.addCandidateUser(delegate);
						userIdMap.put(delegate, new Object());
					}
				}
			}
		}
	}

	protected Map<String, Object> getCandidateUserIds(TaskEntity task)
	{
		Map<String, Object> candidateUserIds = new HashMap<String, Object>();
		for (IdentityLink link : task.getCandidates())
		{
			String userId = link.getUserId();
			if (userId != null)
			{
				candidateUserIds.put(userId, new Object());
			}
		}

		return candidateUserIds;
	}

}
