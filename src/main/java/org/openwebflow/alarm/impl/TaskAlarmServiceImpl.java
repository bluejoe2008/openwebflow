package org.openwebflow.alarm.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.openwebflow.alarm.MessageNotifier;
import org.openwebflow.alarm.NotificationDetailsStore;
import org.openwebflow.alarm.TaskAlarmService;
import org.openwebflow.identity.IdentityMembershipService;
import org.openwebflow.identity.IdentityUserDetails;
import org.openwebflow.identity.UserDetailsService;
import org.openwebflow.util.IdentityUtils;

public class TaskAlarmServiceImpl implements TaskAlarmService
{
	class MonitorThread extends Thread
	{
		Period _parsedPeriodInAdvance;

		public MonitorThread() throws IOException
		{
			_parsedPeriodInAdvance = Period.parse(_periodInAdvance);
		}

		private void checkAndNotify() throws Exception
		{
			//检查即将过期的task
			Date dueDate = DateTime.now().minus(_parsedPeriodInAdvance).toDate();

			for (Task task : _processEngine.getTaskService().createTaskQuery().active().dueAfter(dueDate).list())
			{
				//是否已经通知？
				if (!_notificationDetailsStore.isNotified(task.getId()))
				{
					//没有通知则现在通知
					List<IdentityUserDetails> involvedUsers = IdentityUtils.getUserDetailsFromIds(
						IdentityUtils.getInvolvedUsers(_processEngine.getTaskService(), task, _membershipService),
						_userDetailsService);

					_messageNotifier.notify(involvedUsers.toArray(new IdentityUserDetails[0]), task);
					//设置标志
					_notificationDetailsStore.setNotified(task.getId());
					Logger.getLogger(getClass()).debug(String.format("notified %s", involvedUsers));
				}
			}
		}

		@Override
		public void run()
		{
			while (true)
			{
				try
				{
					checkAndNotify();
					Thread.sleep(_checkInterval);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	long _checkInterval = 10000;

	IdentityMembershipService _membershipService;

	MessageNotifier _messageNotifier;

	NotificationDetailsStore _notificationDetailsStore;

	String _periodInAdvance;

	public long getCheckInterval()
	{
		return _checkInterval;
	}

	public void setCheckInterval(long checkInterval)
	{
		_checkInterval = checkInterval;
	}

	public IdentityMembershipService getMembershipService()
	{
		return _membershipService;
	}

	public void setMembershipService(IdentityMembershipService membershipService)
	{
		_membershipService = membershipService;
	}

	public MessageNotifier getMessageNotifier()
	{
		return _messageNotifier;
	}

	public void setMessageNotifier(MessageNotifier messageNotifier)
	{
		_messageNotifier = messageNotifier;
	}

	public NotificationDetailsStore getNotificationDetailsStore()
	{
		return _notificationDetailsStore;
	}

	public void setNotificationDetailsStore(NotificationDetailsStore notificationDetailsStore)
	{
		_notificationDetailsStore = notificationDetailsStore;
	}

	public String getPeriodInAdvance()
	{
		return _periodInAdvance;
	}

	public void setPeriodInAdvance(String periodInAdvance)
	{
		_periodInAdvance = periodInAdvance;
	}

	public UserDetailsService getUserDetailsService()
	{
		return _userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService)
	{
		_userDetailsService = userDetailsService;
	}

	ProcessEngine _processEngine;

	MonitorThread _thread;

	UserDetailsService _userDetailsService;

	@Override
	public void start(ProcessEngine processEngine) throws Exception
	{
		_processEngine = processEngine;
		_thread = new MonitorThread();
		_thread.setName(_thread.getClass().getName());
		_thread.start();
	}
}
