package org.openwebflow.cfg;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;

public interface StartEngineEventListener
{

	void afterStartEngine(ProcessEngineConfigurationImpl conf, ProcessEngine processEngine) throws Exception;

	void beforeStartEngine(ProcessEngineConfigurationImpl conf) throws Exception;

}
