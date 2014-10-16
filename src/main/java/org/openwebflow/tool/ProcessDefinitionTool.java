package org.openwebflow.tool;

import java.util.Map;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

public interface ProcessDefinitionTool
{
	ProcessDefinition getProcessDefinition();

	ProcessDefinitionEntity getProcessDefinitionEntity();

	String getProcessDefinitionId();

	String getStartFormKey();

	ProcessInstance startProcess(String businessKey, Map<String, Object> variables);
}