fluent-reflection
=================

This is a fluent style reflection library, it makes Java reflection very simple to use and it's succintness heavily reduces code clutter. It improves on Java reflection by explicitly modeling Annotations so that annotation reflection code is more obvious.

The library evolved out of work on another project which required fairly complex reflection usage. As such the fluent library does not offer all of the reflection functionality, it will be further developed as needs arise.

Some Examples:

This code snippet gets the type of the state value from the "OnEvent" annotation declared in the RestService class.

```java
			Class<?> stateType =
					DeclaredClasses
					.from(RestService.class).named("OnEvent")
					.annotationProperties().named("state").type();
```					

This code gets all the methods on the RestService class which are annotated with OnEvent.

```java
			List<Method> methods =
					DeclaredMethods
					.from(RestService.class)
					.annotatedWith(OnEvent.class)
					.setAccessible(true)
					.methods();
```
