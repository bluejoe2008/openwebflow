package org.openwebflow.ctrl.persist;

import java.util.List;

public class SqlRuntimeActivityDefinitionStore implements RuntimeActivityDefinitionStore
{
	SqlActivitiesCreationEntityMapper _mapper;

	public SqlActivitiesCreationEntityMapper getMapper()
	{
		return _mapper;
	}

	public void setMapper(SqlActivitiesCreationEntityMapper mapper)
	{
		_mapper = mapper;
	}

	@Override
	public List<RuntimeActivityDefinition> loadAll()
	{
		return _mapper.findAll();
	}

	@Override
	public void save(RuntimeActivityDefinition entity)
	{
		_mapper.save(entity);
	}

	@Override
	public void removeAll()
	{
		_mapper.deleteAll();
	}
}
