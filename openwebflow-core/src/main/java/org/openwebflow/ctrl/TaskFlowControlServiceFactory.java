package org.openwebflow.ctrl;

public interface TaskFlowControlServiceFactory
{

	TaskFlowControlService create(String processId);

}