package org.openwebflow.mvc.helper;

import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

public interface ProcessDefinitionHelper
{
	ProcessDefinition getProcessDefinition();

	ProcessDefinitionEntity getProcessDefinitionEntity();

	String getProcessDefinitionId();

	ProcessEngine getProcessEngine();

	String getStartFormKey();

	ProcessInstance startProcess(String businessKey, Map<String, Object> variables);
}