package org.openwebflow.parts.hibernate.service;

import org.openwebflow.identity.UserDetailsEntity;
import org.openwebflow.identity.UserDetailsManager;
import org.openwebflow.parts.ext.UserDetailsManagerEx;
import org.openwebflow.parts.hibernate.dao.SqlUserDetailsDao;
import org.openwebflow.parts.hibernate.entity.SqlUserDetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SqlUserDetailsManager implements UserDetailsManager, UserDetailsManagerEx
{
	@Autowired
	SqlUserDetailsDao _dao;

	private void copyProperties(UserDetailsEntity src, SqlUserDetailsEntity target)
	{
		for (String name : src.getPropertyNames())
		{
			target.setProperty(name, src.getProperty(name));
		}
	}

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
		SqlUserDetailsEntity sud = new SqlUserDetailsEntity();
		copyProperties(userDetails, sud);
		_dao.saveUser(sud);
	}
}