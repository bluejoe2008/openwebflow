package org.openwebflow.mvc.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricVariableInstance;

public class ProcessEngineHelperImpl implements ProcessEngineHelper
{
	ProcessEngine _processEngine;

	public ProcessEngineHelperImpl(ProcessEngine processEngine)
	{
		_processEngine = processEngine;
	}

	@Override
	public Map<String, Object> getActiveProcessVariables(String processId)
	{
		return _processEngine.getRuntimeService().getVariables(processId);
	}

	@Override
	public Map<String, Object> getHistoricProcessVariables(String processId)
	{
		List<HistoricVariableInstance> list = _processEngine.getHistoryService().createHistoricVariableInstanceQuery()
				.processInstanceId(processId).list();
		Map<String, Object> vars = new HashMap<String, Object>();
		for (HistoricVariableInstance var : list)
		{
			vars.put(var.getVariableName(), var.getValue());
		}

		return vars;
	}

	public ProcessEngine getProcessEngine()
	{
		return _processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine)
	{
		_processEngine = processEngine;
	}
}
