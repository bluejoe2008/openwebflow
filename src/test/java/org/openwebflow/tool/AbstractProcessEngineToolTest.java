package org.openwebflow.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openwebflow.alarm.impl.AbstractNotificationDetailsStore;
import org.openwebflow.alarm.impl.TaskAlarmServiceImpl;
import org.openwebflow.assign.acl.AbstractActivityAclStore;
import org.openwebflow.assign.delegation.AbstractDelegationStore;
import org.openwebflow.assign.delegation.TaskDelagation;
import org.openwebflow.conf.ProcessEngineConfigurationEx;
import org.openwebflow.conf.ReplaceTaskAssignmentHandler;
import org.openwebflow.ctrl.TaskFlowControlService;
import org.openwebflow.ctrl.TaskFlowControlServiceFactory;
import org.openwebflow.ctrl.persist.RuntimeActivityDefinitionStore;
import org.openwebflow.identity.AbstractUserDetailsStore;
import org.openwebflow.identity.impl.AbstractMembershipStore;
import org.openwebflow.identity.impl.MyUserDetails;
import org.openwebflow.util.ModelUtils;
import org.openwebflow.util.ProcessDefinitionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

public abstract class AbstractProcessEngineToolTest
{
	ProcessEngineTool _tool;

	ProcessEngine _processEngine;

	ApplicationContext _ctx;

	AbstractActivityAclStore _aclStore;

	AbstractDelegationStore _delegationStore;

	TaskFlowControlServiceFactory _taskFlowControlServiceFactory;

	@Before
	public void setUp() throws Exception
	{
		rebuildApplicationContext();

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
		userDetailsStore.saveUser(new MyUserDetails("kermit", "老黄", "heiker@trojo.com", "13800138000"));

		_delegationStore.removeAll();

		//清除自定义活动
		_ctx.getBean(RuntimeActivityDefinitionStore.class).removeAll();

		// 取model，该model会自动注册
		RepositoryService repositoryService = _processEngine.getRepositoryService();
		Model model = repositoryService.createModelQuery().modelKey("test1.bpmn").singleResult();
		//部署该model
		if (repositoryService.createProcessDefinitionQuery().processDefinitionKey("test1").list().isEmpty())
		{
			ModelUtils.deployModel(repositoryService, model.getId());
		}

		Model model2 = repositoryService.createModelQuery().modelKey("test2.bpmn").singleResult();
		//部署该model
		if (repositoryService.createProcessDefinitionQuery().processDefinitionKey("test2").list().isEmpty())
		{
			ModelUtils.deployModel(repositoryService, model2.getId());
		}
	}

	protected void rebuildApplicationContext()
	{
		_ctx = new ClassPathXmlApplicationContext(getConfigFilePath());
		_tool = _ctx.getBean(ProcessEngineTool.class);
		Assert.assertNotNull(_tool);
		_processEngine = _tool.getProcessEngine();

		_aclStore = (AbstractActivityAclStore) _ctx.getBean("myTaskActivityAclManager");
		//代理关系
		_delegationStore = (AbstractDelegationStore) _ctx.getBean("myDelegationDetailsManager");
		_taskFlowControlServiceFactory = _ctx.getBean(TaskFlowControlServiceFactory.class);
	}

	protected abstract String getConfigFilePath();

	@After
	public void tearDown() throws Exception
	{
		_processEngine.close();
		_processEngine = null;
		((AbstractApplicationContext) _ctx).getBeanFactory().destroySingletons();
		_ctx = null;
	}

