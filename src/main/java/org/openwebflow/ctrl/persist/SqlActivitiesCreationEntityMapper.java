package org.openwebflow.ctrl.persist;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface SqlActivitiesCreationEntityMapper
{
	@Select("SELECT * FROM OWF_ACTIVITY_CREATION")
	List<RuntimeActivityDefinition> findAll();

	@Insert("INSERT INTO OWF_ACTIVITY_CREATION (FACTORYNAME,PROCESSDEFINITIONID,PROCESSINSTANCEID,PROPERTIESTEXT) values (#{factoryName},#{processDefinitionId},#{processInstanceId},#{propertiesText})")
	void save(RuntimeActivityDefinition entity);

	@Delete("DELETE from OWF_ACTIVITY_CREATION")
	void deleteAll();
}
