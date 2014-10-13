package org.openwebflow.mvc.event;

public interface EventContextHolder
{
	CompleteTaskFormEventContext getCompleteTaskFormEventContext();

	DoCompleteTaskEventContext getDoCompleteTaskEventContext();

	DoStartProcessEventContext getDoStartProcessEventContext();

	StartProcessFormEvent getStartProcessFormEventContext();
}
