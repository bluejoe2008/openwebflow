package org.openwebflow.mvc.event.ctx;

import org.openwebflow.tool.ProcessEngineTool;

public interface EventContext
{
	<T> T getAttribute(String key);

	ProcessEngineTool getProcessEngineEx();

	void setAttribute(String key, Object value);
}
