package org.openwebflow.tool;

import org.activiti.engine.runtime.ProcessInstance;

/**
 * @deprecated
 * @author bluejoe2008@gmail.com
 *
 */
public interface ProcessInstanceTool
{
	ProcessInstance getProcessInstance();

	String getProcessInstanceId();
}