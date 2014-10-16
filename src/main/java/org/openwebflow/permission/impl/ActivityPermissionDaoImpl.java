package org.openwebflow.permission.impl;

import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openwebflow.permission.ActivityPermission;
import org.openwebflow.permission.ActivityPermissionDao;

public class ActivityPermissionDaoImpl implements ActivityPermissionDao
{
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void delete(ActivityPermission ap)
	{
		String sql = String.format("delete from %s o where o._processDefId=? and o._activityId=?",
			ActivityPermission.class.getName());
		getSession().createQuery(sql).setParameter(0, ap.getProcessDefId()).setParameter(1, ap.getActivityId())
				.executeUpdate();
	}

	public Session getSession()
	{
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void insertOrUpdate(ActivityPermission ap)
	{
		ap.setOpTime(new Date(System.currentTimeMillis()));
		getSession().saveOrUpdate(ap);
	}

	@Override
	public List<ActivityPermission> list()
	{
		return getSession().createQuery(String.format("from %s", ActivityPermission.class.getName())).list();
	}
}
