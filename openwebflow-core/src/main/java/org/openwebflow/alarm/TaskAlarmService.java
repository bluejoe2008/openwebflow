package org.openwebflow.alarm;

import org.activiti.engine.ProcessEngine;

public interface TaskAlarmService
{
	void start(ProcessEngine processEngine) throws Exception;
}