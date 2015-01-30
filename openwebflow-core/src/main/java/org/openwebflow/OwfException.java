package org.openwebflow;

public class OwfException extends RuntimeException
{

	public OwfException()
	{
		super();
	}

	public OwfException(String message)
	{
		super(message);
	}

	public OwfException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public OwfException(Throwable cause)
	{
		super(cause);
	}

}
