package demo.mvc;

public interface VacationRequestService
{
	public VacationRequest getByProcessId(String processId);

	public void insert(VacationRequest vr);
}