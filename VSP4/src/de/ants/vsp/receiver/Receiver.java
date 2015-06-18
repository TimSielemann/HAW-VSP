package de.ants.vsp.receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class Receiver extends Thread implements IReceiver {
	
	private static final long SPOTTIME = 40;
	private static final long FRAMETIME = 1000; 

	private char type;
	private String ifname;
	private String host;
	private int port;
	private String receiverAdress;
	private long timeOffset;
	private List<byte[]> messages;
	private InetAddress inetadress;
	private int[] reservedSpots;
	private DatagramSocket socket;
	private Datensenke datensenke;
	private int nextSlot;
	private int nextSlotLast;
	
	
	public Receiver(char type, String ifname, String host, int port,
			long timeOffset) throws SocketException, UnknownHostException {
		super();
		this.type = type;
		this.ifname = ifname;
		this.host = host;
		this.port = port;
		this.timeOffset = timeOffset;
		this.messages = new ArrayList<byte[]>();
		this.initSocket();
		this.reservedSpots = new int[(int) (FRAMETIME/SPOTTIME)];
		this.datensenke = new Datensenke();
	}


	@Override
	public int getFreeSlotNextFrame() {
		if (this.nextSlot == 0){
			this.nextSlot = this.searchNextSlot();
		}
		return nextSlot;
	}


	private int searchNextSlot() {
		for (int i = 0; i < this.reservedSpots.length; i++){
			if (this.reservedSpots[i] == 0){
				return i+1;
			}
		}
		//Kein freier slot...
		return -1;
	}


	@Override
	public long getTime() {
		return System.currentTimeMillis() + timeOffset;
	}


	@Override
	public boolean isCollision() {		
		return messages.size() >= 1;
	}
	
	private InetAddress getIPAdress(NetworkInterface networkInterface){
		Enumeration<InetAddress> enumeration = networkInterface.getInetAddresses();
		while (enumeration.hasMoreElements()){
			InetAddress adr = enumeration.nextElement();
			return adr;
		}
		return null;
	}

	@Override
	public void run() {
		super.run();
		try {	
			//Warten bis tatsächlich ein Frame beginnt...
			try {
				Thread.sleep(1000 - (this.getTime() % 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			socket = new DatagramSocket(this.port, this.inetadress);
			this.listenOneFrame();
			
			//TODO Sender starten...
			
			
			while (true){
				this.listenOneFrame();
			}			
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void listenOneFrame() throws IOException {
		for(int i=0 ; i<(int) (FRAMETIME/SPOTTIME); i++){
			listenOneSpot((this.getTime()%1000) + i*SPOTTIME , (this.getTime()%1000) + (i+1)*SPOTTIME, i+1);
		}
		this.nextSlotLast = this.nextSlot;
		this.nextSlot = 0;
	}


	private void listenOneSpot(long startTime, long endTime, int spotNo) throws IOException {
		try {
			socket.setSoTimeout((int) (endTime - startTime));
		} catch (SocketException e) {
			e.printStackTrace();
		}
		while (this.getTime() < endTime){
			DatagramPacket packet = new DatagramPacket(new byte[34], 34);
			try {
				socket.receive(packet);
				this.messages.add(packet.getData());
				socket.setSoTimeout((int) (endTime - this.getTime()));
			}	catch (SocketTimeoutException e){
				//Nothing TODO.				
			}
		}
		this.handleMessage(spotNo);
	}


	private void handleMessage(int spotNo) {
		//Kollision!!
		if (this.messages.size() > 1){
			this.messages.clear();
			this.datensenke.logCollision(spotNo == this.nextSlotLast, spotNo);
		}
		else if (this.messages.size() == 1) {
			byte[] message = this.messages.get(0);
			ByteBuffer bb = ByteBuffer.wrap(Arrays.copyOfRange(message, 26, 33));
			this.syncTime((char) message[0], bb.getLong());
			this.reservedSpots[message[25]] = 1;
			this.datensenke.dumpData(message, spotNo);
			this.messages.clear();
		}
	}


	private void syncTime(char type, long timestamp) {
		//Bei B nichts tun.. siehe Zeitkonfiguration
		if (type == 'A'){
			if (this.type == 'B'){
				long newOffset = timestamp - this.getTime();
				datensenke.logNewTimeSet(this.timeOffset, newOffset);
				this.timeOffset = newOffset;
			}
			else {
				long newOffset = System.currentTimeMillis() - (timestamp + this.getTime())/2;
				datensenke.logNewTimeSet(this.timeOffset, newOffset);
				this.timeOffset = newOffset; 
			}
		}
	}


	public void initSocket() throws SocketException, UnknownHostException{
		NetworkInterface networkInterface = NetworkInterface.getByName(this.ifname);
		if (networkInterface == null){
			this.inetadress = InetAddress.getLocalHost();
		
		}else {
			this.inetadress = this.getIPAdress(networkInterface);
			if (this.inetadress == null){
				this.inetadress = InetAddress.getLocalHost();
			}
		}
	}


	@Override
	public int getSlotForCollusion() {
		// TODO Auto-generated method stub
		return -1;
	}
	
	
	
	
}
