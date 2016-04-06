package com.lyx.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;

public class CommUtil {
	
	public static final String TAG = "CommUtil";
	
	/**
	 * 获取机器唯一编码,此代码只在真机有用
	 * @param context
	 * @return
	 */
	public static String getMachineId(Context context){
		
		String machineId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		return machineId;
		
	}
	
	/**
     * 获取当前应用版本号
     * 
     * @author lijc getVersionCode:(这里用一句话描述这个方法的作用). <br/>
     * @param context
     * @return 当前应用的版本号，如果获取失败则返回0
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageManager manager;
        PackageInfo info = null;
        manager = context.getPackageManager();
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (NameNotFoundException e) {
            versionCode = 0;
        }
        return versionCode;
    }
    
    public static String getVersionName(Context context) {
        String versionName = "";
        PackageManager manager;
        PackageInfo info = null;
        manager = context.getPackageManager();
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;
        } catch (NameNotFoundException e) {
        	versionName = "";
        }
        return versionName;
    }
	
	public static int getRIdFromName(Class<?> cls, String filedName)
	{
		try {
			Field f = cls.getField(filedName);
			return Integer.parseInt(f.get(null).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static int getIntAttribute(Attributes attr, String name, int defaultVal)
	{
		String value = attr.getValue(name);
		if(value == null)
			return defaultVal;
		
		try
		{
			return Integer.parseInt(value);
		}
		catch(Exception ex)
		{
			return defaultVal;
		}
	}
	
	public static double getDoubleAttribute(Attributes attr, String name, double defaultVal)
	{
		String value = attr.getValue(name);
		if(value == null)
			return defaultVal;
		
		try
		{
			return Double.parseDouble(value);
		}
		catch(Exception ex)
		{
			return defaultVal;
		}
	}
	
	public static Map<String, String> attributeToStringMap(Attributes attrs)
	{
    	Map<String, String> data = new HashMap<String, String>();
    	for (int i = 0; i < attrs.getLength(); i++) {
    		data.put(attrs.getLocalName(i), attrs.getValue(i).trim());
    	}
    	return data;
	}
	
	public static int getIntFromStr(String val, int defaultVal)
	{
		try
		{
			return Integer.parseInt(val);
		}
		catch(Exception ex)
		{
			return defaultVal;
		}
	}
	
	public static float getFloatFromStr(String val, float defaultVal)
	{
		try
		{
			return Float.parseFloat(val);
		}
		catch(Exception ex)
		{
			return defaultVal;
		}
	}
	
	public static byte[] objectToStream(Object obj)
	{
		try {
			ByteArrayOutputStream buff = new ByteArrayOutputStream();
			ObjectOutputStream stream = new ObjectOutputStream(buff);
			stream.writeObject(obj);
			stream.close();
			return buff.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取XML的子节点中的内容
	 * @param parent
	 * @param subName
	 * @return
	 */
	public static String getXmlSubContent(Element parent, String subName)
	{
		NodeList list = parent.getElementsByTagName(subName);
		if(list == null || list.getLength() == 0)
			return null;
		Element subItem = (Element)list.item(0);
		return subItem.getFirstChild().getNodeValue();		
	}
	
