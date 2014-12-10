package org.openwebflow.tool.impl;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.openwebflow.tool.ActivityTool;
import org.openwebflow.tool.ProcessEngineTool;
import org.openwebflow.util.ProcessDefinitionUtils;

public class ActivityToolImpl extends AbstractTool implements ActivityTool
{
	ActivityImpl _activityImpl;

	public ActivityToolImpl(ProcessEngineTool processEngineTool, ActivityImpl activityImpl)
	{
		super(processEngineTool);
		_activityImpl = activityImpl;
	}

	@Override
	public ActivityImpl getActivity()
	{
		return _activityImpl;
	}

	@Override
	public void grantPermission(String assigneeExpression, String candidateGroupIdExpressions,
			String candidateUserIdExpressions) throws Exception
	{
		ProcessDefinitionUtils.grantPermission(_activityImpl, assigneeExpression, candidateGroupIdExpressions,
			candidateUserIdExpressions);
	}
}
