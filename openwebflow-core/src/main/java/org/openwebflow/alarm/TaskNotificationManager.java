package org.openwebflow.alarm;

public interface TaskNotificationManager
{
	/**
	 * 判断指定任务是否通知过
	 */
	boolean isNotified(String taskId) throws Exception;

	/**
	 * 设置指定任务的通知状态
	 */
	void setNotified(String taskId) throws Exception;

}
