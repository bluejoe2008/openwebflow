package org.openwebflow.permission;

import java.util.List;

public interface ActivityPermissionDao
{

	void delete(ActivityPermission ap);

	void insertOrUpdate(ActivityPermission ap);

	List<ActivityPermission> list();

}