package org.openwebflow.ctrl;

import org.activiti.engine.ProcessEngine;
import org.openwebflow.ctrl.persist.RuntimeActivityDefinitionStore;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskFlowControlServiceFactory
{
	@Autowired
	RuntimeActivityDefinitionStore _activitiesCreationStore;

	@Autowired
	ProcessEngine _processEngine;

	public TaskFlowControlService create(String processId)
	{
		return new TaskFlowControlService(_activitiesCreationStore, _processEngine, processId);
	}
}
