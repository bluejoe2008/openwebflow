package org.openwebflow.cfg;

import org.activiti.engine.form.AbstractFormType;

public class DummyFormType extends AbstractFormType
{
	String _name;

	public DummyFormType(String name)
	{
		super();
		_name = name;
	}

	@Override
	public Object convertFormValueToModelValue(String propertyValue)
	{
		return null;
	}

	@Override
	public String convertModelValueToFormValue(Object modelValue)
	{
		return null;
	}

	@Override
	public String getName()
	{
		return _name;
	}

}
