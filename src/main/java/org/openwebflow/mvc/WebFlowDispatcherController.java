package org.openwebflow.mvc;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.openwebflow.mvc.event.EventType;
import org.openwebflow.mvc.event.ctx.EventContext;
import org.openwebflow.mvc.event.ctx.ProcessEventContextImpl;
import org.openwebflow.mvc.event.ctx.TaskEventContextImpl;
import org.openwebflow.mvc.event.handler.EventHandler;
import org.openwebflow.mvc.event.handler.EventHandlerFactory;
import org.openwebflow.mvc.event.handler.NullEventHandler;
import org.openwebflow.mvc.tool.WebFlowParam;
import org.openwebflow.tool.ContextToolHolder;
import org.openwebflow.tool.ProcessDefinitionTool;
import org.openwebflow.tool.ProcessEngineTool;
import org.openwebflow.tool.TaskTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/workflow/")
public class WebFlowDispatcherController
{
	@Resource(name = "customProcessActionHandlerFactory")
	private EventHandlerFactory _customProcessActionHandlerFactory;

	@Autowired
	private ProcessEngineTool _processEngineEx;

	@RequestMapping("claimTask.action")
	public String claimTask(@WebFlowParam
	ContextToolHolder holder)
	{
		TaskTool tool = holder.getTaskTool();
		tool.claim();
		return _processEngineEx.getWebFlowConfiguration().getDefaultClaimTaskActionView();
	}

	@RequestMapping("completeTaskForm.action")
	public String completeTaskForm(@WebFlowParam
	ContextToolHolder holder, ModelAndView mav, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		TaskTool tool = holder.getTaskTool();
		Task task = tool.getTask();
		String formKey = task.getFormKey();

		//创建event
		TaskEventContextImpl ctx = new TaskEventContextImpl();
		ctx.setProcessEngineEx(_processEngineEx);
		ctx.setTaskId(tool.getTaskId());
		ctx.setTask(tool.getTask());
		ctx.setProcessInstance(tool.getProcessInstance());

		fireEvent(request, response, mav, formKey, EventType.OnCompleteTaskForm, ctx);

		if (formKey == null)
		{
			formKey = _processEngineEx.getWebFlowConfiguration().getDefaultCompleteTaskFormView();
		}

		model.put("task", task);
		model.put("process", tool.getProcessInstance());
		return formKey;
	}

	@RequestMapping("doCompleteTask.action")
	public String doCompleteTask(String taskId, @WebFlowParam
	ContextToolHolder holder, ModelAndView mav, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		TaskTool tool = holder.getTaskTool();
		Task task = tool.getTask();
		String formKey = task.getFormKey();
		ProcessInstance processInstance = tool.getProcessInstance();

		//创建event
		TaskEventContextImpl ctx = new TaskEventContextImpl();
		ctx.setProcessEngineEx(_processEngineEx);
		ctx.setTaskId(tool.getTaskId());
		ctx.setTask(tool.getTask());
		ctx.setProcessInstance(processInstance);
		ctx.getProcessVariableMap().putAll(
			_processEngineEx.getWebFlowConfiguration().getFormVariablesFilter().filterRequestParameters(request));

		fireEvent(request, response, mav, formKey, EventType.BeforeDoCompleteTask, ctx);
		tool.completeTask(ctx.getProcessVariableMap());
		fireEvent(request, response, mav, formKey, EventType.AfterDoCompleteTask, ctx);

		model.put("task", task);
		model.put("process", processInstance);

		return mav.hasView() ? mav.getViewName() : _processEngineEx.getWebFlowConfiguration()
				.getDefaultCompleteTaskActionView();
	}

	@RequestMapping("doStartProcess.action")
	public String doStartProcess(@WebFlowParam
	ContextToolHolder holder, ModelAndView mav, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProcessDefinitionTool tool = holder.getProcessDefinitionTool();
		String startFormKey = tool.getStartFormKey();

		//创建event
		ProcessEventContextImpl ctx = new ProcessEventContextImpl();
		ctx.setProcessEngineEx(_processEngineEx);
		ctx.setProcessDefinitionId(tool.getProcessDefinitionId());
		ctx.setProcessDefinition(tool.getProcessDefinition());
		ctx.getProcessVariableMap().putAll(
			_processEngineEx.getWebFlowConfiguration().getFormVariablesFilter().filterRequestParameters(request));

		fireEvent(request, response, mav, startFormKey, EventType.BeforeDoStartProcess, ctx);

		String businessKey = ctx.getBussinessKey();
		ProcessInstance processInstance = tool.startProcess(businessKey, ctx.getProcessVariableMap());
		model.put("processDef", tool.getProcessDefinition());
		model.put("processInstance", processInstance);

		ctx.setProcessInstance(processInstance);

		fireEvent(request, response, mav, startFormKey, EventType.AfterDoStartProcess, ctx);
		return mav.hasView() ? mav.getViewName() : _processEngineEx.getWebFlowConfiguration()
				.getDefaultStartProcessActionView();
	}

	private void fireEvent(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, String formKey,
			EventType eventType, EventContext event) throws Exception
	{
		getActionHandler(eventType, formKey).onEvent(request, response, mav, event);
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
	ContextToolHolder holder, ModelAndView mav, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProcessDefinitionTool tool = holder.getProcessDefinitionTool();
		model.put("processDef", tool.getProcessDefinition());
		String startFormKey = tool.getStartFormKey();

		//创建event
		ProcessEventContextImpl ctx = new ProcessEventContextImpl();
		ctx.setProcessEngineEx(_processEngineEx);
		ctx.setProcessDefinitionId(tool.getProcessDefinitionId());
		ctx.setProcessDefinition(tool.getProcessDefinition());

		fireEvent(request, response, mav, startFormKey, EventType.OnStartProcessForm, ctx);

		String viewName = (startFormKey == null ? _processEngineEx.getWebFlowConfiguration()
				.getDefaultStartProcessFormView() : startFormKey);
		return viewName;
	}
}
