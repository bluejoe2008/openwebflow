package org.openwebflow.parts.mybatis.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openwebflow.identity.UserDetailsEntity;

public interface SqlUserDetailsEntityMapper
{
	@Delete("DELETE from OWF_USER")
	void deleteAll();

	@Select("SELECT * FROM OWF_USER where USERID=#{userId}")
	UserDetailsEntity findUserDetailsById(@Param("userId")
	String userId);

	@Insert("INSERT INTO OWF_USER (USERID,EMAIL,NICKNAME,MOBILEPHONENUMBER) values (#{userId},#{email},#{nickName},#{mobilePhoneNumber})")
	void saveUserDetails(UserDetailsEntity userDetails);
}
