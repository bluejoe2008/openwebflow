package org.openwebflow.ctrl.persist;

import java.util.List;

public interface RuntimeActivityDefinitionStore
{

	List<RuntimeActivityDefinition> list() throws Exception;

	void save(RuntimeActivityDefinition entity) throws Exception;

	void removeAll() throws Exception;

}