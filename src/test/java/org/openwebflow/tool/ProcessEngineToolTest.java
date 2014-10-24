package org.openwebflow.tool;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

public class ProcessEngineToolTest
{
	ToolFactory _tool;

	@Before
	public void setUp() throws Exception
	{
		ApplicationContext acf = new ClassPathXmlApplicationContext("classpath:activiti.cfg.xml");
		_tool = acf.getBean(ProcessEngineTool.class);
		Assert.notNull(_tool);
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test()
	{
	}

}
