package org.openwebflow.tool.impl;

import org.activiti.engine.ProcessEngine;
import org.openwebflow.tool.ProcessEngineTool;

public abstract class AbstractTool
{
	protected ProcessEngineTool _processEngineTool;

	public AbstractTool(ProcessEngineTool processEngineTool)
	{
		super();
		_processEngineTool = processEngineTool;
	}

	public ProcessEngine getProcessEngine()
	{
		return _processEngineTool.getProcessEngine();
	}

	public ProcessEngineTool getProcessEngineTool()
	{
		return _processEngineTool;
	}

	public void setProcessEngineTool(ProcessEngineTool processEngineTool)
	{
		_processEngineTool = processEngineTool;
	}
}