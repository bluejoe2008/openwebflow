package org.openwebflow.tool;

import org.activiti.engine.ProcessEngine;

public interface ProcessEngineTool extends ProcessEngineQueryTool
{
	ActivityTool createActivityTool(String processDefId, String activityId);

	ProcessDefinitionTool createProcessDefinitionTool(String processDefId);

	ProcessInstanceTool createProcessInstanceTool(String processInstanceId);

	TaskTool createTaskTool(String taskId);

	ProcessEngine getProcessEngine();
}