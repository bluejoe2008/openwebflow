package org.openwebflow.parts.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwebflow.ctrl.RuntimeActivityDefinitionEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleRuntimeActivityDefinitionEntity implements RuntimeActivityDefinitionEntity
{
	String _factoryName;

	String _processDefinitionId;

	String _processInstanceId;

	Map<String, Object> _properties = new HashMap<String, Object>();

	String _propertiesText;

	@Override
	public void deserializeProperties() throws IOException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		_properties = objectMapper.readValue(_propertiesText, Map.class);
	}

	@Override
	public List<String> getAssignees()
	{
		return getProperty("assignees");
	}

	@Override
	public String getCloneActivityId()
	{
		return getProperty("cloneActivityId");
	}

	@Override
	public List<String> getCloneActivityIds()
	{
		return getProperty("cloneActivityIds");
	}

	@Override
	public Class<?> getFactoryClass() throws ClassNotFoundException
	{
		return Class.forName(_factoryName);
	}

	@Override
	public String getFactoryName()
	{
		return _factoryName;
	}

	@Override
	public String getNextActivityId()
	{
		return getProperty("nextActivityId");
	}

	@Override
	public String getProcessDefinitionId()
	{
		return _processDefinitionId;
	}

	@Override
	public String getProcessInstanceId()
	{
		return _processInstanceId;
	}

	@Override
	public String getPropertiesText()
	{
		return _propertiesText;
	}

	@Override
	public <T> T getProperty(String name)
	{
		return (T) _properties.get(name);
	}

	@Override
	public String getPrototypeActivityId()
	{
		return getProperty("prototypeActivityId");
	}

	@Override
	public boolean getSequential()
	{
		return getProperty("sequential");
	}

	@Override
	public void serializeProperties() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		_propertiesText = objectMapper.writeValueAsString(_properties);
	}

	@Override
	public void setAssignees(List<String> assignees)
	{
		setProperty("assignees", assignees);
	}

	@Override
	public void setCloneActivityId(String cloneActivityId)
	{
		setProperty("cloneActivityId", cloneActivityId);
	}

	@Override
	public void setCloneActivityIds(List<String> cloneActivityIds)
	{
		setProperty("cloneActivityIds", cloneActivityIds);
	}

	@Override
	public void setFactoryName(String factoryName)
	{
		_factoryName = factoryName;
	}

	@Override
	public void setNextActivityId(String nextActivityId)
	{
		setProperty("nextActivityId", nextActivityId);
	}

	@Override
	public void setProcessDefinitionId(String processDefinitionId)
	{
		_processDefinitionId = processDefinitionId;
	}

	@Override
	public void setProcessInstanceId(String processInstanceId)
	{
		_processInstanceId = processInstanceId;
	}

	@Override
	public void setPropertiesText(String propertiesText)
	{
		_propertiesText = propertiesText;
	}

	private <T> void setProperty(String name, T value)
	{
		_properties.put(name, value);
	}

	@Override
	public void setPrototypeActivityId(String prototypeActivityId)
	{
		setProperty("prototypeActivityId", prototypeActivityId);
	}

	@Override
	public void setSequential(boolean sequential)
	{
		setProperty("sequential", sequential);
	}
}
