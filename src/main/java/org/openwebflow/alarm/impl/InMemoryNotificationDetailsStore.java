package org.openwebflow.alarm.impl;

import java.util.HashMap;
import java.util.Map;

import org.openwebflow.alarm.NotificationDetailsStore;

public class InMemoryNotificationDetailsStore implements NotificationDetailsStore
{
	Map<String, Boolean> _notificationMap = new HashMap<String, Boolean>();

	@Override
	public boolean isNotified(String taskId)
	{
		return _notificationMap.containsKey(taskId) && _notificationMap.get(taskId) == true;
	}

	@Override
	public void setNotified(String taskId)
	{
		_notificationMap.put(taskId, true);
	}

}
