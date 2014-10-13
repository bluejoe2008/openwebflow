package org.openwebflow.mvc.event;

import org.activiti.engine.repository.ProcessDefinition;

public interface StartProcessFormEvent extends EventContext
{
	ProcessDefinition getProcessDefinition();

	String getProcessDefinitionId();
}