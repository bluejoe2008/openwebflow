package org.openwebflow.mvc.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnexpectedTargetClassException extends RuntimeException
{
	private List<Class<?>> _expectedParameterTypes = new ArrayList<Class<?>>();

	private Class<?> _parameterType;

	public UnexpectedTargetClassException(Class<?> parameterType, Class<?>... expectedParameterTypes)
	{
		_parameterType = parameterType;
		Collections.addAll(_expectedParameterTypes, expectedParameterTypes);
	}

	@Override
	public String getMessage()
	{
		return String.format("unexpected parameter class: %s, expected: %s", _parameterType, _expectedParameterTypes);
	}

}
