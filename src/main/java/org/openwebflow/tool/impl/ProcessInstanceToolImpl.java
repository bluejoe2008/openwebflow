package org.openwebflow.tool.impl;

import org.activiti.engine.runtime.ProcessInstance;
import org.openwebflow.tool.ProcessEngineTool;
import org.openwebflow.tool.ProcessInstanceTool;

public class ProcessInstanceToolImpl extends AbstractTool implements ProcessInstanceTool
{
	private ProcessInstance _processInstance;

	public ProcessInstanceToolImpl(ProcessEngineTool processEngineTool, ProcessInstance processInstance)
	{
		super(processEngineTool);
		_processInstance = processInstance;
	}

	@Override
	public ProcessInstance getProcessInstance()
	{
		return _processInstance;
	}

	public String getProcessInstanceId()
	{
		return _processInstance.getId();
	}
}
