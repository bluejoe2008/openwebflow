package org.openwebflow.identity.impl;

import org.openwebflow.identity.IdentityUserDetails;

public class MyUserDetails implements IdentityUserDetails, HasEmail, HasMobilePhoneNumber, HasNickName
{
	private String _email;

	private String _mobilePhoneNumber;

	private String _nickName;

	private String _userId;

	public MyUserDetails(String userId, String nickName, String email, String mobilePhoneNumber)
	{
		super();
		_userId = userId;
		_nickName = nickName;
		_email = email;
		_mobilePhoneNumber = mobilePhoneNumber;
	}

	@Override
	public String getEmail()
	{
		return _email;
	}

	@Override
	public String getMobilePhoneNumber()
	{
		return _mobilePhoneNumber;
	}

	public String getNickName()
	{
		return _nickName;
	}

	public String getUserId()
	{
		return _userId;
	}

	public void setEmail(String email)
	{
		_email = email;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber)
	{
		_mobilePhoneNumber = mobilePhoneNumber;
	}

	public void setNickName(String nickName)
	{
		_nickName = nickName;
	}

	public void setUserId(String userId)
	{
		_userId = userId;
	}

	@Override
	public String toString()
	{
		return String.format("%s(%s)", _userId, _nickName);
	}
}