package org.openwebflow.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;

public abstract class ExpressionUtils
{
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
