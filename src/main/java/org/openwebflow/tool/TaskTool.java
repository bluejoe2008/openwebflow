package org.openwebflow.tool;

import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.openwebflow.util.MapFactory;

/**
 * @deprecated
 * @author bluejoe2008@gmail.com
 *
 */
public interface TaskTool
{
	void claim(String userId);

	void completeTask(Map<String, Object> variables);

	void completeTask(MapFactory filter);

	ProcessInstance getProcessInstance();

	Task getTask();

	String getTaskFormKey();

	String getTaskId();

}