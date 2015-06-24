package de.ants.vsp.sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import de.ants.vsp.receiver.Datensenke;
import de.ants.vsp.receiver.IReceiver;
import de.ants.vsp.receiver.Receiver;

public class Sender extends Thread implements ISender {

	private int slot;
	private int port;
	private InetAddress ip;
	private SendBuffer sendBuffer;
	private IReceiver empfaenger;
	private Datensenke datensenke;
	private char type;
	private boolean hasSend;
	private long endTime;
	private String ifname;
	private MulticastSocket socket;
	
	
	public Sender(String ip, int port, IReceiver rec, Datensenke datensenke,
			char type, String ifname) throws IOException {
		this.slot = 0;
		this.ip = InetAddress.getByName(ip);
		this.port = port;
		this.sendBuffer = new SendBuffer();
		this.empfaenger = rec;
		this.datensenke = datensenke;
		sendBuffer.start();
		this.type = type;
		this.ifname = ifname;
		socket = new MulticastSocket(this.port);
		socket.setTimeToLive(1);
		socket.joinGroup(this.ip);
		socket.setNetworkInterface(NetworkInterface.getByName(this.ifname));
	}

	@Override
	public boolean isRightSlot() {
		if (this.slot < 1 || this.slot >= 25) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isCollusionFromReceiver() {
		return this.empfaenger.isCollision();
	}

	public long getTimeFromReceiver() {
		return this.empfaenger.getTime();
	}

	public int getSlotforNextFrameFromReceiver() {
		return this.empfaenger.getFreeSlotNextFrame();
	}

	@Override
	public void logCollusion() {
		this.datensenke.logCollision(true, this.slot);

	}

	@Override
	public void sendData() throws SocketException {
		this.hasSend = false;
		try {
			Thread.sleep(Receiver.SPOTTIME / 2);
		} catch (InterruptedException e1) {
			this.interrupt();
		}
		if (!isCollusionFromReceiver()) {
			//siehe Sender - Ablauf
			try {

			if (rightTimeToSend()) {
					byte[] toSend = this.prepareMessage();
					//Kommunikation UDP
					socket.send(new DatagramPacket(toSend, toSend.length,
							this.ip, port));
					this.hasSend = true;
				// 
			}
			else 
				this.datensenke.logMessage("It's to late for sending the Message aborded...");
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			this.datensenke.logMessage("Sending Message would have coused a Collusion aborded...");
	}
	
	private boolean rightTimeToSend() {
		long time = this.getTimeFromReceiver();		
		return time < this.endTime;
	}

	public byte[] prepareMessage() {
		// laut Nachrichtenformat
		byte[] toSend = new byte[34];
		byte[] message = this.sendBuffer.getData();
		int nextSlot = this.getSlotforNextFrameFromReceiver();
		Long time = this.getTimeFromReceiver();
		ByteBuffer bb = ByteBuffer.allocate(8);
		byte[] timeBytes = bb.putLong(time).array();
		toSend[0] = (byte) this.type;
		for (int i = 0; i < message.length; i++) {
			toSend[i + 1] = message[i];
		}
		toSend[25] = (byte) nextSlot;
		for (int i = 0; i < timeBytes.length; i++) {
			toSend[i + 26] = timeBytes[i];
		}
		return toSend;
	}


	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		super.run();
		while (!this.isInterrupted()) {
			try {
				// �nderung im Entwurf Pkt. 1
				this.wait();
				try {
					this.sendData();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				this.interrupt();
			}
		}
		socket.close();
		this.sendBuffer.interrupt();
	}

	@Override
	public boolean hasSend() {
		return this.hasSend;
	}

	@Override
	public synchronized void notifySender(long endTime) {
		this.endTime = endTime;
		this.notify();		
	}

}
