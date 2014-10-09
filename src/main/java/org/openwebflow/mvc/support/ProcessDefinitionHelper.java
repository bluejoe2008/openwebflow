package org.openwebflow.mvc.support;

import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

public interface ProcessDefinitionHelper
{
	ProcessDefinition getProcessDefinition();

	String getProcessDefinitionId();

	ProcessEngine getProcessEngine();

	String getStartFormKey();

	ProcessInstance startProcess(String businessKey, Map<String, Object> variables);
}