	@Test
	public void testInsertTasksBefore() throws Exception
	{
		//测试前加签
		ProcessInstance instance = _processEngine.getRuntimeService().startProcessInstanceByKey("test2");
		TaskService taskService = _processEngine.getTaskService();
		TaskFlowControlService tfcs = _taskFlowControlServiceFactory.create(instance.getId());
		//到了step2
		Assert.assertEquals("step2", taskService.createTaskQuery().singleResult().getTaskDefinitionKey());
		//在前面加两个节点
		ActivityImpl[] as = tfcs.insertTasksBefore("step2", "bluejoe", "alex");
		//应该执行到了第一个节点
		Assert.assertEquals(as[0].getId(), taskService.createTaskQuery().singleResult().getTaskDefinitionKey());
		Assert.assertEquals("bluejoe", taskService.createTaskQuery().singleResult().getAssignee());
		//完成该节点
		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		//应该到了下一个节点
		Assert.assertEquals(as[1].getId(), taskService.createTaskQuery().singleResult().getTaskDefinitionKey());
		Assert.assertEquals("alex", taskService.createTaskQuery().singleResult().getAssignee());
		//完成该节点
		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		//应该到了下一个节点
		Assert.assertEquals("step2", taskService.createTaskQuery().singleResult().getTaskDefinitionKey());
		//完成step2
		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		//应该到了step3
		Assert.assertEquals("step3", taskService.createTaskQuery().singleResult().getTaskDefinitionKey());

		//确认历史轨迹里已保存
		//step1,step2,step2-1,step2-2,step2,step3
		List<HistoricActivityInstance> activities = _processEngine.getHistoryService()
				.createHistoricActivityInstanceQuery().processInstanceId(instance.getId()).list();
		Assert.assertEquals(6, activities.size());

		//删掉流程
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");
	}

	@Test
	public void testInsertTasksAfter() throws Exception
	{
		//测试后加签
		ProcessInstance instance = _processEngine.getRuntimeService().startProcessInstanceByKey("test2");
		TaskService taskService = _processEngine.getTaskService();
		TaskFlowControlService tfcs = _taskFlowControlServiceFactory.create(instance.getId());
		//到了step2
		Assert.assertEquals("step2", taskService.createTaskQuery().singleResult().getTaskDefinitionKey());
		//在前面加两个节点
		Authentication.setAuthenticatedUserId("kermit");
		ActivityImpl[] as = tfcs.insertTasksAfter("step2", "bluejoe", "alex");
		//应该执行到了第一个节点
		Assert.assertEquals(as[0].getId(), taskService.createTaskQuery().singleResult().getTaskDefinitionKey());
		Assert.assertEquals("kermit", taskService.createTaskQuery().singleResult().getAssignee());
		//完成该节点
		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		//应该到了下一个节点
		Assert.assertEquals(as[1].getId(), taskService.createTaskQuery().singleResult().getTaskDefinitionKey());
		Assert.assertEquals("bluejoe", taskService.createTaskQuery().singleResult().getAssignee());
		//完成该节点
		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		//应该到了下一个节点
		Assert.assertEquals(as[2].getId(), taskService.createTaskQuery().singleResult().getTaskDefinitionKey());
		Assert.assertEquals("alex", taskService.createTaskQuery().singleResult().getAssignee());
		//完成该节点
		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		//应该到了下一个节点
		Assert.assertEquals("step3", taskService.createTaskQuery().singleResult().getTaskDefinitionKey());

		//确认历史轨迹里已保存
		//step1,step2,step2,step2-1,step2-2,step3
		List<HistoricActivityInstance> activities = _processEngine.getHistoryService()
				.createHistoricActivityInstanceQuery().processInstanceId(instance.getId()).list();
		Assert.assertEquals(6, activities.size());

		//删掉流程
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");
	}

