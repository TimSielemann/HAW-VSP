package mware_lib;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
		
		while (!interrupted()){
			try {
				Socket userSocket = socket.accept();
				new MethodThread(userSocket, refmodul, debug).start();
			} catch (IOException e) {
				if (! socket.isClosed()){
					throw new MWareException("Fehler am Kommunikationssocket", e);					
				}
				else {
					Util.println(this + ": Receive Thread shutdown...", debug);
				}
			}
			
		}
	}
	
	public int getPort(){
		return socket.getLocalPort();
	}
	
	public String getAdress(){
		return socket.getInetAddress().getHostAddress();
	}

	public void shutdown(){
		try {
			socket.close();
		} catch (IOException e) {
			throw new MWareException("Kommunikationensocket konnte nicht geschlossen werden", e);
		}
	}
}
