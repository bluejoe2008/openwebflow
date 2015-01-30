package org.openwebflow.assign.permission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.openwebflow.OwfException;
import org.openwebflow.assign.TaskAssignmentHandler;
import org.openwebflow.assign.TaskAssignmentHandlerChain;

public class ActivityPermissionAssignmentHandler implements TaskAssignmentHandler
{
	ActivityPermissionManager _activityPermissionManager;

	private Collection<String> asList(String[] ids)
	{
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, ids);
		return list;
	}

	public ActivityPermissionManager getActivityPermissionManager()
	{
		return _activityPermissionManager;
	}

	@Override
	public void handleAssignment(TaskAssignmentHandlerChain chain, TaskEntity task, ActivityExecution execution)
	{
		//设置assignment信息
		String processDefinitionId = task.getProcessDefinitionId();
		String taskDefinitionKey = task.getTaskDefinitionKey();

		ActivityPermissionEntity entity;
		try
		{
			entity = _activityPermissionManager.load(processDefinitionId, taskDefinitionKey, true);
		}
		catch (Exception e)
		{
			throw new OwfException(e);
		}

		//没有自定义授权规则
		if (entity == null)
		{
			chain.resume(task, execution);
			return;
		}

		//彻底忽略原有规则
		task.setAssignee(entity.getAssignee());
		task.addCandidateGroups(asList(entity.getGrantedGroupIds()));
		task.addCandidateUsers(asList(entity.getGrantedUserIds()));
	}

	public void setActivityPermissionManager(ActivityPermissionManager activityPermissionManager)
	{
		_activityPermissionManager = activityPermissionManager;
	}

}
