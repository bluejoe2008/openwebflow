package org.openwebflow.mvc.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class NullEventHandler implements EventHandler
{
	@Override
	public void onEvent(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, EventContext ctx)
	{
	}
}
