package org.openwebflow.mgr.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import org.openwebflow.ctrl.RuntimeActivityDefinitionEntity;
import org.openwebflow.ctrl.RuntimeActivityDefinitionManager;
import org.openwebflow.mgr.common.SimpleRuntimeActivityDefinitionEntity;
import org.openwebflow.mgr.hibernate.dao.SqlRuntimeActivityDefinitionDao;
import org.openwebflow.mgr.hibernate.entity.SqlRuntimeActivityDefinitionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class SqlRuntimeActivityDefinitionManager implements RuntimeActivityDefinitionManager
{
	@Autowired
	SqlRuntimeActivityDefinitionDao _dao;

	@Override
	public List<RuntimeActivityDefinitionEntity> list() throws Exception
	{
		List<RuntimeActivityDefinitionEntity> list = new ArrayList<RuntimeActivityDefinitionEntity>();
		for (SqlRuntimeActivityDefinitionEntity entity : _dao.list())
		{
			SimpleRuntimeActivityDefinitionEntity rad = new SimpleRuntimeActivityDefinitionEntity();
			rad.setFactoryName(entity.getFactoryName());
			rad.setProcessDefinitionId(entity.getProcessDefinitionId());
			rad.setProcessInstanceId(entity.getProcessInstanceId());
			rad.setPropertiesText(entity.getPropertiesText());

			rad.deserializeProperties();
			list.add(rad);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeAll() throws Exception
	{
		_dao.deleteAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(RuntimeActivityDefinitionEntity entity) throws Exception
	{
		entity.serializeProperties();

		SqlRuntimeActivityDefinitionEntity srad = new SqlRuntimeActivityDefinitionEntity();
		srad.setFactoryName(entity.getFactoryName());
		srad.setProcessDefinitionId(entity.getProcessDefinitionId());
		srad.setProcessInstanceId(entity.getProcessInstanceId());
		srad.setPropertiesText(entity.getPropertiesText());

		_dao.save(srad);
	}

}