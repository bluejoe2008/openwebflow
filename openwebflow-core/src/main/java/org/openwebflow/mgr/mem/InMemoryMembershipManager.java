package org.openwebflow.mgr.mem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.openwebflow.identity.IdentityMembershipManager;
import org.openwebflow.mgr.ext.IdentityMembershipManagerEx;

/**
 * 本类演示自定义用户管理
 * 
 * @author bluejoe2008@gmail.com
 * 
 */
public class InMemoryMembershipManager implements IdentityMembershipManager, IdentityMembershipManagerEx
{
	class Membership
	{
		String _groupId;

		String _userId;

		public Membership(String userId, String group)
		{
			super();
			_userId = userId;
			_groupId = group;
		}
	}

	private Map<String, Group> _groups = new HashMap<String, Group>();

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

	protected Group getGroupById(String groupId)
	{
		return createGroup(groupId, null);
	}

	public Collection<Group> getGroups()
	{
		return _groups.values();
	}

	@Override
	public void removeAll()
	{
		_groups.clear();
		_memberships.clear();
	}

	public void saveMembership(String userId, String groupId)
	{
		_memberships.add(new Membership(userId, groupId));
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
			saveMembership(gp[0], gp[1]);
		}
	}
}
