package org.openwebflow.permission.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.openwebflow.permission.ActivityPermission;
import org.openwebflow.permission.ActivityPermissionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class SqlActivityPermissionManager implements ActivityPermissionManager
{
	class MyRowMapper implements RowMapper
	{
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			ActivityPermissionImpl ap = new ActivityPermissionImpl();
			ap.setId(rs.getLong("ID"));
			ap.setProcessDefId(rs.getString("PROCESS_DEF_ID"));
			ap.setActivityId(rs.getString("ACTIVITY_ID"));
			ap.setAssignedUser(rs.getString("ASSIGNED_USER"));
			ap.setGrantedGroups(rs.getString("GRANTED_GROUPS"));
			ap.setGrantedUsers(rs.getString("GRANTED_USERS"));
			ap.setOpTime(rs.getDate("OP_TIME"));
			return ap;
		}
	}

	private DataSource _dataSource;

	public DataSource getDataSource()
	{
		return _dataSource;
	}

	public void setDataSource(DataSource dataSource)
	{
		_dataSource = dataSource;
	}

	@Override
	public List<ActivityPermission> loadAll()
	{
		return new JdbcTemplate(_dataSource).query("select * from ACTIVITY_PERMISSION_TAB", new MyRowMapper());
	}

	@Override
	public ActivityPermission loadById(String processDefId, String activityId)
	{
		return new JdbcTemplate(_dataSource).queryForObject(
			"select * from ACTIVITY_PERMISSION_TAB where PROCESS_DEF_ID=? and ACTIVITY_ID=?", new Object[] {
					processDefId, activityId }, new MyRowMapper()
			{
			});
	}

	public void save(ActivityPermission ap) throws Exception
	{
		//先删除
		new JdbcTemplate(_dataSource).update(
			"delete from ACTIVITY_PERMISSION_TAB where PROCESS_DEF_ID=? and ACTIVITY_ID=?",
			new Object[] { ap.getProcessDefId(), ap.getActivityId() });
		//再增加
		new JdbcTemplate(_dataSource)
				.update(
					"insert into ACTIVITY_PERMISSION_TAB (ID,PROCESS_DEF_ID,ACTIVITY_ID,ASSIGNED_USER,GRANTED_GROUPS,GRANTED_USERS,OP_TIME) values (?,?,?,?,?,?,?)",
					new Object[] { System.currentTimeMillis(), ap.getProcessDefId(), ap.getActivityId(),
							ap.getAssignedUser(), ap.getGrantedGroups(), ap.getGrantedUsers(),
							new Date(System.currentTimeMillis()) });
	}
}
