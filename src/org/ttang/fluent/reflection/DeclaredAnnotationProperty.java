package org.ttang.fluent.reflection;

import java.lang.reflect.Method;

public class DeclaredAnnotationProperty {
	private Method method;

	private DeclaredAnnotationProperty(Method method) {
		this.method = method;
	}
	
	public static DeclaredAnnotationProperty from(Method method) {
		return new DeclaredAnnotationProperty(method);
	}
	
	public Class<?> type() {
		return method.getReturnType();
	}
	
	public String name() {
		return method.getName();
	}
}
