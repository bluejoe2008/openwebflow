package org.openwebflow.tool;

public interface ToolFactory
{

	ActivityTool createActivityTool(String processDefId, String activityId);

	ProcessDefinitionTool createProcessDefinitionTool(String processDefId);

	ProcessInstanceTool createProcessInstanceTool(String processInstanceId);

	TaskTool createTaskTool(String taskId);

}