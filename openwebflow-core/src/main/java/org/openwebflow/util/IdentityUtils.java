package org.openwebflow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.openwebflow.identity.IdentityMembershipManager;
import org.openwebflow.identity.UserDetailsEntity;
import org.openwebflow.identity.UserDetailsManager;

/**
 * 实现用户、成员关系等相关操作
 * 
 * @author bluejoe2008@gmail.com
 *
 */
public abstract class IdentityUtils
{
	public static List<Group> getGroupsFromIds(List<String> groupIds)
	{
		List<Group> groups = new ArrayList<Group>();
		for (String groupId : groupIds)
		{
			groups.add(new GroupEntity(groupId));
		}

		return groups;
	}

	public static List<String> getInvolvedUsers(TaskService taskService, Task task,
			IdentityMembershipManager membershipManager) throws Exception
	{
		Map<String, Object> userIds = new HashMap<String, Object>();
		String assignee = task.getAssignee();
		//如果直接指定了任务执行人，则忽略其他候选人
		if (assignee != null)
		{
			userIds.put(assignee, new Object());
		}
		else
		{
			for (IdentityLink link : taskService.getIdentityLinksForTask(task.getId()))
			{
				String userId = link.getUserId();
				if (userId != null)
				{
					userIds.put(userId, new Object());
				}

				String groupId = link.getGroupId();
				if (groupId != null)
				{
					for (String gUserId : membershipManager.findUserIdsByGroup(groupId))
					{
						userIds.put(gUserId, new Object());
					}
				}
			}
		}

		return new ArrayList<String>(userIds.keySet());
	}

	public static List<UserDetailsEntity> getUserDetailsFromIds(List<String> userIds,
			UserDetailsManager userDetailsManager) throws Exception
	{
		List<UserDetailsEntity> detailsList = new ArrayList<UserDetailsEntity>();
		for (String userId : userIds)
		{
			detailsList.add(userDetailsManager.findUserDetails(userId));
		}

		return detailsList;
	}
}
