package org.openwebflow.ctrl;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface RuntimeActivityDefinitionEntity
{

	void deserializeProperties() throws IOException;

	List<String> getAssignees();

	String getCloneActivityId();

	List<String> getCloneActivityIds();

	Class<?> getFactoryClass() throws ClassNotFoundException;

	String getFactoryName();

	String getNextActivityId();

	String getProcessDefinitionId();

	String getProcessInstanceId();

	String getPropertiesText();

	<T> T getProperty(String name);

	String getPrototypeActivityId();

	boolean getSequential();

	void serializeProperties() throws JsonProcessingException;

	void setAssignees(List<String> assignees);

	void setCloneActivityId(String cloneActivityId);

	void setCloneActivityIds(List<String> cloneActivityIds);

	void setFactoryName(String factoryName);

	void setNextActivityId(String nextActivityId);

	void setProcessDefinitionId(String processDefinitionId);

	void setProcessInstanceId(String processInstanceId);

	void setPropertiesText(String propertiesText);

	void setPrototypeActivityId(String prototypeActivityId);

	void setSequential(boolean sequential);

}