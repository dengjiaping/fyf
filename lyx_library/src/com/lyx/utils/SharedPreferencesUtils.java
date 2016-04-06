package com.lyx.utils;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
	
	private Context context;
	private SharedPreferences sp;
	private String fileName;

	public SharedPreferencesUtils(Context context, String fileName) {
		this.context = context;
		this.fileName = fileName;
		this.sp = context.getSharedPreferences(this.fileName,
				Context.MODE_PRIVATE);
	}

	public void clearData() {
		sp.edit().clear().commit();

	}

	public void deleteFile() {
		String dirPath = "/data/data/" + context.getPackageName()
				+ "/shared_prefs";
		String name = this.fileName + ".xml";
		File file = new File(dirPath, name);
		// LogUtils.print(tag, dirPath + "/" + name);
		if (file.exists()) {
			file.delete();
		}
	}
	
	public boolean hasKey(String key){
		return sp.contains(key) ;
	}

	public void setInt(String key, int value) {
		sp.edit().putInt(key, value).commit();
	}

	public int getInt(String key) {
		return sp.getInt(key, 0);
	}

	public void setlong(String key, long value) {
		sp.edit().putLong(key, value).commit();
	}

	public long getlong(String key) {
		return sp.getLong(key, 0l);
	}

	public void setFloat(String key, float value) {
		sp.edit().putFloat(key, value).commit();
	}

	public Float getFloat(String key) {
		return sp.getFloat(key, 0f);
	}

	public void setBoolean(String key, boolean value) {
		sp.edit().putBoolean(key, value).commit();
	}

	public Boolean getBoolean(String key) {
		return sp.getBoolean(key, false);
	}

	public void setString(String key, String value) {
		sp.edit().putString(key, value).commit();
	}

	public String getString(String key) {
		return sp.getString(key, "");
	}
}
