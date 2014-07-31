package org.ttang.fluent.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * A fluent style API for reflecting over Annotations.
 * Use BoundAnnotationProperties to access the actual values.
 * The properties method will convert a declared annotation into a bound annotation.
 * <p>
 * Sample Usage: DeclaredAnnotations.on(MyClass".class.named("Singleton","Immutable").properties()
 * @author ttang
 *
 */
public class BoundAnnotations implements Iterable<Annotation> {

	private List<Annotation> annotations;

	private BoundAnnotations(Annotation[] annotations) {
		this.annotations = Arrays.asList(annotations);
	}

	/**
	 * Generates a list of annotations applied to a class
	 * <p>
	 * Sample Usage: DeclaredAnnotations.on(MyClass.class)
	 * @param annotatedClass the annotated class
	 * @return annotations for chaining
	 */
	public static BoundAnnotations on(Class<?> annotatedClass) {
		return new BoundAnnotations(annotatedClass.getDeclaredAnnotations());
	}

	/**
	 * Generates a list of annotations applied to a method
	 * <p>
	 * Sample Usage: DeclaredAnnotations.on(MyMethod)
	 * @param annotatedMethod the annotated method
	 * @return annotations for chaining
	 */
	public static BoundAnnotations on(Method annotatedMethod) {
		return new BoundAnnotations(annotatedMethod.getAnnotations());
	}

	/**
	 * Filters the annotations to only contain names
	 * <p>
	 * Sample Usage: DeclaredAnnotations.on(MyClass.class).named("Singleton","Immutable")
	 * @param names for filtering with
	 * @return annotations for chaining
	 */
	public BoundAnnotations named(String... names) {
		List<Annotation> filteredAnnotations = new ArrayList<Annotation>();
		for (Annotation annotation : annotations) {
			if (ArrayUtils.contains(names, annotation.annotationType().getSimpleName())) {
				filteredAnnotations.add(annotation);
			}
		}

		this.annotations = filteredAnnotations;
		return this;
	}

	/**
	 * Filters the list to only contain specific Annotations
	 * <p>
	 * Sample Usage: DeclaredAnnotations.on(MyClass.class).matching(Singleton.class,Immutable.class)
	 * @param types for filtering with
	 * @return annotations for chaining
	 */
	@SafeVarargs
	public final BoundAnnotations matching(Class<? extends Annotation>... types) {
		List<Annotation> filteredAnnotations = new ArrayList<Annotation>();
		for (Annotation annotation : annotations) {
			if (ArrayUtils.contains(types, annotation.annotationType())) {
				filteredAnnotations.add(annotation);
			}
		}

		this.annotations = filteredAnnotations;
		return this;
	}

	/**
	 * Ensure only one annotation in the list
	 * @return annotations for chaining
	 * @throws NoSuchPropertyException unless exactly one annotation in the list
	 */
	public BoundAnnotations exactlyOne() throws NoSuchPropertyException { 
		Throw.Unless(annotations.size()==1).NoSuchProperty("Expected exactly one annotation. Found " + annotations.size());
		return this;
	}

	/**
	 * Converts the single annotation in the list to a Java Annotation 
	 * @return Java Annotation
	 * @throws NoSuchPropertyException unless there is exactly one annotation in the list
	 */
	public Annotation annotation() throws NoSuchPropertyException {
		return exactlyOne().annotations.get(0);
	}

	/**
	 * Converts the annotations into a Java List<Annotation>
	 * @return an unmodifiable Java List<Annotation>
	 */
	public List<Annotation> annotations() {
		return Collections.unmodifiableList(annotations);
	}


	/**
	 * Convert the single annotation into BoundAnnotationProperties
	 * @return BoundAnnotationProperties for further processing
	 * @throws NoSuchPropertyException unless there is exactly one annotation in the list
	 */
	public BoundAnnotationProperties properties() throws NoSuchPropertyException {
		return BoundAnnotationProperties.of(this.annotation());
	}

	@Override
	public Iterator<Annotation> iterator() {
		return Collections.unmodifiableList(annotations).iterator();
	}
}
