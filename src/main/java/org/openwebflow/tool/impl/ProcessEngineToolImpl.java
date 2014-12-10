package org.openwebflow.tool.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.openwebflow.tool.ActivityTool;
import org.openwebflow.tool.ProcessDefinitionTool;
import org.openwebflow.tool.ProcessEngineTool;
import org.openwebflow.tool.ProcessInstanceTool;
import org.openwebflow.tool.TaskTool;
import org.openwebflow.util.ProcessDefinitionUtils;

public class ProcessEngineToolImpl implements ProcessEngineTool
{
	private ProcessEngine _processEngine;

	@Override
	public ActivityTool createActivityTool(String processDefId, String activityId)
	{
		return new ActivityToolImpl(this, ProcessDefinitionUtils.getActivity(_processEngine, processDefId, activityId));
	}

	@Override
	public ProcessDefinitionTool createProcessDefinitionTool(String processDefId)
	{
		return new ProcessDefinitionToolImpl(this, _processEngine.getRepositoryService().getProcessDefinition(
			processDefId));
	}

	@Override
	public ProcessInstanceTool createProcessInstanceTool(String processInstanceId)
	{
		return new ProcessInstanceToolImpl(this, _processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult());
	}

	@Override
	public TaskTool createTaskTool(String taskId)
	{
		return new TaskToolImpl(this, _processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult());
	}

	@Override
	public long getActiveProcessesCount()
	{
		return _processEngine.getHistoryService().createHistoricProcessInstanceQuery().unfinished().count();
	}

	@Override
	public long getActiveProcessesCount(String userId)
	{
		return _processEngine.getHistoryService().createHistoricProcessInstanceQuery().unfinished().startedBy(userId)
				.count();
	}

	@Override
	public Map<String, Object> getActiveProcessVariables(String processId)
	{
		return _processEngine.getRuntimeService().getVariables(processId);
	}

	@Override
	public long getAssignedTasksCount(String userId)
	{
		return _processEngine.getTaskService().createTaskQuery().taskAssignee(userId).count();
	}

	@Override
	public long getHistoricProcessesCount()
	{
		return _processEngine.getHistoryService().createHistoricProcessInstanceQuery().finished().count();
	}

	@Override
	public long getHistoricProcessesCount(String userId)
	{
		return _processEngine.getHistoryService().createHistoricProcessInstanceQuery().finished().startedBy(userId)
				.count();
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

	public List<ProcessDefinition> getProcessDefs()
	{
		return _processEngine.getRepositoryService().createProcessDefinitionQuery().list();
	}

	@Override
	public long getProcessDefsCount()
	{
		return _processEngine.getRepositoryService().createProcessDefinitionQuery().count();
	}

	@Override
	public ProcessEngine getProcessEngine()
	{
		return _processEngine;
	}

	@Override
	public long getTaskQueueCount(String userId)
	{
		return _processEngine.getTaskService().createTaskQuery().taskCandidateUser(userId).count();
	}

	@Override
	public List<HistoricProcessInstance> listActiveProcessInstances()
	{
		return getProcessEngine().getHistoryService().createHistoricProcessInstanceQuery().unfinished()
				.orderByProcessInstanceStartTime().desc().list();
	}

	@Override
	public List<HistoricProcessInstance> listActiveProcessInstances(String userId)
	{
		return getProcessEngine().getHistoryService().createHistoricProcessInstanceQuery().startedBy(userId)
				.unfinished().orderByProcessInstanceStartTime().desc().list();
	}

	@Override
	public List<Task> listAssignedTasks(String userId)
	{
		return getProcessEngine().getTaskService().createTaskQuery().taskAssignee(userId).orderByTaskCreateTime()
				.desc().list();
	}

	@Override
	public List<HistoricActivityInstance> listHistoricActivities(String processId)
	{
		return getProcessEngine().getHistoryService().createHistoricActivityInstanceQuery().executionId(processId)
				.finished().orderByHistoricActivityInstanceStartTime().asc().list();
	}

	@Override
	public List<HistoricProcessInstance> listHistoricProcesseInstances()
	{
		return getProcessEngine().getHistoryService().createHistoricProcessInstanceQuery().finished()
				.orderByProcessInstanceEndTime().desc().list();
	}

	@Override
	public List<HistoricProcessInstance> listHistoricProcesseInstances(String userId)
	{
		return getProcessEngine().getHistoryService().createHistoricProcessInstanceQuery().startedBy(userId).finished()
				.orderByProcessInstanceEndTime().desc().list();
	}

	@Override
	public List<Task> listTaskQueue(String userId)
	{
		return getProcessEngine().getTaskService().createTaskQuery().taskCandidateUser(userId).orderByTaskCreateTime()
				.desc().list();
	}

	public void setProcessEngine(ProcessEngine processEngine)
	{
		_processEngine = processEngine;
	}

}
