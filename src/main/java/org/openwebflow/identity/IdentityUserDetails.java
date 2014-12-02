package org.openwebflow.identity;

public interface IdentityUserDetails
{
	String getUserId();

	public <T> void setProperty(String name, T value);

	public <T> T getProperty(String name);

	static String STRING_PROPERTY_USER_ID = "STRING_PROPERTY_USER_ID";

	static String STRING_PROPERTY_EMAIL = "STRING_PROPERTY_EMAIL";

	static String STRING_PROPERTY_NICK_NAME = "STRING_PROPERTY_NICK_NAME";

	static String STRING_PROPERTY_MOBILE_PHONE_NUMBER = "STRING_PROPERTY_MOBILE_PHONE_NUMBER";
}