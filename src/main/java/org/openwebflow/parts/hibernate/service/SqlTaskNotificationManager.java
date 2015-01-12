package org.openwebflow.parts.hibernate.service;

import org.openwebflow.alarm.TaskNotificationManager;
import org.openwebflow.parts.ext.TaskNotificationManagerEx;
import org.openwebflow.parts.hibernate.dao.SqlNotificationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SqlTaskNotificationManager implements TaskNotificationManager, TaskNotificationManagerEx
{
	@Autowired
	SqlNotificationDao _dao;

	@Override
	public boolean isNotified(String taskId) throws Exception
	{
		return _dao.findByTaskId(taskId) != null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeAll() throws Exception
	{
		_dao.deleteAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void setNotified(String taskId) throws Exception
	{
		_dao.save(taskId);
	}

}