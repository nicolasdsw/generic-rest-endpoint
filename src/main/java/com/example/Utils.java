package com.example;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Utils {

	private Utils() throws IllegalAccessException {
		throw new IllegalAccessException("Util class!");
	}

	/**
	 * Helper method to check if an object is a primitive type or a wrapper class of
	 * 
	 * @param object
	 * @return true | false
	 */
	// a primitive type
	public static boolean isPrimitiveOrWrapper(Object object) {
		return object.getClass().isPrimitive() || object instanceof Boolean || object instanceof Character
				|| object instanceof Byte || object instanceof Short || object instanceof Integer
				|| object instanceof Long || object instanceof Float || object instanceof Double
				|| object instanceof Void;
	}

	public static Object addTypes(Object object) {
		if (object == null) {
			return null;
		}
		try {
			if (Utils.isPrimitiveOrWrapper(object)) {
				return object;
			}
			JsonWrapper<Object> jsonWrapper = new JsonWrapper<>();
			jsonWrapper.setType(object.getClass().getName());
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.trySetAccessible();
				if (field.canAccess(object)) {
					Object fieldValue = field.get(object);
					field.set(object, addTypes(fieldValue));
				}
			}
			jsonWrapper.setInner(object);
			return jsonWrapper;
		} catch (Exception e) {
		}
		return object;
	}

	public static List<Object> removeTypes(List<Object> objects) {
		List<Object> arr = new ArrayList<>();
		for (Object entry : objects) {
			Object e = Utils.removeTypes(entry);
			arr.add(e);
		}
		return arr;
	}

	public static List<Object> addTypes(List<Object> objects) {
		List<Object> arr = new ArrayList<>();
		for (Object entry : objects) {
			Object e = Utils.addTypes(entry);
			arr.add(e);
		}
		return arr;
	}

	public static Object removeTypes(Object object) {
		if (object == null) {
			return null;
		}
		if (!(object instanceof LinkedHashMap)) {
			return object;
		}
		try {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> attributesMap = (LinkedHashMap<String, Object>) object;
			String typeName = (String) attributesMap.get("__type");
			Class<?> objectType = Class.forName(typeName);
			Object originalObject = objectType.getDeclaredConstructor().newInstance();
			attributesMap.remove("__type");

			Field[] fields = objectType.getDeclaredFields();
			for (Field field : fields) {
				field.trySetAccessible();
				if (field.canAccess(originalObject)) {
					String fieldName = field.getName();
					Object processedFieldValue = attributesMap.get(fieldName);
					Object originalFieldValue = removeTypes(processedFieldValue);
					field.set(originalObject, originalFieldValue);
				}
			}
			return originalObject;
		} catch (Exception e) {
		}
		return null;
	}
}
