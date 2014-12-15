package org.openwebflow.ctrl.persist;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RuntimeActivityDefinition
{
	String _factoryName;

	String _processDefinitionId;

	String _processInstanceId;

	Map<String, Object> _properties = new HashMap<String, Object>();

	String _propertiesText;

	public void deserializeProperties() throws IOException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		_properties = objectMapper.readValue(_propertiesText, Map.class);
	}

	public List<String> getAssignees()
	{
		return getProperty("assignees");
	}

	public String getCloneActivityId()
	{
		return getProperty("cloneActivityId");
	}

	public List<String> getCloneActivityIds()
	{
		return getProperty("cloneActivityIds");
	}

	public Class<?> getFactoryClass() throws ClassNotFoundException
	{
		return Class.forName(_factoryName);
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

	public void serializeProperties() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		_propertiesText = objectMapper.writeValueAsString(_properties);
	}

	public void setAssignees(List<String> assignees)
	{
		setProperty("assignees", assignees);
	}

	public void setCloneActivityId(String cloneActivityId)
	{
		setProperty("cloneActivityId", cloneActivityId);
	}

	public void setCloneActivityIds(List<String> cloneActivityIds)
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
