package org.openwebflow.permission.acl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.openwebflow.permission.TaskAssignmentHandler;
import org.openwebflow.permission.TaskAssignmentHandlerChain;

public class TaskActivityAcl implements TaskAssignmentHandler
{
	ActivityAclManager _assignmentEntryManager;

	public ActivityAclManager getAssignmentEntryManager()
	{
		return _assignmentEntryManager;
	}

	public void setAssignmentEntryManager(ActivityAclManager assignmentEntryManager)
	{
		_assignmentEntryManager = assignmentEntryManager;
	}

	@Override
	public void handleAssignment(TaskAssignmentHandlerChain chain, TaskEntity task, ActivityExecution execution)
	{
		//设置assignment信息
		String processDefinitionId = task.getProcessDefinitionId();
		String taskDefinitionKey = task.getTaskDefinitionKey();

		ActivityAclEntry entry = _assignmentEntryManager.load(processDefinitionId, taskDefinitionKey, true);

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

}
