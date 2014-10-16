package org.openwebflow.mvc.event.ctx;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.openwebflow.mvc.event.StartProcessFormEvent;

public class ProcessEventContextImpl extends AbstractWebFlowEventContext implements StartProcessFormEvent,
		DoStartProcessEventContext
{
	String _bussinessKey;

	ProcessDefinition _processDefinition;

	String _processDefinitionId;

	ProcessInstance _processInstance;

	Map<String, Object> _processVariables = new HashMap<String, Object>();

	public String getBussinessKey()
	{
		return _bussinessKey;
	}

	public ProcessDefinition getProcessDefinition()
	{
		return _processDefinition;
	}

	public String getProcessDefinitionId()
	{
		return _processDefinitionId;
	}

	public ProcessInstance getProcessInstance()
	{
		return _processInstance;
	}

	@Override
	public Map<String, Object> getProcessVariableMap()
	{
		return _processVariables;
	}

	public void setBussinessKey(String bussinessKey)
	{
		_bussinessKey = bussinessKey;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition)
	{
		_processDefinition = processDefinition;
	}

	public void setProcessDefinitionId(String processDefinitionId)
	{
		_processDefinitionId = processDefinitionId;
	}

	public void setProcessInstance(ProcessInstance processInstance)
	{
		_processInstance = processInstance;
	}
}
