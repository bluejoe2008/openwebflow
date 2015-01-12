package org.openwebflow.parts.mybatis.service;

import org.openwebflow.identity.UserDetailsEntity;
import org.openwebflow.identity.UserDetailsManager;
import org.openwebflow.parts.ext.UserDetailsManagerEx;
import org.openwebflow.parts.mybatis.mapper.SqlUserDetailsEntityMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class SqlUserDetailsManager extends SqlMapperBasedServiceBase<SqlUserDetailsEntityMapper> implements
		UserDetailsManager, UserDetailsManagerEx
{
	@Override
	public UserDetailsEntity findUserDetails(String userId)
	{
		return _mapper.findUserDetailsById(userId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeAll()
	{
		_mapper.deleteAll();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveUserDetails(UserDetailsEntity userDetails)
	{
		_mapper.saveUserDetails(userDetails);
	}
}
