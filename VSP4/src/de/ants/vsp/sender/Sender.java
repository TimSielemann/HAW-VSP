package de.ants.vsp.sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import de.ants.vsp.receiver.Datensenke;
import de.ants.vsp.receiver.Receiver;

public class Sender implements ISender {

	private int slot;
	private int port;
	private InetAddress ip;
	private byte[] message = new byte[] {};
	private SendBuffer sendBuffer;
	private Receiver empfaenger;
	private Datensenke datensenke;

	public Sender(String ip, String port, SendBuffer sendBuffer, Receiver rec,
			Datensenke datensenke) throws UnknownHostException {
		this.slot = 0;
		this.ip = InetAddress.getByName(ip);
		this.port = Integer.parseInt(port);
		this.sendBuffer = sendBuffer;
		this.empfaenger = rec;
		this.datensenke = datensenke;
		sendBuffer.run();

	}

	public Sender(SendBuffer sendbuf, Receiver rec, Datensenke datasenke)
			throws UnknownHostException {
		this.slot = 0;
		this.ip = InetAddress.getByName("225.10.1.2");
		this.port = 15007;
		this.sendBuffer = sendbuf;
		this.datensenke = datasenke;
		this.empfaenger = rec;
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

	public void getDataFromBuffer() {
		this.message = this.sendBuffer.getData();
	}
	public long getTimeFromReceiver(){
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
		if (isRightSlot()) {
			if (!isCollusionFromReceiver()) {
				if (rightTimeToSend()) {
					try {
						DatagramSocket socket = new DatagramSocket();
						socket.send(new DatagramPacket(this.message,
								this.message.length, this.ip, port));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	private boolean rightTimeToSend() {
		boolean flag=false;
		long warteDauerSlot=this.slot*40l;
		long time=this.getTimeFromReceiver();
		if(time>time+warteDauerSlot)
		return flag;
		
	}

}
