package org.openwebflow.assign.acl.sql;

import java.sql.Date;

import org.openwebflow.assign.acl.AbstractActivityAclStore;
import org.openwebflow.assign.acl.ActivityAclEntry;
import org.openwebflow.assign.acl.ActivityAclManager;

public class SqlActivityAclStore extends AbstractActivityAclStore implements ActivityAclManager
{
	SqlActivityAclStoreMapper _mapper;

	public SqlActivityAclStoreMapper getMapper()
	{
		return _mapper;
	}

	public void setMapper(SqlActivityAclStoreMapper mapper)
	{
		_mapper = mapper;
	}

	@Override
	public ActivityAclEntry load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove)
	{
		return _mapper.load(processDefinitionId, taskDefinitionKey);
	}

	@Override
	public void save(String processDefId, String taskDefinitionKey, String assignee, String[] candidateGroupIds,
			String[] candidateUserIds) throws Exception
	{
		SqlActivityAclEntry ap = new SqlActivityAclEntry();
		ap.setProcessDefinitionId(processDefId);
		ap.setActivityKey(taskDefinitionKey);
		ap.setAssignee(assignee);
		ap.setGrantedGroupIds(candidateGroupIds);
		ap.setGrantedUserIds(candidateUserIds);
		ap.setOpTime(new Date(System.currentTimeMillis()));

		_mapper.save(ap);
	}

	@Override
	public void removeAll()
	{
		_mapper.deleteAll();
	}
}
