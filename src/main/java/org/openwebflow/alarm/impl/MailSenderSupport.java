package org.openwebflow.alarm.impl;

public class MailSenderSupport
{
	protected String _authPassword;

	protected String _authUserName;

	protected String _mailFrom;

	protected String _serverHost;

	protected int _serverPort;

	public String getAuthPassword()
	{
		return _authPassword;
	}

	public String getAuthUserName()
	{
		return _authUserName;
	}

	public String getMailFrom()
	{
		return _mailFrom;
	}

	public String getServerHost()
	{
		return _serverHost;
	}

	public int getServerPort()
	{
		return _serverPort;
	}

	public void setAuthPassword(String authPassword)
	{
		_authPassword = authPassword;
	}

	public void setAuthUserName(String authUserName)
	{
		_authUserName = authUserName;
	}

	public void setMailFrom(String mailFrom)
	{
		_mailFrom = mailFrom;
	}

	public void setServerHost(String serverHost)
	{
		_serverHost = serverHost;
	}

	public void setServerPort(int serverPort)
	{
		_serverPort = serverPort;
	}

}