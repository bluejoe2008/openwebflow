package org.openwebflow.permission.delegation;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

public class SqlDelegationDetailsStore implements DelegationDetailsManager
{
	DataSource _dataSource;

	public DataSource getDataSource()
	{
		return _dataSource;
	}

	public void setDataSource(DataSource dataSource)
	{
		_dataSource = dataSource;
	}

	public void update(String delegated, String[] delegates)
	{
		//先删除
		new JdbcTemplate(_dataSource)
				.update("delete from DELEGATION_TAB where DELEGATED=?", new Object[] { delegated });
		//再增加
		new JdbcTemplate(_dataSource).update(
			"insert into DELEGATION_TAB (ID,DELEGATED,DELEGATES,OP_TIME) values (?,?,?,?)",
			new Object[] { System.currentTimeMillis(), delegated, StringUtils.arrayToDelimitedString(delegates, ";"),
					new Date(System.currentTimeMillis()) });
	}

	@Override
	public String[] getDelegates(String delegated)
	{
		return new JdbcTemplate(_dataSource).queryForObject("select * from DELEGATION_TAB where DELEGATED=?",
			new Object[] { delegated }, new RowMapper<String[]>()
			{

				@Override
				public String[] mapRow(ResultSet rs, int arg1) throws SQLException
				{
					return StringUtils.delimitedListToStringArray(rs.getString("DELEGATES"), ";");
				}
			});
	}

}
