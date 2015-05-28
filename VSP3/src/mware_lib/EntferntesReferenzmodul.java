package mware_lib;

import java.util.HashMap;
import java.util.Map;

public class EntferntesReferenzmodul {
	
	private static EntferntesReferenzmodul INSTANCE = new EntferntesReferenzmodul();
	
	private Map<String, Skeleton> map = new HashMap<String, Skeleton>();
	
	
	public Skeleton getSkeleton(String objektname){
		return map.get(objektname);
	}
	
	public void put(String objektname, Skeleton skeleton){
		map.put(objektname, skeleton);
	}
	
	public static EntferntesReferenzmodul getInstance(){
		return INSTANCE;
	}
	
	public void reset(){
		
	}
}
