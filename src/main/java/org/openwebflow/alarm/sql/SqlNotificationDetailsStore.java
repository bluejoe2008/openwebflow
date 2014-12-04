package org.openwebflow.alarm.sql;

import java.sql.Date;

import org.openwebflow.alarm.NotificationDetailsStore;
import org.openwebflow.alarm.impl.AbstractNotificationDetailsStore;

public class SqlNotificationDetailsStore extends AbstractNotificationDetailsStore implements NotificationDetailsStore
{
	SqlNotificationDetailsMapper _mapper;

	public SqlNotificationDetailsMapper getMapper()
	{
		return _mapper;
	}

	public void setMapper(SqlNotificationDetailsMapper mapper)
	{
		_mapper = mapper;
	}

	@Override
	public boolean isNotified(String taskId)
	{
		return !_mapper.findByTaskId(taskId).isEmpty();
	}

	@Override
	public void setNotified(String taskId)
	{
		SqlNotificationDetails sde = new SqlNotificationDetails();
		sde.setTaskId(taskId);
		sde.setOpTime(new Date(System.currentTimeMillis()));
		_mapper.saveNotificationDetails(sde);
	}

	@Override
	public void removeAll()
	{
		_mapper.deleteAll();
	}

}
