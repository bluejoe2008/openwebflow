package org.openwebflow.mvc.event;

import javax.servlet.http.HttpServletRequest;

import org.openwebflow.mvc.event.ctx.EventContextHolder;
import org.openwebflow.mvc.event.ctx.EventContextHolderImpl;
import org.openwebflow.tool.ProcessEngineTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class EventHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver
{
	@Autowired
	private ProcessEngineTool _processEngineEx;

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception
	{
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		return new EventContextHolderImpl(_processEngineEx, request);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter)
	{
		return parameter.getParameterType() == EventContextHolder.class;
	}
}
