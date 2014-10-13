package org.openwebflow.mvc.helper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class WebFlowHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver
{
	@Resource(name = "processEngine")
	private ProcessEngine _processEngine;

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception
	{
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		WebFlowParam an = parameter.getParameterAnnotation(WebFlowParam.class);

		if (parameter.getParameterType() == WebFlowHelperHolder.class)
		{
			return new WebFlowHelperHolderImpl(_processEngine, request, an);
		}

		throw new UnexpectedTargetClassException(parameter.getParameterType(), WebFlowHelperHolder.class);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter)
	{
		return parameter.hasParameterAnnotation(WebFlowParam.class);
	}
}
