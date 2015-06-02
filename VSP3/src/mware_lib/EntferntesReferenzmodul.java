package mware_lib;

import java.util.HashMap;
import java.util.Map;

/**
 * Das EntfernteReferenzmodul hält die Objekte auf die entfernt zugegriffen werden kann.
 * 
 * Hierzu enthält es eine Map welches den Namen auf das jeweilige Objektskeleton abbildet 
 * 
 * (siehe Entwurf)
 * 
 * @author Tim
 * 
 * Komponente: Entferntes Referenzmodul
 */
public class EntferntesReferenzmodul {
	
	private Map<String, Skeleton> map = new HashMap<String, Skeleton>();
	private boolean debug;
	
	
	public EntferntesReferenzmodul(boolean debug) {
		this.debug = debug;
	}
	
	/**
	 * Schnittstelle zum Abfragen
	 * 
	 *  * (siehe Entwurf)
	 * @param objektname
	 * @return
	 */
	public Skeleton getSkeleton(String objektname){
		Util.println(this.toString() + ": Sekeleton für Name " + objektname + " angefragt", debug);
		return map.get(objektname);
	}
	
	/**
	 * Schnittstelle zum Einfügen
	 * 
	 *  * (siehe Entwurf)
	 * @param objektname
	 * @param skeleton
	 */
	public void put(String objektname, Skeleton skeleton){
		Util.println(this.toString() + ": Sekeleton für Name " + objektname + " abgelegt", debug);
		map.put(objektname, skeleton);
	}
	
}
