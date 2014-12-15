package org.openwebflow.ctrl.cmd;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.log4j.Logger;
import org.openwebflow.ctrl.TaskFlowControlService;

public class DeleteRunningTaskCmd implements Command<java.lang.Void>
{
	private TaskEntity _currentTaskEntity;

	public DeleteRunningTaskCmd(TaskEntity currentTaskEntity)
	{
		_currentTaskEntity = currentTaskEntity;
	}

	@Override
	public Void execute(CommandContext commandContext)
	{
		//删除当前的任务
		//不能删除当前正在执行的任务，所以要先清除掉关联
		if (_currentTaskEntity != null)
		{
			Logger.getLogger(TaskFlowControlService.class).debug(
				String.format("deleting task: %s [id=%s]", _currentTaskEntity.getName(), _currentTaskEntity.getId()));

			Context.getCommandContext().getTaskEntityManager()
					.deleteTask(_currentTaskEntity, TaskEntity.DELETE_REASON_DELETED, false);
		}

		return null;
	}
}