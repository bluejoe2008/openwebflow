package org.openwebflow.mgr.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.openwebflow.assign.delegation.DelegationEntity;
import org.openwebflow.mgr.mybatis.entity.SqlDelegationEntity;

public interface SqlDelegationEntityMapper
{
	@Delete("DELETE from OWF_DELEGATION")
	public void deleteAll();

	@Select("SELECT * FROM OWF_DELEGATION where DELEGATED=#{delegated}")
	@Results(value = { @Result(property = "opTime", column = "OP_TIME") })
	List<SqlDelegationEntity> findByDelegated(@Param("delegated")
	String delegated);

	@Select("SELECT * FROM OWF_DELEGATION")
	@Results(value = { @Result(property = "opTime", column = "OP_TIME") })
	List<SqlDelegationEntity> list();

	@Insert("INSERT INTO OWF_DELEGATION (DELEGATED,DELEGATE,OP_TIME) values (#{delegated},#{delegate},#{opTime})")
	void saveDelegation(DelegationEntity sde);
}
