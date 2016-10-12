package sinia.com.smartmart.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import sinia.com.smartmart.bean.JsonBean;

public class JsonUtil {

	public static boolean isJsonObject(String jsonData) {
		boolean flag = true;
		try {
			JsonParser jsonParser = new JsonParser();
			flag = jsonParser.parse(jsonData).getAsJsonObject().isJsonObject();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean isJsonArray(String jsonData) {
		boolean flag = true;
		try {
			JsonParser jsonParser = new JsonParser();
			flag = jsonParser.parse(jsonData).getAsJsonArray().isJsonArray();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static JsonBean getJsonBean(String jsonData) {
		JsonBean obj = null;
		Gson gson = new Gson();
		if (isJsonObject(jsonData)) {
			obj = gson.fromJson(jsonData, new TypeToken<JsonBean>() {
			}.getType());
		}
		return obj;
	}

}
