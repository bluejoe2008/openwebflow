What's OpenWebFlow
===========

OpenWebFlow是基于Activiti扩展的工作流引擎，确切的说，它的核心还是Activiti，OpenWebFlow更多的工作是针对Web环境做了一些包装工作。简单的说，OpenWebFlow：

* 提供了一系列的Tool工具类，你可以在java应用中、甚至在自己的Controller的方法里取到这些Tool；
* 包装了一系列的事件，注意这些事件是Web上下文的（不同于Activiti的事件机制），开发用户可以基于事件机制写自己的EventHandler，比较方便的是，EventHandler是基于注解的一些方法，这些方法的写法与Spring Controller的方法一样（支持参数自动映射）；
* 剥离了活动（activity）权限管理，即用户对活动的访问控制信息单独管理（而不是在流程定义中预先写死），这样有利于动态调整权限；

感谢咖啡兔<http://www.kafeitu.me/>，里面有很多的关于Activiti应用方案的讨论。

How to start
===========

OpenWebFlow可以使用到任何java程序中，当然你需要准备一个Spring IoC的配置文件，一般这个配置文件命名为activiti-cfg-xml，如下是一段配置的例子：

	<bean id="processEngineConfiguration" class="org.activiti.spring.SpringProcessEngineConfiguration">
		<property name="dataSource" ref="dataSource" />
		<property name="transactionManager" ref="transactionManager" />
		<property name="databaseSchemaUpdate" value="true" />
		<property name="jobExecutorActivate" value="false" />
		
		<!-- 邮件服务器 -->
		<property name="mailServerHost" value="smtp.cnic.cn" />
		<property name="mailServerPort" value="25" />
		<property name="mailServerDefaultFrom" value="bluejoe@cnic.cn" />
		<property name="mailServerUsername" value="sdb-support@cnic.cn" />
		<property name="mailServerPassword" value="sdb-support@cnic.cn" />

		<property name="customFormTypes">
			<list>
				<bean class="org.openwebflow.UserFormType" />
			</list>
		</property>
		
		<!-- 自动部署 -->
		<property name="deploymentResources" value="./WEB-INF/models/*.bpmn" />
		<property name="deploymentMode" value="single-resource" />
	</bean>

	<bean id="transactionManager"
		class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>

	<bean class="org.activiti.spring.ProcessEngineFactoryBean">
		<property name="processEngineConfiguration" ref="processEngineConfiguration" />
	</bean>

	<!-- 主要用到的对象 -->
	<bean id="processEngineTool" class="org.openwebflow.tool.impl.ProcessEngineToolImpl">
		<property name="webFlowConfiguration">
			<bean id="webFlowConfiguration" class="org.openwebflow.mvc.WebFlowConfiguration">
				<property name="defaultStartProcessFormView" value="/startProcessForm" />
				<property name="defaultStartProcessActionView" value="/doStartProcess" />
				<property name="defaultCompleteTaskFormView" value="/completeTaskForm" />
				<property name="defaultCompleteTaskActionView" value="/doCompleteTask" />
				<property name="defaultClaimTaskActionView" value="redirect:/workflow/listTaskQueue.action" />
				<property name="formVariablesFilter">
					<bean class="org.openwebflow.mvc.PrefixFormVariablesFilter">
						<property name="prefix" value="var_" />
					</bean>
				</property>
			</bean>
		</property>
		<!-- 自定义权限管理 -->
		<property name="loadPermissionsOnStartup" value="true" />
		<property name="activityPermissionService">
			<bean class="org.openwebflow.permission.impl.ActivityPermissionServiceImpl">
				<property name="permissionDao">
					<bean class="org.openwebflow.permission.impl.ActivityPermissionDaoImpl"/>
				</property>
			</bean>
		</property>
	</bean>
	
	<!-- 加载service和entity -->
	<context:component-scan base-package="org.openwebflow.permission" />
	<tx:annotation-driven transaction-manager="transactionManager" />
	
	
注意前面的processEngineConfiguration配置，其实是activiti的配置要求。processEngineTool则是OpenWebFlow的唯一配置，它也是连接到activiti工作流引擎的一把钥匙。

ProcessEngineTool
===========

ProcessEngineTool是通往Activiti工作流引擎的最佳通道，它提供的方法很多：

	interface ProcessEngineTool extends ProcessEngineQueryTool
	{
		ActivityTool createActivityTool(String processDefId, String activityId);
	
		ProcessDefinitionTool createProcessDefinitionTool(String processDefId);
	
		ProcessInstanceTool createProcessInstanceTool(String processInstanceId);
	
		TaskTool createTaskTool(String taskId);
	
		ActivityPermissionService getActivityPermissionService();
	
		ProcessEngine getProcessEngine();
	
		WebFlowConfiguration getWebFlowConfiguration();
		
		long getActiveProcessesCount();
	
		Map<String, Object> getActiveProcessVariables(String processId);
	
		long getAssignedTasksCount(String userId);
	
		long getHistoricProcessesCount();
	
		Map<String, Object> getHistoricProcessVariables(String processId);
	
		List<ProcessDefinition> getProcessDefs();
	
		long getProcessDefsCount();
	
		long getTaskQueueCount(String userId);
	
		List<HistoricProcessInstance> listActiveProcessInstances();
	
		List<Task> listAssignedTasks(String userId);
	
		List<HistoricActivityInstance> listHistoricActivities(String processId);
	
		List<HistoricProcessInstance> listHistoricProcesseInstances();
	
		List<Task> listTaskQueue(String userId);
	}
	
