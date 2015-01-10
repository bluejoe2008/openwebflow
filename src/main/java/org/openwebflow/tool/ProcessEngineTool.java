package org.openwebflow.tool;

import org.activiti.engine.ProcessEngine;

/**
 * @deprecated
 * @author bluejoe2008@gmail.com
 *
 */
public interface ProcessEngineTool extends ProcessEngineQueryTool, ToolFactory
{
	ProcessEngine getProcessEngine();
}