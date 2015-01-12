package org.openwebflow.parts.mybatis.service;

import java.util.ArrayList;
import java.util.List;

import org.openwebflow.ctrl.RuntimeActivityDefinitionEntity;
import org.openwebflow.ctrl.RuntimeActivityDefinitionManager;
import org.openwebflow.parts.mybatis.mapper.SqlRuntimeActivityDefinitionManagerMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class SqlRuntimeActivityDefinitionManager extends
		SqlMapperBasedServiceBase<SqlRuntimeActivityDefinitionManagerMapper> implements
		RuntimeActivityDefinitionManager
{
	@Override
	public List<RuntimeActivityDefinitionEntity> list()
	{
		List<RuntimeActivityDefinitionEntity> list = new ArrayList<RuntimeActivityDefinitionEntity>();
		list.addAll(_mapper.findAll());
		return list;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeAll()
	{
		_mapper.deleteAll();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(RuntimeActivityDefinitionEntity entity)
	{
		_mapper.save(entity);
	}
}
