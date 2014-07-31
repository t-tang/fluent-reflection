package org.ttang.fluent.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A fluent api for reflecting over Annotations. This class represents a single property of an Annotation.
 * It would usually be created by another class in the fluent reflection api,
 * such as BoundAnnotationProperty boundProperty = BoundAnnotations.on(aClass.class).matching(Threading.class).boundAnnotationProperties().named("model");
 * @author ttang
 *
 */
public class BoundAnnotationProperty {
	private Annotation annotation;
	private Method method;

	private BoundAnnotationProperty(Annotation annotation,Method method) {
		this.annotation = annotation;
		this.method = method;
	}
	
	/* package */ static List<BoundAnnotationProperty> from(Annotation annotation,Method[] methods) {
		List<BoundAnnotationProperty> properties = new ArrayList<BoundAnnotationProperty>();
		for (Method method : Arrays.asList(methods)) {
			properties.add(BoundAnnotationProperty.from(annotation,method));
		}
		return properties;
	}

	/* package */ static BoundAnnotationProperty from(Annotation annotation,Method method) {
		return new BoundAnnotationProperty(annotation,method);
	}
	
	/**
	 * type of the annotation property
	 * @return annotation property type
	 */
	public Class<?> type() {
		return method.getReturnType();
	}
	
	/**
	 * name of the annotation property
	 * @return annotation property name
	 */
	public String name() {
		return method.getName();
	}
	
	/**
	 * value of the annotation property
	 * @return annotation property value
	 * @throws NoSuchValueException if the value could not be retrieved
	 */
	public Object value() throws NoSuchValueException  {
		try {
			return method.invoke(annotation);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new NoSuchValueException("Value not retrievable for " + method.getName(),e);
		}
	}
}
