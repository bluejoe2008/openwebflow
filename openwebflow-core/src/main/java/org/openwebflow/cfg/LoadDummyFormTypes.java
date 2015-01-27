package org.openwebflow.cfg;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.form.AbstractFormType;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;

public class LoadDummyFormTypes implements StartEngineEventListener
{
	List<String> _typeNames;

	@Override
	public void afterStartEngine(ProcessEngineConfigurationImpl conf, ProcessEngine processEngine)
	{
	}

	@Override
	public void beforeStartEngine(ProcessEngineConfigurationImpl conf)
	{
		//避免User类型报错
		List<AbstractFormType> types = conf.getCustomFormTypes();
		if (types == null)
		{
			types = new ArrayList<AbstractFormType>();
			conf.setCustomFormTypes(types);
		}

		for (String typeName : _typeNames)
		{
			types.add(new DummyFormType(typeName));
		}
	}

	public List<String> getTypeNames()
	{
		return _typeNames;
	}

	public void setTypeNames(List<String> typeNames)
	{
		_typeNames = typeNames;
	}

}
