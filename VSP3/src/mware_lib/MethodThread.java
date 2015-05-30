package mware_lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MethodThread extends Thread {
	
	private Socket socket;
	private EntferntesReferenzmodul refModul;
	private boolean debug;

	public MethodThread(Socket userSocket, EntferntesReferenzmodul refmodul, boolean debug){
		this.socket = userSocket;
		this.refModul = refmodul;
		this.debug = debug;
	}

	public void run(){
		Util.println(this + ": Method Thread gestartet", debug);
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Object o = ois.readObject();
			//ois.close();
			if (o instanceof RequestObject){
				RequestObject request = (RequestObject) o;
				Skeleton skeleton  = refModul.getSkeleton(request.getObjectRefName());
				Object result = skeleton.doMethodCall(request.getMethodName(), request.getParams());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(result);
				//oos.close();
			}
			else {
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(new MWareException("MWARE_LIB: Wrong request Type"));
				//oos.close();
			}
			Util.println(this + ": Method Thread ausgeführt", debug);			
		} catch (IOException e) {
			throw new MWareException("Methode konnte nicht ausgeführt werden", e);
		} catch (ClassNotFoundException e) {
			//Falls das Socket noch erreichbar ist, Fehlernachricht raus schreiben
			ObjectOutputStream oos;			
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(new MWareException("MWARE_LIB: Wrong request Type", e));
			} catch (IOException e1) {
				//Dann kann auch nix mehr gemacht werden....
			}
		}
		try {
			this.socket.close();
		} catch (IOException e) {
			//schon geschlossen...
		}	
	}
}
