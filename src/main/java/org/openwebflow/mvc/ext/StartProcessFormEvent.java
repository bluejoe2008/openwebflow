package org.openwebflow.mvc.ext;

import org.activiti.engine.repository.ProcessDefinition;

public interface StartProcessFormEvent extends EventContext
{
	ProcessDefinition getProcessDefinition();

	String getProcessDefinitionId();
}