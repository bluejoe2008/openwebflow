package org.openwebflow.mgr.ext;

public interface ActivityPermissionManagerEx
{
	/**
	 * 删除所有权限定义信息
	 */
	public abstract void removeAll() throws Exception;

	/**
	 * 保存一条权限定义信息
	 */
	public abstract void save(String processDefId, String taskDefinitionKey, String assignee,
			String[] candidateGroupIds, String[] candidateUserIds) throws Exception;
}