package mware_lib;

import java.util.HashMap;
import java.util.Map;

public class Util {
	 public static Map<Class<?>, Class<?>> PRIMITIVESMAP = getMap();
	 
	 public static void println(String string, boolean debug){
		 if (debug){
			System.out.println(string); 
		 }
	 }
	 
	 	 
	 
	 private static Map<Class<?>, Class<?>> getMap(){
		 Map<Class<?>, Class<?>> map= new HashMap<Class<?>, Class<?>>();
	     map.put(boolean.class, Boolean.class);
	     map.put(byte.class, Byte.class);
	     map.put(char.class, Character.class);
	     map.put(double.class, Double.class);
	     map.put(float.class, Float.class);
	     map.put(int.class, Integer.class);
	     map.put(long.class, Long.class);
	     map.put(short.class, Short.class);
	     map.put(void.class, Void.class);		 
		 return map;
	 }
	 
	 
}
