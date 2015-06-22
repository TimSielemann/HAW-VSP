package de.ants.vsp.receiver;

public interface IReceiver {
	/**
	 * Gibt einen noch nicht reservierten Slot zurück
	 * (nimmt den ersten der gefunden wird)
	 * @return Slotnummer
	 */
	public int getFreeSlotNextFrame();
	
	/**
	 * Gibt die aktuelle Zeit in ms zurück
	 * @return
	 */
	public long getTime();
	
	/**
	 * Überprüft ob ein Senden zu einer Kollision führen würde
	 * @return
	 */
	public boolean isCollision();
	
	/**
	 * Sucht aus den noch nicht reservierten Slots einen zufälligen raus
	 * @return
	 */
	public int getSlotForCollusion();
}
