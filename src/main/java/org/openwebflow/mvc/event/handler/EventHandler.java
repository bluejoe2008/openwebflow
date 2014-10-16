package org.openwebflow.mvc.event.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openwebflow.mvc.event.ctx.EventContext;
import org.springframework.web.servlet.ModelAndView;

public interface EventHandler
{
	void onEvent(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, EventContext ctx)
			throws Exception;
}