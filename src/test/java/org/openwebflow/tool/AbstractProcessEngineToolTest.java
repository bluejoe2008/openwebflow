package org.openwebflow.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openwebflow.alarm.impl.AbstractNotificationDetailsStore;
import org.openwebflow.conf.ProcessEngineConfigurationEx;
import org.openwebflow.conf.ReplaceTaskAssignmentManager;
import org.openwebflow.ctrl.TaskFlowControlService;
import org.openwebflow.identity.AbstractUserDetailsStore;
import org.openwebflow.identity.impl.AbstractMembershipStore;
import org.openwebflow.identity.impl.MyUserDetails;
import org.openwebflow.permission.acl.AbstractActivityAclStore;
import org.openwebflow.permission.delegation.AbstractDelegationStore;
import org.openwebflow.permission.delegation.TaskDelagation;
import org.openwebflow.util.ModelUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class AbstractProcessEngineToolTest
{
	ProcessEngineTool _tool;

	ProcessEngine _processEngine;

	ApplicationContext _ctx;

	AbstractActivityAclStore _aclStore;

	AbstractDelegationStore _delegationStore;

	ProcessDefinition _processDef;

	@Before
	public void setUp() throws Exception
	{
		_ctx = new ClassPathXmlApplicationContext(getConfigFilePath());
		_tool = _ctx.getBean(ProcessEngineTool.class);
		Assert.assertNotNull(_tool);
		_processEngine = _tool.getProcessEngine();

		_aclStore = (AbstractActivityAclStore) _ctx.getBean("myTaskActivityAclManager");
		_aclStore.removeAll();

		((AbstractNotificationDetailsStore) _ctx.getBean("myNotificationDetailsStore")).removeAll();

		//用户关系管理
		AbstractMembershipStore myMembershipManager = (AbstractMembershipStore) _ctx.getBean("myMembershipManager");
		myMembershipManager.removeAll();
		myMembershipManager.saveMembership("bluejoe", "engineering");
		myMembershipManager.saveMembership("gonzo", "sales");
		myMembershipManager.saveMembership("kermit", "management");

		//设置用户email等信息
		AbstractUserDetailsStore userDetailsStore = (AbstractUserDetailsStore) _ctx.getBean("myUserDetailsManager");
		userDetailsStore.removeAll();
		userDetailsStore.saveUser(new MyUserDetails("bluejoe", "白乔", "bluejoe2008@gmail.com", "13800138000"));
		userDetailsStore.saveUser(new MyUserDetails("kermit", "老黄", "bluejoe@cnic.cn", "13800138000"));

		//代理关系
		_delegationStore = (AbstractDelegationStore) _ctx.getBean("myDelegationDetailsManager");
		_delegationStore.removeAll();

		// 取model，该model会自动注册
		RepositoryService repositoryService = _processEngine.getRepositoryService();
		Model model = repositoryService.createModelQuery().modelKey("test.bpmn").singleResult();
		//部署该model
		if (repositoryService.createProcessDefinitionQuery().list().isEmpty())
		{
			ModelUtils.deployModel(repositoryService, model.getId());
		}

		_processDef = repositoryService.createProcessDefinitionQuery().singleResult();
	}

	protected abstract String getConfigFilePath();

	@After
	public void tearDown() throws Exception
	{
		_processEngine.close();
		_processEngine = null;
		((AbstractApplicationContext) _ctx).getBeanFactory().destroySingletons();
		_ctx = null;
		_processDef = null;
	}

	@Test
	public void testModelDeployment() throws Exception
	{
		// 取model，该model会自动注册
		RepositoryService repositoryService = _processEngine.getRepositoryService();
		Model model = repositoryService.createModelQuery().modelKey("vacation.bpmn").singleResult();
		//确定已注册
		Assert.assertNotNull(model);
		//由于没有部署，应该拿不到
		Assert.assertEquals(1, repositoryService.createProcessDefinitionQuery().list().size());

		//部署该model
		Deployment dep = ModelUtils.deployModel(repositoryService, model.getId());
		//现在应该拿得到了
		Assert.assertEquals(2, repositoryService.createProcessDefinitionQuery().list().size());

		//删除掉
		repositoryService.deleteDeployment(dep.getId(), true);
		//现在再取就拿不到了
		Assert.assertEquals(1, repositoryService.createProcessDefinitionQuery().list().size());
	}

	@Test
	public void testTaskSequence() throws Exception
	{
		//_processDef对应于vacationRequest流程，参见https://github.com/bluejoe2008/openwebflow/blob/master/models/test.bpmn
		ProcessInstance instance = _processEngine.getRuntimeService().startProcessInstanceByKey(_processDef.getKey());
		String instanceId = instance.getId();

		TaskService taskService = _processEngine.getTaskService();
		Task task1 = taskService.createTaskQuery().singleResult();
		Assert.assertEquals("step2", task1.getTaskDefinitionKey());

		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("vacationApproved", false);
		vars.put("numberOfDays", 10);
		vars.put("managerMotivation", "get sick");

		String taskId = taskService.createTaskQuery().taskCandidateUser("kermit").singleResult().getId();
		taskService.complete(taskId, vars);
		Task task2 = taskService.createTaskQuery().singleResult();
		Assert.assertEquals("adjustVacationRequestTask", task2.getTaskDefinitionKey());

		TaskFlowControlService tfcs = new TaskFlowControlService(_processEngine, instanceId);

		//跳回至 step2
		tfcs.jump("step2");
		Task task3 = taskService.createTaskQuery().singleResult();
		Assert.assertEquals("step2", task3.getTaskDefinitionKey());

		//确认权限都拷贝过来了
		//management可以访问该task
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateGroup("management").count());
		//engineering不可以访问该task
		Assert.assertEquals(0, taskService.createTaskQuery().taskCandidateGroup("engineering").count());

		//确认历史轨迹里已保存
		List<HistoricActivityInstance> activities = _processEngine.getHistoryService()
				.createHistoricActivityInstanceQuery().processInstanceId(instanceId).list();
		Assert.assertEquals(5, activities.size());
		Assert.assertEquals("step1", activities.get(0).getActivityId());
		Assert.assertEquals("step2", activities.get(1).getActivityId());
		Assert.assertEquals("requestApprovedDecision", activities.get(2).getActivityId());
		Assert.assertEquals("adjustVacationRequestTask", activities.get(3).getActivityId());
		Assert.assertEquals("step2", activities.get(4).getActivityId());

		//测试一下往前跳
		tfcs.jump("adjustVacationRequestTask");
		Task task4 = taskService.createTaskQuery().singleResult();
		Assert.assertEquals("adjustVacationRequestTask", task4.getTaskDefinitionKey());

		activities = _processEngine.getHistoryService().createHistoricActivityInstanceQuery()
				.processInstanceId(instanceId).list();
		Assert.assertEquals(6, activities.size());
		Assert.assertEquals("adjustVacationRequestTask", activities.get(5).getActivityId());
		_processEngine.getRuntimeService().deleteProcessInstance(instanceId, "test");
	}

	@Test
	public void testAcl() throws Exception
	{
		String processDefId = _processDef.getId();
		// 启动流程实例
		ProcessInstance instance = _processEngine.getRuntimeService().startProcessInstanceByKey(_processDef.getKey());
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

		//现在允许step2可以让engineering操作
		_aclStore.save(processDefId, "step2", null, new String[] { "engineering" }, new String[0]);

		//对现已执行的task没有影响
		Assert.assertEquals(0, taskService.createTaskQuery().taskCandidateGroup("engineering").count());

		//删除掉该流程
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");

		//再启动一个流程
		instance = _processEngine.getRuntimeService().startProcessInstanceByKey(_processDef.getKey());
		//engineering应该可以执行任务了
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateGroup("engineering").count());
		//management不可以执行任务
		Assert.assertEquals(0, taskService.createTaskQuery().taskCandidateGroup("management").count());

		//删掉流程实例
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");
		_aclStore.removeAll();

		//允许step2可以让engineering和management操作，允许neo操作
		_aclStore.save(processDefId, "step2", null, new String[] { "engineering", "management" },
			new String[] { "neo" });

		//再启动一个流程
		instance = _processEngine.getRuntimeService().startProcessInstanceByKey(_processDef.getKey());
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
	}

	@Test
	public void testDelegation()
	{
		ProcessInstance instance;
		TaskService taskService = _processEngine.getTaskService();

		((TaskDelagation) (((ReplaceTaskAssignmentManager) (((ProcessEngineConfigurationEx) _processEngine
				.getProcessEngineConfiguration()).getStartEngineEventListeners().get(2))).getAssignmentHandlers()
				.get(1))).setHideDelegated(false);

		//代理关系
		//alex将代理kermit
		_delegationStore.addDelegation("kermit", "alex");

		//启动一个流程
		instance = _processEngine.getRuntimeService().startProcessInstanceByKey(_processDef.getKey());
		//kermit是management，所以可以访问
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateUser("kermit").count());
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateUser("alex").count());
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");

		//设置屏蔽被代理人
		((TaskDelagation) (((ReplaceTaskAssignmentManager) (((ProcessEngineConfigurationEx) _processEngine
				.getProcessEngineConfiguration()).getStartEngineEventListeners().get(2))).getAssignmentHandlers()
				.get(1))).setHideDelegated(true);

		//再启动一个流程
		instance = _processEngine.getRuntimeService().startProcessInstanceByKey(_processDef.getKey());
		//neo被屏蔽了
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateUser("kermit").count());
		Assert.assertEquals(0, taskService.createTaskQuery().taskCandidateUser("neo").count());
		//删掉流程
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");
	}

	@Test
	public void testAlarm() throws Exception
	{
		ProcessInstance instance;
		instance = _processEngine.getRuntimeService().startProcessInstanceByKey(_processDef.getKey());
		//等待催办事件发生
		Thread.sleep(60000);
		//删掉流程
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");
	}

}
