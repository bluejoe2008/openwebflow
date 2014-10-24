package org.openwebflow.tool;

import org.activiti.engine.ProcessEngine;

public interface ProcessEngineTool extends ProcessEngineQueryTool, ToolFactory
{
	ProcessEngine getProcessEngine();
}