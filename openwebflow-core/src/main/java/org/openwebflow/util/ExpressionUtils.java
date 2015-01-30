package org.openwebflow.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.FixedValue;

/**
 * 实现常见类型的expression的包装和转换
 * 
 * @author bluejoe2008@gmail.com
 *
 */
public abstract class ExpressionUtils
{
	public static Expression stringToExpression(ProcessEngineConfigurationImpl conf, String expr)
	{
		return conf.getExpressionManager().createExpression(expr);
	}

	public static Expression stringToExpression(String expr)
	{
		return new FixedValue(expr);
	}

	public static Set<Expression> stringToExpressionSet(String exprs)
	{
		Set<Expression> set = new LinkedHashSet<Expression>();
		for (String expr : exprs.split(";"))
		{
			set.add(stringToExpression(expr));
		}

		return set;
	}
}
