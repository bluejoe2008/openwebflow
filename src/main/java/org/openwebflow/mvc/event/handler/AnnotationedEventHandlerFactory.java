package org.openwebflow.mvc.event.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openwebflow.mvc.event.EventId;
import org.openwebflow.mvc.event.EventType;
import org.openwebflow.mvc.event.ctx.EventContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

public class AnnotationedEventHandlerFactory implements EventHandlerFactory, ApplicationContextAware
{
	public class SmartWebFlowEventHandler implements EventHandler
	{
		private HandlerMethod _handlerMethod;

		public SmartWebFlowEventHandler(HandlerMethod handlerMethod)
		{
			_handlerMethod = handlerMethod;
		}

		public void onEvent(HttpServletRequest request, HttpServletResponse response, ModelAndView mav,
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

	private Map<EventId, HandlerMethod> _handlerMap;

	@Resource(type = RequestMappingHandlerAdapter.class)
	private RequestMappingHandlerAdapter _mappingHandler;

	private Map<EventId, HandlerMethod> buildEventHandlerMap(ApplicationContext applicationContext)
	{
		Map<EventId, HandlerMethod> handlerMap = new HashMap<EventId, HandlerMethod>();
		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(EventHandlerClass.class);
		for (Object bean : beans.values())
		{
			for (Method method : bean.getClass().getMethods())
			{
				EventHandlerMethod an = method.getAnnotation(EventHandlerMethod.class);
				if (an != null)
				{
					handlerMap.put(new EventId(an.eventType(), an.formKey()), new HandlerMethod(bean, method));
				}
			}
		}

		return handlerMap;
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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		_handlerMap = buildEventHandlerMap(applicationContext);
	}
}
