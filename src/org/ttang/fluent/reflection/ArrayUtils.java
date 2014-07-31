package org.ttang.fluent.reflection;

/**
 * Array utility class
 * @author ttang
 *
 */
public class ArrayUtils {
	/**
	 * @param array the array to be searched
	 * @param item the item to be matched
	 * @return true if the array contains item as determined by equals
	 */
	public static <T> boolean contains(T[] array, T item) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(item)) {
				return true;
			}
		}
		return false;
	}
}
