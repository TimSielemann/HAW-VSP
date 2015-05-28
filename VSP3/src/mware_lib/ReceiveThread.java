package mware_lib;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveThread extends Thread {
	
	private ServerSocket socket;
	
	public ReceiveThread(int port) throws IOException{
		socket = new ServerSocket(port);
	}
	
	public void run(){
		while (!interrupted()){
			try {
				Socket userSocket = socket.accept();
				new MethodThread(userSocket).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
