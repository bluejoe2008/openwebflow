package org.openwebflow;

import org.activiti.engine.form.AbstractFormType;

public class UserFormType extends AbstractFormType
{

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
		return "user";
	}

}
