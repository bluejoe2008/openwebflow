package org.openwebflow.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openwebflow.mvc.event.EventType;
import org.openwebflow.mvc.event.ctx.DoStartProcessEventContext;
import org.openwebflow.mvc.event.ctx.EventContextHolder;
import org.openwebflow.mvc.event.handler.EventHandlerClass;
import org.openwebflow.mvc.event.handler.EventHandlerMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;


@EventHandlerClass
@Component
public class MyEventHandler
{
	@Autowired
	VacationRequestService _vacationRequestService;

	@EventHandlerMethod(eventType = EventType.BeforeDoStartProcess, formKey = "/startVacationRequest")
	public void beforeDoStartVacationRequest(EventContextHolder holder, String processDefId, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DoStartProcessEventContext event = holder.getDoStartProcessEventContext();
		event.setBussinessKey("" + System.currentTimeMillis());
	}

	@EventHandlerMethod(eventType = EventType.AfterDoStartProcess, formKey = "/startVacationRequest")
	public void afterDoStartVacationRequest(EventContextHolder holder, String processDefId, long var_numberOfDays,
			String var_vacationMotivation, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		DoStartProcessEventContext event = holder.getDoStartProcessEventContext();
		VacationRequest vr = new VacationRequest();
		vr.setId(Long.parseLong(event.getBussinessKey()));
		vr.setDays(var_numberOfDays);
		vr.setMotivation(var_vacationMotivation);
		vr.setProcessId(event.getProcessInstance().getId());
		_vacationRequestService.insert(vr);
	}
}
