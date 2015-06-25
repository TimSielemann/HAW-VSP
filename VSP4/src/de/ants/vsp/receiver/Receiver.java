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
	private long timeOffset;
	private List<ReceiveWrapObject> messages;
	private InetAddress inetadress;
	private int[] reservedSpots;
	private MulticastSocket socket;
	private Datensenke datensenke;
	private int nextSlot;
	private int nextSlotLast;
	private Sender sender;
	private String name;
	private int sendFrames;
	private int notSendFrames;
	private boolean hasSend;
	private int timecount = 0;
	private int sumOffset = 0;
	private ReceiverThread recThread;
	
	public static void main(String[] args) throws NumberFormatException, SecurityException, IOException{
		Receiver receiver = new Receiver(args[0].charAt(0), args[1], args[2], Integer.parseInt(args[3]), Long.parseLong(args[4]), Integer.parseInt(args[5]));
		receiver.start();
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
		Sender sender = new Sender(host, port, this, this.datensenke, type, ifname);
		sender.start();
		this.sender = sender;
		this.nextSlot = -1;
		this.datensenke.logMessage("Receiver initialised");
		this.recThread = new ReceiverThread(this.port, this.ifname, this.inetadress, this);
		
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

	@Override
	public void run() {
		super.run();
		try {	
			this.recThread.start();
			//Warten bis tats�chlich ein Frame beginnt...
			// (Entwurf)
			try {
				Thread.sleep(1000 - (this.getTime() % 1000));
			} catch (InterruptedException e) {
			}
			
			socket = new MulticastSocket(this.port);
			socket.setNetworkInterface(NetworkInterface.getByName(this.ifname));
			socket.joinGroup(this.inetadress);
			this.listenOneFrame();
		
			while (!this.isInterrupted()){
				this.listenOneFrame();
			}			
			this.sender.interrupt();
			socket.close();
			this.datensenke.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void listenOneFrame() throws IOException {
		for(int i=0 ; i<(int) (FRAMETIME/SPOTTIME); i++){
			long time = this.getTime();
//			this.datensenke.logMessageSevere("---------------FRAME:" + (time - (time%1000)) + "--------------------");
			listenOneSpot(time - (time%1000) + i*SPOTTIME , time - (time%1000) + (i+1)*SPOTTIME, i+1);
		}
		if (!this.hasSend){
			this.nextSlot = this.getSlotForCollusion();
			this.notSendFrames +=1;
			this.datensenke.logMessageSevere("has not send! " + this.nextSlot + " will be the next..." );
		}
		else {
			this.sendFrames +=1;
		}
		this.nextSlotLast = this.nextSlot;
//		this.datensenke.logMessageSevere("Receiver: " + this.name + " reservedSlots: " + Arrays.toString(this.reservedSpots));
		this.reservedSpots = new int[(int) (FRAMETIME/SPOTTIME)];
//		this.datensenke.logMessageSevere("Receiver: " + this.name + " will send at slot " + this.nextSlot + ". Received Message? " + this.sender.hasSend());
		this.datensenke.logMessageSevere("Receiver: " + this.name + " Frames Send: " + this.sendFrames + " Frames not Send: " + this.notSendFrames);
		this.hasSend = false;
		if (timecount > 0){
			int newOffset = (this.sumOffset / this.timecount);
//			datensenke.logNewTimeSet(this.timeOffset, newOffset);
			if (this.type == 'B'){
				this.timeOffset = this.timeOffset + Math.round((float)newOffset/4.0f);
			}
			else {
				this.timeOffset = this.timeOffset + Math.round((float)newOffset/4.0f);
			}
//			this.datensenke.logMessageSevere("OffsetNew"+this.timeOffset);
		}
		
		this.timecount = 0;
		this.sumOffset = 0;
	}

	
	private void listenOneSpot(long startTime, long endTime, int spotNo) throws IOException {
	//Wenn wir uns im Sender Spot befinden, muss der Sender benachrichtigt werden, dass er jetzt senden darf
	// Entwurf (neu)
	if (spotNo == this.nextSlot){
		this.nextSlot = 0;
		sender.notifySender(endTime);
	}
	while (this.getTime() < endTime){
			int timeout = (int) (endTime - this.getTime());
			try {
				if (timeout > 0){
					Thread.sleep(timeout);					
				}
			} catch (InterruptedException e) {
				this.interrupt();
			}
			
	}
	this.messages = this.recThread.getMessages();
	this.handleMessage(spotNo);
}


//	private void listenOneSpot(long startTime, long endTime, int spotNo) throws IOException {
//		//Wenn wir uns im Sender Spot befinden, muss der Sender benachrichtigt werden, dass er jetzt senden darf
//		// Entwurf (neu)
//		if (spotNo == this.nextSlot){
//			this.nextSlot = 0;
//			sender.notifySender(endTime);
//		}
//		try {
//			int timeout = (int) (endTime - this.getTime());
//			if (timeout > 0 ){
//				socket.setSoTimeout(timeout);				
//			}
//		} catch (SocketException e) {
//			e.printStackTrace();
//		}
//		while (this.getTime() < endTime){
//			byte[] buffer = new byte[34];
//			DatagramPacket packet = new DatagramPacket(buffer, 34);
//			try {
//				int timeout = (int) (endTime - this.getTime());
//				if (timeout > 0 ){
//					socket.setSoTimeout(timeout);				
//					socket.receive(packet);
//					this.messages.add(new ReceiveWrapObject(packet.getData(), this.getTime()));
//				}
//			}	catch (SocketTimeoutException e){
//				//Nothing TODO.				
//			}
//		}
//		this.handleMessage(spotNo);
//	}


	private void handleMessage(int spotNo) {
		//Kollision!!
		if (this.messages.size() > 1){
			this.messages.clear();
			this.datensenke.logCollision(spotNo == this.nextSlotLast, spotNo);
		}
		else if (this.messages.size() == 1) {
			// Der sender darf nur einmal pro Frame Senden
			if (this.nextSlotLast == spotNo && this.sender.hasSend()){
				this.hasSend = true;
			}
			byte[] message = this.messages.get(0).getMsg();
			ByteBuffer bb = ByteBuffer.wrap(Arrays.copyOfRange(message, 26, 34));
			this.syncTime((char) message[0], bb.getLong(), this.messages.get(0).getTimeReceived());
			//-1 weil Spotnummern bei 1 anfangen
			this.reservedSpots[message[25]-1] = 1;
			this.datensenke.dumpData(message, spotNo);
			this.messages.clear();
		}
	}
	
	

	private void syncTime(char type, long timestamp, long received) {
		//Bei B nichts tun.. siehe Zeitkonfiguration
		if (type == 'A'){
			this.timecount++;
			long newOffset = (timestamp - received);
			this.sumOffset += newOffset;
//			datensenke.logNewTimeSet(this.timeOffset, newOffset);
//			this.timeOffset = newOffset;
			
//			if (this.type == 'B'){
//			}
//			else {
//				long newOffset = (timestamp - received)/2;
//				datensenke.logNewTimeSet(this.timeOffset, newOffset);
//				this.timeOffset = newOffset; 
//			}
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
