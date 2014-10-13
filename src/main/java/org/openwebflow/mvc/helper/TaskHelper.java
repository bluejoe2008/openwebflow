package org.openwebflow.mvc.helper;

import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.openwebflow.util.MapFactory;

public interface TaskHelper
{
	void claim();

	void completeTask(Map<String, Object> variables);

	void completeTask(MapFactory filter);

	ProcessEngine getProcessEngine();

	ProcessInstance getProcessInstance();

	Task getTask();

	String getTaskFormKey();

	String getTaskId();

}