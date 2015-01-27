package org.openwebflow.mgr.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwebflow.identity.IdentityMembershipManager;
import org.openwebflow.mgr.ext.IdentityMembershipManagerEx;
import org.openwebflow.mgr.mybatis.entity.SqlMembershipEntity;
import org.openwebflow.mgr.mybatis.mapper.SqlMembershipEntityMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class SqlMembershipManager extends SqlMapperBasedServiceBase<SqlMembershipEntityMapper> implements
		IdentityMembershipManager, IdentityMembershipManagerEx
{
	public List<String> findGroupIdsByUser(String userId)
	{
		Map<String, Object> names = new HashMap<String, Object>();
		for (SqlMembershipEntity ms : _mapper.findMembershipsByUserId(userId))
		{
			names.put(ms.getGroupId(), 0);
		}

		return new ArrayList<String>(names.keySet());
	}

	public List<String> findUserIdsByGroup(String groupId)
	{
		Map<String, Object> names = new HashMap<String, Object>();
		for (SqlMembershipEntity ms : _mapper.findMembershipsByGroupId(groupId))
		{
			names.put(ms.getUserId(), 0);
		}

		return new ArrayList<String>(names.keySet());
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeAll()
	{
		_mapper.deleteAll();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveMembership(String userId, String groupId)
	{
		SqlMembershipEntity mse = new SqlMembershipEntity();
		mse.setGroupId(groupId);
		mse.setUserId(userId);
		_mapper.saveMembership(mse);
	}
}
