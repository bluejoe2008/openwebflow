package org.openwebflow.tool;

public interface ContextToolHolder
{
	ActivityTool getActivityTool();

	ProcessDefinitionTool getProcessDefinitionTool();

	ToolFactory getProcessEngineTool();

	ProcessInstanceTool getProcessInstanceTool();

	TaskTool getTaskTool();
}
