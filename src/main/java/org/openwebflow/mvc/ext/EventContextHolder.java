package org.openwebflow.mvc.ext;

public interface EventContextHolder
{
	CompleteTaskFormEventContext getCompleteTaskFormEventContext();

	DoCompleteTaskEventContext getDoCompleteTaskEventContext();

	DoStartProcessEventContext getDoStartProcessEventContext();

	StartProcessFormEvent getStartProcessFormEventContext();
}
