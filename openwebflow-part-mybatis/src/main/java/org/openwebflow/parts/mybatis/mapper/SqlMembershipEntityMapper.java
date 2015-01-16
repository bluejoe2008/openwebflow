package org.openwebflow.parts.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.openwebflow.parts.mybatis.entity.SqlMembershipEntity;

public interface SqlMembershipEntityMapper
{
	@Delete("DELETE from OWF_MEMBERSHIP")
	void deleteAll();

	@Select("SELECT * FROM OWF_MEMBERSHIP where GROUP_ID=#{groupId}")
	List<SqlMembershipEntity> findMembershipsByGroupId(@Param("groupId") String groupId);

	@Results(value = { @Result(property = "userId", column = "USER_ID"),
			@Result(property = "groupId", column = "GROUP_ID") })
	@Select("SELECT * FROM OWF_MEMBERSHIP where USER_ID=#{userId}")
	List<SqlMembershipEntity> findMembershipsByUserId(@Param("userId") String userId);

	@Insert("INSERT INTO OWF_MEMBERSHIP (USER_ID,GROUP_ID) values (#{userId},#{groupId})")
	void saveMembership(SqlMembershipEntity mse);
}
