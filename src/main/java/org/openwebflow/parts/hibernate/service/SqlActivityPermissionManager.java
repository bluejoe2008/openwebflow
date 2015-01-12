package org.openwebflow.parts.hibernate.service;

import org.openwebflow.assign.permission.ActivityPermissionEntity;
import org.openwebflow.assign.permission.ActivityPermissionManager;
import org.openwebflow.parts.ext.ActivityPermissionManagerEx;
import org.openwebflow.parts.hibernate.dao.SqlActivityPermissionEntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
public class SqlActivityPermissionManager implements ActivityPermissionManager, ActivityPermissionManagerEx
{
	@Autowired
	SqlActivityPermissionEntityDao _dao;

	public ActivityPermissionEntity load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove)
			throws Exception
	{
		return _dao.load(processDefinitionId, taskDefinitionKey, addOrRemove);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeAll() throws Exception
	{
		_dao.deleteAll();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(String processDefId, String taskDefinitionKey, String assignee, String[] candidateGroupIds,
			String[] candidateUserIds) throws Exception
	{
		_dao.save(processDefId, taskDefinitionKey, assignee,
			StringUtils.arrayToDelimitedString(candidateGroupIds, ";"),
			StringUtils.arrayToDelimitedString(candidateUserIds, ";"));
	}
}