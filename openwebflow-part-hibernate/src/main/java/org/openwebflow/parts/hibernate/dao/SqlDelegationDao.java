package org.openwebflow.parts.hibernate.dao;

import java.sql.Date;
import java.util.List;

import org.openwebflow.assign.delegation.DelegationEntity;
import org.openwebflow.parts.hibernate.entity.SqlDelegationEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SqlDelegationDao extends SqlDaoBase<SqlDelegationEntity>
{
	public void deleteAll() throws Exception
	{
		super.executeUpdate("DELETE from SqlDelegationEntity");
	}

	public List<SqlDelegationEntity> findByDelegated(String delegated) throws Exception
	{
		return super.queryForObjects("from SqlDelegationEntity where DELEGATED=?", delegated);
	}

	public List<DelegationEntity> list() throws Exception
	{
		return super.queryForObjects();
	}

	public void saveDelegation(String delegated, String delegate) throws Exception
	{
		SqlDelegationEntity sde = new SqlDelegationEntity();
		sde.setDelegated(delegated);
		sde.setDelegate(delegate);
		sde.setOpTime(new Date(System.currentTimeMillis()));
		super.saveObject(sde);
	}
}