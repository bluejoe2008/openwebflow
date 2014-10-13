package org.openwebflow.mvc.helper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebFlowParam
{
	String keyProcessDefinitionId() default "processDefId";

	String keyProcessInstanceId() default "processId";

	String keyTaskId() default "taskId";
}
