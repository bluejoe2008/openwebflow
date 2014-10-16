package org.openwebflow.mvc.event.handler;

import org.openwebflow.mvc.event.EventType;

public interface EventHandlerFactory
{
	EventHandler getEventHandler(EventType eventType, String formKey);
}