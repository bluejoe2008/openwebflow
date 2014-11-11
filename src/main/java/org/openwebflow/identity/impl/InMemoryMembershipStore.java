package org.openwebflow.identity.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.openwebflow.identity.IdentityMembershipManager;

/**
 * 本类演示自定义用户管理
 * 
 * @author bluejoe2008@gmail.com
 * 
 */
public class InMemoryMembershipStore implements IdentityMembershipManager
{
	private Map<String, Group> _groups = new HashMap<String, Group>();

	class Membership
	{
		String _userId;

		public Membership(String userId, String group)
		{
			super();
			_userId = userId;
			_groupId = group;
		}

		String _groupId;
	}

	private List<Membership> _memberships = new ArrayList<Membership>();

	public Group createGroup(String groupId, String groupName)
	{
		Group group = _groups.get(groupId);
		if (group == null)
		{
			group = new GroupEntity(groupId);
			_groups.put(groupId, group);
		}

		if (groupName != null)
		{
			group.setName(groupName);
		}

		return group;
	}

	public void createMembership(String userId, String groupId)
	{
		_memberships.add(new Membership(userId, groupId));
	}

	protected Group getGroupById(String groupId)
	{
		return createGroup(groupId, null);
	}

	@Override
	public List<String> findGroupIdsByUser(String userId)
	{
		Map<String, Object> groupIds = new HashMap<String, Object>();
		for (Membership ms : _memberships)
		{
			if (userId.equals(ms._userId))
			{
				groupIds.put(ms._groupId, new Object());
			}
		}

		return new ArrayList<String>(groupIds.keySet());
	}

	public Collection<Group> getGroups()
	{
		return _groups.values();
	}

	public void setGroupsText(String text)
	{
		for (String pair : text.split(";"))
		{
			String[] gp = pair.split(":");
			createGroup(gp[0], gp[1]);
		}
	}

	public void setPermissionsText(String text)
	{
		for (String pair : text.split(";"))
		{
			String[] gp = pair.split(":");
			createMembership(gp[0], gp[1]);
		}
	}

	@Override
	public List<String> findUserIdsByGroup(String groupId)
	{
		Map<String, Object> userIds = new HashMap<String, Object>();
		for (Membership ms : _memberships)
		{
			if (groupId.equals(ms._groupId))
			{
				userIds.put(ms._userId, new Object());
			}
		}

		return new ArrayList<String>(userIds.keySet());
	}
}
