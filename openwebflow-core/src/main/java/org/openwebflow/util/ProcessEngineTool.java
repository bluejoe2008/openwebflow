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

	/**
	 * 创建一个空白的Model对象
	 */
	public Model createNewModel(String name, String description) throws IOException
	{
		return ModelUtils.createNewModel(_processEngine.getRepositoryService(), name, description);
	}

	/**
	 * 部署一个已注册的model
	 */
	public Deployment deployModel(String modelId) throws IOException
	{
		return ModelUtils.deployModel(_processEngine.getRepositoryService(), modelId);
	}

	/**
	 * 获取指定名字的活动
	 */
	public ActivityImpl getActivity(String processDefId, String activityId)
	{
		return ProcessDefinitionUtils.getActivity(_processEngine, processDefId, activityId);
	}

	/**
	 * 获取指定历史流程的变量列表
	 */
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

	/**
	 * 获取指定ID的流程定义
	 */
	public ProcessDefinitionEntity getProcessDefinition(String processDefId)
	{
		return ProcessDefinitionUtils.getProcessDefinition(_processEngine, processDefId);
	}

	public ProcessEngine getProcessEngine()
	{
		return _processEngine;
	}

	/**
	 * 设置指定活动的用户权限，包括钦定用户、候选用户、候选组
	 */
	public void grantPermission(ActivityImpl activity, String assigneeExpression, String candidateGroupIdExpressions,
			String candidateUserIdExpressions) throws Exception
	{
		ProcessDefinitionUtils.grantPermission(activity, assigneeExpression, candidateGroupIdExpressions,
			candidateUserIdExpressions);
	}

	/**
	 * 设置指定活动的用户权限，包括钦定用户、候选用户、候选组
	 */
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
