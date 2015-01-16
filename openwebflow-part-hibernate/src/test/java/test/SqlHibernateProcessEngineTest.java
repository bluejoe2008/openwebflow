package test;

public class SqlHibernateProcessEngineTest extends AbstractProcessEngineTest
{
	protected String getConfigFilePath()
	{
		return "classpath:activiti.cfg.sql.hibernate.xml";
	}
}
