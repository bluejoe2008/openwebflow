package org.openwebflow.tool.impl;

import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.log4j.Logger;
import org.openwebflow.tool.ActivityTool;
import org.openwebflow.tool.ProcessEngineTool;
import org.openwebflow.util.ExpressionUtils;

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
		TaskDefinition taskDefinition = ((UserTaskActivityBehavior) _activityImpl.getActivityBehavior())
				.getTaskDefinition();
		taskDefinition.setAssigneeExpression(assigneeExpression == null ? null : new FixedValue(assigneeExpression));
		FieldUtils.writeField(taskDefinition, "candidateUserIdExpressions",
			ExpressionUtils.stringToExpressionSet(candidateUserIdExpressions), true);
		FieldUtils.writeField(taskDefinition, "candidateGroupIdExpressions",
			ExpressionUtils.stringToExpressionSet(candidateGroupIdExpressions), true);

		Logger.getLogger(this.getClass()).info(
			String.format("granting previledges for [%s, %s, %s] on [%s, %s]", assigneeExpression,
				candidateGroupIdExpressions, candidateUserIdExpressions, _activityImpl.getProcessDefinition().getKey(),
				_activityImpl.getProperty("name")));
	}
}
