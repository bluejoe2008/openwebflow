package org.openwebflow.conf;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.openwebflow.ctrl.create.RuntimeActivityCreator;
import org.openwebflow.ctrl.persist.RuntimeActivityDefinition;
import org.openwebflow.ctrl.persist.RuntimeActivityDefinitionStore;
import org.openwebflow.util.ProcessDefinitionUtils;

public class LoadRuntimeActivityDefinitions implements StartEngineEventListener
{
	RuntimeActivityDefinitionStore _activityDefinitionStore;

	public RuntimeActivityDefinitionStore getActivityDefinitionStore()
	{
		return _activityDefinitionStore;
	}

	public void setActivityDefinitionStore(RuntimeActivityDefinitionStore activityDefinitionStore)
	{
		_activityDefinitionStore = activityDefinitionStore;
	}

	@Override
	public void afterStartEngine(ProcessEngineConfigurationImpl conf, ProcessEngine processEngine) throws Exception
	{
		for (RuntimeActivityDefinition entity : _activityDefinitionStore.list())
		{
			ProcessDefinitionEntity processDefinition = ProcessDefinitionUtils.getProcessDefinition(processEngine,
				entity.getProcessDefinitionId());
			if (processDefinition != null)
			{
				RuntimeActivityCreator activitiesCreator = (RuntimeActivityCreator) entity.getFactoryClass()
						.newInstance();

				//创建activity
				entity.deserializeProperties();
				activitiesCreator.createActivities(processEngine, processDefinition, entity);
			}
		}
	}

	@Override
	public void beforeStartEngine(ProcessEngineConfigurationImpl conf) throws Exception
	{
	}
}
