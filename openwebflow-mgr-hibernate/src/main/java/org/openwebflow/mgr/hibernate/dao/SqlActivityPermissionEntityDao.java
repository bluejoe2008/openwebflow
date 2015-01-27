package org.openwebflow.mgr.hibernate.dao;

import java.sql.Date;

import org.openwebflow.mgr.hibernate.entity.SqlActivityPermissionEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SqlActivityPermissionEntityDao extends SqlDaoBase<SqlActivityPermissionEntity>
{
	public void deleteAll() throws Exception
	{
		super.executeUpdate("DELETE from SqlActivityPermissionEntity");
	}

	public SqlActivityPermissionEntity load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove)
			throws Exception
	{
		SqlActivityPermissionEntity entity = super.queryForObject(
			"from SqlActivityPermissionEntity where PROCESS_DEFINITION_ID=? and ACTIVITY_KEY=?", processDefinitionId,
			taskDefinitionKey);

		if (entity != null)
		{
			entity.deserializeProperties();
		}

		return entity;
	}

	public void save(String processDefId, String taskDefinitionKey, String assignee, String candidateGroupIds,
			String candidateUserIds) throws Exception
	{
		SqlActivityPermissionEntity ap = new SqlActivityPermissionEntity();
		ap.setProcessDefinitionId(processDefId);
		ap.setActivityKey(taskDefinitionKey);
		ap.setAssignee(assignee);
		ap.setGrantedGroupString(candidateGroupIds);
		ap.setGrantedUserString(candidateUserIds);
		ap.setOpTime(new Date(System.currentTimeMillis()));

		super.saveObject(ap);
	}
}