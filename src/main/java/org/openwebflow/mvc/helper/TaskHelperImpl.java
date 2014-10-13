package org.openwebflow.mvc.helper;

import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.openwebflow.util.MapFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public class TaskHelperImpl implements TaskHelper
{
	private ProcessEngine _processEngine;

	private String _taskId;

	@Override
	public void claim()
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		_processEngine.getTaskService().claim(_taskId, userId);
	}

	@Override
	public void completeTask(Map<String, Object> variables)
	{
		_processEngine.getTaskService().complete(_taskId, variables);
	}

	@Override
	public void completeTask(MapFactory mapFactory)
	{
		completeTask(mapFactory.getMap());
	}

	public ProcessEngine getProcessEngine()
	{
		return _processEngine;
	}

	@Override
	public ProcessInstance getProcessInstance()
	{
		return _processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(getTask().getProcessInstanceId()).singleResult();
	}

	@Override
	public Task getTask()
	{
		return _processEngine.getTaskService().createTaskQuery().taskId(_taskId).singleResult();
	}

	@Override
	public String getTaskFormKey()
	{
		return getTask().getFormKey();
	}

	public String getTaskId()
	{
		return _taskId;
	}

	public void setProcessEngine(ProcessEngine processEngine)
	{
		_processEngine = processEngine;
	}

	public void setTaskId(String taskId)
	{
		_taskId = taskId;
	}

}
