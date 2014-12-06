package org.openwebflow.conf;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.openwebflow.alarm.TaskAlarmService;

public class StartTaskAlarmService implements StartEngineEventListener
{
	TaskAlarmService _taskAlarmService;

	boolean _runOnStartup = true;

	public boolean isRunOnStartup()
	{
		return _runOnStartup;
	}

	public void setRunOnStartup(boolean runOnStartup)
	{
		_runOnStartup = runOnStartup;
	}

	public TaskAlarmService getTaskAlarmService()
	{
		return _taskAlarmService;
	}

	public void setTaskAlarmService(TaskAlarmService taskAlarmService)
	{
		_taskAlarmService = taskAlarmService;
	}

	@Override
	public void afterStartEngine(ProcessEngineConfigurationImpl conf, ProcessEngine processEngine) throws Exception
	{
		if (_runOnStartup)
		{
			_taskAlarmService.start(processEngine);
		}
	}

	@Override
	public void beforeStartEngine(ProcessEngineConfigurationImpl conf) throws Exception
	{
	}
}
