package org.openwebflow.cfg;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.openwebflow.assign.TaskAssignmentHandler;
import org.openwebflow.assign.impl.MyActivityBehaviorFactory;

public class ReplaceTaskAssignmentHandler implements StartEngineEventListener
{
	List<TaskAssignmentHandler> _handlers;

	@Override
	public void afterStartEngine(ProcessEngineConfigurationImpl conf, ProcessEngine processEngine) throws Exception
	{
	}

	@Override
	public void beforeStartEngine(ProcessEngineConfigurationImpl processEngineConfiguration)
	{
		ActivityBehaviorFactory activityBehaviorFactory = processEngineConfiguration.getActivityBehaviorFactory();
		if (activityBehaviorFactory == null)
		{
			activityBehaviorFactory = new DefaultActivityBehaviorFactory();
		}

		processEngineConfiguration.setActivityBehaviorFactory(new MyActivityBehaviorFactory(activityBehaviorFactory,
				_handlers));
	}

	public List<TaskAssignmentHandler> getHandlers()
	{
		return _handlers;
	}

	public void setHandlers(List<TaskAssignmentHandler> handlers)
	{
		_handlers = handlers;
	}
}
