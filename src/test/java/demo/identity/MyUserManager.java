package demo.identity;

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
public class MyUserManager implements CustomMembershipManager
{
	private Map<String, Group> _groups = new HashMap<String, Group>();

	private Map<String, List<Group>> _userGroupMembership = new HashMap<String, List<Group>>();

	public void createGroup(String groupId, String groupName)
	{
		GroupEntity group = new GroupEntity(groupId);
		group.setName(groupName);
		_groups.put(groupId, group);
	}

	public void createMembership(String userId, String groupId)
	{
		List<Group> groups = _userGroupMembership.get(userId);
		if (groups == null)
		{
			groups = new ArrayList<Group>();
			_userGroupMembership.put(userId, groups);
		}

		groups.add(_groups.get(groupId));
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
}
