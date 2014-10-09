package org.openwebflow.mvc.ext;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;

public class AbstractWebFlowEventContext
{
	Map<String, Object> _attributes = new HashMap<String, Object>();

	ProcessEngine _processEngine;

	public <T> T getAttribute(String key)
	{
		return (T) _attributes.get(key);
	}

	public ProcessEngine getProcessEngine()
	{
		return _processEngine;
	}

	public void setAttribute(String key, Object value)
	{
		_attributes.put(key, value);
	}

	public void setProcessEngine(ProcessEngine processEngine)
	{
		_processEngine = processEngine;
	}
}