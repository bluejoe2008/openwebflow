package org.openwebflow.mvc.support;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;

public class WebFlowHelperHolderImpl implements WebFlowHelperHolder
{
	private WebFlowParam _an;

	private ProcessEngine _processEngine;

	private HttpServletRequest _request;

	public WebFlowHelperHolderImpl(ProcessEngine processEngine, HttpServletRequest request, WebFlowParam an)
	{
		super();
		_processEngine = processEngine;
		_request = request;
		_an = an;
	}

	private String getNotNullParameter(HttpServletRequest request, String name)
	{
		String parameter = request.getParameter(name);
		if (parameter == null)
			throw new HttpRequestParameterRequiredException(name);

		return parameter;
	}

	@Override
	public ProcessDefinitionHelper getProcessDefinitionHelper()
	{
		ProcessDefinitionHelperImpl holder = new ProcessDefinitionHelperImpl();
		holder.setProcessEngine(_processEngine);
		holder.setProcessDefinitionId(getNotNullParameter(_request, _an.keyProcessDefinitionId()));
		return holder;
	}

	@Override
	public ProcessInstanceHelper getProcessInstanceHelper()
	{
		ProcessInstanceHelperImpl holder = new ProcessInstanceHelperImpl();
		holder.setProcessEngine(_processEngine);
		holder.setProcessInstanceId(getNotNullParameter(_request, _an.keyProcessInstanceId()));
		return holder;
	}

	@Override
	public TaskHelper getTaskHelper()
	{
		TaskHelperImpl holder = new TaskHelperImpl();
		holder.setProcessEngine(_processEngine);
		holder.setTaskId(getNotNullParameter(_request, _an.keyTaskId()));
		return holder;
	}

}
