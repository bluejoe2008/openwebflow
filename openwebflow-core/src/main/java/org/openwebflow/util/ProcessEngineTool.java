package org.openwebflow.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessEngineTool
{
	@Autowired
	private ProcessEngine _processEngine;

	public Model createNewModel(String name, String description) throws IOException
	{
		return ModelUtils.createNewModel(_processEngine.getRepositoryService(), name, description);
	}

	public Deployment deployModel(String modelId) throws IOException
	{
		return ModelUtils.deployModel(_processEngine.getRepositoryService(), modelId);
	}

	public ActivityImpl getActivity(String processDefId, String activityId)
	{
		return ProcessDefinitionUtils.getActivity(_processEngine, processDefId, activityId);
	}

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

	public ProcessDefinitionEntity getProcessDefinition(ProcessEngine processEngine, String processDefId)
	{
		return ProcessDefinitionUtils.getProcessDefinition(_processEngine, processDefId);
	}

	public ProcessEngine getProcessEngine()
	{
		return _processEngine;
	}

	public void grantPermission(ActivityImpl activity, String assigneeExpression, String candidateGroupIdExpressions,
			String candidateUserIdExpressions) throws Exception
	{
		ProcessDefinitionUtils.grantPermission(activity, assigneeExpression, candidateGroupIdExpressions,
			candidateUserIdExpressions);
	}

	public void grantPermission(String processDefId, String activityId, String assigneeExpression,
			String candidateGroupIdExpressions, String candidateUserIdExpressions) throws Exception
	{
		ProcessDefinitionUtils.grantPermission(
			ProcessDefinitionUtils.getActivity(_processEngine, processDefId, activityId), assigneeExpression,
			candidateGroupIdExpressions, candidateUserIdExpressions);
	}

	public void setProcessEngine(ProcessEngine processEngine)
	{
		_processEngine = processEngine;
	}

}
