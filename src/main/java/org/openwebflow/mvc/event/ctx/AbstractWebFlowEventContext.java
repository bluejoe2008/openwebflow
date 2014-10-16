package org.openwebflow.mvc.event.ctx;

import java.util.HashMap;
import java.util.Map;

import org.openwebflow.tool.ProcessEngineTool;

public class AbstractWebFlowEventContext
{
	Map<String, Object> _attributes = new HashMap<String, Object>();

	ProcessEngineTool _processEngineEx;

	public <T> T getAttribute(String key)
	{
		return (T) _attributes.get(key);
	}

	public ProcessEngineTool getProcessEngineEx()
	{
		return _processEngineEx;
	}

	public void setAttribute(String key, Object value)
	{
		_attributes.put(key, value);
	}

	public void setProcessEngineEx(ProcessEngineTool processEngineEx)
	{
		_processEngineEx = processEngineEx;
	}
}