	/**
	 * * This method convets dp unit to equivalent device specific value in
	 * pixels. * * @param dp A value in dp(Device independent pixels) unit.
	 * Which we need to convert into pixels * @param context Context to get
	 * resources and device specific display metrics * @return A float value to
	 * represent Pixels equivalent to dp according to device
	 */
	public static float dp2px(Context context,float dp) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	/**
	 * * This method converts device specific pixels to device independent
	 * pixels. * * @param px A value in px (pixels) unit. Which we need to
	 * convert into db * @param context Context to get resources and device
	 * specific display metrics * @return A float value to represent db
	 * equivalent to px value
	 */
	public static float px2dp(Context context,float px) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}
	
	 /** 
     * 将px值转换为sp值，保证文字大小不变 
     *  
     * @param pxValue 
     * @param fontScale 
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static float px2sp(Context context, float pxValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (float) (pxValue / fontScale + 0.5f);  
    }  
  
    /** 
     * 将sp值转换为px值，保证文字大小不变 
     *  
     * @param spValue 
     * @param fontScale 
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static float sp2px(Context context, float spValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (float) (spValue * fontScale + 0.5f);  
    }  
	/**
	 * 通过Uri得到绝对路径
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getPath(Context context, Uri uri) {
		// 查询，返回cursor
		Cursor cursor = context.getContentResolver().query(uri, null, null,
				null, null);
		// 第一行第二列保存路径strRingPath
		cursor.moveToFirst();
		String strRingPath = cursor.getString(1);
		cursor.close();
		return strRingPath;
	}
	
	/**    
	 
	 * java反射bean的get方法    
	 
	 *     
	 
	 * @param objectClass    
	 
	 * @param fieldName    
	 
	 * @return    
	 
	 */       
	  
	@SuppressWarnings("unchecked")       
	  
	public static Method getGetMethod(Class objectClass, String fieldName) {       
	  
	    StringBuffer sb = new StringBuffer();       
	  
	    sb.append("get");       
	  
	    sb.append(fieldName.substring(0, 1).toUpperCase());       
	  
	    sb.append(fieldName.substring(1));       
	  
	    try {       
	  
	        return objectClass.getMethod(sb.toString());       
	  
	    } catch (Exception e) {       
	  
	    }       
	  
	    return null;       
	  
	}       
	  
	       
	  
	/**    
	 
	 * java反射bean的set方法    
	 
	 *     
	 
	 * @param objectClass    
	 
	 * @param fieldName    
	 
	 * @return    
	 
	 */       
	  
	@SuppressWarnings("unchecked")       
	  
	public static Method getSetMethod(Class objectClass, String fieldName) {       
	  
	    try {       
	  
	        Class[] parameterTypes = new Class[1];       
	  
	        Field field = objectClass.getDeclaredField(fieldName);       
	  
	        parameterTypes[0] = field.getType();       
	  
	        StringBuffer sb = new StringBuffer();       
	  
	        sb.append("set");       
	  
	        sb.append(fieldName.substring(0, 1).toUpperCase());       
	  
	        sb.append(fieldName.substring(1));       
	  
	        Method method = objectClass.getMethod(sb.toString(), parameterTypes);       
	  
	        return method;       
	  
	    } catch (Exception e) {       
	  
	        e.printStackTrace();       
	  
	    }       
	  
	    return null;       
	  
	}       
	  
	       
	  
	/**    
	 
	 * 执行set方法    
	 
	 *     
	 
	 * @param o执行对象    
	 
	 * @param fieldName属性    
	 
	 * @param value值    
	 
	 */       
	  
	public static void invokeSet(Object o, String fieldName, Object value) {       
	  
	    Method method = getSetMethod(o.getClass(), fieldName);       
	  
	    try {       
	  
	        method.invoke(o, new Object[] { value });       
	  
	    } catch (Exception e) {       
	  
	        e.printStackTrace();       
	  
	    }       
	  
	}       
	  
	       
	  
	/**    
	 
	 * 执行get方法    
	 
	 *     
	 
	 * @param o执行对象    
	 
	 * @param fieldName属性    
	 
	 */       
	  
	public static Object invokeGet(Object o, String fieldName) {       
	  
	    Method method = getGetMethod(o.getClass(), fieldName);       
	  
	    try {       
	  
	        return method.invoke(o, new Object[0]);       
	  
	    } catch (Exception e) {       
	  
	        e.printStackTrace();       
	  
	    }       
	  
	    return null;       
	  
	}  
	
	
	 public static String getFromAssets(Context context,String fileName){ 
         try { 
              InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open(fileName) ); 
             BufferedReader bufReader = new BufferedReader(inputReader);
             String line="";
             String Result="";
             while((line = bufReader.readLine()) != null)
                 Result += line;
             return Result;
         } catch (Exception e) { 
             e.printStackTrace(); 
         }
		return null;
	 } 
	 
	 public static String IntegerToString(String str) {
			Pattern p = Pattern.compile("&#[0-9]{5};");
			Matcher m = p.matcher(str);
			String g = "";
			String num = "";
			char c = 0;
			while (m.find()) {
				g = m.group();
				num = g.substring(2, 7);
				c = (char) (Integer.parseInt(num));
				str = str.replace(g, "" + c);
			}
			return str;
	}
	
	 public static <T> List<T> obtainList(){
		 return new ArrayList<T>();
	 }
	 
	 public static <K,V> Map<K,V> obtainMap(){
		 return new HashMap<K,V>();
	 }
	 
	 public static <K,V> LinkedHashMap<K,V> obtainLinkedMap(){
		 return new LinkedHashMap<K,V>();
	 }
	 
	 public static Map<String,String> converJson2Map(JSONObject jsonObject){
		 if(jsonObject == null) return null ;
		 @SuppressWarnings("unchecked")
		Iterator <String> ite =  jsonObject.keys()  ;
		 Map<String,String>  map = new HashMap<String,String>() ;
		 while(ite.hasNext()){
			 String key = ite.next()  ;
			 map.put(key, jsonObject.optString(key))  ;
		 }
		 return map ;
	 }
	 public static List<Map<String,String>> converJson2List(JSONArray jsonArray){
		 List<Map<String,String>> list = CommUtil.obtainList()  ;
		 for(int index = 0 ;index<jsonArray.length();index++){
			 JSONObject obj = null;
			try {
				obj = jsonArray.getJSONObject(index);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			 if(obj != null) list.add(converJson2Map(obj))  ;
		 }
		 return list ;
	 }
	 public static <T> T converMap2Object(Map<String,String> map,Class<T> cls){
		 
		 Field[] fields = cls.getDeclaredFields()  ;
		 
		T t = null;
		try {
			t = (T) cls.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		 for(Field f : fields){
			 f.setAccessible(true);
			 invokeSet(t, f.getName(), map.get(f.getName()));
		 }
		 return t ;
	 }
	 
	 /**
	  * 对象复制
	  * @param from
	  * @param to
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	  */
	 public static void objectCopy(Object from,Object to) throws Exception{
		 
		 if(from.getClass() != to.getClass()){
			 throw new IllegalArgumentException("[objectCopy]The left and right must be same class") ;
		 }
		 Class<?> clz = from.getClass() ;
		 Field[] fs =  clz.getDeclaredFields() ;
		 for (int i = 0; i < fs.length; i++) {
			Field field = fs[i];
			
			field.setAccessible(true) ;
			
			Object value = field.get(from) ;
			field.set(to, value) ;
		}
	 }
}
