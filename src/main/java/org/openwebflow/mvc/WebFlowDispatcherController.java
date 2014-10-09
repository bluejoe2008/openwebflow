package org.openwebflow.mvc;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.openwebflow.mvc.ext.EventContext;
import org.openwebflow.mvc.ext.EventHandler;
import org.openwebflow.mvc.ext.EventHandlerFactory;
import org.openwebflow.mvc.ext.EventType;
import org.openwebflow.mvc.ext.NullEventHandler;
import org.openwebflow.mvc.ext.ProcessEventContextImpl;
import org.openwebflow.mvc.ext.TaskEventContextImpl;
import org.openwebflow.mvc.support.ProcessDefinitionHelper;
import org.openwebflow.mvc.support.TaskHelper;
import org.openwebflow.mvc.support.WebFlowHelperHolder;
import org.openwebflow.mvc.support.WebFlowParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/workflow/")
public class WebFlowDispatcherController
{
	@Resource(name = "webFlowConfiguration")
	private WebFlowConfiguration _conf;

	@Resource(name = "customProcessActionHandlerFactory")
	private EventHandlerFactory _customProcessActionHandlerFactory;

	@Resource(name = "processEngine")
	private ProcessEngine _processEngine;

	@RequestMapping("claimTask.action")
	public String claimTask(@WebFlowParam
	WebFlowHelperHolder holder)
	{
		TaskHelper helper = holder.getTaskHelper();
		helper.claim();
		return _conf.getDefaultClaimTaskActionView();
	}

	@RequestMapping("completeTaskForm.action")
	public String completeTaskForm(@WebFlowParam
	WebFlowHelperHolder holder, ModelAndView mav, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		TaskHelper helper = holder.getTaskHelper();
		Task task = helper.getTask();
		String formKey = task.getFormKey();

		//创建event
		TaskEventContextImpl ctx = new TaskEventContextImpl();
		ctx.setProcessEngine(_processEngine);
		ctx.setTaskId(helper.getTaskId());
		ctx.setTask(helper.getTask());
		ctx.setProcessInstance(helper.getProcessInstance());

		fireEvent(request, response, mav, formKey, EventType.OnCompleteTaskForm, ctx);

		if (formKey == null)
		{
			formKey = _conf.getDefaultCompleteTaskFormView();
		}

		model.put("task", task);
		model.put("process", helper.getProcessInstance());
		return formKey;
	}

	@RequestMapping("doCompleteTask.action")
	public String doCompleteTask(String taskId, @WebFlowParam
	WebFlowHelperHolder holder, ModelAndView mav, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		TaskHelper helper = holder.getTaskHelper();
		Task task = helper.getTask();
		String formKey = task.getFormKey();
		ProcessInstance processInstance = helper.getProcessInstance();

		//创建event
		TaskEventContextImpl ctx = new TaskEventContextImpl();
		ctx.setProcessEngine(_processEngine);
		ctx.setTaskId(helper.getTaskId());
		ctx.setTask(helper.getTask());
		ctx.setProcessInstance(processInstance);
		ctx.getProcessVariableMap().putAll(_conf.getFormVariablesFilter().filterRequestParameters(request));

		fireEvent(request, response, mav, formKey, EventType.BeforeDoCompleteTask, ctx);
		helper.completeTask(ctx.getProcessVariableMap());
		fireEvent(request, response, mav, formKey, EventType.AfterDoCompleteTask, ctx);

		model.put("task", task);
		model.put("process", processInstance);

		return mav.hasView() ? mav.getViewName() : _conf.getDefaultCompleteTaskActionView();
	}

	@RequestMapping("doStartProcess.action")
	public String doStartProcess(@WebFlowParam
	WebFlowHelperHolder holder, ModelAndView mav, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		ProcessDefinitionHelper helper = holder.getProcessDefinitionHelper();
		String startFormKey = helper.getStartFormKey();

		//创建event
		ProcessEventContextImpl ctx = new ProcessEventContextImpl();
		ctx.setProcessEngine(_processEngine);
		ctx.setProcessDefinitionId(helper.getProcessDefinitionId());
		ctx.setProcessDefinition(helper.getProcessDefinition());
		ctx.getProcessVariableMap().putAll(_conf.getFormVariablesFilter().filterRequestParameters(request));

		fireEvent(request, response, mav, startFormKey, EventType.BeforeDoStartProcess, ctx);

		String businessKey = ctx.getBussinessKey();
		ProcessInstance processInstance = helper.startProcess(businessKey, ctx.getProcessVariableMap());
		model.put("processDef", helper.getProcessDefinition());
		model.put("processInstance", processInstance);

		ctx.setProcessInstance(processInstance);

		fireEvent(request, response, mav, startFormKey, EventType.AfterDoStartProcess, ctx);
		return mav.hasView() ? mav.getViewName() : _conf.getDefaultStartProcessActionView();
	}

	private void fireEvent(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, String formKey,
			EventType eventType, EventContext event) throws Exception
	{
		getActionHandler(eventType, formKey).handle(request, response, mav, event);
	}

	/**
	 * 查找指定event的自定义handler
	 * 
	 * @param startFormKey
	 * @return
	 */
	private EventHandler getActionHandler(EventType eventType, String eventKey)
	{
		if (eventKey == null)
			return new NullEventHandler();

		EventHandler handler = _customProcessActionHandlerFactory.getEventHandler(eventType, eventKey);

		if (handler == null)
			return new NullEventHandler();

		return handler;
	}

	/**
	 * 显示启动进程表单
	 * 
	 * @param processDefId
	 *            流程的ID，如：process:1:4
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("startProcessForm.action")
	public String startProcessForm(@WebFlowParam
	WebFlowHelperHolder holder, ModelAndView mav, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		ProcessDefinitionHelper helper = holder.getProcessDefinitionHelper();
		model.put("processDef", helper.getProcessDefinition());
		String startFormKey = helper.getStartFormKey();

		//创建event
		ProcessEventContextImpl ctx = new ProcessEventContextImpl();
		ctx.setProcessEngine(_processEngine);
		ctx.setProcessDefinitionId(helper.getProcessDefinitionId());
		ctx.setProcessDefinition(helper.getProcessDefinition());

		fireEvent(request, response, mav, startFormKey, EventType.OnStartProcessForm, ctx);

		String viewName = (startFormKey == null ? _conf.getDefaultStartProcessFormView() : startFormKey);
		return viewName;
	}
}
