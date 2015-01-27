package org.openwebflow.cfg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.openwebflow.util.ModelUtils;

public class ImportDefinedProcessModels implements StartEngineEventListener
{
	File _modelDir;

	@Override
	public void afterStartEngine(ProcessEngineConfigurationImpl conf, ProcessEngine processEngine) throws Exception
	{
		checkAndImportNewModels(processEngine.getRepositoryService());
	}

	@Override
	public void beforeStartEngine(ProcessEngineConfigurationImpl conf)
	{
	}

	public void checkAndImportNewModels(RepositoryService repositoryService) throws IOException, XMLStreamException
	{
		List<File> newModelFiles = checkNewModelNames(repositoryService);
		for (File modelFile : newModelFiles)
		{
			ModelUtils.importModel(repositoryService, modelFile);
		}
	}

	private List<File> checkNewModelNames(RepositoryService repositoryService) throws FileNotFoundException
	{
		if (!_modelDir.exists())
			throw new FileNotFoundException(_modelDir.getPath());

		File[] files = _modelDir.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.endsWith(".bpmn");
			}
		});

		List<File> newModelFiles = new ArrayList<File>();
		if (files != null)
		{
			for (File file : files)
			{
				if (!exists(repositoryService, file.getName()))
				{
					newModelFiles.add(file);
				}
			}
		}

		return newModelFiles;
	}

	public boolean exists(RepositoryService repositoryService, String name)
	{
		return repositoryService.createModelQuery().modelKey(name).count() != 0;
	}

	public File getModelDir()
	{
		return _modelDir;
	}

	public void setModelDir(File modelDir)
	{
		_modelDir = modelDir;
	}
}
