package org.openwebflow.mvc.event.ctx;

import javax.servlet.http.HttpServletRequest;

import org.openwebflow.mvc.event.StartProcessFormEvent;
import org.openwebflow.tool.ProcessEngineTool;

public class EventContextHolderImpl implements EventContextHolder
{
	private ProcessEngineTool _processEngineEx;

	private HttpServletRequest _request;

	public EventContextHolderImpl(ProcessEngineTool processEngineEx, HttpServletRequest request)
	{
		super();
		_processEngineEx = processEngineEx;
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
