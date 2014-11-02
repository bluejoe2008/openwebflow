package org.openwebflow.conf;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.openwebflow.permission.AccessControlStrategy;
import org.openwebflow.permission.impl.MyActivityBehaviorFactory;

public class ReplaceActivityAccessController implements StartEngineEventListener
{
	List<AccessControlStrategy> _accessControlStrategies;

	public List<AccessControlStrategy> getAccessControlStrategies()
	{
		return _accessControlStrategies;
	}

	public void setAccessControlStrategies(List<AccessControlStrategy> accessControlStrategies)
	{
		_accessControlStrategies = accessControlStrategies;
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

		conf.setActivityBehaviorFactory(new MyActivityBehaviorFactory(abf, _accessControlStrategies));
	}
}
