package org.openwebflow.mgr.hibernate.service;

import org.openwebflow.identity.UserDetailsEntity;
import org.openwebflow.identity.UserDetailsManager;
import org.openwebflow.mgr.ext.UserDetailsManagerEx;
import org.openwebflow.mgr.hibernate.dao.SqlUserDetailsDao;
import org.openwebflow.mgr.hibernate.entity.SqlUserDetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class SqlUserDetailsManager implements UserDetailsManager, UserDetailsManagerEx
{
	@Autowired
	SqlUserDetailsDao _dao;

	@Override
	public UserDetailsEntity findUserDetails(String userId) throws Exception
	{
		return _dao.findUser(userId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeAll() throws Exception
	{
		_dao.deleteAll();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void saveUserDetails(UserDetailsEntity userDetails) throws Exception
	{
		SqlUserDetailsEntity sud = new SqlUserDetailsEntity(userDetails);
		_dao.saveUser(sud);
	}
}