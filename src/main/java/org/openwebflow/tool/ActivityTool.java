package org.openwebflow.tool;

import org.activiti.engine.impl.pvm.process.ActivityImpl;

public interface ActivityTool
{
	ActivityImpl getActivity();

	void grantPermission(String assigneeExpression, String candidateGroupIdExpressions,
			String candidateUserIdExpressions) throws Exception;
}