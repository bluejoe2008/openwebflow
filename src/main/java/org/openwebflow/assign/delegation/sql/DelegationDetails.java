package org.openwebflow.assign.delegation.sql;

public class DelegationDetails
{
	String _delegated;

	public DelegationDetails()
	{
	}

	public DelegationDetails(String delegated, String delegate)
	{
		_delegated = delegated;
		_delegate = delegate;
	}

	String _delegate;

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DelegationDetails other = (DelegationDetails) obj;
		if (_delegate == null)
		{
			if (other._delegate != null)
				return false;
		}
		else if (!_delegate.equals(other._delegate))
			return false;
		if (_delegated == null)
		{
			if (other._delegated != null)
				return false;
		}
		else if (!_delegated.equals(other._delegated))
			return false;
		return true;
	}

	public String getDelegate()
	{
		return _delegate;
	}

	public String getDelegated()
	{
		return _delegated;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_delegate == null) ? 0 : _delegate.hashCode());
		result = prime * result + ((_delegated == null) ? 0 : _delegated.hashCode());
		return result;
	}

	public void setDelegate(String delegates)
	{
		_delegate = delegates;
	}

	public void setDelegated(String delegated)
	{
		_delegated = delegated;
	}

}