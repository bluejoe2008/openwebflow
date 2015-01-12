package org.openwebflow.parts.hibernate.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OWF_NOTIFICATION")
public class SqlNotificationEntity
{
	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int _id;

	@Column(name = "OPTIME")
	Date _opTime;

	@Column(name = "TASKID")
	String _taskId;

	public int getId()
	{
		return _id;
	}

	public Date getOpTime()
	{
		return _opTime;
	}

	public String getTaskId()
	{
		return _taskId;
	}

	public void setId(int id)
	{
		_id = id;
	}

	public void setOpTime(Date opTime)
	{
		_opTime = opTime;
	}

	public void setTaskId(String taskId)
	{
		_taskId = taskId;
	}
}
