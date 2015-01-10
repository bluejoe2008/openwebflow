package org.openwebflow.tool.impl;

import java.util.Map;

import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.openwebflow.tool.ProcessDefinitionTool;
import org.openwebflow.tool.ProcessEngineTool;

public class ProcessDefinitionToolImpl extends AbstractTool implements ProcessDefinitionTool
{
	private ProcessDefinition _processDefinition;

	public ProcessDefinitionToolImpl(ProcessEngineTool processEngineTool, ProcessDefinition processDefinition)
	{
		super(processEngineTool);
		_processDefinition = processDefinition;
	}

	@Override
	public ProcessDefinition getProcessDefinition()
	{
		return _processDefinition;
	}

	@Override
	public ProcessDefinitionEntity getProcessDefinitionEntity()
	{
		return (ProcessDefinitionEntity) ((RepositoryServiceImpl) getProcessEngine().getRepositoryService())
				.getDeployedProcessDefinition(getProcessDefinitionId());
	}

	@Override
	public String getProcessDefinitionId()
	{
		return _processDefinition.getId();
	}

	@Override
	public String getStartFormKey()
	{
		return getProcessEngine().getFormService().getStartFormKey(getProcessDefinitionId());
	}

	@Override
	public ProcessInstance startProcess(String businessKey, Map<String, Object> variables)
	{
		ProcessInstance instance = getProcessEngine().getRuntimeService().startProcessInstanceById(
			getProcessDefinitionId(), businessKey, variables);

		return instance;
	}
}
