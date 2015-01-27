package org.openwebflow.ctrl;

import java.io.IOException;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

public interface TaskFlowControlService
{

	/**
	 * 后加签
	 */
	ActivityImpl[] insertTasksAfter(String targetTaskDefinitionKey, String... assignees) throws Exception;

	/**
	 * 前加签
	 */
	ActivityImpl[] insertTasksBefore(String targetTaskDefinitionKey, String... assignees) throws Exception;

	/**
	 * 后退一步
	 */
	void moveBack() throws Exception;

	/**
	 * 后退至指定活动
	 */
	void moveBack(TaskEntity currentTaskEntity) throws Exception;

	/**
	 * 前进一步
	 */
	void moveForward() throws Exception;

	/**
	 * 前进至指定活动
	 */
	void moveForward(TaskEntity currentTaskEntity) throws Exception;

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 */
	void moveTo(String targetTaskDefinitionKey) throws Exception;

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 */
	void moveTo(String currentTaskId, String targetTaskDefinitionKey) throws Exception;

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 * 
	 * @param currentTaskEntity
	 *            当前任务节点
	 * @param targetTaskDefinitionKey
	 *            目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	void moveTo(TaskEntity currentTaskEntity, String targetTaskDefinitionKey) throws Exception;

	/**
	 * 分裂某节点为多实例节点
	 * 
	 * @param targetTaskDefinitionKey
	 * @param assignee
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	ActivityImpl split(String targetTaskDefinitionKey, boolean isSequential, String... assignees) throws Exception;

	/**
	 * 分裂某节点为多实例节点
	 */
	ActivityImpl split(String targetTaskDefinitionKey, String... assignee) throws Exception;

}