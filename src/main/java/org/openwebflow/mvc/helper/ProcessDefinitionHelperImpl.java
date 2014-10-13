package org.openwebflow.mvc.helper;

import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.security.core.context.SecurityContextHolder;

public class ProcessDefinitionHelperImpl implements ProcessDefinitionHelper
{
	private String _processDefId;

	private ProcessEngine _processEngine;

	@Override
	public ProcessDefinition getProcessDefinition()
	{
		return _processEngine.getRepositoryService().getProcessDefinition(_processDefId);
	}

	@Override
	public String getProcessDefinitionId()
	{
		return _processDefId;
	}

	public ProcessEngine getProcessEngine()
	{
		return _processEngine;
	}

	@Override
	public String getStartFormKey()
	{
		return _processEngine.getFormService().getStartFormKey(_processDefId);
	}

	public void setProcessDefinitionId(String processDefId)
	{
		_processDefId = processDefId;
	}

	public void setProcessEngine(ProcessEngine processEngine)
	{
		_processEngine = processEngine;
	}

	@Override
	public ProcessInstance startProcess(String businessKey, Map<String, Object> variables)
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		_processEngine.getIdentityService().setAuthenticatedUserId(userId);
		ProcessInstance instance = _processEngine.getRuntimeService().startProcessInstanceById(_processDefId,
			businessKey, variables);

		return instance;
	}

	@Override
	public ProcessDefinitionEntity getProcessDefinitionEntity()
	{
		return (ProcessDefinitionEntity) ((RepositoryServiceImpl) _processEngine.getRepositoryService())
				.getDeployedProcessDefinition(_processDefId);
	}
}
