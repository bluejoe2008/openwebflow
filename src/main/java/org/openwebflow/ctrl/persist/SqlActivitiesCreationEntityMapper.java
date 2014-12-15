package org.openwebflow.ctrl.persist;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface SqlActivitiesCreationEntityMapper
{
	@Select("SELECT * FROM ACTIVITY_CREATION_TAB")
	List<RuntimeActivityDefinition> findAll();

	@Insert("INSERT INTO ACTIVITY_CREATION_TAB (FACTORYNAME,PROCESSDEFINITIONID,PROCESSINSTANCEID,PROPERTIESTEXT) values (#{factoryName},#{processDefinitionId},#{processInstanceId},#{propertiesText})")
	void save(RuntimeActivityDefinition entity);

	@Delete("DELETE from ACTIVITY_CREATION_TAB")
	void deleteAll();
}
