package org.openwebflow.ctrl;

import java.util.HashMap;
import java.util.Map;

public class ActivitiesCreationEntity
{
	String _factoryName;

	String _processDefinitionId;

	String _processInstanceId;

	Map<String, Object> _properties = new HashMap<String, Object>();

	String _propertiesText;

	public String[] getAssignees()
	{
		return getProperty("assignees");
	}

	public String getCloneActivityId()
	{
		return getProperty("cloneActivityId");
	}

	public String[] getCloneActivityIds()
	{
		return getProperty("cloneActivityIds");
	}

	public String getFactoryName()
	{
		return _factoryName;
	}

	public String getNextActivityId()
	{
		return getProperty("nextActivityId");
	}

	public String getProcessDefinitionId()
	{
		return _processDefinitionId;
	}

	public String getProcessInstanceId()
	{
		return _processInstanceId;
	}

	public String getPropertiesText()
	{
		//组配
		return _propertiesText;
	}

	public <T> T getProperty(String name)
	{
		return (T) _properties.get(name);
	}

	public String getPrototypeActivityId()
	{
		return getProperty("prototypeActivityId");
	}

	public boolean getSequential()
	{
		return getProperty("sequential");
	}

	public void setAssignees(String[] assignees)
	{
		setProperty("assignees", assignees);
	}

	public void setCloneActivityId(String cloneActivityId)
	{
		setProperty("cloneActivityId", cloneActivityId);
	}

	public void setCloneActivityIds(String[] cloneActivityIds)
	{
		setProperty("cloneActivityIds", cloneActivityIds);
	}

	public void setFactoryName(String factoryName)
	{
		_factoryName = factoryName;
	}

	public void setNextActivityId(String nextActivityId)
	{
		setProperty("nextActivityId", nextActivityId);
	}

	public void setProcessDefinitionId(String processDefinitionId)
	{
		_processDefinitionId = processDefinitionId;
	}

	public void setProcessInstanceId(String processInstanceId)
	{
		_processInstanceId = processInstanceId;
	}

	public void setPropertiesText(String propertiesText)
	{
		_propertiesText = propertiesText;
		//解析
	}

	private <T> void setProperty(String name, T value)
	{
		_properties.put(name, value);
	}

	public void setPrototypeActivityId(String prototypeActivityId)
	{
		setProperty("prototypeActivityId", prototypeActivityId);
	}

	public void setSequential(boolean sequential)
	{
		setProperty("sequential", sequential);
	}
}
