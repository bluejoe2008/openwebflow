package org.openwebflow.mvc;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class PrefixFormVariablesFilter implements FormVariablesFilter
{
	String _prefix = "var_";

	@Override
	public Map<String, Object> filterRequestParameters(HttpServletRequest request)
	{
		Map<String, Object> vars = new HashMap<String, Object>();

		Enumeration parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements())
		{
			String name = (String) parameterNames.nextElement();
			if (name.startsWith(_prefix))
			{
				vars.put(name.substring(_prefix.length()), request.getParameter(name));
			}
		}

		return vars;
	}

	public String getPrefix()
	{
		return _prefix;
	}

	public void setPrefix(String prefix)
	{
		_prefix = prefix;
	}
}
