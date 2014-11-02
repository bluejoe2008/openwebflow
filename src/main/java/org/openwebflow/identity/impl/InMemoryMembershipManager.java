package org.openwebflow.identity.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.openwebflow.identity.CustomMembershipManager;

/**
 * 本类演示自定义用户管理
 * 
 * @author bluejoe2008@gmail.com
 * 
 */
public class InMemoryMembershipManager implements CustomMembershipManager
{
	private Map<String, Group> _groups = new HashMap<String, Group>();

	private Map<String, List<Group>> _userGroupMembership = new HashMap<String, List<Group>>();

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
		List<Group> groups = _userGroupMembership.get(userId);
		if (groups == null)
		{
			groups = new ArrayList<Group>();
			_userGroupMembership.put(userId, groups);
		}

		groups.add(getGroupById(groupId));
	}

	protected Group getGroupById(String groupId)
	{
		return createGroup(groupId, null);
	}

	@Override
	public List<Group> findGroupsByUser(String userId)
	{
		List<Group> groups = _userGroupMembership.get(userId);
		if (groups == null)
		{
			return new ArrayList<Group>();
		}

		return groups;
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
}
