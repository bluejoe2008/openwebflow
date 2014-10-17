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
