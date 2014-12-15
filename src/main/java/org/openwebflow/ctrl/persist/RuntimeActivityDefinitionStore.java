package org.openwebflow.ctrl.persist;

import java.util.List;

public interface RuntimeActivityDefinitionStore
{

	List<RuntimeActivityDefinition> loadAll();

	void save(RuntimeActivityDefinition entity);

	void removeAll();

}