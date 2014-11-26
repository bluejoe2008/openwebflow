package org.openwebflow.tool;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openwebflow.conf.ProcessEngineConfigurationEx;
import org.openwebflow.conf.ReplaceTaskAssignmentManager;
import org.openwebflow.identity.impl.InMemoryMembershipStore;
import org.openwebflow.identity.impl.InMemoryUserDetailsStore;
import org.openwebflow.identity.impl.MyUserDetails;
import org.openwebflow.permission.delegation.InMemoryDelegationDetailsStore;
import org.openwebflow.permission.delegation.TaskDelagation;
import org.openwebflow.permission.list.InMemoryTaskAssignementEntryStore;
import org.openwebflow.util.ModelUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProcessEngineToolTest
{
	ProcessEngineTool _tool;

	ProcessEngine _processEngine;

	ApplicationContext _ctx;

	InMemoryTaskAssignementEntryStore _myTaskAssignmentManager;

	@Before
	public void setUp() throws Exception
	{
		_ctx = new ClassPathXmlApplicationContext("classpath:activiti.cfg.xml");
		_tool = _ctx.getBean(ProcessEngineTool.class);
		Assert.assertNotNull(_tool);
		_processEngine = _tool.getProcessEngine();

		_myTaskAssignmentManager = _ctx.getBean(InMemoryTaskAssignementEntryStore.class);

		//用户关系管理
		if (!_ctx.getBeansOfType(InMemoryMembershipStore.class).isEmpty())
		{
			InMemoryMembershipStore myMembershipManager = _ctx.getBean(InMemoryMembershipStore.class);
			//设置用户
			myMembershipManager.createGroup("management", "管理员");
			myMembershipManager.createGroup("sales", "销售");
			myMembershipManager.createGroup("engineering", "工程师");

			myMembershipManager.createMembership("bluejoe", "engineering");
			myMembershipManager.createMembership("gonzo", "sales");
			myMembershipManager.createMembership("kermit", "management");
		}

		//设置用户email等信息
		if (!_ctx.getBeansOfType(InMemoryUserDetailsStore.class).isEmpty())
		{
			InMemoryUserDetailsStore userDetailsStore = _ctx.getBean(InMemoryUserDetailsStore.class);
			userDetailsStore.add(new MyUserDetails("bluejoe", "白乔", "bluejoe2008@gmail.com", "13800138000"));
		}
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test() throws Exception
	{
		// 取model，该model会自动注册
		RepositoryService repositoryService = _processEngine.getRepositoryService();
		Model model = repositoryService.createModelQuery().modelKey("test.bpmn").singleResult();
		//确定已注册
		Assert.assertNotNull(model);
		//由于没有部署，应该拿不到
		Assert.assertEquals(0, repositoryService.createProcessDefinitionQuery().list().size());

		//部署该model
		ModelUtils.deployModel(repositoryService, model.getId());
		//现在应该拿得到了
		Assert.assertEquals(1, repositoryService.createProcessDefinitionQuery().list().size());
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().singleResult();
		Assert.assertNotNull(pd);
		String processDefId = pd.getId();

		// 启动流程实例
		ProcessInstance instance = _processEngine.getRuntimeService().startProcessInstanceByKey(pd.getKey());
		Assert.assertNotNull(instance);

		TaskService taskService = _processEngine.getTaskService();
		//会自动跳转到第一个task
		//management可以访问该task
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateGroup("management").count());
		//engineering不可以访问该task
		Assert.assertEquals(0, taskService.createTaskQuery().taskCandidateGroup("engineering").count());
		//kermit属于management
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateUser("kermit").count());
		Assert.assertEquals(0, taskService.createTaskQuery().taskAssignee("kermit").count());
		Assert.assertEquals(0, taskService.createTaskQuery().taskCandidateUser("bluejoe").count());

		//允许step2可以让engineering操作
		_myTaskAssignmentManager.addEntry(processDefId, "step2", null, new String[] { "engineering" }, new String[0]);

		//对现已执行的task没有影响
		Assert.assertEquals(0, taskService.createTaskQuery().taskCandidateGroup("engineering").count());
		//删除掉该流程
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");

		//再启动一个流程
		instance = _processEngine.getRuntimeService().startProcessInstanceByKey(pd.getKey());
		//engineering应该可以执行任务了
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateGroup("engineering").count());
		//management不可以执行任务
		Assert.assertEquals(0, taskService.createTaskQuery().taskCandidateGroup("management").count());

		//删掉流程实例
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");

		//允许step2可以让engineering和management操作，允许neo操作
		_myTaskAssignmentManager.addEntry(processDefId, "step2", null, new String[] { "engineering", "management" },
			new String[] { "neo" });

		//再启动一个流程
		instance = _processEngine.getRuntimeService().startProcessInstanceByKey(pd.getKey());
		//engineering应该可以执行任务
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateGroup("engineering").count());
		//management应该可以执行任务
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateGroup("management").count());
		//bluejoe属于engineering
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateUser("bluejoe").count());
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateUser("kermit").count());
		//neo也可以的
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateUser("neo").count());

		//删掉流程
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");

		//代理关系
		if (!_ctx.getBeansOfType(InMemoryDelegationDetailsStore.class).isEmpty())
		{
			InMemoryDelegationDetailsStore imdm = _ctx.getBean(InMemoryDelegationDetailsStore.class);
			imdm.addDelegation("neo", "alex");
		}

		//再启动一个流程
		instance = _processEngine.getRuntimeService().startProcessInstanceByKey(pd.getKey());
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateUser("neo").count());
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateUser("alex").count());
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");

		//设置屏蔽被代理人
		((TaskDelagation) (((ReplaceTaskAssignmentManager) (((ProcessEngineConfigurationEx) _processEngine
				.getProcessEngineConfiguration()).getStartEngineEventListeners().get(2))).getAssignmentHandlers()
				.get(1))).setHideDelegated(true);

		//再启动一个流程
		instance = _processEngine.getRuntimeService().startProcessInstanceByKey(pd.getKey());
		//neo被屏蔽了
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateUser("alex").count());
		Assert.assertEquals(0, taskService.createTaskQuery().taskCandidateUser("neo").count());

		//等待催办
		Thread.sleep(60000);
	}
}
