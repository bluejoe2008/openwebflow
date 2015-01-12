package org.openwebflow.parts.mybatis.service;

import java.sql.Date;

import org.openwebflow.alarm.TaskNotificationManager;
import org.openwebflow.parts.ext.TaskNotificationManagerEx;
import org.openwebflow.parts.mybatis.entity.SqlNotificationEntity;
import org.openwebflow.parts.mybatis.mapper.SqlNotificationEntityMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class SqlTaskNotificationManager extends SqlMapperBasedServiceBase<SqlNotificationEntityMapper> implements
		TaskNotificationManager, TaskNotificationManagerEx
{
	@Override
	public boolean isNotified(String taskId)
	{
		return !_mapper.findByTaskId(taskId).isEmpty();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeAll()
	{
		_mapper.deleteAll();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void setNotified(String taskId)
	{
		SqlNotificationEntity sde = new SqlNotificationEntity();
		sde.setTaskId(taskId);
		sde.setOpTime(new Date(System.currentTimeMillis()));
		_mapper.saveNotificationDetails(sde);
	}

}
