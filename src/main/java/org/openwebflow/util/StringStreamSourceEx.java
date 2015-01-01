package org.openwebflow.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.activiti.engine.impl.util.io.StringStreamSource;

public class StringStreamSourceEx extends StringStreamSource
{

	private String _bytesEncoding;

	private String _text;

	public StringStreamSourceEx(String text)
	{
		this(text, "utf-8");
	}

	public StringStreamSourceEx(String text, String bytesEncoding)
	{
		super(text);
		_text = text;
		_bytesEncoding = bytesEncoding;
	}

	@Override
	public InputStream getInputStream()
	{
		try
		{
			return new ByteArrayInputStream(_text.getBytes(_bytesEncoding));
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}
}
