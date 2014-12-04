package org.openwebflow.identity.sql;

import org.openwebflow.identity.AbstractUserDetailsStore;
import org.openwebflow.identity.IdentityUserDetails;
import org.openwebflow.identity.UserDetailsManager;

public class SqlUserDetailsStore extends AbstractUserDetailsStore implements UserDetailsManager
{
	SqlUserDetailsMapper _mapper;

	public SqlUserDetailsMapper getMapper()
	{
		return _mapper;
	}

	public void setMapper(SqlUserDetailsMapper mapper)
	{
		_mapper = mapper;
	}

	@Override
	public IdentityUserDetails findUser(String userId)
	{
		return _mapper.findUserById(userId);
	}

	@Override
	public void saveUser(IdentityUserDetails userDetails)
	{
		_mapper.saveUser(userDetails);
	}

	@Override
	public void removeAll()
	{
		_mapper.deleteAll();
	}
}
