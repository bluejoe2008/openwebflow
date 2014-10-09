package org.openwebflow.mvc.support;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;

public class ProcessInstanceHelperImpl implements ProcessInstanceHelper
{
	private ProcessEngine _processEngine;

	private String _processInstanceId;

	public ProcessEngine getProcessEngine()
	{
		return _processEngine;
	}

	@Override
	public ProcessInstance getProcessInstance()
	{
		return _processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(_processInstanceId)
				.singleResult();
	}

	public String getProcessInstanceId()
	{
		return _processInstanceId;
	}

	public void setProcessEngine(ProcessEngine processEngine)
	{
		_processEngine = processEngine;
	}

	public void setProcessInstanceId(String processInstanceId)
	{
		_processInstanceId = processInstanceId;
	}
}
