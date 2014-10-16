package org.openwebflow.tool;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

public interface ProcessEngineQueryTool
{
	public abstract long getActiveProcessesCount();

	Map<String, Object> getActiveProcessVariables(String processId);

	public abstract long getAssignedTasksCount(String userId);

	public abstract long getHistoricProcessesCount();

	Map<String, Object> getHistoricProcessVariables(String processId);

	public List<ProcessDefinition> getProcessDefs();

	public abstract long getProcessDefsCount();

	public abstract long getTaskQueueCount(String userId);

	public abstract List<HistoricProcessInstance> listActiveProcessInstances();

	public abstract List<Task> listAssignedTasks(String userId);

	public abstract List<HistoricActivityInstance> listHistoricActivities(String processId);

	public abstract List<HistoricProcessInstance> listHistoricProcesseInstances();

	public abstract List<Task> listTaskQueue(String userId);
}
