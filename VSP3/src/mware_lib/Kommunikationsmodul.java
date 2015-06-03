package mware_lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Modul f�r die Kommunikation zwischen den Entfernt laufenden Middleware anwendungen
 * 
 * (siehe Entwurf)
 * 
 * Dieses Modul ist f�r das versenden von Daten zust�ndig und die erzeugung eines Receive Threads, an den die Anfragenbearbeitung delegiert wird
 * @author Tim
 *
 * Komponente: Kommunikationsmodul
 */
public class Kommunikationsmodul {
	
	private ReceiveThread thread;
	private boolean debug;
	
	public Kommunikationsmodul(int port, EntferntesReferenzmodul refmodul, boolean debug){
		this.thread = new ReceiveThread(port, refmodul, debug);
		this.thread.start();
		this.debug = debug;
		Util.println(this + ": gestartet", debug);
	}
	/**
	 * Versenden eines Methodenaufrufs an ein entferntes Objekt
	 * @param ref
	 * @param method
	 * @param params
	 * @return
	 */
	public Object send(ObjectReference ref, String method, Object[] params) {
		Util.println(this + ": Versende an " + ref + " methode " + method + " params " + params, debug);
		Socket socket = null;
		try {
			socket = new Socket(ref.getHost(), ref.getPort());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(new RequestObject(ref.getName(), method, params));
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());			
			Object returnObj = ois.readObject();
			socket.close();
			Util.println(this + ": R�ckgabewert: " + returnObj, debug);
			if (returnObj instanceof MWareException){
				throw (MWareException)returnObj;
			}
			return returnObj;
		} catch (IOException e) {
			if (socket != null){
				try {
					socket.close();
				} catch (IOException e1) {
					//Dann wohl schon geschlossen...
				}
			}
			return new MWareException("Kein Verbindung zum Entfernten Objekt", e);
		} catch (ClassNotFoundException e) {
			if (socket != null){
				try {
					socket.close();
				} catch (IOException e1) {
					//Dann wohl schon geschlossen...
				}
			}
			return new MWareException("Objekttyp der R�ckgabe nicht vorhanden", e);
		}		
	}
	
	public int getPort(){
		return this.thread.getPort();
	}
	
	public String getAdress(){
		return this.thread.getAdress();
	}

	public void shutdown() {
		this.thread.shutdown();
	}
}
