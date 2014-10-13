package org.openwebflow.mvc.event;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class TaskEventContextImpl extends AbstractWebFlowEventContext implements CompleteTaskFormEventContext,
		DoCompleteTaskEventContext
{
	ProcessInstance _processInstance;

	Map<String, Object> _processVariables = new HashMap<String, Object>();

	Task _task;

	String _taskId;

	public ProcessInstance getProcessInstance()
	{
		return _processInstance;
	}

	@Override
	public Map<String, Object> getProcessVariableMap()
	{
		return _processVariables;
	}

	public Task getTask()
	{
		return _task;
	}

	public String getTaskId()
	{
		return _taskId;
	}

	public void setProcessInstance(ProcessInstance processInstance)
	{
		_processInstance = processInstance;
	}

	public void setTask(Task task)
	{
		_task = task;
	}

	public void setTaskId(String taskId)
	{
		_taskId = taskId;
	}
}
