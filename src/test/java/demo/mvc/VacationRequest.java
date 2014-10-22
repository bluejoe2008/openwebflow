package demo.mvc;

public class VacationRequest
{
	long _days;

	long _id;

	String _motivation;

	String _processId;

	public long getDays()
	{
		return _days;
	}

	public long getId()
	{
		return _id;
	}

	public String getMotivation()
	{
		return _motivation;
	}

	public String getProcessId()
	{
		return _processId;
	}

	public void setDays(long days)
	{
		_days = days;
	}

	public void setId(long id)
	{
		_id = id;
	}

	public void setMotivation(String motivation)
	{
		_motivation = motivation;
	}

	public void setProcessId(String processId)
	{
		_processId = processId;
	}
}
