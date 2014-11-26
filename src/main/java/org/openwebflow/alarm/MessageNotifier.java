package org.openwebflow.alarm;

import org.activiti.engine.task.Task;
import org.openwebflow.identity.IdentityUserDetails;

public interface MessageNotifier
{
	void notify(IdentityUserDetails[] users, Task task) throws Exception;
}