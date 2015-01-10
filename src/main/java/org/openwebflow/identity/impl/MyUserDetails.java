package org.openwebflow.identity.impl;

import java.util.HashMap;
import java.util.Map;

import org.openwebflow.identity.IdentityUserDetails;

public class MyUserDetails implements IdentityUserDetails
{
	private Map<String, Object> _map = new HashMap<String, Object>();

	public MyUserDetails()
	{

	}

	public MyUserDetails(String userId, String nickName, String email, String mobilePhoneNumber)
	{
		super();
		_map.put(STRING_PROPERTY_USER_ID, userId);
		_map.put(STRING_PROPERTY_EMAIL, email);
		_map.put(STRING_PROPERTY_MOBILE_PHONE_NUMBER, mobilePhoneNumber);
		_map.put(STRING_PROPERTY_NICK_NAME, nickName);
	}

	public String getEmail()
	{
		return getProperty(STRING_PROPERTY_EMAIL);
	}

	public String getMobilePhoneNumber()
	{
		return getProperty(STRING_PROPERTY_MOBILE_PHONE_NUMBER);
	}

	public String getNickName()
	{
		return getProperty(STRING_PROPERTY_NICK_NAME);
	}

	public <T> T getProperty(String name)
	{
		return (T) _map.get(name);
	}

	public String getUserId()
	{
		return getProperty(STRING_PROPERTY_USER_ID);
	}

	public void setEmail(String email)
	{
		setProperty(STRING_PROPERTY_EMAIL, email);
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber)
	{
		setProperty(STRING_PROPERTY_MOBILE_PHONE_NUMBER, mobilePhoneNumber);
	}

	public void setNickName(String nickName)
	{
		setProperty(STRING_PROPERTY_NICK_NAME, nickName);
	}

	public <T> void setProperty(String name, T value)
	{
		_map.put(name, value);
	}

	public void setUserId(String userId)
	{
		setProperty(STRING_PROPERTY_USER_ID, userId);
	}

	@Override
	public String toString()
	{
		return _map.toString();
	}

	@Override
	public String[] getPropertyNames()
	{
		return _map.keySet().toArray(new String[0]);
	}
}