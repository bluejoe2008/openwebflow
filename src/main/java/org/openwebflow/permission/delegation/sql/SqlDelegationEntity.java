package org.openwebflow.permission.delegation.sql;

import java.sql.Date;

public class SqlDelegationEntity
{
	String _delegated;

	String _delegates;

	public String getDelegates()
	{
		return _delegates;
	}

	public void setDelegates(String delegates)
	{
		_delegates = delegates;
	}

	long _id;

	Date _opTime;

	public String getDelegated()
	{
		return _delegated;
	}

	public long getId()
	{
		return _id;
	}

	public Date getOpTime()
	{
		return _opTime;
	}

	public void setDelegated(String delegated)
	{
		_delegated = delegated;
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
