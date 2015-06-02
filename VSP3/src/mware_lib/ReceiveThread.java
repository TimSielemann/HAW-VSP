package mware_lib;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Der Receive Thread ist f�r den Verbindungsaufbau f�r Methodenanfragen die f�r diesen Broker gesendet werden verantwortlich.
 * 
 * Es versucht am �bergebenen Port ein ServerSocket zu erstellen, ist dieser belegt nimmt er den n�chsten Port
 * 
 * Sobald eine Verbindung aufgebaut ist, wird ein Method-Thread gestartet der asynchron ausgef�hrt wird
 * 
 * (siehe Entwurf)
 * 
 * Komponente: Kommunikationsmodul
 * 
 * @author Tim
 *
 */
public class ReceiveThread extends Thread {
	
	private static final int MAXPORT = 65535;
	private ServerSocket socket;
//	private int port;
	private EntferntesReferenzmodul refmodul;
	private boolean debug;
	
	public ReceiveThread(int port, EntferntesReferenzmodul refmodul, boolean debug) {
//		this.port = port;
		this.refmodul = refmodul;
		this.debug = debug;
		boolean socketCreated = false;
		while (!socketCreated){
			try {
				socket = new ServerSocket(port);
				socketCreated = true;
			} catch (IOException e) {
				if (port < MAXPORT){
					port++;					
				}
				else {
					throw new MWareException("No more Ports Available",  e);
				}
			}
		}
		Util.println("Receive Thread aktive at Adress: " + this.getAdress() + ":" + this.getPort(), debug);
	}
	
	public void run(){
		
		while (!this.isInterrupted()){
			try {
				Socket userSocket = socket.accept();
				new MethodThread(userSocket, refmodul, debug).start();
			} catch (IOException e) {
				if (! socket.isClosed()){
					throw new MWareException("Fehler am Kommunikationssocket", e);					
				}
				this.interrupt();
			}
			
		}
	}
	
	public int getPort(){
		return socket.getLocalPort();
	}
	
	public String getAdress(){
		try {
			return InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			throw new MWareException("Hostname kann nicht gefunden werden... ", e);
		}
	}

	public void shutdown(){
		Util.println(this + ": Receive Thread shutdown...", debug);
		try {
			socket.close();			
		} catch (IOException e) {
			throw new MWareException("Kommunikationensocket konnte nicht geschlossen werden", e);
		}
		this.interrupt();
	}
}
