package org.openwebflow.assign.permission;

public interface ActivityPermissionManager
{
	/**
	 * 获取指定活动的权限定义信息 
	 */
	ActivityPermissionEntity load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove)
			throws Exception;
}