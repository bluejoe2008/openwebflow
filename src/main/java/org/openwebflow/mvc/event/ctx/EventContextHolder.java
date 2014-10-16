package org.openwebflow.mvc.event.ctx;

import org.openwebflow.mvc.event.StartProcessFormEvent;

public interface EventContextHolder
{
	CompleteTaskFormEventContext getCompleteTaskFormEventContext();

	DoCompleteTaskEventContext getDoCompleteTaskEventContext();

	DoStartProcessEventContext getDoStartProcessEventContext();

	StartProcessFormEvent getStartProcessFormEventContext();
}
