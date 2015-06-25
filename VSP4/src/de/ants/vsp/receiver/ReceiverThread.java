package de.ants.vsp.receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Deprecated
public class ReceiverThread extends Thread {


	private MulticastSocket socket;
	private int port;
	private String ifname;
	private InetAddress inetadress;
	private Receiver receiver;
	private List<ReceiveWrapObject> messages;	
	
	public ReceiverThread(int port, String ifname, InetAddress inetadress,
			Receiver receiver) {
		super();
		this.port = port;
		this.ifname = ifname;
		this.inetadress = inetadress;
		this.receiver = receiver;
		this.messages = new ArrayList<ReceiveWrapObject>();
	}
	
	
	@Override
	public void run() {
		super.run();
		try {	
			//Warten bis tats�chlich ein Frame beginnt...
			// (Entwurf)
			try {
				Thread.sleep(1000 - (this.receiver.getTime() % 1000));
			} catch (InterruptedException e) {
			}
			
			socket = new MulticastSocket(this.port);
			socket.setNetworkInterface(NetworkInterface.getByName(this.ifname));
			socket.joinGroup(this.inetadress);
		
			while (!this.isInterrupted()){
				byte[] buffer = new byte[34];
				DatagramPacket packet = new DatagramPacket(buffer, 34);
//					int timeout = (int) (endTime - this.getTime());
//					if (timeout > 0 ){
//						socket.setSoTimeout(timeout);				
					socket.receive(packet);
					this.addMessage(new ReceiveWrapObject(packet.getData(), this.receiver.getTime()));
			}			
			socket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized List<ReceiveWrapObject> getMessages(){
		List<ReceiveWrapObject> dest = new ArrayList<ReceiveWrapObject>(this.messages);
//		.copy(dest, this.messages);
		this.messages.clear();
		return dest;
	}
	
	public synchronized void addMessage(ReceiveWrapObject obj){
		this.messages.add(obj);
	}
	
}
