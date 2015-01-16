package org.openwebflow.parts.mybatis.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.openwebflow.identity.UserDetailsEntity;

public interface SqlUserDetailsEntityMapper
{
	@Delete("DELETE from OWF_USER")
	void deleteAll();

	@Select("SELECT * FROM OWF_USER where USER_ID=#{userId}")
	@Results(value = { @Result(property = "userId", column = "USER_ID"), @Result(property = "email", column = "EMAIL"),
			@Result(property = "nickName", column = "NICK_NAME"),
			@Result(property = "mobilePhoneNumber", column = "MOBILE_PHONE_NUMBER") })
	UserDetailsEntity findUserDetailsById(@Param("userId") String userId);

	@Insert("INSERT INTO OWF_USER (USER_ID,EMAIL,NICK_NAME,MOBILE_PHONE_NUMBER) values (#{userId},#{email},#{nickName},#{mobilePhoneNumber})")
	void saveUserDetails(UserDetailsEntity userDetails);
}