	@Test
	public void testInsertTasksWithPersistence() throws Exception
	{
		//测试加签功能的持久化
		ProcessInstance instance = _processEngine.getRuntimeService().startProcessInstanceByKey("test2");
		String processInstanceId = instance.getId();
		TaskService taskService = _processEngine.getTaskService();
		TaskFlowControlService tfcs = _taskFlowControlServiceFactory.create(instance.getId());
		//到了step2
		//在前面加两个节点
		Authentication.setAuthenticatedUserId("kermit");
		ActivityImpl[] as = tfcs.insertTasksAfter("step2", "bluejoe", "alex");
		//应该执行到了第一个节点
		//完成该节点
		taskService.complete(taskService.createTaskQuery().singleResult().getId());

		//此时模拟服务器重启
		ProcessEngine oldProcessEngine = _processEngine;
		rebuildApplicationContext();
		Assert.assertNotSame(oldProcessEngine, _processEngine);

		//重新构造以上对象
		instance = _processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		taskService = _processEngine.getTaskService();
		tfcs = _taskFlowControlServiceFactory.create(instance.getId());

		//应该到了下一个节点
		Assert.assertEquals("bluejoe", taskService.createTaskQuery().singleResult().getAssignee());
		//完成该节点
		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		//应该到了下一个节点
		Assert.assertEquals(as[2].getId(), taskService.createTaskQuery().singleResult().getTaskDefinitionKey());
		Assert.assertEquals("alex", taskService.createTaskQuery().singleResult().getAssignee());
		//完成该节点
		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		//应该到了下一个节点
		Assert.assertEquals("step3", taskService.createTaskQuery().singleResult().getTaskDefinitionKey());

		//确认历史轨迹里已保存
		//step1,step2,step2,step2-1,step2-2,step3
		List<HistoricActivityInstance> activities = _processEngine.getHistoryService()
				.createHistoricActivityInstanceQuery().processInstanceId(instance.getId()).list();
		Assert.assertEquals(6, activities.size());

		//删掉流程
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");
	}

	@Test
	public void testSplit() throws Exception
	{
		//测试节点分裂
		String processDefId = _processEngine.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionKey("test2").singleResult().getId();
		int size = ProcessDefinitionUtils.getProcessDefinition(_processEngine, processDefId).getActivities().size();

		//重复执行，以确定当前流程的修改不会影响其他流程
		for (int i = 0; i < 2; i++)
		{
			ProcessInstance instance = _processEngine.getRuntimeService().startProcessInstanceByKey("test2");
			TaskFlowControlService tfcs = _taskFlowControlServiceFactory.create(instance.getId());
			TaskService taskService = _processEngine.getTaskService();
			//应该有1个step2任务在执行
			Assert.assertEquals(1, taskService.createTaskQuery().taskDefinitionKey("step2").count());

			//有1个execution
			Assert.assertEquals(1, _processEngine.getRuntimeService().createExecutionQuery().count());
			//step2分裂成2个
			ActivityImpl newActivity = tfcs.split("step2", "bluejoe", "alex");

			//只有1个step2任务
			Assert.assertEquals(1, taskService.createTaskQuery().taskDefinitionKey("step2").count());
			Assert.assertEquals(2, _processEngine.getRuntimeService().createExecutionQuery().count());

			//依次完成2个任务
			taskService.complete(taskService.createTaskQuery().list().get(0).getId());
			taskService.complete(taskService.createTaskQuery().list().get(0).getId());

			Assert.assertEquals(0, taskService.createTaskQuery().taskDefinitionKey("step2").count());
			//应该顺利到达step3
			Assert.assertEquals("step3", taskService.createTaskQuery().singleResult().getTaskDefinitionKey());
			Assert.assertEquals(1, _processEngine.getRuntimeService().createExecutionQuery().count());

			Assert.assertNotNull(ProcessDefinitionUtils.getProcessDefinition(_processEngine, processDefId)
					.findActivity(newActivity.getId()));

			//确认历史轨迹里已保存
			//step1,step2,step2-split,step2-split,step3
			List<HistoricActivityInstance> activities = _processEngine.getHistoryService()
					.createHistoricActivityInstanceQuery().processInstanceId(instance.getId()).list();
			Assert.assertEquals(5, activities.size());

			//删掉流程
			_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");
		}

		Assert.assertEquals(size + 2, ProcessDefinitionUtils.getProcessDefinition(_processEngine, processDefId)
				.getActivities().size());
	}

