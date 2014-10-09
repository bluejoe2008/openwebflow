package org.openwebflow.mvc.ext;

import org.activiti.engine.ProcessEngine;

public interface EventContext
{
	<T> T getAttribute(String key);

	ProcessEngine getProcessEngine();

	void setAttribute(String key, Object value);
}
