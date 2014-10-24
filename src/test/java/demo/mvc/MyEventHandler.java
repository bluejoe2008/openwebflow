package demo.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openwebflow.mvc.event.EventType;
import org.openwebflow.mvc.event.ctx.DoStartProcessEventContext;
import org.openwebflow.mvc.event.ctx.EventContextHolder;
import org.openwebflow.mvc.event.handler.EventHandlerClass;
import org.openwebflow.mvc.event.handler.EventHandlerMethod;
import org.openwebflow.tool.ProcessEngineTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@EventHandlerClass
@Component
public class MyEventHandler
{
	@Autowired
	private ProcessEngineTool _processEngineTool;

	@EventHandlerMethod(eventType = EventType.BeforeDoStartProcess, formKey = "/startVacationRequest")
	public void beforeDoStartVacationRequest(EventContextHolder holder, String processDefId, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Logger.getLogger(this.getClass()).debug("ProcessEngineTool: " + _processEngineTool);

		DoStartProcessEventContext event = holder.getDoStartProcessEventContext();
		event.setBussinessKey("" + System.currentTimeMillis());
	}
}
