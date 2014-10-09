package org.openwebflow.mvc.support;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;

public interface ProcessInstanceHelper
{
	ProcessEngine getProcessEngine();

	ProcessInstance getProcessInstance();

	String getProcessInstanceId();
}