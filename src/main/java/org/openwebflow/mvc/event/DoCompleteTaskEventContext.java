package org.openwebflow.mvc.event;

import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public interface DoCompleteTaskEventContext extends EventContext
{
	public abstract ProcessInstance getProcessInstance();

	public abstract Map<String, Object> getProcessVariableMap();

	public abstract Task getTask();

	public abstract String getTaskId();
}
