package org.openwebflow.mgr.hibernate.dao;

import java.util.List;

import org.openwebflow.mgr.hibernate.entity.SqlMembershipEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SqlMembershipDao extends SqlDaoBase<SqlMembershipEntity>
{
	public void deleteAll() throws Exception
	{
		super.executeUpdate("DELETE from SqlMembershipEntity");
	}

	public List<SqlMembershipEntity> findByGroup(String groupId) throws Exception
	{
		return super.queryForObjects("from SqlMembershipEntity where GROUP_ID=?", groupId);
	}

	public List<SqlMembershipEntity> findByUser(String userId) throws Exception
	{
		return super.queryForObjects("from SqlMembershipEntity where USER_ID=?", userId);
	}

	public void saveMembership(String userId, String groupId) throws Exception
	{
		SqlMembershipEntity mse = new SqlMembershipEntity();
		mse.setGroupId(groupId);
		mse.setUserId(userId);
		super.saveObject(mse);
	}
}