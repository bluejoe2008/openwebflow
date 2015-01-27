package org.openwebflow.assign.delegation;

import java.util.List;

public interface DelegationManager
{
	/**
	 * 获取指定用户的代理人列表 
	 */
	String[] getDelegates(String delegated);

	/**
	 * 获取所有的代理信息列表，引擎会在启动的时候加载
	 */
	List<DelegationEntity> listDelegationEntities();
}