package org.openwebflow.parts.hibernate.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SqlDaoBase<EntityClass>
{
	private static final Logger logger = LoggerFactory.getLogger(SqlDaoBase.class);

	private Class<EntityClass> _entityClass;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public SqlDaoBase()
	{
		_entityClass = (Class<EntityClass>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	private Query createParameteredQuery(String hql, Object... parameters) throws Exception
	{
		Query query = this.getSession().createQuery(hql);
		this.setParameters(query, parameters);
		return query;
	}

	public int executeUpdate(String hql, Object... parameters) throws Exception
	{
		Query query = createParameteredQuery(hql, parameters);
		return query.executeUpdate();
	}

	// 获取session信息
	protected Session getSession() throws Exception
	{
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	public EntityClass queryForObject(String hql, Object... parameters) throws Exception
	{
		List<EntityClass> list = queryForObjects(hql, parameters);
		return list.isEmpty() ? null : list.get(0);
	}

	public <T> List<T> queryForObjects() throws Exception
	{
		Criteria criteria = getSession().createCriteria(_entityClass);
		return criteria.list();
	}

	public <T> List<T> queryForObjects(String hql, Object... parameters) throws Exception
	{
		Query query = createParameteredQuery(hql, parameters);
		return query.list();
	}

	public EntityClass queryForSingleObject(String hql, Object... parameters) throws Exception
	{
		List<EntityClass> list = queryForObjects(hql, parameters);
		Assert.assertEquals(1, list.size());

		return list.get(0);
	}

	public <PK> PK saveObject(EntityClass entity) throws Exception
	{
		return (PK) this.getSession().save(entity);
	}

	private void setParameters(Query query, Object[] parameters)
	{
		if (parameters != null)
		{
			for (int i = 0; i < parameters.length; i++)
			{
				if (parameters[i] instanceof Date)
				{
					query.setTimestamp(i, (Date) parameters[i]);
				}
				else
				{
					query.setParameter(i, parameters[i]);
				}
			}
		}
	}
}