	@Test
	public void testCachedDefinitions()
	{
		//获取TaskDefinition
		String processDefId = _processEngine.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionKey("test1").singleResult().getId();

		ActivityImpl activity1 = ProcessDefinitionUtils.getActivity(_processEngine, processDefId, "step2");
		ActivityImpl activity2 = ProcessDefinitionUtils.getActivity(_processEngine, processDefId, "step2");
		Assert.assertSame(activity1, activity2);

		ExecutionEntity instance1 = (ExecutionEntity) _processEngine.getRuntimeService().startProcessInstanceByKey(
			"test2");
		TaskService taskService = _processEngine.getTaskService();
		ExecutionEntity instance2 = (ExecutionEntity) _processEngine.getRuntimeService().startProcessInstanceByKey(
			"test2");
		TaskEntity task1 = (TaskEntity) taskService.createTaskQuery().active().list().get(0);
		TaskEntity task2 = (TaskEntity) taskService.createTaskQuery().active().list().get(1);

		Assert.assertSame(instance1.getProcessDefinition(), instance2.getProcessDefinition());
		Assert.assertSame(((ProcessDefinitionEntity) instance1.getProcessDefinition()).getTaskDefinitions(),
			((ProcessDefinitionEntity) instance2.getProcessDefinition()).getTaskDefinitions());

		Assert.assertTrue(((ProcessDefinitionEntity) instance1.getProcessDefinition()).getTaskDefinitions()
				.containsKey("step2"));
		Assert.assertTrue(((ProcessDefinitionEntity) instance1.getProcessDefinition()).getTaskDefinitions()
				.containsKey("step3"));
		Assert.assertTrue(((ProcessDefinitionEntity) instance1.getProcessDefinition()).getTaskDefinitions()
				.containsKey("step4"));
		Assert.assertTrue(((ProcessDefinitionEntity) instance1.getProcessDefinition()).getTaskDefinitions()
				.containsKey("step5"));
		Assert.assertTrue(((ProcessDefinitionEntity) instance1.getProcessDefinition()).getTaskDefinitions()
				.containsKey("step6"));

		//删除流程
		_processEngine.getRuntimeService().deleteProcessInstance(instance1.getId(), "test");
		_processEngine.getRuntimeService().deleteProcessInstance(instance2.getId(), "test");
	}

