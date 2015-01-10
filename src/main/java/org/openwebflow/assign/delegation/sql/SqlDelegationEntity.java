package org.openwebflow.assign.delegation.sql;

import java.sql.Date;

import org.openwebflow.assign.delegation.DelegationDetailsImpl;

public class SqlDelegationEntity extends DelegationDetailsImpl
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
