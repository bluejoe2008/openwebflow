package org.openwebflow.mvc.event;

import org.activiti.engine.repository.ProcessDefinition;
import org.openwebflow.mvc.event.ctx.EventContext;

public interface StartProcessFormEvent extends EventContext
{
	ProcessDefinition getProcessDefinition();

	String getProcessDefinitionId();
}