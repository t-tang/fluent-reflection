package org.ttang.fluent.reflection;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A fluent api for reflection over Annotation properties (aka values)
 * @author ttang
 *
 */
public class BoundAnnotationProperties implements Iterable<BoundAnnotationProperty>{
	private List<BoundAnnotationProperty> properties;

	private BoundAnnotationProperties(Annotation annotation) {
		this.properties = BoundAnnotationProperty.from(annotation,annotation.annotationType().getDeclaredMethods());
	}
	
	/**
	 * Create from a Java Annotation
	 * @param annotation
	 * @return BoundAnnotationProperties for fluent chaining
	 */
	public static BoundAnnotationProperties of(Annotation annotation) {
		return new BoundAnnotationProperties(annotation);
	}

	/**
	 * Fetch the BoundAnnotationProperty named by name
	 * @param name
	 * @return BoundAnnotationProperty for fluent chaining
	 * @throws NoSuchPropertyException if there is no value called name
	 */
	public BoundAnnotationProperty named(String name) throws NoSuchPropertyException {
		for (BoundAnnotationProperty property : properties) {
			if (property.name().equals(name)) {
				return property;
			}
		}
		
		throw new NoSuchPropertyException(name + " was not found");
	}

	@Override
	public Iterator<BoundAnnotationProperty> iterator() {
		return Collections.unmodifiableList(properties).iterator();
	}
}
