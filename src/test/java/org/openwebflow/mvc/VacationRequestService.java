package org.openwebflow.mvc;

public interface VacationRequestService
{
	public void insert(VacationRequest vr);

	public VacationRequest getByProcessId(String processId);
}