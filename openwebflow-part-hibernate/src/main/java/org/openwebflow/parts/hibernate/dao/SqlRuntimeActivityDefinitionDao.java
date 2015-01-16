package org.openwebflow.parts.hibernate.dao;

import java.util.List;

import org.openwebflow.parts.hibernate.entity.SqlRuntimeActivityDefinitionEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SqlRuntimeActivityDefinitionDao extends SqlDaoBase<SqlRuntimeActivityDefinitionEntity>
{
	public void deleteAll() throws Exception
	{
		super.executeUpdate("DELETE from SqlRuntimeActivityDefinitionEntity");
	}

	public List<SqlRuntimeActivityDefinitionEntity> list() throws Exception
	{
		return super.queryForObjects("from SqlRuntimeActivityDefinitionEntity");
	}

	public void save(SqlRuntimeActivityDefinitionEntity entity) throws Exception
	{
		super.saveObject(entity);
	}
}