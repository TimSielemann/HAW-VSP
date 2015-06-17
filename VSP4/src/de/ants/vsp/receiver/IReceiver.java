package de.ants.vsp.receiver;

public interface IReceiver {
	
	public int getFreeSlotNextFrame();
	
	public long getTime();
	
	public boolean isCollision();
}
