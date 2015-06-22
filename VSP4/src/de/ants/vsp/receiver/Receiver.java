package de.ants.vsp.receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import de.ants.vsp.sender.ISender;
import de.ants.vsp.sender.Sender;

public class Receiver extends Thread implements IReceiver {
	
	public static final long SPOTTIME = 40;
	public static final long FRAMETIME = 1000; 

	private char type;
	private String ifname;
	private String host;
	private int port;
	private String receiverAdress;
	private long timeOffset;
	private List<ReceiveWrapObject> messages;
	private InetAddress inetadress;
	private int[] reservedSpots;
	private MulticastSocket socket;
	private Datensenke datensenke;
	private int nextSlot;
	private int nextSlotLast;
	private ISender sender;
	private String name;
	
	public static void main(String[] args) throws NumberFormatException, SecurityException, IOException{
		new Receiver(args[0].charAt(0), args[1], args[2], Integer.parseInt(args[3]), Long.parseLong(args[4]), Integer.parseInt(args[5])).start();
	}
	
	
	public Receiver(char type, String ifname, String host, int port,
			long timeOffset, int nr) throws SecurityException, IOException {
		super();
		this.type = type;
		this.ifname = ifname;
		this.host = host;
		this.port = port;
		this.timeOffset = timeOffset;
		this.messages = new ArrayList<ReceiveWrapObject>();
		if (nr < 10){
			this.name = "team 07-0" + nr;
		}
		else {
			this.name = "team 07-" + nr;
		}
		//this.initSocket();
		this.inetadress = InetAddress.getByName(host);
		this.reservedSpots = new int[(int) (FRAMETIME/SPOTTIME)];
		this.datensenke = new Datensenke(this.name);
		Sender sender = new Sender(host, port, this, this.datensenke, type);
		sender.start();
		this.sender = sender;
		this.nextSlot = -1;
		this.datensenke.logMessage("Receiver initialised");
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
			//Warten bis tats�chlich ein Frame beginnt...
			// (Entwurf)
			try {
				Thread.sleep(1000 - (this.getTime() % 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			socket = new MulticastSocket(this.port);
//			socket = new DatagramSocket(this.port, this.inetadress);
			socket.joinGroup(this.inetadress);
			this.listenOneFrame();
		
			while (!this.isInterrupted()){
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
//		this.datensenke.logMessage("Listen to Frame. Time: " + this.getTime());
		for(int i=0 ; i<(int) (FRAMETIME/SPOTTIME); i++){
			long time = this.getTime();
			listenOneSpot(time - (time%1000) + i*SPOTTIME , time - (time%1000) + (i+1)*SPOTTIME, i+1);
		}
//		this.datensenke.logMessage("Frame End. Time: " + this.getTime());
		if (!this.sender.hasSend()){
			this.nextSlot = this.getSlotForCollusion();
		}
		this.nextSlotLast = this.nextSlot;
		this.reservedSpots = new int[(int) (FRAMETIME/SPOTTIME)];
	}


	private void listenOneSpot(long startTime, long endTime, int spotNo) throws IOException {
		//Wenn wir uns im Sender Spot befinden, muss der Sender benachrichtigt werden, dass er jetzt senden darf
		// Entwurf (neu)
		if (spotNo == this.nextSlot){
			this.nextSlot = 0;
			sender.notifySender(endTime);
		}
		try {
			int timeout = (int) (endTime - this.getTime());
			if (timeout > 0 ){
				socket.setSoTimeout(timeout);				
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		while (this.getTime() < endTime){
			byte[] buffer = new byte[34];
			DatagramPacket packet = new DatagramPacket(buffer, 34);
			try {
				socket.receive(packet);
				this.messages.add(new ReceiveWrapObject(packet.getData(), this.getTime()));
				int timeout = (int) (endTime - this.getTime());
				if (timeout > 0 ){
					socket.setSoTimeout(timeout);				
				}
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
			byte[] message = this.messages.get(0).getMsg();
			ByteBuffer bb = ByteBuffer.wrap(Arrays.copyOfRange(message, 26, 34));
			this.syncTime((char) message[0], bb.getLong(), this.messages.get(0).getTimeReceived());
			this.reservedSpots[message[25]] = 1;
			this.datensenke.dumpData(message, spotNo);
			this.messages.clear();
		}
	}


	private void syncTime(char type, long timestamp, long received) {
		//Bei B nichts tun.. siehe Zeitkonfiguration
		if (type == 'A'){
			if (this.type == 'B'){
				long newOffset = timestamp - received;
				datensenke.logNewTimeSet(this.timeOffset, newOffset);
				this.timeOffset = newOffset;
			}
			else {
				long newOffset = (timestamp - received)/2;
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
		List<Integer> freeslots = new ArrayList<Integer>();
		for (int i = 0; i < this.reservedSpots.length; i++){
			if (this.reservedSpots[i] == 0){
				freeslots.add(i+1);
			}
		}
		if (freeslots.size() > 0){
			int index = (int)(Math.random() * freeslots.size());
			return freeslots.get(index);		
		}
		else {
			return 0;
		}
	}
	
	
	
	
}
