package org.openwebflow.parts.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openwebflow.parts.mybatis.entity.SqlMembershipEntity;

public interface SqlMembershipEntityMapper
{
	@Delete("DELETE from OWF_MEMBERSHIP")
	void deleteAll();

	@Select("SELECT * FROM OWF_MEMBERSHIP where GROUPID=#{groupId}")
	List<SqlMembershipEntity> findMembershipsByGroupId(@Param("groupId")
	String groupId);

	@Select("SELECT * FROM OWF_MEMBERSHIP where USERID=#{userId}")
	List<SqlMembershipEntity> findMembershipsByUserId(@Param("userId")
	String userId);

	@Insert("INSERT INTO OWF_MEMBERSHIP (USERID,GROUPID) values (#{userId},#{groupId})")
	void saveMembership(SqlMembershipEntity mse);
}
