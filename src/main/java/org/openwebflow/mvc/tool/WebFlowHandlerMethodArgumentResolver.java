package org.openwebflow.mvc.tool;

import javax.servlet.http.HttpServletRequest;

import org.openwebflow.tool.ContextToolHolder;
import org.openwebflow.tool.ProcessEngineTool;
import org.openwebflow.tool.impl.ContextToolHolderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class WebFlowHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver
{
	@Autowired
	private ProcessEngineTool _processEngineTool;

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception
	{
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		WebFlowParam an = parameter.getParameterAnnotation(WebFlowParam.class);

		if (parameter.getParameterType() == ContextToolHolder.class)
		{
			return new ContextToolHolderImpl(_processEngineTool, request, an);
		}

		throw new UnexpectedTargetClassException(parameter.getParameterType(), ContextToolHolder.class);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter)
	{
		return parameter.hasParameterAnnotation(WebFlowParam.class);
	}
}
