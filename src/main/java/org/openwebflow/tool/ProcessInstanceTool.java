package org.openwebflow.tool;

import org.activiti.engine.runtime.ProcessInstance;

public interface ProcessInstanceTool
{
	ProcessInstance getProcessInstance();

	String getProcessInstanceId();
}