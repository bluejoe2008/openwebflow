package org.openwebflow.parts.hibernate.dao;

import java.sql.Date;

import org.openwebflow.parts.hibernate.entity.SqlActivityPermissionEntity;
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
		return super.queryForObject("from SqlActivityPermissionEntity where PROCESS_DEF_ID=? and ACTIVITY_KEY=?",
			processDefinitionId, taskDefinitionKey);
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