package org.openwebflow.identity.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwebflow.identity.IdentityMembershipManager;
import org.openwebflow.identity.impl.AbstractMembershipStore;

public class SqlMembershipStore extends AbstractMembershipStore implements IdentityMembershipManager
{
	SqlMembershipMapper _mapper;

	public SqlMembershipMapper getMapper()
	{
		return _mapper;
	}

	public void setMapper(SqlMembershipMapper mapper)
	{
		_mapper = mapper;
	}

	@Override
	public List<String> findGroupIdsByUser(String userId)
	{
		Map<String, Object> names = new HashMap<String, Object>();
		for (SqlMembershipEntity ms : _mapper.findMembershipsByUserId(userId))
		{
			names.put(ms.getGroupId(), 0);
		}

		return new ArrayList<String>(names.keySet());
	}

	@Override
	public List<String> findUserIdsByGroup(String groupId)
	{
		Map<String, Object> names = new HashMap<String, Object>();
		for (SqlMembershipEntity ms : _mapper.findMembershipsByGroupId(groupId))
		{
			names.put(ms.getGroupId(), 0);
		}

		return new ArrayList<String>(names.keySet());
	}

	@Override
	public void saveMembership(String userId, String groupId)
	{
		SqlMembershipEntity mse = new SqlMembershipEntity();
		mse.setGroupId(groupId);
		mse.setUserId(userId);
		_mapper.saveMembership(mse);
	}

	@Override
	public void removeAll()
	{
		_mapper.deleteAll();
	}
}
