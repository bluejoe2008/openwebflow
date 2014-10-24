package org.openwebflow.tool.impl;

import javax.servlet.http.HttpServletRequest;

import org.openwebflow.mvc.tool.HttpRequestParameterRequiredException;
import org.openwebflow.mvc.tool.WebFlowParam;
import org.openwebflow.tool.ActivityTool;
import org.openwebflow.tool.ContextToolHolder;
import org.openwebflow.tool.ProcessDefinitionTool;
import org.openwebflow.tool.ProcessInstanceTool;
import org.openwebflow.tool.TaskTool;
import org.openwebflow.tool.ToolFactory;

public class ContextToolHolderImpl implements ContextToolHolder
{
	private WebFlowParam _an;

	private ToolFactory _processEngineTool;

	private HttpServletRequest _request;

	public ContextToolHolderImpl(ToolFactory processEngineTool, HttpServletRequest request, WebFlowParam an)
	{
		super();
		_processEngineTool = processEngineTool;
		_request = request;
		_an = an;
	}

	@Override
	public ActivityTool getActivityTool()
	{
		return _processEngineTool.createActivityTool(getNotNullParameter(_request, _an.keyProcessDefinitionId()),
			getNotNullParameter(_request, _an.keyActivityId()));
	}

	private String getNotNullParameter(HttpServletRequest request, String name)
	{
		String parameter = request.getParameter(name);
		if (parameter == null)
			throw new HttpRequestParameterRequiredException(name);

		return parameter;
	}

	@Override
	public ProcessDefinitionTool getProcessDefinitionTool()
	{
		return _processEngineTool.createProcessDefinitionTool(getNotNullParameter(_request,
			_an.keyProcessDefinitionId()));

	}

	@Override
	public ToolFactory getProcessEngineTool()
	{
		return _processEngineTool;
	}

	@Override
	public ProcessInstanceTool getProcessInstanceTool()
	{
		return _processEngineTool.createProcessInstanceTool(getNotNullParameter(_request, _an.keyProcessInstanceId()));
	}

	@Override
	public TaskTool getTaskTool()
	{
		return _processEngineTool.createTaskTool(getNotNullParameter(_request, _an.keyTaskId()));
	}
}
