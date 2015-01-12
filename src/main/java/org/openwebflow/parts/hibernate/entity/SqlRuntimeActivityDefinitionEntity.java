package org.openwebflow.parts.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OWF_ACTIVITY_CREATION")
public class SqlRuntimeActivityDefinitionEntity
{
	@Column(name = "FACTORYNAME")
	String _factoryName;

	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int _id;

	@Column(name = "PROCESSDEFINITIONID")
	String _processDefinitionId;

	@Column(name = "PROCESSINSTANCEID")
	String _processInstanceId;

	@Column(name = "PROPERTIESTEXT")
	String _propertiesText;

	public Class<?> getFactoryClass() throws ClassNotFoundException
	{
		return Class.forName(_factoryName);
	}

	public String getFactoryName()
	{
		return _factoryName;
	}

	public int getId()
	{
		return _id;
	}

	public String getProcessDefinitionId()
	{
		return _processDefinitionId;
	}

	public String getProcessInstanceId()
	{
		return _processInstanceId;
	}

	public String getPropertiesText()
	{
		return _propertiesText;
	}

	public void setFactoryName(String factoryName)
	{
		_factoryName = factoryName;
	}

	public void setId(int id)
	{
		_id = id;
	}

	public void setProcessDefinitionId(String processDefinitionId)
	{
		_processDefinitionId = processDefinitionId;
	}

	public void setProcessInstanceId(String processInstanceId)
	{
		_processInstanceId = processInstanceId;
	}

	public void setPropertiesText(String propertiesText)
	{
		_propertiesText = propertiesText;
	}
}
