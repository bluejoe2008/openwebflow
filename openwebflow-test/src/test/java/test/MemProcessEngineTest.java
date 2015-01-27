package test;

public class MemProcessEngineTest extends AbstractProcessEngineTest
{
	protected String getConfigFilePath()
	{
		return "classpath:activiti.cfg.mem.xml";
	}
}
