package mware_lib;



//- Front-End der Middleware -
/**
 * Schnittstelle nach Entwurf
 * @author Tim
 *
 * Komponente: MWare_Lib
 */
public class ObjectBroker { 

	private static final int PORT = 53627;
	
	private NameService nameService;
	private Kommunikationsmodul kommunikationsmodul;
	private EntferntesReferenzmodul refmodul;
	private boolean debug;
	
	
	
	public ObjectBroker(String serviceHost, int listenPort, boolean debug) {
		this.refmodul = new EntferntesReferenzmodul(debug);
		this.kommunikationsmodul = new Kommunikationsmodul(PORT, refmodul, debug);		
		this.nameService = new NameService(serviceHost, listenPort, debug, refmodul, kommunikationsmodul);
		this.debug = debug;
	}


	
	

	// Das hier zurückgelieferte Objekt soll der zentrale Einstiegspunkt
		// der Middleware aus Applikationssicht sein.
		// Parameter: Host und Port, bei dem die Dienste (hier: Namensdienst)
		// kontaktiert werden sollen. Mit debug sollen Test-
		// ausgaben der Middleware ein- oder ausgeschaltet werden
		// können.
	public static ObjectBroker init(String serviceHost,
	int listenPort, boolean debug) {  
		return new ObjectBroker(serviceHost, listenPort, debug);
	}
	
	
	 // Liefert den Namensdienst (Stellvetreterobjekt).
	public NameService getNameService() {
		return this.nameService;
		
	}
	
	// Beendet die Benutzung der Middleware in dieser Anwendung.
	public void shutDown() {
		this.kommunikationsmodul.shutdown();
		Util.println("Object Brooker heruntergefahren", debug);
	}
	
}
