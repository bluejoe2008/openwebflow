package org.openwebflow.mvc.support;

public interface WebFlowHelperHolder
{
	ProcessDefinitionHelper getProcessDefinitionHelper();

	ProcessInstanceHelper getProcessInstanceHelper();

	TaskHelper getTaskHelper();
}
