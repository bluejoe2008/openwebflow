package org.openwebflow.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.stream.XMLStreamException;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.log4j.Logger;
import org.h2.util.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class ModelUtils
{
	private static String EMPTY_MODEL_XML = "";

	private static String EMPTY_MODEL_XML_PATH = "empty-model.bpmn";

	static
	{
		Resource defaultModelXmlResource = new ClassPathResource(EMPTY_MODEL_XML_PATH);
		try
		{
			EMPTY_MODEL_XML = IOUtils.readStringAndClose(
				new InputStreamReader(defaultModelXmlResource.getInputStream()),
				(int) defaultModelXmlResource.contentLength());
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static Deployment deployModel(RepositoryService repositoryService, String modelId) throws IOException
	{
		Model modelData = repositoryService.getModel(modelId);
		//EditorSource就是XML格式的
		byte[] bpmnBytes = repositoryService.getModelEditorSource(modelId);

		String processName = modelData.getName() + ".bpmn20.xml";
		Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
				.addString(processName, new String(bpmnBytes, "utf-8")).deploy();

		//设置部署ID
		modelData.setDeploymentId(deployment.getId());
		repositoryService.saveModel(modelData);

		return deployment;
	}

	public static Model createNewModel(RepositoryService repositoryService, String name, String description)
			throws IOException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		Model modelData = repositoryService.newModel();

		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);

		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		modelData.setMetaInfo(modelObjectNode.toString());
		modelData.setName(name);

		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(), EMPTY_MODEL_XML.getBytes("utf-8"));
		return modelData;
	}

	public static void importModel(RepositoryService repositoryService, File modelFile) throws IOException,
			XMLStreamException
	{
		InputStreamReader reader = new InputStreamReader(new FileInputStream(modelFile), "utf-8");
		String fileContent = IOUtils.readStringAndClose(reader, (int) modelFile.length());

		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(new StringStreamSourceEx(fileContent), false,
			false);

		String processName = bpmnModel.getMainProcess().getName();
		if (processName == null || processName.isEmpty())
		{
			processName = bpmnModel.getMainProcess().getId();
		}

		Model modelData = repositoryService.newModel();
		ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processName);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		modelData.setMetaInfo(modelObjectNode.toString());
		modelData.setName(processName);
		modelData.setKey(modelFile.getName());

		repositoryService.saveModel(modelData);

		repositoryService.addModelEditorSource(modelData.getId(), fileContent.getBytes("utf-8"));
		Logger.getLogger(ModelUtils.class)
				.info(String.format("importing model file: %s", modelFile.getCanonicalPath()));
	}
}
