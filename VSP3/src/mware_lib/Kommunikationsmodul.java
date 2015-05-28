package mware_lib;

import java.io.IOException;
import java.net.ServerSocket;

public class Kommunikationsmodul {
	
	public ReceiveThread thread;
	
	public Kommunikationsmodul(int port) throws IOException{
		this.thread = new ReceiveThread(port);
		this.thread.start();
	}
	
}
