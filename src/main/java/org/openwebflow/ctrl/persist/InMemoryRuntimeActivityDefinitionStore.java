package org.openwebflow.ctrl.persist;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRuntimeActivityDefinitionStore implements RuntimeActivityDefinitionStore
{
	private static List<RuntimeActivityDefinition> _list = new ArrayList<RuntimeActivityDefinition>();

	@Override
	public List<RuntimeActivityDefinition> list()
	{
		return _list;
	}

	@Override
	public void save(RuntimeActivityDefinition entity)
	{
		_list.add(entity);
	}

	@Override
	public void removeAll()
	{
		_list.clear();
	}
}
