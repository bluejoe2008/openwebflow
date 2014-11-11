package org.openwebflow.permission.list;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

public class SqlTaskAssignementEntryStore implements TaskAssignementEntryManager
{
	class MyRowMapper implements RowMapper
	{
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			TaskAssignmentEntryImpl ap = new TaskAssignmentEntryImpl();
			ap.setId(rs.getLong("ID"));
			ap.setProcessDefinitionId(rs.getString("PROCESS_DEF_ID"));
			ap.setActivityKey(rs.getString("ACTIVITY_KEY"));
			ap.setAssignee(rs.getString("ASSIGNED_USER"));
			ap.setGrantedGroupIds(StringUtils.delimitedListToStringArray(rs.getString("GRANTED_GROUPS"), ";"));
			ap.setGrantedUserIds(StringUtils.delimitedListToStringArray(rs.getString("GRANTED_USERS"), ";"));
			ap.setOpTime(rs.getDate("OP_TIME"));
			return ap;
		}
	}

	private DataSource _dataSource;

	public DataSource getDataSource()
	{
		return _dataSource;
	}

	public void save(TaskAssignmentEntryImpl ap) throws Exception
	{
		//先删除
		new JdbcTemplate(_dataSource).update("delete from ACTIVITY_ACL_TAB where PROCESS_DEF_ID=? and ACTIVITY_KEY=?",
			new Object[] { ap.getProcessDefinitionId(), ap.getActivityKey() });
		//再增加
		new JdbcTemplate(_dataSource)
				.update(
					"insert into ACTIVITY_ACL_TAB (ID,PROCESS_DEF_ID,ACTIVITY_KEY,ASSIGNED_USER,GRANTED_GROUPS,GRANTED_USERS,OP_TIME) values (?,?,?,?,?,?,?)",
					new Object[] { System.currentTimeMillis(), ap.getProcessDefinitionId(), ap.getActivityKey(),
							ap.getAssignee(), StringUtils.arrayToDelimitedString(ap.getGrantedGroupIds(), ";"),
							StringUtils.arrayToDelimitedString(ap.getGrantedUserIds(), ";"),
							new Date(System.currentTimeMillis()) });
	}

	public void setDataSource(DataSource dataSource)
	{
		_dataSource = dataSource;
	}

	@Override
	public TaskAssignementEntry load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove)
	{
		if (!addOrRemove)
			return null;

		return new JdbcTemplate(_dataSource).queryForObject(
			"select * from ACTIVITY_ACL_TAB where PROCESS_DEF_ID=? and ACTIVITY_KEY=?", new Object[] {
					processDefinitionId, taskDefinitionKey }, new MyRowMapper());
	}
}
