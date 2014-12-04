package org.openwebflow.identity.sql;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openwebflow.identity.IdentityUserDetails;

public interface SqlUserDetailsMapper
{
	@Select("SELECT * FROM USER_TAB where USERID=#{userId}")
	IdentityUserDetails findUserById(@Param("userId")
	String userId);

	@Insert("INSERT INTO USER_TAB (USERID,EMAIL,NICKNAME,MOBILEPHONENUMBER) values (#{userId},#{email},#{nickName},#{mobilePhoneNumber})")
	void saveUser(IdentityUserDetails userDetails);

	@Delete("DELETE from USER_TAB")
	void deleteAll();
}
