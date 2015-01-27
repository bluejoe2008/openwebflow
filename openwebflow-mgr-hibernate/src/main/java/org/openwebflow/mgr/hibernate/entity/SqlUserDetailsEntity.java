package org.openwebflow.mgr.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.openwebflow.identity.UserDetailsEntity;
import org.openwebflow.mgr.common.UserDetailsEntitySupport;

@Entity
@Table(name = "OWF_USER")
public class SqlUserDetailsEntity extends UserDetailsEntitySupport
{
	public SqlUserDetailsEntity()
	{
	}

	public SqlUserDetailsEntity(UserDetailsEntity src)
	{
		super.copyProperties(src);
	}

	@Column(name = "EMAIL")
	String _email;

	@Column(name = "NICK_NAME")
	String _nickName;

	@Column(name = "MOBILE_PHONE_NUMBER")
	String _phoneNumber;

	@Column(name = "USER_ID")
	@Id
	String _userId;

	public String getEmail()
	{
		return _email;
	}

	public String getNickName()
	{
		return _nickName;
	}

	public String getPhoneNumber()
	{
		return _phoneNumber;
	}

	@Override
	public <T> T getProperty(String name)
	{
		if (UserDetailsEntity.STRING_PROPERTY_EMAIL.equals(name))
			return (T) _email;

		if (UserDetailsEntity.STRING_PROPERTY_MOBILE_PHONE_NUMBER.equals(name))
			return (T) _phoneNumber;

		if (UserDetailsEntity.STRING_PROPERTY_NICK_NAME.equals(name))
			return (T) _nickName;

		if (UserDetailsEntity.STRING_PROPERTY_USER_ID.equals(name))
			return (T) _userId;

		return null;
	}

	@Override
	public String[] getPropertyNames()
	{
		return new String[] { UserDetailsEntity.STRING_PROPERTY_EMAIL,
				UserDetailsEntity.STRING_PROPERTY_MOBILE_PHONE_NUMBER, UserDetailsEntity.STRING_PROPERTY_NICK_NAME,
				UserDetailsEntity.STRING_PROPERTY_USER_ID };
	}

	@Override
	public String getUserId()
	{
		return _userId;
	}

	public void setEmail(String email)
	{
		_email = email;
	}

	public void setNickName(String nickName)
	{
		_nickName = nickName;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		_phoneNumber = phoneNumber;
	}

	@Override
	public <T> void setProperty(String name, T value)
	{
		if (UserDetailsEntity.STRING_PROPERTY_EMAIL.equals(name))
			_email = (String) value;

		if (UserDetailsEntity.STRING_PROPERTY_MOBILE_PHONE_NUMBER.equals(name))
			_phoneNumber = (String) value;

		if (UserDetailsEntity.STRING_PROPERTY_NICK_NAME.equals(name))
			_nickName = (String) value;

		if (UserDetailsEntity.STRING_PROPERTY_USER_ID.equals(name))
			_userId = (String) value;
	}

	public void setUserId(String userId)
	{
		_userId = userId;
	}

}
