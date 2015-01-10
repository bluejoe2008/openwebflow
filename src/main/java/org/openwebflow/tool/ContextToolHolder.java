package org.openwebflow.tool;

/**
 * @deprecated
 * @author bluejoe2008@gmail.com
 *
 */
public interface ContextToolHolder
{
	ActivityTool getActivityTool();

	ProcessDefinitionTool getProcessDefinitionTool();

	ProcessEngineTool getProcessEngineTool();

	ProcessInstanceTool getProcessInstanceTool();

	TaskTool getTaskTool();
}
