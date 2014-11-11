package org.openwebflow.conf;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.openwebflow.permission.TaskAssignmentHandler;
import org.openwebflow.permission.impl.MyActivityBehaviorFactory;

public class ReplaceTaskAssignmentManager implements StartEngineEventListener
{
	List<TaskAssignmentHandler> _assignmentHandlers;

	public List<TaskAssignmentHandler> getAssignmentHandlers()
	{
		return _assignmentHandlers;
	}

	public void setAssignmentHandlers(List<TaskAssignmentHandler> assignmentHandlers)
	{
		_assignmentHandlers = assignmentHandlers;
	}

	@Override
	public void afterStartEngine(ProcessEngineConfigurationImpl conf, ProcessEngine processEngine) throws Exception
	{
	}

	@Override
	public void beforeStartEngine(ProcessEngineConfigurationImpl conf)
	{
		ActivityBehaviorFactory abf = conf.getActivityBehaviorFactory();
		if (abf == null)
		{
			abf = new DefaultActivityBehaviorFactory();
		}

		conf.setActivityBehaviorFactory(new MyActivityBehaviorFactory(abf, _assignmentHandlers));
	}
}
