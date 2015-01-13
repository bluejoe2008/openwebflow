package org.openwebflow.parts.hibernate.dao;

import java.sql.Date;

import org.openwebflow.parts.hibernate.entity.SqlNotificationEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SqlNotificationDao extends SqlDaoBase<SqlNotificationEntity>
{
	public void deleteAll() throws Exception
	{
		super.executeUpdate("DELETE from SqlNotificationEntity");
	}

	public SqlNotificationEntity findByTaskId(String taskId) throws Exception
	{
		return super.queryForObject("from SqlNotificationEntity where TASK_ID=?", taskId);
	}

	public void save(String taskId) throws Exception
	{
		SqlNotificationEntity sde = new SqlNotificationEntity();
		sde.setTaskId(taskId);
		sde.setOpTime(new Date(System.currentTimeMillis()));
		super.saveObject(sde);
	}
}