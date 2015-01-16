package org.openwebflow.identity;

public interface UserDetailsEntity
{
	static String STRING_PROPERTY_EMAIL = "STRING_PROPERTY_EMAIL";

	static String STRING_PROPERTY_MOBILE_PHONE_NUMBER = "STRING_PROPERTY_MOBILE_PHONE_NUMBER";

	static String STRING_PROPERTY_NICK_NAME = "STRING_PROPERTY_NICK_NAME";

	static String STRING_PROPERTY_USER_ID = "STRING_PROPERTY_USER_ID";

	public <T> T getProperty(String name);

	String[] getPropertyNames();

	String getUserId();

	public <T> void setProperty(String name, T value);
}