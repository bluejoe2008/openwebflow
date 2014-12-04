package org.openwebflow.alarm.sql;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface SqlNotificationDetailsMapper
{
	@Select("SELECT * FROM NOTIFICATION_TAB where TASKID=#{taskId}")
	@Results(value = { @Result(property = "opTime", column = "OP_TIME") })
	List<SqlNotificationDetails> findByTaskId(@Param("taskId")
	String taskId);

	@Insert("INSERT INTO NOTIFICATION_TAB (TASKID,OPTIME) values (#{taskId},#{opTime})")
	void saveNotificationDetails(SqlNotificationDetails sde);

	@Delete("DELETE from NOTIFICATION_TAB")
	public void deleteAll();
}
