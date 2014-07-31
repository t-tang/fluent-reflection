package org.ttang.fluent.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A fluent API for reflecting over Methods declared within a class.
 * <p>
 * Sample usage:
 *		List<Method> methods =
 *		DeclaredMethods
 *		.from(RestService.class)
 *		.annotatedWith(onEventAnnotation.class)
 *		.setAccessible(true)
 *		.methods();
 * @author ttang
 *
 */
public class DeclaredMethods implements Iterable<Method> {
	
	private List<Method> methods;
	
	private DeclaredMethods(Method[] methods) {
		this.methods = Arrays.asList(methods);
	}

	/**
	 * Fetches all of the methods on a class
	 * @param type the enclosing class
	 * @return the list for chaining
	 */
	public static DeclaredMethods from(Class<?> type) {
		return new DeclaredMethods(type.getDeclaredMethods());
	}

	/**
	 * @return the Java unmodifiable list
	 */
	public List<Method> methods() {
		return Collections.unmodifiableList(methods);
	}

	/**
	 * Filters the list to contain methods annotated with annotationClass
	 * @param annotationClass
	 * @return the filtered list for chaining
	 */
	public DeclaredMethods annotatedWith(Class<? extends Annotation> annotationClass) {
		List<Method> filteredMethods = new ArrayList<Method>();
		for (Method method : methods ) {
			if (method.isAnnotationPresent(annotationClass)) {
				filteredMethods.add(method);
			}
		}

		this.methods = filteredMethods;
		return this;
	}
	
	/**
	 * Filters the methods to contain the specified method names
	 * @param names array of names
	 * @return the filtered list for chaining
	 */
	public DeclaredMethods named(String... names) {
		List<Method> filteredMethods = new ArrayList<Method>();
		for (Method method : methods ) {
			if (ArrayUtils.contains(names,method.getName())) {
				filteredMethods.add(method);
			}
		}

		this.methods = filteredMethods;
		return this;
	}
	
	/**
	 * Filters the list to contain methods with a specific return type
	 * @param returnType the return type to filter on
	 * @return the filtered list for chaining
	 */
	public DeclaredMethods withReturnType(Class<?> returnType) {
		List<Method> filteredMethods = new ArrayList<Method>();
		for (Method method : methods ) {
			if (method.getReturnType().equals(returnType)) {
				filteredMethods.add(method);
			}
		}

		this.methods = filteredMethods;
		return this;
	}
	
	/**
	 * Filter the methods to those with specific parameter types
	 * @param parameterTypes the parameter types used for matching
	 * @return the filtered list for chaining
	 */
	public DeclaredMethods withParameterTypes(Class<?>... parameterTypes) {
		List<Method> filteredMethods = new ArrayList<Method>();
		for (Method method : methods ) {
			if (Arrays.equals(method.getParameterTypes(),parameterTypes)) {
				filteredMethods.add(method);
			}
		}

		this.methods = filteredMethods;
		return this;
		
	}
	
	/**
	 * @return method list for chaining
	 * @throws NoSuchMethodException throws unless there is at least one method in the list
	 */
	public DeclaredMethods atLeastOne() throws NoSuchMethodException {
		Throw.Unless(methods.size()>1).NoSuchMethod("There are no methods");
		return this;
	}
	
	/**
	 * @return method list for chaining
	 * @throws NoSuchMethodException unless there is exactly one method in the list
	 */
	public DeclaredMethods exactlyOne() throws NoSuchMethodException {
		Throw.Unless(methods.size()==1).NoSuchMethod("Expected only one method. Found " + methods.size());
		return this;
	}

	/**
	 * @return the Java Method of the single Method in the list
	 * @throws NoSuchMethodException unless there is exactly one method in the list
	 */
	public Method method() throws NoSuchMethodException {
		return exactlyOne().methods.get(0);
	}
	
	/**
	 * Sets Accessible on all methods in the list
	 * @param flag accessibility flag
	 * @return methods list for chaining
	 */
	public DeclaredMethods setAccessible(boolean flag) {
		for (Method method : methods) {
			method.setAccessible(flag);
		}
		
		return this;
	}

	@Override
	public Iterator<Method> iterator() {
		return Collections.unmodifiableList(methods).iterator();
	}
}
