package org.openwebflow.cfg;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.openwebflow.ctrl.RuntimeActivityDefinitionEntity;
import org.openwebflow.ctrl.RuntimeActivityDefinitionManager;
import org.openwebflow.ctrl.creator.RuntimeActivityCreator;
import org.openwebflow.util.ProcessDefinitionUtils;

public class LoadRuntimeActivityDefinitions implements StartEngineEventListener
{
	RuntimeActivityDefinitionManager _activityDefinitionManager;

	@Override
	public void afterStartEngine(ProcessEngineConfigurationImpl conf, ProcessEngine processEngine) throws Exception
	{
		for (RuntimeActivityDefinitionEntity entity : _activityDefinitionManager.list())
		{
			ProcessDefinitionEntity processDefinition = ProcessDefinitionUtils.getProcessDefinition(processEngine,
				entity.getProcessDefinitionId());
			if (processDefinition != null)
			{
				RuntimeActivityCreator activitiesCreator = (RuntimeActivityCreator) Class.forName(
					entity.getFactoryName()).newInstance();

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

	public RuntimeActivityDefinitionManager getActivityDefinitionManager()
	{
		return _activityDefinitionManager;
	}

	public void setActivityDefinitionManager(RuntimeActivityDefinitionManager activityDefinitionManager)
	{
		_activityDefinitionManager = activityDefinitionManager;
	}
}
