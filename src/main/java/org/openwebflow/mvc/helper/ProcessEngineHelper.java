package org.openwebflow.mvc.helper;

import java.util.Map;

import org.activiti.engine.ProcessEngine;

public interface ProcessEngineHelper
{

	Map<String, Object> getActiveProcessVariables(String processId);

	Map<String, Object> getHistoricProcessVariables(String processId);

	ProcessEngine getProcessEngine();

}