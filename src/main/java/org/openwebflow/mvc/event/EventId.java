package org.openwebflow.mvc.event;

public class EventId
{
	private String _eventKey;

	private EventType _eventType;

	public EventId(EventType eventType, String eventKey)
	{
		_eventKey = eventKey;
		_eventType = eventType;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventId other = (EventId) obj;
		if (_eventType != other._eventType)
			return false;
		if (_eventKey == null)
		{
			if (other._eventKey != null)
				return false;
		}
		else if (!_eventKey.equals(other._eventKey))
			return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_eventType == null) ? 0 : _eventType.hashCode());
		result = prime * result + ((_eventKey == null) ? 0 : _eventKey.hashCode());
		return result;
	}

}
