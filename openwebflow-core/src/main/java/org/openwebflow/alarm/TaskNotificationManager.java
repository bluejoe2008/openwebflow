package org.openwebflow.alarm;

public interface TaskNotificationManager
{

	boolean isNotified(String taskId) throws Exception;

	void setNotified(String taskId) throws Exception;

}
