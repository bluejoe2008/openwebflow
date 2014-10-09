package org.openwebflow.mvc.ext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openwebflow.util.PackageUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

public class AnnotationedEventHandlerFactory implements EventHandlerFactory, InitializingBean
{
	public class SmartWebFlowEventHandler implements EventHandler
	{
		private HandlerMethod _handlerMethod;

		public SmartWebFlowEventHandler(HandlerMethod handlerMethod)
		{
			_handlerMethod = handlerMethod;
		}

		public void handle(HttpServletRequest request, HttpServletResponse response, ModelAndView mav,
				EventContext context) throws Exception
		{
			request.setAttribute(EventContext.class.getName(), context);
			ModelAndView returnValue = _mappingHandler.handle(request, response, _handlerMethod);
			if (returnValue != null)
			{
				View view = returnValue.getView();
				if (view != null)
				{
					mav.setView(view);
				}

				String viewName = returnValue.getViewName();
				if (viewName != null)
				{
					mav.setViewName(viewName);
				}

				mav.getModelMap().putAll(returnValue.getModelMap());
			}
		}
	}

	private List<String> _basePackages = new ArrayList<String>();

	private Map<EventId, HandlerMethod> _handlerMap = new HashMap<EventId, HandlerMethod>();

	@Resource(type = RequestMappingHandlerAdapter.class)
	private RequestMappingHandlerAdapter _mappingHandler;

	@Override
	public void afterPropertiesSet() throws Exception
	{
		for (String basePackage : _basePackages)
		{
			for (String className : PackageUtils.getClassName(basePackage, true))
			{
				Class clz = Class.forName(className);
				if (clz.getAnnotation(EventHandlerClass.class) != null)
				{
					Object handlerObject = clz.newInstance();
					for (Method method : clz.getMethods())
					{
						EventHandlerMethod an = method.getAnnotation(EventHandlerMethod.class);
						if (an != null)
						{
							_handlerMap.put(new EventId(an.eventType(), an.formKey()), new HandlerMethod(handlerObject,
									method));
						}
					}
				}
			}
		}
	}

	public List<String> getBasePackages()
	{
		return _basePackages;
	}

	@Override
	public EventHandler getEventHandler(EventType eventType, String formKey)
	{
		HandlerMethod handlerMethod = _handlerMap.get(new EventId(eventType, formKey));
		if (handlerMethod == null)
		{
			Logger.getLogger(this.getClass()).warn(String.format("ignoring event: %s(%s)", eventType, formKey));
			return null;
		}

		return new SmartWebFlowEventHandler(handlerMethod);
	}

	public void setBasePackages(List<String> basePackages)
	{
		_basePackages = basePackages;
	}
}
