package org.openwebflow.permission.acl.sql;

import org.openwebflow.permission.acl.ActivityAclEntryImpl;
import org.springframework.util.StringUtils;

public class SqlActivityAclEntry extends ActivityAclEntryImpl
{
	private String _grantedGroupString;

	private String _grantedUserString;

	public String getGrantedGroupString()
	{
		return _grantedGroupString;
	}

	public String getGrantedUserString()
	{
		return _grantedUserString;
	}

	@Override
	public void setGrantedGroupIds(String[] grantedGroupIds)
	{
		super.setGrantedGroupIds(grantedGroupIds);
		setGrantedGroupString(StringUtils.arrayToDelimitedString(grantedGroupIds, ";"));
	}

	public void setGrantedGroupString(String grantedGroupString)
	{
		_grantedGroupString = grantedGroupString;
		super.setGrantedGroupIds(StringUtils.delimitedListToStringArray(grantedGroupString, ";"));
	}

	@Override
	public void setGrantedUserIds(String[] grantedUserIds)
	{
		super.setGrantedUserIds(grantedUserIds);
		setGrantedUserString(StringUtils.arrayToDelimitedString(grantedUserIds, ";"));
	}

	public void setGrantedUserString(String grantedUserString)
	{
		_grantedUserString = grantedUserString;
		super.setGrantedUserIds(StringUtils.delimitedListToStringArray(grantedUserString, ";"));
	}
}
