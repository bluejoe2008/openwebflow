package org.openwebflow.alarm.sql;

import java.sql.Date;

public class SqlNotificationDetails
{
	String _taskId;

	int _id;

	public int getId()
	{
		return _id;
	}

	public void setId(int id)
	{
		_id = id;
	}

	public String getTaskId()
	{
		return _taskId;
	}

	public void setTaskId(String taskId)
	{
		_taskId = taskId;
	}

	Date _opTime;

	public Date getOpTime()
	{
		return _opTime;
	}

	public void setOpTime(Date opTime)
	{
		_opTime = opTime;
	}
}
