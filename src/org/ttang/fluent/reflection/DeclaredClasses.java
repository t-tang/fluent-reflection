package org.ttang.fluent.reflection;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A fluent style API for reflecting over classes declared within a class.
 * <p>
 * Sample Usage: 
 * 	DeclaredClasses
 * 	.from(aClass.class).named("OnEvent")
 *	.annotationProperties().named("state").value();
 * @author ttang
 *
 */
public class DeclaredClasses implements Iterable<Class<?>> {

	private List<Class<?>> classes;

	private DeclaredClasses(Class<?>[] classes) {
		this.classes = Arrays.asList(classes);
	}
	
	/**
	 * Retrieves all the classes contained within the enclosing class
	 */
	public static DeclaredClasses from(Class<?> enclosingClass) {
		return new DeclaredClasses(enclosingClass.getDeclaredClasses());
	}

	/**
	 * Narrows the list to the class called <b>name</b> if it exists
	 * <p>
	 * Sample usage: DeclaredClasses.from(MyClass.class).named("MyContainedClass")
	 */
	public DeclaredClasses named(String name) {
		List<Class<?>> filteredClasses = new ArrayList<Class<?>>();
		for (Class<?> clazz : classes) {
			if (clazz.getSimpleName().equals(name)) {
				filteredClasses.add(clazz);
			}
		}
		
		this.classes = filteredClasses;
		return this;
	}
	
	/**
	 * throws if the list has not been narrowed to contain exactly one class
	 * <p>
	 * Sample usage: DeclaredClasses.from(MyClass.class).named("MyContainedClass").exactlyOne()
	 * @throws ClassNotFoundException if the list does not contain one class
	 */
	public DeclaredClasses exactlyOne() throws ClassNotFoundException {
		Throw.Unless(classes.size() == 1).ClassNotFound("Expected exactly one class. Found " + classes.size());
		return this;
	}
	
	/**
	 * Returns the Java class of the one and only class in the list
	 * <p>
	 * Sample usage: Class<?> clazz = DeclaredClasses.from(MyClass.class).named("MyContainedClass").type();
	 * @throws ClassNotFoundException if the list has not been narrowed to a single class
	 */
	public Class<?> type() throws ClassNotFoundException {
		return exactlyOne().classes.get(0);
	}
	
	/**
	 * 
	 * Sample usage: List<Class<?>> classes = DeclaredClasses.from(MyClass.class).types();
	 * @return unmodifiable list of classes
	 */
	public List<Class<?>> types() {
		return Collections.unmodifiableList(classes);
	}
	
	/**
	 * Casts the single class into an annotation and retrieves it's properties
	 * <p>
	 * Sample usage: AnnotationProperties properties = DeclaredClasses.from(MyClass.class).named("MyContainedClass").annotationProperties();
	 * @return The annotation properties for an annotation
	 * @throws ClassNotFoundException unless there is exactly one class in the list
	 */
	public DeclaredAnnotationProperties annotationProperties() throws ClassNotFoundException {
		return DeclaredAnnotationProperties.from(this.annotationType());
	}
	
	/**
	 * Fetches the annotations on the single class
	 * @return BoundAnnotations for fluent chaining
	 * @throws ClassNotFoundException unless there is exactly one class in the list
	 */
	public BoundAnnotations boundAnnotations() throws ClassNotFoundException  {
		return BoundAnnotations.on(this.type());
	}
	
	/**
	 * Casts the single class to a Java annotation class
	 * <p>
	 * Sample usage: Class<? extends Annotation> clazz = DeclaredClasses.from(MyClass.class).named("MyContainedClass").annotationType();
	 * @return the Java class for the annotation
	 * @throws ClassNotFoundException unless the class is an Annotation or if the list has not been narrowed to a single class
	 */
	@SuppressWarnings("unchecked")
	public Class<? extends Annotation>annotationType() throws ClassNotFoundException {
		Class<?> type = this.type();
		Throw.Unless(this.type().isAnnotation()).ClassNotFound(type.getCanonicalName() + " is not an annotation");
		return (Class<? extends Annotation>)type;
	}

	@Override
	public Iterator<Class<?>> iterator() {
		return Collections.unmodifiableList(classes).iterator();
	}
}
