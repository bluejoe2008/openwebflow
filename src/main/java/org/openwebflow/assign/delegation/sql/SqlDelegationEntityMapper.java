package org.openwebflow.assign.delegation.sql;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface SqlDelegationEntityMapper
{
	@Select("SELECT * FROM DELEGATION_TAB where DELEGATED=#{delegated}")
	@Results(value = { @Result(property = "opTime", column = "OP_TIME") })
	List<SqlDelegationEntity> findByDelegated(@Param("delegated")
	String delegated);

	@Insert("INSERT INTO DELEGATION_TAB (DELEGATED,DELEGATE,OP_TIME) values (#{delegated},#{delegate},#{opTime})")
	void saveDelegation(DelegationDetails sde);

	@Delete("DELETE from DELEGATION_TAB")
	public void deleteAll();

	@Select("SELECT * FROM DELEGATION_TAB")
	@Results(value = { @Result(property = "opTime", column = "OP_TIME") })
	List<SqlDelegationEntity> selectAll();
}
