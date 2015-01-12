package org.openwebflow.parts.hibernate.dao;

import java.util.List;

import org.openwebflow.identity.UserDetailsEntity;
import org.openwebflow.parts.hibernate.entity.SqlUserDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SqlUserDetailsDao extends SqlDaoBase<SqlUserDetailsEntity>
{
	public void deleteAll() throws Exception
	{
		super.executeUpdate("DELETE from SqlUserDetailsEntity");
	}

	public SqlUserDetailsEntity findUser(String userId) throws Exception
	{
		return super.queryForObject("from SqlUserDetailsEntity where USERID=?", userId);
	}

	public List<UserDetailsEntity> list() throws Exception
	{
		return super.queryForObjects("from SqlUserDetailsEntity");
	}

	public void saveUser(SqlUserDetailsEntity userDetails) throws Exception
	{
		super.saveObject(userDetails);
	}
}