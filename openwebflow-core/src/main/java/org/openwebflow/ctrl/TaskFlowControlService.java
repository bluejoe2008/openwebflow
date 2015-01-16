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

	void moveBack() throws Exception;

	void moveBack(TaskEntity currentTaskEntity) throws Exception;

	void moveForward() throws Exception;

	void moveForward(TaskEntity currentTaskEntity) throws Exception;

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 * 
	 * @param targetTaskDefinitionKey
	 * @throws Exception
	 */
	void moveTo(String targetTaskDefinitionKey) throws Exception;

	void moveTo(String currentTaskId, String targetTaskDefinitionKey) throws Exception;

	/**
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

	ActivityImpl split(String targetTaskDefinitionKey, String... assignee) throws Exception;

}