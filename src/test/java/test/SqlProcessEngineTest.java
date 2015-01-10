package test;

public class SqlProcessEngineTest extends AbstractProcessEngineTest
{
	protected String getConfigFilePath()
	{
		return "classpath:activiti.cfg.sql.xml";
	}
}
