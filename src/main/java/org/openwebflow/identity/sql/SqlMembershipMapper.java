package org.openwebflow.identity.sql;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SqlMembershipMapper
{
	@Select("SELECT * FROM OWF_MEMBERSHIP where USERID=#{userId}")
	List<SqlMembershipEntity> findMembershipsByUserId(@Param("userId")
	String userId);

	@Select("SELECT * FROM OWF_MEMBERSHIP where GROUPID=#{groupId}")
	List<SqlMembershipEntity> findMembershipsByGroupId(@Param("groupId")
	String groupId);

	@Insert("INSERT INTO OWF_MEMBERSHIP (USERID,GROUPID) values (#{userId},#{groupId})")
	void saveMembership(SqlMembershipEntity mse);

	@Delete("DELETE from OWF_MEMBERSHIP")
	void deleteAll();
}
