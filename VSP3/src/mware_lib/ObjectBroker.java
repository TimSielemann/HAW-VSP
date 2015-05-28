package mware_lib;

public class ObjectBroker { //- Front-End der Middleware -
	
	
	
	
	public static ObjectBroker init(String serviceHost,
	int listenPort, boolean debug) {  
		return new ObjectBroker();
	}
	
	
	 // Das hier zurückgelieferte Objekt soll der zentrale Einstiegspunkt
	 // der Middleware aus Applikationssicht sein.
	 // Parameter: Host und Port, bei dem die Dienste (hier: Namensdienst)
	 // kontaktiert werden sollen. Mit debug sollen Test-
	 // ausgaben der Middleware ein- oder ausgeschaltet werden
	 // können.
	public NameService getNameService() {
		return null;
		
	}
	 // Liefert den Namensdienst (Stellvetreterobjekt).
	public void shutDown() {
		
	}
	 // Beendet die Benutzung der Middleware in dieser Anwendung.
}
