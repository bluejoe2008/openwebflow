package org.openwebflow.mvc.event.ctx;

import org.openwebflow.tool.ToolFactory;

public interface EventContext
{
	<T> T getAttribute(String key);

	ToolFactory getProcessEngineTool();

	void setAttribute(String key, Object value);
}
