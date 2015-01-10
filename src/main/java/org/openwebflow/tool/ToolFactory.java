package org.openwebflow.tool;

/**
 * @deprecated
 * @author bluejoe2008@gmail.com
 *
 */
public interface ToolFactory
{

	ActivityTool createActivityTool(String processDefId, String activityId);

	ProcessDefinitionTool createProcessDefinitionTool(String processDefId);

	ProcessInstanceTool createProcessInstanceTool(String processInstanceId);

	TaskTool createTaskTool(String taskId);

}