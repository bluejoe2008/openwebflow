package org.openwebflow.mvc.helper;

public interface WebFlowHelperHolder
{
	ProcessDefinitionHelper getProcessDefinitionHelper();

	ProcessEngineHelper getProcessEngineHelper();

	ProcessInstanceHelper getProcessInstanceHelper();

	TaskHelper getTaskHelper();
}
