package org.openwebflow.identity;

public interface UserDetailsEntity
{
	/**
	 * EMAIL属性名
	 */
	static String STRING_PROPERTY_EMAIL = "STRING_PROPERTY_EMAIL";

	/**
	 * 手机号码属性名
	 */
	static String STRING_PROPERTY_MOBILE_PHONE_NUMBER = "STRING_PROPERTY_MOBILE_PHONE_NUMBER";

	/**
	 * 昵称属性名
	 */
	static String STRING_PROPERTY_NICK_NAME = "STRING_PROPERTY_NICK_NAME";

	/**
	 * 用户ID属性名
	 */
	static String STRING_PROPERTY_USER_ID = "STRING_PROPERTY_USER_ID";

	/**
	 * 获取指定属性的值
	 */
	public <T> T getProperty(String name);

	/**
	 * 获取所有的属性名
	 */
	String[] getPropertyNames();

	/**
	 * 获取用户的ID
	 */
	String getUserId();

	/**
	 * 设置指定属性的值
	 */
	public <T> void setProperty(String name, T value);
}