package org.openwebflow.alarm;

public interface NotificationDetailsStore
{

	boolean isNotified(String taskId);

	void setNotified(String taskId);

}
