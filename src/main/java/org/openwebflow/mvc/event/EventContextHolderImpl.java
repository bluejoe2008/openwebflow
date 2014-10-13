package org.openwebflow.mvc.event;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;

public class EventContextHolderImpl implements EventContextHolder
{
	private ProcessEngine _processEngine;

	private HttpServletRequest _request;

	public EventContextHolderImpl(ProcessEngine processEngine, HttpServletRequest request)
	{
		super();
		_processEngine = processEngine;
		_request = request;
	}

	@Override
	public CompleteTaskFormEventContext getCompleteTaskFormEventContext()
	{
		return getNotNullTypedAttribute(_request, CompleteTaskFormEventContext.class);
	}

	@Override
	public DoCompleteTaskEventContext getDoCompleteTaskEventContext()
	{
		return getNotNullTypedAttribute(_request, DoCompleteTaskEventContext.class);
	}

	@Override
	public DoStartProcessEventContext getDoStartProcessEventContext()
	{
		return getNotNullTypedAttribute(_request, DoStartProcessEventContext.class);
	}

	private <T> T getNotNullTypedAttribute(HttpServletRequest request, Class<?> clazz)
	{
		Object ctx = request.getAttribute(EventContext.class.getName());

		if (ctx == null)
			throw new EventContextRequiredException(clazz);

		if (!clazz.isAssignableFrom(ctx.getClass()))
			throw new EventContextRequiredException(clazz);

		return (T) ctx;
	}

	@Override
	public StartProcessFormEvent getStartProcessFormEventContext()
	{
		return getNotNullTypedAttribute(_request, StartProcessFormEvent.class);
	}
}
