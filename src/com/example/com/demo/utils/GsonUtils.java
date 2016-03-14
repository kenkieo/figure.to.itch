package com.example.com.demo.utils;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;

public class GsonUtils {

	private static Gson gson = new Gson();

	private volatile static GsonUtils instance;

	private GsonUtils() {

	}

	public static GsonUtils getInstance() {
		if (instance == null) {
			synchronized (GsonUtils.class) {
				if (instance == null) {
					instance = new GsonUtils();
				}
			}
		}
		return instance;
	}

	public static String toJson(List<?> list, Type typeOfT) {
		String result = null;
		try {
			result = gson.toJson(list, typeOfT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static <T> T fromJson(String response, Type typeOfT) {
		try {
			return gson.fromJson(response, typeOfT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
