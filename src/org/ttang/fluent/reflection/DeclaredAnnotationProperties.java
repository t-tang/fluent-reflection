package org.ttang.fluent.reflection;

import java.util.Arrays;
import java.util.List;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * A fluent api for reflecting over annotation declarations
 * @author ttang
 *
 */
public class DeclaredAnnotationProperties {

	private List<Method> methods;

	private DeclaredAnnotationProperties(Method[] methods) {
		this.methods = Arrays.asList(methods);
	}

	/**
	 * Create from the annotation class
	 * @param annotationType Class<? extends Annotation>
	 * @return DeclaredAnnotationProperties for fluent chaining
	 */
	public static DeclaredAnnotationProperties from (Class<? extends Annotation> annotationType) {
		return new DeclaredAnnotationProperties(annotationType.getMethods());
	}
	
	/**
	 * Fetch the property (aka value) identified by name
	 * @param name
	 * @return DeclaredAnnotationProperty for fluent chaining
	 * @throws NoSuchPropertyException if the name was not found
	 */
	public DeclaredAnnotationProperty named(String name) throws NoSuchPropertyException {
		for (Method method : methods) {
			if (method.getName().equals(name)) {
				return DeclaredAnnotationProperty.from(method);
			}
		}
		
		throw new NoSuchPropertyException(name + " property not found");
	}
}
