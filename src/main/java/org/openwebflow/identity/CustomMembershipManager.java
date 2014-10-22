package org.openwebflow.identity;

import java.util.List;

import org.activiti.engine.identity.Group;

public interface CustomMembershipManager
{
	public List<Group> findGroupsByUser(String userId);
}
