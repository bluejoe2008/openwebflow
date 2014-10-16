package org.openwebflow.tool;

import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.openwebflow.util.MapFactory;

public interface TaskTool
{
	void claim();

	void completeTask(Map<String, Object> variables);

	void completeTask(MapFactory filter);

	ProcessInstance getProcessInstance();

	Task getTask();

	String getTaskFormKey();

	String getTaskId();

}