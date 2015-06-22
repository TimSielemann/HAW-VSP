package de.ants.vsp.sender;

import java.net.SocketException;

public interface ISender {
	public void notifySender();
	public boolean isRightSlot();
	public void logCollusion();
	public void sendData() throws SocketException;
}
