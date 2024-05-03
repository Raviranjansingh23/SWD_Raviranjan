package com.taskmaster.utility;

import com.google.gson.Gson;

public class Transformer {

	public static String getJSONString(Object data) {
		Gson gson = new Gson();
		String gsonData = gson.toJson(data);
		return gsonData;
	}
	
	public static Object getJSONObject(String data) {
		Gson gson = new Gson();
		
		try {
			Object result = gson.fromJson(data, Object.class);
			return result;
		} catch (Exception e) {
			System.out.println("Not a valid JSON string!");
		}
		return null;
	}
}
