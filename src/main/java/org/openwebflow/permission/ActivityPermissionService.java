package org.openwebflow.permission;

import java.util.List;

public interface ActivityPermissionService
{
	List<ActivityPermission> list();

	void update(ActivityPermission ap) throws Exception;
}