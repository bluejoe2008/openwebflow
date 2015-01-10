package org.openwebflow.tool;

import org.activiti.engine.impl.pvm.process.ActivityImpl;

/**
 * @deprecated
 * @author bluejoe2008@gmail.com
 *
 */
public interface ActivityTool
{
	ActivityImpl getActivity();

	void grantPermission(String assigneeExpression, String candidateGroupIdExpressions,
			String candidateUserIdExpressions) throws Exception;
}