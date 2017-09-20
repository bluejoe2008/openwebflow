package org.openwebflow.assign.delegation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.task.IdentityLink;
import org.openwebflow.assign.TaskAssignmentHandler;
import org.openwebflow.assign.TaskAssignmentHandlerChain;
import org.openwebflow.identity.IdentityMembershipManager;

public class TaskDelagationAssignmentHandler implements TaskAssignmentHandler
{
	DelegationManager _delegationManager;

	boolean _hideDelegated = false;

	IdentityMembershipManager _membershipManager;

	private void addCandidateUsers(TaskEntity task, Set<String> delegates)
	{
		for (String delegate : delegates)
		{
			task.addCandidateUser(delegate);
		}
	}

	public DelegationManager getDelegationManager()
	{
		return _delegationManager;
	}

	public IdentityMembershipManager getMembershipManager()
	{
		return _membershipManager;
	}

	@Override
	public void handleAssignment(TaskAssignmentHandlerChain chain, Expression assigneeExpression,
			Expression ownerExpression, Set<Expression> candidateUserExpressions,
			Set<Expression> candidateGroupExpressions, TaskEntity task, ActivityExecution execution)
	{
		//先执行其它规则
		chain.resume(assigneeExpression, ownerExpression, candidateUserExpressions,
			      candidateGroupExpressions, task, execution);

		overwriteAssignee(task);

		Map<String, Object> userIdMap = new HashMap<String, Object>();
		Map<String, Object> groupIdMap = new HashMap<String, Object>();
		retrieveCandidateUserIdsAndGroupIds(task, userIdMap, groupIdMap);
		Map<String, Object> newUserIdMap = new HashMap<String, Object>();
		Map<String, Object> removeUserIdMap = new HashMap<String, Object>();

		//遍历所有的被代理人
		List<DelegationEntity> entries = _delegationManager.listDelegationEntities();
		overwriteCandicateUserIds(userIdMap, newUserIdMap, removeUserIdMap, entries);
		overwriteCandicateGroupIds(groupIdMap, newUserIdMap, entries);

		addCandidateUsers(task, newUserIdMap.keySet());
		removeCandidateUsers(task, removeUserIdMap.keySet());
	}

	private boolean isCandicateGroupMember(String delegated, Map<String, Object> groupIds)
	{
		for (String groupId : _membershipManager.findGroupIdsByUser(delegated))
		{
			if (groupIds.containsKey(groupId))
				return true;
		}

		return false;
	}

	public boolean isHideDelegated()
	{
		return _hideDelegated;
	}

	protected void overwriteAssignee(TaskEntity task)
	{
		String assignee = task.getAssignee();

		//受理人是否被代理
		if (assignee != null)
		{
			String[] delegates = _delegationManager.getDelegates(assignee);
			if (delegates != null && delegates.length > 0)
			{
				task.setAssignee(delegates[0]);
			}
		}
	}

	protected void overwriteCandicateGroupIds(Map<String, Object> groupIdMap, Map<String, Object> newUserIdMap,
			List<DelegationEntity> entries)
	{
		for (DelegationEntity en : entries)
		{
			//代理人已经在map里了
			if (newUserIdMap.containsKey(en.getDelegate()))
			{
				continue;
			}

			//该被代理人是否属于候选组
			if (isCandicateGroupMember(en.getDelegated(), groupIdMap))
			{
				newUserIdMap.put(en.getDelegate(), 0);
			}
		}
	}

	protected void overwriteCandicateUserIds(Map<String, Object> userIdMap, Map<String, Object> newUserIdMap,
			Map<String, Object> removeUserIdMap, List<DelegationEntity> entries)
	{
		for (DelegationEntity en : entries)
		{
			//代理人已经在map里了
			if (newUserIdMap.containsKey(en.getDelegate()))
			{
				continue;
			}

			//被代理人作为候选用户
			if (userIdMap.containsKey(en.getDelegated()))
			{
				newUserIdMap.put(en.getDelegate(), 0);
				if (this.isHideDelegated())
				{
					removeUserIdMap.put(en.getDelegated(), 0);
				}
			}
		}
	}

	private void removeCandidateUsers(TaskEntity task, Set<String> delegates)
	{
		for (String delegate : delegates)
		{
			task.deleteCandidateUser(delegate);
		}
	}

	private void retrieveCandidateUserIdsAndGroupIds(TaskEntity task, Map<String, Object> userIdMap,
			Map<String, Object> groupIdMap)
	{
		for (IdentityLink link : task.getCandidates())
		{
			String userId = link.getUserId();
			if (userId != null)
			{
				userIdMap.put(userId, 0);
			}

			String groupId = link.getGroupId();
			if (groupId != null)
			{
				groupIdMap.put(groupId, 0);
			}
		}
	}

	public void setDelegationManager(DelegationManager delegationManager)
	{
		_delegationManager = delegationManager;
	}

	public void setHideDelegated(boolean hideDelegated)
	{
		_hideDelegated = hideDelegated;
	}

	public void setMembershipManager(IdentityMembershipManager membershipManager)
	{
		_membershipManager = membershipManager;
	}

}
