package org.openwebflow.mvc.event;

public interface EventHandlerFactory
{
	EventHandler getEventHandler(EventType eventType, String formKey);
}