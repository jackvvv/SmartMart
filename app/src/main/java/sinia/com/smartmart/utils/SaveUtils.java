package sinia.com.smartmart.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SaveUtils {
	public static Object getShareValue(Context context, String space, String key) {
		SharedPreferences sp;
		if (StringUtil.isEmpty(space)) {
			sp = context.getSharedPreferences("default", 0);
		} else {
			sp = context.getSharedPreferences(space, 0);
		}

		return sp.getAll().get(key);

	}

	public static Object getShareValue(Context context, String key) {
		return getShareValue(context, null, key);

	}

	public static void putShareValue(Context context, String space, String key,
									 Object value) {
		SharedPreferences sp;
		SharedPreferences.Editor editor;
		if (StringUtil.isEmpty(space)) {
			sp = context.getSharedPreferences("default", 0);
		} else {
			sp = context.getSharedPreferences(space, 0);
		}
		editor = sp.edit();

		if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Float) {
			editor.putFloat(key, (Float) value);
		} else if (value instanceof Long) {
			editor.putLong(key, (Long) value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		}
		editor.commit();
	}

	public static void putShareValue(Context context, String key, Object value) {
		putShareValue(context, null, key, value);
	}

	public static void removeShareValue(Context context, String space,
										String key) {
		SharedPreferences sp;
		SharedPreferences.Editor editor;
		if (StringUtil.isEmpty(space)) {
			sp = context.getSharedPreferences("default", 0);
		} else {
			sp = context.getSharedPreferences(space, 0);
		}
		editor = sp.edit();
		editor.remove(key);
		editor.commit();

	}

	public static void removeShareValue(Context context, String key) {
		removeShareValue(context, null, key);
	}

	public static void clearShareValue(Context context, String space, String key) {
		SharedPreferences sp;
		SharedPreferences.Editor editor;
		if (StringUtil.isEmpty(space)) {
			sp = context.getSharedPreferences("default", 0);
		} else {
			sp = context.getSharedPreferences(space, 0);
		}
		editor = sp.edit();
		editor.clear();
		editor.commit();

	}

	public static void clearShareValue(Context context, String key) {
		clearShareValue(context, null, key);
	}

	public static void putShareObject(Context context, String space,
									  String key, Object value) {
		SharedPreferences sp;
		SharedPreferences.Editor editor;
		if (StringUtil.isEmpty(space)) {
			sp = context.getSharedPreferences("default", 0);
		} else {
			sp = context.getSharedPreferences(space, 0);
		}
		editor = sp.edit();
		editor.putString(key, objectToJson(value).toString());

		editor.commit();
	}

	public static void putShareObject(Context context, String key, Object value) {
		putShareObject(context, null, key, value);
	}

	public static String getShareJson(Context context, String space, String key) {
		SharedPreferences sp;
		if (StringUtil.isEmpty(space)) {
			sp = context.getSharedPreferences("default", 0);
		} else {
			sp = context.getSharedPreferences(space, 0);
		}
		String json = sp.getString(key, "");

		return json;

	}

	public static String getShareJson(Context context, String key) {
		return getShareJson(context, null, key);

	}

	public static <T> T getShareObject(Context context, String space,
									   String key, Class cls) {
		SharedPreferences sp;
		if (StringUtil.isEmpty(space)) {
			sp = context.getSharedPreferences("default", 0);
		} else {
			sp = context.getSharedPreferences(space, 0);
		}
		String json = sp.getString(key, "");

		return (T) jsonToObject(json, cls);

	}

	public static <T> T getShareObject(Context context, String key, Class cls) {
		return (T) getShareObject(context, null, key, cls);

	}

	public static Object objectToJson(Object object) {
		Class cls = object.getClass();
		Field[] fields = cls.getDeclaredFields();
		JSONObject jsonObject = new JSONObject();
		for (Field field : fields) {
			field.setAccessible(true);
			try {

				if (field.get(object) instanceof List) {
					List<?> lists = (List<?>) field.get(object);

					JSONArray jsonArray = new JSONArray();
					for (int i = 0; i < lists.size(); i++) {
						if (lists.get(i) instanceof Object
								&& !isBasicType(lists.get(i))) {
							jsonArray.put(objectToJson(lists.get(i)));
						} else {
							jsonArray.put(lists.get(i));
						}

					}
					jsonObject.put(field.getName(), jsonArray);

				} else {

					if (field.get(object) instanceof Object
							&& !isBasicType(field.get(object))) {
						jsonObject.put(field.getName(),
								objectToJson(field.get(object)));

					} else {
						jsonObject.put(field.getName(), field.get(object));
					}

				}

			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}

	public static <T> T jsonToObject(String json, Class cls) {
		Field[] fields = cls.getDeclaredFields();
		Object object = null;
		try {
			object = cls.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}

		try {
			if (!StringUtil.isEmpty(json)) {
				JSONObject jsonObject = new JSONObject(json);
				for (Field field : fields) {
					field.setAccessible(true);
					Type type = field.getGenericType();
					isBasicClass(type);
					try {
						if (!jsonObject.isNull(field.getName())
								&& jsonObject.get(field.getName()) instanceof JSONArray) {

							JSONArray jsonArray = (JSONArray) jsonObject
									.get(field.getName());

							List<T> list = new ArrayList<T>();

							for (int i = 0; i < jsonArray.length(); i++) {
								Object obj = jsonArray.get(i);
								if (!isBasicClass(type)) {
									list.add((T) jsonToObject(obj.toString(),
											getClass(type)));
								} else {
									list.add((T) jsonArray.get(i));
								}
							}
							field.set(object, list);
						} else {
							if (!isBasicClass(type)) {
								field.set(
										object,
										jsonToObject(
												jsonObject.get(field.getName())
														.toString(),
												getClass(type)));
							} else {
								if (!jsonObject.isNull(field.getName())) {
									field.set(object,
											jsonObject.get(field.getName()));
								} else {

								}

							}

							if (!jsonObject.isNull(field.getName())) {
								field.set(object,
										jsonObject.get(field.getName()));
							}

						}

					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return (T) object;

	}

	private static boolean isBasicType(Object object) {
		boolean isBasicType = false;
		if (object instanceof String) {
			isBasicType = true;
		} else if (object instanceof Integer) {
			isBasicType = true;
		} else if (object instanceof Boolean) {
			isBasicType = true;
		} else if (object instanceof Long) {
			isBasicType = true;
		} else if (object instanceof Character) {
			isBasicType = true;
		} else if (object instanceof Float) {
			isBasicType = true;
		} else if (object instanceof Double) {
			isBasicType = true;
		} else if (object instanceof Short) {
			isBasicType = true;
		} else if (object instanceof Byte) {
			isBasicType = true;
		} else if (object instanceof Enum) {
			isBasicType = true;
		}

		return isBasicType;
	}

	@SuppressWarnings("unused")
	private static boolean isBasicClass(Type type) {
		boolean isBasicClass = false;
		String typeName = type.toString();
		if (type.toString().contains("java.lang.String")
				|| type.toString().contains("java.lang.Integer")
				|| type.toString().contains("java.lang.Boolean")
				|| type.toString().contains("java.lang.Long")
				|| type.toString().contains("java.lang.Double")
				|| type.toString().contains("java.lang.Float")
				|| type.toString().contains("java.lang.Character")
				|| type.toString().contains("java.lang.Short")
				|| type.toString().contains("java.lang.Byte")
				|| type.toString().equals("java.lang.Enum")) {
			isBasicClass = true;
		} else if (type.toString().equals("int")
				|| type.toString().equals("boolean")
				|| type.toString().equals("long")
				|| type.toString().equals("char")
				|| type.toString().equals("float")
				|| type.toString().equals("double")
				|| type.toString().equals("short")
				|| type.toString().equals("byte")
				|| type.toString().equals("enum")) {
			isBasicClass = true;
		}
		return isBasicClass;
	}

	@SuppressWarnings("rawtypes")
	private static Class getClass(Type type) {
		String typeName = type.toString();

		if (typeName.contains("<") && typeName.contains(">")) {
			int begin = typeName.indexOf("<");
			int end = typeName.lastIndexOf(">");
			typeName = typeName.substring(begin + 1, end);
		} else {
			typeName = typeName.substring(5).trim();
		}

		try {
			Class cls = Class.forName(typeName);
			return cls;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
