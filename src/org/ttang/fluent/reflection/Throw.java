package org.ttang.fluent.reflection;

/**
 * A Utility class for reducing code clutter when throwing Exceptions bounded by a condition
 * <p>
 * Sample Usage: Throw.If(true).NoSuchMethod("Always thrown");
 * Sample Usage: Throw.Unless(true).NoSuchMethod("Never thrown");
 * @author ttang
 *
 */
public class Throw {
	/**
	 * Throws if a condition is true
	 * Sample Usage: Throw.If(true).NoSuchMethod("Always thrown");
	 * @param bool - the condition to be evaluated
	 */
	public static Thrower If(boolean bool) {
		return bool ? ThrowingThrower.instance() : DefaultThrower.instance();
	}

	/**
	 * Throws unless a condition is true
	 * Sample Usage: Throw.Unless(true).NoSuchMethod("Never thrown");
	 * @param bool - the condition to be evaluated
	 */
	public static Thrower Unless(boolean bool) {
		return !bool ? ThrowingThrower.instance() : DefaultThrower.instance();
	}
	
	public interface Thrower {
		public void NoSuchMethod(String message) throws NoSuchMethodException;
		public void ClassNotFound(String message) throws ClassNotFoundException;
		public void NoSuchValue(String message) throws NoSuchValueException;
		public void NoSuchProperty(String message) throws NoSuchPropertyException;
	}
	
	public static class DefaultThrower implements Thrower {
		private static Thrower instance = new DefaultThrower();
		public static Thrower instance() { return instance; }

		public void NoSuchMethod(String message) throws NoSuchMethodException {}
		public void ClassNotFound(String message) throws ClassNotFoundException {}
		public void NoSuchValue(String message) throws NoSuchValueException {};
		public void NoSuchProperty(String message) throws NoSuchPropertyException {};
	}
	
	public static class ThrowingThrower implements Thrower {
		private static ThrowingThrower instance = new ThrowingThrower();
		public static Thrower instance() { return instance; }

		public void NoSuchMethod(String message) throws NoSuchMethodException { throw new NoSuchMethodException(message); }
		public void ClassNotFound(String message) throws ClassNotFoundException { throw new ClassNotFoundException(message); }
		public void NoSuchValue(String message) throws NoSuchValueException { throw new NoSuchValueException(message); };
		public void NoSuchProperty(String message) throws NoSuchPropertyException { throw new NoSuchPropertyException(message); };
	}
}
