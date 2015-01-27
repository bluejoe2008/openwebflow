package org.openwebflow.mgr.mem;

import java.util.ArrayList;
import java.util.List;

import org.openwebflow.ctrl.RuntimeActivityDefinitionEntity;
import org.openwebflow.ctrl.RuntimeActivityDefinitionManager;

public class InMemoryRuntimeActivityDefinitionManager implements RuntimeActivityDefinitionManager
{
	private static List<RuntimeActivityDefinitionEntity> _list = new ArrayList<RuntimeActivityDefinitionEntity>();

	@Override
	public List<RuntimeActivityDefinitionEntity> list()
	{
		return _list;
	}

	@Override
	public void removeAll()
	{
		_list.clear();
	}

	@Override
	public void save(RuntimeActivityDefinitionEntity entity)
	{
		_list.add(entity);
	}
}
