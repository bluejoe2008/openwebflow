package org.openwebflow.alarm;

import org.activiti.engine.task.Task;
import org.openwebflow.identity.UserDetailsEntity;

public interface MessageNotifier
{
	void notify(UserDetailsEntity[] users, Task task) throws Exception;
}