package org.openwebflow.ioc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

public class BPMNDeploymentManager implements InitializingBean, ServletContextAware
{
	File _modelDir;

	String _modelDirPath;

	private ProcessEngine _processEngine;

	@Override
	public void afterPropertiesSet() throws Exception
	{
		deployNewModels();
	}

	private List<String> checkNewModelNames()
	{
		String[] names = _modelDir.list(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.endsWith(".bpmn");
			}
		});

		RepositoryService repositoryService = _processEngine.getRepositoryService();
		List<String> newModelNames = new ArrayList<String>();
		for (String name : names)
		{
			if (!exists(repositoryService, name))
			{
				newModelNames.add(name);
			}
		}

		return newModelNames;
	}

	public void deployModel(String bpmn) throws FileNotFoundException
	{
		RepositoryService repositoryService = _processEngine.getRepositoryService();
		// 部署流程定义
		repositoryService.createDeployment().name(bpmn)
				.addInputStream("vacation.bpmn", new FileInputStream(new File(_modelDir, bpmn))).deploy();
	}

	void deployNewModels() throws FileNotFoundException
	{
		List<String> newModelNames = checkNewModelNames();
		for (String modelName : newModelNames)
		{
			deployModel(modelName);
		}
	}

	public boolean exists(RepositoryService repositoryService, String name)
	{
		return repositoryService.createDeploymentQuery().deploymentName(name).count() != 0;
	}

	public String getModelDirPath()
	{
		return _modelDirPath;
	}

	public ProcessEngine getProcessEngine()
	{
		return _processEngine;
	}

	public void setModelDirPath(String modelDirPath)
	{
		_modelDirPath = modelDirPath;
	}

	public void setProcessEngine(ProcessEngine processEngine)
	{
		_processEngine = processEngine;
	}

	@Override
	public void setServletContext(ServletContext servletContext)
	{
		_modelDir = new File(servletContext.getRealPath(_modelDirPath));
	}
}
