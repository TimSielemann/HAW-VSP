package mware_lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Kommunikationsmodul {
	
	public ReceiveThread thread;
	private boolean debug;
	
	public Kommunikationsmodul(int port, EntferntesReferenzmodul refmodul, boolean debug){
		this.thread = new ReceiveThread(port, refmodul, debug);
		this.thread.start();
		this.debug = debug;
		Util.println(this + ": gestartet", debug);
	}
	
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
			Util.println(this + ": Rückgabewert: " + returnObj, debug);
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
			return new MWareException("Objekttyp der Rückgabe nicht vorhanden", e);
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
