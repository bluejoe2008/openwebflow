package org.openwebflow.tool.impl;

import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.openwebflow.tool.ProcessEngineTool;
import org.openwebflow.tool.TaskTool;
import org.openwebflow.util.MapFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public class TaskToolImpl extends AbstractTool implements TaskTool
{
	private Task _task;

	public TaskToolImpl(ProcessEngineTool processEngineTool, Task task)
	{
		super(processEngineTool);
		_task = task;
	}

	@Override
	public void claim()
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		getProcessEngine().getTaskService().claim(_task.getId(), userId);
	}

	@Override
	public void completeTask(Map<String, Object> variables)
	{
		getProcessEngine().getTaskService().complete(_task.getId(), variables);
	}

	@Override
	public void completeTask(MapFactory mapFactory)
	{
		completeTask(mapFactory.getMap());
	}

	@Override
	public ProcessInstance getProcessInstance()
	{
		return getProcessEngine().getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(getTask().getProcessInstanceId()).singleResult();
	}

	@Override
	public Task getTask()
	{
		return _task;
	}

	@Override
	public String getTaskFormKey()
	{
		return getTask().getFormKey();
	}

	public String getTaskId()
	{
		return _task.getId();
	}
}
