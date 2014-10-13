package org.openwebflow.mvc.event;

import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

public interface DoStartProcessEventContext extends EventContext
{
	String getBussinessKey();

	ProcessDefinition getProcessDefinition();

	String getProcessDefinitionId();

	ProcessInstance getProcessInstance();

	public abstract Map<String, Object> getProcessVariableMap();

	void setBussinessKey(String bussinessKey);
}