	@Test
	public void testMultiInstancesLoop()
	{
		//测试多实例节点
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("assigneeList", CollectionUtils.arrayToList(new String[] { "kermit", "bluejoe" }));
		ProcessInstance instance = _processEngine.getRuntimeService().startProcessInstanceByKey("test2", variables);

		TaskService taskService = _processEngine.getTaskService();

		//完成step2
		taskService.complete(taskService.createTaskQuery().singleResult().getId());

		//应该有1个step3任务在执行
		Assert.assertEquals(1, taskService.createTaskQuery().taskDefinitionKey("step3").count());
		//有1个execution
		Assert.assertEquals(1, _processEngine.getRuntimeService().createExecutionQuery().count());
		Assert.assertEquals("step3", _processEngine.getRuntimeService().createExecutionQuery().singleResult()
				.getActivityId());

		//完成step3，抵达step4
		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		//step4是个并行多实例节点
		//应该启动了3个step4，1个二级执行，2个三级执行，加上前面的主执行，共4个
		List<Execution> list = _processEngine.getRuntimeService().createExecutionQuery().list();
		Assert.assertEquals(4, list.size());

		ExecutionEntity execution1 = (ExecutionEntity) list.get(0);
		ExecutionEntity execution11 = (ExecutionEntity) list.get(1);
		ExecutionEntity execution111 = (ExecutionEntity) list.get(2);
		ExecutionEntity execution112 = (ExecutionEntity) list.get(3);

		Assert.assertEquals(null, execution1.getActivityId());
		Assert.assertEquals("step4", execution11.getActivityId());
		Assert.assertEquals("step4", execution111.getActivityId());
		Assert.assertEquals("step4", execution112.getActivityId());

		Assert.assertEquals(false, execution11.isActive());
		Assert.assertEquals(true, execution111.isActive());
		Assert.assertEquals(true, execution112.isActive());
		Assert.assertEquals(true, execution111.isConcurrent());
		Assert.assertEquals(true, execution112.isConcurrent());

		//父子关系
		Assert.assertEquals(execution11.getParentId(), execution1.getId());
		Assert.assertEquals(execution111.getParentId(), execution112.getParentId(), execution11.getId());

		//活动任务是2个
		List<Task> tasks = taskService.createTaskQuery().taskDefinitionKey("step4").list();
		Assert.assertEquals(2, tasks.size());
		Task task41 = tasks.get(0);
		Task task42 = tasks.get(1);
		Assert.assertEquals(execution111.getId(), task41.getExecutionId());
		Assert.assertEquals(execution112.getId(), task42.getExecutionId());

		//完成task41
		taskService.complete(task41.getId());
		//任务删除了1个
		Assert.assertEquals(1, taskService.createTaskQuery().taskDefinitionKey("step4").count());
		Assert.assertEquals(task42.getId(), taskService.createTaskQuery().taskDefinitionKey("step4").singleResult()
				.getId());

		//execution还是4个
		Assert.assertEquals(4, _processEngine.getRuntimeService().createExecutionQuery().count());
		//完成task42
		taskService.complete(task42.getId());
		//没有step4的任何任务了
		Assert.assertEquals(0, taskService.createTaskQuery().taskDefinitionKey("step4").count());
		Assert.assertEquals(1, taskService.createTaskQuery().taskDefinitionKey("step5").count());

		//execution又恢复成1个
		Assert.assertEquals(1, _processEngine.getRuntimeService().createExecutionQuery().count());
		Assert.assertEquals("step5", _processEngine.getRuntimeService().createExecutionQuery().singleResult()
				.getActivityId());

		//完成step5，抵达step6
		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		//step6是一个串行多实例节点
		//此时应该有2个execution
		Assert.assertEquals(2, _processEngine.getRuntimeService().createExecutionQuery().count());
		Assert.assertEquals(1, taskService.createTaskQuery().taskDefinitionKey("step6").count());
		Assert.assertEquals(taskService.createTaskQuery().taskDefinitionKey("step6").singleResult().getExecutionId(),
			_processEngine.getRuntimeService().createExecutionQuery().list().get(1).getId());
		//执行step6-1
		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		//还剩下1个
		Assert.assertEquals(1, taskService.createTaskQuery().taskDefinitionKey("step6").count());
		//执行step6-2
		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		//到达step7
		Assert.assertEquals(1, taskService.createTaskQuery().taskDefinitionKey("step7").count());

		//删掉流程
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");
	}

	@Test
	public void testModelDeployment() throws Exception
	{
		//测试流程模型部署
		// 取model，该model会自动注册
		RepositoryService repositoryService = _processEngine.getRepositoryService();
		Model model = repositoryService.createModelQuery().modelKey("vacation.bpmn").singleResult();
		//确定已注册
		Assert.assertNotNull(model);
		//由于没有部署，应该拿不到
		Assert.assertEquals(0, repositoryService.createProcessDefinitionQuery().processDefinitionKey("vacationRequest")
				.list().size());

		//部署该model
		Deployment dep = ModelUtils.deployModel(repositoryService, model.getId());
		//现在应该拿得到了
		Assert.assertEquals(1, repositoryService.createProcessDefinitionQuery().processDefinitionKey("vacationRequest")
				.list().size());

		//删除掉
		repositoryService.deleteDeployment(dep.getId(), true);
		//现在再取就拿不到了
		Assert.assertEquals(0, repositoryService.createProcessDefinitionQuery().processDefinitionKey("vacationRequest")
				.list().size());
	}

