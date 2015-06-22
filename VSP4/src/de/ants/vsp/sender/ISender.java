package de.ants.vsp.sender;

import java.net.SocketException;

public interface ISender {
	public void notifySender(long endTime);
	public boolean isRightSlot();
	public void logCollusion();
	public void sendData() throws SocketException;
	
	public boolean hasSend();
}
