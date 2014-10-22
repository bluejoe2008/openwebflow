package org.openwebflow.mvc;

public class VacationRequest
{
	long _id;

	public long getId()
	{
		return _id;
	}

	public void setId(long id)
	{
		_id = id;
	}

	public long getDays()
	{
		return _days;
	}

	public void setDays(long days)
	{
		_days = days;
	}

	public String getMotivation()
	{
		return _motivation;
	}

	public void setMotivation(String motivation)
	{
		_motivation = motivation;
	}

	public String getProcessId()
	{
		return _processId;
	}

	public void setProcessId(String processId)
	{
		_processId = processId;
	}

	long _days;

	String _motivation;

	String _processId;
}
