package org.openwebflow.mgr.mybatis.entity;

import java.sql.Date;

import org.openwebflow.mgr.common.SimpleDelegationEntity;

public class SqlDelegationEntity extends SimpleDelegationEntity
{
	long _id;

	Date _opTime;

	public long getId()
	{
		return _id;
	}

	public Date getOpTime()
	{
		return _opTime;
	}

	public void setId(long id)
	{
		_id = id;
	}

	public void setOpTime(Date opTime)
	{
		_opTime = opTime;
	}
}
