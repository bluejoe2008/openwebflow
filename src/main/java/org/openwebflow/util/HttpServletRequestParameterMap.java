package org.openwebflow.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestParameterMap implements MapFactory
{
	String _prefix;

	HttpServletRequest _request;

	public HttpServletRequestParameterMap(HttpServletRequest request)
	{
		super();
		_request = request;
	}

	public HttpServletRequestParameterMap(String prefix, HttpServletRequest request)
	{
		super();
		_prefix = prefix;
		_request = request;
	}

	@Override
	public Map<String, Object> getMap()
	{
		Map<String, Object> vars = new HashMap<String, Object>();
		Map<String, Object> map = _request.getParameterMap();
		for (Entry<String, Object> en : map.entrySet())
		{
			String key = en.getKey();
			Object value = en.getValue();
			if (_prefix == null)
			{
				vars.put(key, value);
			}
			else
			{
				if (key.startsWith(_prefix))
				{
					vars.put(key.substring(_prefix.length()), value);
				}
			}
		}

		return vars;
	}

	public String getPrefix()
	{
		return _prefix;
	}

	public HttpServletRequest getRequest()
	{
		return _request;
	}

	public void setPrefix(String prefix)
	{
		_prefix = prefix;
	}

	public void setRequest(HttpServletRequest request)
	{
		_request = request;
	}
}
