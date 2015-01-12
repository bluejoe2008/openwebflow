package org.openwebflow.parts.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.openwebflow.ctrl.RuntimeActivityDefinitionEntity;
import org.openwebflow.parts.common.SimpleRuntimeActivityDefinitionEntity;

public interface SqlRuntimeActivityDefinitionManagerMapper
{
	@Delete("DELETE from OWF_ACTIVITY_CREATION")
	void deleteAll();

	@Select("SELECT * FROM OWF_ACTIVITY_CREATION")
	List<SimpleRuntimeActivityDefinitionEntity> findAll();

	@Insert("INSERT INTO OWF_ACTIVITY_CREATION (FACTORYNAME,PROCESSDEFINITIONID,PROCESSINSTANCEID,PROPERTIESTEXT) values (#{factoryName},#{processDefinitionId},#{processInstanceId},#{propertiesText})")
	void save(RuntimeActivityDefinitionEntity entity);
}
