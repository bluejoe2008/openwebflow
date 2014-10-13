package org.openwebflow.mvc.event;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class EventHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver
{
	@Resource(name = "processEngine")
	private ProcessEngine _processEngine;

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception
	{
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		return new EventContextHolderImpl(_processEngine, request);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter)
	{
		return parameter.getParameterType() == EventContextHolder.class;
	}
}
