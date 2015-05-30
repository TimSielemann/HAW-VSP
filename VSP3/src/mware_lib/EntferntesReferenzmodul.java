package mware_lib;

import java.util.HashMap;
import java.util.Map;

public class EntferntesReferenzmodul {
	
	private Map<String, Skeleton> map = new HashMap<String, Skeleton>();
	private boolean debug;
	
	
	public EntferntesReferenzmodul(boolean debug) {
		this.debug = debug;
	}

	public Skeleton getSkeleton(String objektname){
		Util.println(this.toString() + ": Sekeleton für Name " + objektname + " angefragt", debug);
		return map.get(objektname);
	}
	
	public void put(String objektname, Skeleton skeleton){
		Util.println(this.toString() + ": Sekeleton für Name " + objektname + " abgelegt", debug);
		map.put(objektname, skeleton);
	}
	
	public void reset(){
		Util.println(this.toString() + ": Referenzmodul geleert", debug);
		map.clear();
	}
}
