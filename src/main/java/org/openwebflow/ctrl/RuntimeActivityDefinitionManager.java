package org.openwebflow.ctrl;

import java.util.List;

public interface RuntimeActivityDefinitionManager
{

	List<RuntimeActivityDefinitionEntity> list() throws Exception;

	void removeAll() throws Exception;

	void save(RuntimeActivityDefinitionEntity entity) throws Exception;

}