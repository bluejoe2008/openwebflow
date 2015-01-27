package test;

public class SqlMybatisProcessEngineTest extends AbstractProcessEngineTest
{
	protected String getConfigFilePath()
	{
		return "classpath:activiti.cfg.sql.mybatis.xml";
	}
}
