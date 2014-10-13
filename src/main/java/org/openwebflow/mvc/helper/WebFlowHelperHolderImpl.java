package org.openwebflow.mvc.helper;

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
		ProcessDefinitionHelperImpl helper = new ProcessDefinitionHelperImpl();
		helper.setProcessEngine(_processEngine);
		helper.setProcessDefinitionId(getNotNullParameter(_request, _an.keyProcessDefinitionId()));
		return helper;
	}

	@Override
	public ProcessEngineHelper getProcessEngineHelper()
	{
		return new ProcessEngineHelperImpl(_processEngine);
	}

	@Override
	public ProcessInstanceHelper getProcessInstanceHelper()
	{
		ProcessInstanceHelperImpl helper = new ProcessInstanceHelperImpl();
		helper.setProcessEngine(_processEngine);
		helper.setProcessInstanceId(getNotNullParameter(_request, _an.keyProcessInstanceId()));
		return helper;
	}

	@Override
	public TaskHelper getTaskHelper()
	{
		TaskHelperImpl helper = new TaskHelperImpl();
		helper.setProcessEngine(_processEngine);
		helper.setTaskId(getNotNullParameter(_request, _an.keyTaskId()));
		return helper;
	}

}
