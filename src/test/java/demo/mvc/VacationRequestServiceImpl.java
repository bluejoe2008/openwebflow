package demo.mvc;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class VacationRequestServiceImpl implements VacationRequestService
{
	@Resource(name = "bussinessDataSource")
	DataSource _bussinessDataSource;

	@Override
	public VacationRequest getByProcessId(String processId)
	{
		return new JdbcTemplate(_bussinessDataSource).queryForObject(
			"select * from VACATION_REQUEST_TAB where PROCESSID=?", new Object[] { processId }, new RowMapper()
			{

				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					VacationRequest vr = new VacationRequest();
					vr.setId(rs.getLong("ID"));
					vr.setDays(rs.getLong("DAYS"));
					vr.setMotivation(rs.getString("MOTIVATION"));
					vr.setProcessId(rs.getString("PROCESSID"));

					return vr;
				}
			});
	}

	@Override
	public void insert(VacationRequest vr)
	{
		new JdbcTemplate(_bussinessDataSource).update(
			"insert into VACATION_REQUEST_TAB (ID,DAYS,MOTIVATION,PROCESSID) values (?,?,?,?)", vr.getId(),
			vr.getDays(), vr.getMotivation(), vr.getProcessId());
	}
}
