package org.openwebflow.mvc.support;

import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public interface TaskHelper
{
	void claim();

	void completeTask(Map<String, Object> variables);

	ProcessEngine getProcessEngine();

	ProcessInstance getProcessInstance();

	Task getTask();

	String getTaskFormKey();

	String getTaskId();

}