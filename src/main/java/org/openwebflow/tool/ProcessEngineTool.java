package org.openwebflow.tool;

import org.activiti.engine.ProcessEngine;
import org.openwebflow.mvc.WebFlowConfiguration;
import org.openwebflow.permission.ActivityPermissionService;

public interface ProcessEngineTool extends ProcessEngineQueryTool
{
	ActivityTool createActivityTool(String processDefId, String activityId);

	ProcessDefinitionTool createProcessDefinitionTool(String processDefId);

	ProcessInstanceTool createProcessInstanceTool(String processInstanceId);

	TaskTool createTaskTool(String taskId);

	ActivityPermissionService getActivityPermissionService();

	ProcessEngine getProcessEngine();

	WebFlowConfiguration getWebFlowConfiguration();
}