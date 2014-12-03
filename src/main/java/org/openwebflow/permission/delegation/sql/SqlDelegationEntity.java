package org.openwebflow.permission.delegation.sql;

import java.sql.Date;

public class SqlDelegationEntity extends DelegationDetails
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
