package mware_lib;

public class NameService implements INameService {
	
	private String serviceHost;
	private int listenPort;
	private boolean debug;
	private EntferntesReferenzmodul refmodul;
	private Kommunikationsmodul kommModul;
	
	public NameService(String serviceHost, int listenPort, boolean debug, EntferntesReferenzmodul refmodul, Kommunikationsmodul kommModul) {
		this.serviceHost = serviceHost;
		this.listenPort = listenPort;
		this.debug = debug;
		this.refmodul = refmodul;
		this.kommModul = kommModul;
	}

	@Override
	public void rebind(Object servant, String name) {
		this.refmodul.put(name, new Skeleton(servant));
		ObjectReference ref = new ObjectReference(this.kommModul.getAdress(), this.kommModul.getPort(), name);
		this.kommModul.send(new ObjectReference(serviceHost, listenPort, INameService.NAMESERVICENAME), "rebind", new Object[]{ref, name});
		Util.println(this + ": Object " + servant + " wird an Namensdienst gebunden mit folgender Referenz: " + ref, debug);
	}

	@Override
	public Object resolve(String name) {
		 Util.println(this + ": Versuche Object abzurufen " + name, debug);
		 Object ret = this.kommModul.send(new ObjectReference(serviceHost, listenPort, INameService.NAMESERVICENAME), "resolve", new Object[]{name});
		 if (ret instanceof Exception){
			 throw new MWareException("Fehler im Namensdienst", (Exception)ret);
		 }
		 if (ret == null){
			 return null;
		 }
		 return new RawObject((ObjectReference)ret, kommModul,debug);
	}

}
