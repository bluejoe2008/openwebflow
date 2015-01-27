package org.openwebflow.mgr.ext;

public interface DelegationManagerEx
{
	/**
	 * 删除所有代理信息
	 */
	public abstract void removeAll() throws Exception;

	/**
	 * 保存一条代理信息
	 */
	public abstract void saveDelegation(String delegated, String delegate) throws Exception;
}