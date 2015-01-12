package org.openwebflow.parts.mybatis.service;

import java.sql.Date;

import org.openwebflow.assign.permission.ActivityPermissionEntity;
import org.openwebflow.assign.permission.ActivityPermissionManager;
import org.openwebflow.parts.ext.ActivityPermissionManagerEx;
import org.openwebflow.parts.mybatis.entity.SqlActivityPermissionEntity;
import org.openwebflow.parts.mybatis.mapper.SqlActivityPermissionEntityMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class SqlActivityPermissionManager extends SqlMapperBasedServiceBase<SqlActivityPermissionEntityMapper>
		implements ActivityPermissionManager, ActivityPermissionManagerEx
{
	public ActivityPermissionEntity load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove)
	{
		return _mapper.load(processDefinitionId, taskDefinitionKey);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeAll()
	{
		_mapper.deleteAll();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(String processDefId, String taskDefinitionKey, String assignee, String[] candidateGroupIds,
			String[] candidateUserIds) throws Exception
	{
		SqlActivityPermissionEntity ap = new SqlActivityPermissionEntity();
		ap.setProcessDefinitionId(processDefId);
		ap.setActivityKey(taskDefinitionKey);
		ap.setAssignee(assignee);
		ap.setGrantedGroupIds(candidateGroupIds);
		ap.setGrantedUserIds(candidateUserIds);
		ap.setOpTime(new Date(System.currentTimeMillis()));

		_mapper.save(ap);
	}
}