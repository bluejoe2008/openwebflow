package org.openwebflow.ctrl.persist;

import java.util.List;

public interface RuntimeActivityDefinitionStore
{

	List<RuntimeActivityDefinition> loadAll() throws Exception;

	void save(RuntimeActivityDefinition entity) throws Exception;

	void removeAll() throws Exception;

}