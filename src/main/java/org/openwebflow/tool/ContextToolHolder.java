package org.openwebflow.tool;

public interface ContextToolHolder
{
	ActivityTool getActivityTool();

	ProcessDefinitionTool getProcessDefinitionTool();

	ProcessEngineTool getProcessEngineTool();

	ProcessInstanceTool getProcessInstanceTool();

	TaskTool getTaskTool();
}
