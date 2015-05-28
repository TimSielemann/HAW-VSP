package mware_lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MethodThread extends Thread {
	
	private Socket socket;

	public MethodThread(Socket userSocket){
		this.socket = userSocket;
	}

	public void run(){
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Object o = ois.readObject();
			if (o instanceof RequestObject){
				RequestObject request = (RequestObject) o;
				Skeleton skeleton  = EntferntesReferenzmodul.getInstance().getSkeleton(request.getObjectRefName());
				Object result = skeleton.doMethodCall(request.getMethodName(), request.getParams());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(result);
			}
			else {
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(new Exception("MWARE_LIB: Wrong request Type"));
			}
						
		} catch (IOException e) {
			
		} catch (ClassNotFoundException e) {

		}
			
	}
}
