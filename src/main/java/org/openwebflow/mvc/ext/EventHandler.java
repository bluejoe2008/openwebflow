package org.openwebflow.mvc.ext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface EventHandler
{
	void handle(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, EventContext ctx)
			throws Exception;
}