	@Test
	public void testMove() throws Exception
	{
		//测试自由跳转
		ProcessInstance instance = _processEngine.getRuntimeService().startProcessInstanceByKey("test2");
		String instanceId = instance.getId();

		TaskService taskService = _processEngine.getTaskService();
		Assert.assertEquals("step2", taskService.createTaskQuery().singleResult().getTaskDefinitionKey());

		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		Assert.assertEquals("step3", taskService.createTaskQuery().singleResult().getTaskDefinitionKey());

		TaskFlowControlService tfcs = _taskFlowControlServiceFactory.create(instance.getId());
		//测试一下往前跳
		tfcs.moveTo("step5");
		Assert.assertEquals("step5", taskService.createTaskQuery().singleResult().getTaskDefinitionKey());

		//跳回至 step2
		tfcs.moveTo("step2");
		Assert.assertEquals("step2", taskService.createTaskQuery().singleResult().getTaskDefinitionKey());

		//前进一步
		tfcs.moveForward();
		Assert.assertEquals("step3", taskService.createTaskQuery().singleResult().getTaskDefinitionKey());

		//后退一步
		tfcs.moveBack();
		Assert.assertEquals("step2", taskService.createTaskQuery().singleResult().getTaskDefinitionKey());

		//确认历史轨迹里已保存
		List<HistoricActivityInstance> activities = _processEngine.getHistoryService()
				.createHistoricActivityInstanceQuery().processInstanceId(instanceId).list();

		Assert.assertEquals(7, activities.size());
		Assert.assertEquals("step1", activities.get(0).getActivityId());
		Assert.assertEquals("step2", activities.get(1).getActivityId());
		Assert.assertEquals("step3", activities.get(2).getActivityId());
		Assert.assertEquals("step5", activities.get(3).getActivityId());
		Assert.assertEquals("step2", activities.get(4).getActivityId());
		Assert.assertEquals("step3", activities.get(5).getActivityId());
		Assert.assertEquals("step2", activities.get(6).getActivityId());

		_processEngine.getRuntimeService().deleteProcessInstance(instanceId, "test");
	}

	@Test
	public void testAcl() throws Exception
	{
		//测试流程动态授权
		String processDefId = _processEngine.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionKey("test1").singleResult().getId();
		// 启动流程实例
		ProcessInstance instance = _processEngine.getRuntimeService().startProcessInstanceByKey("test1");
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
		instance = _processEngine.getRuntimeService().startProcessInstanceByKey("test1");
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
		instance = _processEngine.getRuntimeService().startProcessInstanceByKey("test1");
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
		//测试代理功能
		ProcessInstance instance;
		TaskService taskService = _processEngine.getTaskService();

		((TaskDelagation) (((ReplaceTaskAssignmentHandler) (((ProcessEngineConfigurationEx) _processEngine
				.getProcessEngineConfiguration()).getStartEngineEventListeners().get(2))).getHandlers().get(1)))
				.setHideDelegated(false);

		//代理关系
		//alex将代理kermit
		_delegationStore.addDelegation("kermit", "alex");

		//启动一个流程
		instance = _processEngine.getRuntimeService().startProcessInstanceByKey("test1");
		//kermit是management，所以可以访问
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateUser("kermit").count());
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateUser("alex").count());
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");

		//设置屏蔽被代理人
		((TaskDelagation) (((ReplaceTaskAssignmentHandler) (((ProcessEngineConfigurationEx) _processEngine
				.getProcessEngineConfiguration()).getStartEngineEventListeners().get(2))).getHandlers().get(1)))
				.setHideDelegated(true);

		//再启动一个流程
		instance = _processEngine.getRuntimeService().startProcessInstanceByKey("test1");
		//neo被屏蔽了
		Assert.assertEquals(1, taskService.createTaskQuery().taskCandidateUser("kermit").count());
		Assert.assertEquals(0, taskService.createTaskQuery().taskCandidateUser("neo").count());
		//删掉流程
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");
	}

	@Test
	public void testAlarm() throws Exception
	{
		//测试催办功能
		ProcessInstance instance = _processEngine.getRuntimeService().startProcessInstanceByKey("test1");
		((TaskAlarmServiceImpl) _ctx.getBean(TaskAlarmServiceImpl.class)).start(_processEngine);
		//等待催办事件发生
		Thread.sleep(60000);
		//删掉流程
		_processEngine.getRuntimeService().deleteProcessInstance(instance.getId(), "test");
	}

}
