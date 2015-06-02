package mware_lib;
/**
 * Schnittstelle des Nameservices
 * 
 * 
 * @author Tim
 * 
 * Komponente mwar_lib
 */
public interface INameService {
	/**
	 * Name des Nameservice Objektes auf dem Nameservice Server
	 */
	public static final String NAMESERVICENAME = "nameservice";
	
	/**
	 * Verbindet ein übergebenes Objekt mit einem Namen
	 * (siehe Entwurf)
	 * @param servant
	 * @param name
	 */
	void rebind(Object servant, String name);
	
	/**
	 * Gibt ein Object zu einem Namen oder null zurück
	 * (siehe Entwurf)
	 * @param name
	 * @return
	 */
	Object resolve(String name);
	
}
