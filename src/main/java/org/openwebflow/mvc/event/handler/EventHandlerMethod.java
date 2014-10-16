package org.openwebflow.mvc.event.handler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openwebflow.mvc.event.EventType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventHandlerMethod
{

	EventType eventType();

	String formKey();

}