很显然，它是个门面，后面也许还会增加更多的方法。

在Spring应用中，任何待扫描的类中增加一句：

	@Autowired
	private ProcessEngineTool _processEngineTool;

就可以实现对ProcessEngineTool对象的引用。

当然在EventContext和ContextToolHolder里面，都可以很便捷的获取到ProcessEngineTool对象。

Custom Controller
===========

开发用户可以像往常一样定义Controller，但可能需要在代码中启动一个流程、或者完成一个任务，这时候需要用到ContextToolHolder：

	public interface ContextToolHolder
	{
		ActivityTool getActivityTool();
	
		ProcessDefinitionTool getProcessDefinitionTool();
	
		ProcessEngineTool getProcessEngineTool();
	
		ProcessInstanceTool getProcessInstanceTool();
	
		TaskTool getTaskTool();
	}
	
ContextToolHolder就是一堆tool的factory，想用它也很简单，在自己的Controller方法中加上一个@WebFlowParam标注就可以使用ContextToolHolder对象了：

	@RequestMapping("/doCompleteAdjustTask.action")
	public String doCompleteAdjustTask(@WebFlowParam
	ContextToolHolder holder, @RequestParam
	Map<String, Object> formValues, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		...
	}
	
WebFlowParam具有一些字段，可以指定request参数中对应的参数名：

	public @interface WebFlowParam
	{
		String keyActivityId() default "activityId";
	
		String keyProcessDefinitionId() default "processDefId";
	
		String keyProcessInstanceId() default "processId";
	
		String keyTaskId() default "taskId";
	}
	
记住，你可以不用ContextToolHolder对象，因为前面已经说过了，你直接用ProcessEngineTool对象也是可以的，如：

	@Autowired
	private ProcessEngineTool _processEngineTool;

	@RequestMapping("/doCompleteAdjustTask.action")
	public String doCompleteAdjustTask(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		...
		_processEngineTool.createTaskTool("myTaskId");
	}

Events
===========

实际上，关于工作流的很多页面流程是固定的，譬如：指定流程定义ID-->展示流程启动表单-->提交表单，启动该流程。

org.openwebflow.mvc.WebFlowDispatcherController把这些固定的工作都做了，但是问题来了：如何在启动流程的时候做一些业务的操作，譬如保存表单什么的？解决方案是使用事件机制。

OpenWebFlow的事件机制是针对Web上下文的，每个事件除了EventId、EventType，还有EventContext，EventContext负责保存环境信息，譬如一个DoStartProcessEventContext内容如下：

	public interface DoStartProcessEventContext extends EventContext
	{
		String getBussinessKey();
	
		ProcessDefinition getProcessDefinition();
	
		String getProcessDefinitionId();
	
		ProcessInstance getProcessInstance();
	
		public abstract Map<String, Object> getProcessVariableMap();
	
		void setBussinessKey(String bussinessKey);
	}
	
看到了吗？Activiti关心的bussiness key由这个DoStartProcessEventContext负责维护。类似的EventContext还有一些，各有千秋。

一个典型的EventHandler写成这样：

	@EventHandlerClass
	@Component
	public class MyEventHandler
	{
	
		@EventHandlerMethod(eventType = EventType.BeforeDoStartProcess, formKey = "/startVacationRequest")
		public void beforeDoStartVacationRequest(EventContextHolder holder, String processDefId, ModelMap model,
				HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			DoStartProcessEventContext event = holder.getDoStartProcessEventContext();
			event.setBussinessKey("" + System.currentTimeMillis());
		}
	}
	
@EventHandlerClass标注当前的类是一个包含Handler方法的类，@EventHandlerMethod标注处理事件的具体方法。可以看到，@EventHandlerMethod有两个参数，前面指定事件的类型，后者是与事件相关的formKey，这个在定义流程的时候会接触到。

目前的事件类型还不是很多，但这并不意味着以后不会再增加新的事件：

	public enum EventType
	{
		AfterDoCompleteTask, AfterDoStartProcess, BeforeDoCompleteTask, BeforeDoStartProcess, OnCompleteTaskForm, OnStartProcessForm
	}

最后注意的是，这种模式是可选项，你可以不用这些固定的流程，可以不用事件机制，别管它，直接写自己的Controller就是了！
