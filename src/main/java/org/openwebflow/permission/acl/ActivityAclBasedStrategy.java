package org.openwebflow.permission.acl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.openwebflow.permission.ActivityAcessControlEntry;
import org.openwebflow.permission.AccessControlStrategy;
import org.openwebflow.permission.AccessControlStrategyChain;

public class ActivityAclBasedStrategy implements AccessControlStrategy
{
	ActivityAccessControlList _accessControlList;

	@Override
	public void apply(AccessControlStrategyChain chain, TaskEntity task, ActivityExecution execution)
	{
		//设置assignment信息
		String processDefinitionId = task.getProcessDefinitionId();
		String taskDefinitionKey = task.getTaskDefinitionKey();

		ActivityAcessControlEntry entry = _accessControlList.load(processDefinitionId, taskDefinitionKey, true);

		//没有自定义授权规则
		if (entry == null)
		{
			chain.resume(task, execution);
			return;
		}

		//彻底忽略原有规则
		task.setAssignee(entry.getAssignee());
		task.addCandidateGroups(asList(entry.getGrantedGroupIds()));
		task.addCandidateUsers(asList(entry.getGrantedUserIds()));
	}

	private Collection<String> asList(String[] ids)
	{
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, ids);
		return list;
	}

	public ActivityAccessControlList getAccessControlList()
	{
		return _accessControlList;
	}

	public void setAccessControlList(ActivityAccessControlList accessControlList)
	{
		_accessControlList = accessControlList;
	}

}
