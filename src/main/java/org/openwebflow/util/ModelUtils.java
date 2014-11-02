package org.openwebflow.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class ModelUtils
{
	public static Deployment deployModel(RepositoryService repositoryService, String modelId) throws IOException
	{
		Model modelData = repositoryService.getModel(modelId);
		ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService
				.getModelEditorSource(modelData.getId()));
		byte[] bpmnBytes = null;

		BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		bpmnBytes = new BpmnXMLConverter().convertToXML(model);

		String processName = modelData.getName() + ".bpmn20.xml";
		Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
				.addString(processName, new String(bpmnBytes, "utf-8")).deploy();

		modelData.setDeploymentId(deployment.getId());
		repositoryService.saveModel(modelData);

		return deployment;
	}

	public static Model createNewModel(RepositoryService repositoryService, String name, String description)
			throws IOException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilSetNode);
		Model modelData = repositoryService.newModel();

		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);

		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		modelData.setMetaInfo(modelObjectNode.toString());
		modelData.setName(name);

		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
		return modelData;
	}

	public static void importModel(RepositoryService repositoryService, File modelFile) throws IOException,
			XMLStreamException
	{
		BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in = new InputStreamReader(new FileInputStream(modelFile), "utf-8");
		XMLStreamReader xtr = xif.createXMLStreamReader(in);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
		xmlConverter.convertToBpmnModel(xtr);

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

		BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
		ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);

		repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
		Logger.getLogger(ModelUtils.class)
				.info(String.format("importing model file: %s", modelFile.getCanonicalPath()));
	}
}
