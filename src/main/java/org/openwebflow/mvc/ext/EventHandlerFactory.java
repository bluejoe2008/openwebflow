package org.openwebflow.mvc.ext;

public interface EventHandlerFactory
{
	EventHandler getEventHandler(EventType eventType, String formKey);
}