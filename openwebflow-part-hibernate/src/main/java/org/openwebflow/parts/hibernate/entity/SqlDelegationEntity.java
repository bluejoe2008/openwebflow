package org.openwebflow.parts.hibernate.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.openwebflow.assign.delegation.DelegationEntity;

@Entity
@Table(name = "OWF_DELEGATION")
public class SqlDelegationEntity implements DelegationEntity
{
	@Column(name = "DELEGATE")
	String _delegate;

	@Column(name = "DELEGATED")
	String _delegated;

	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long _id;

	@Column(name = "OP_TIME")
	Date _opTime;

	public String getDelegate()
	{
		return _delegate;
	}

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

	public void setDelegate(String delegates)
	{
		_delegate = delegates;
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
