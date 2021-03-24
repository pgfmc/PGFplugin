package net.pgfmc;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

public class PlayerData {
	
	private Player player;
	
	public enum DuelStatus {
		NONE,
		PENDING,
		JOINING,
		DUELING,
		KILLED
	}
	
	private DuelStatus duelStatus;
	
	private boolean AFK;
	
	private static Set<PlayerData> instances = new HashSet<PlayerData>();
	
	public PlayerData(Player plr) { // class init
		
		player = plr;
		duelStatus = DuelStatus.NONE;
		AFK = false;
		instances.add(this);
	}
	
	// -------------------------------------------------------------------- Player G
	
	public Player getPlayer() {
		return player;
	}
	
	// -------------------------------------------------------------------- DuelStatus G/S
	
	public DuelStatus getDuelStatus() {
		return duelStatus;
	}
	
	public void setDuelStatus(DuelStatus gme) {
		duelStatus = gme;
	}
	
	// -------------------------------------------------------------------- AFK G/S
	
	public boolean isAFK() {
		return AFK;
	}
	
	public void setAFK(boolean ak) {
		AFK = ak;
	}
	
	// -------------------------------------------------------------------- PlayerData G
	
	public static PlayerData getPlrData(Player plr) { // gets a player's accociated PlayerData
		for (PlayerData PD : instances) {
			if (PD.getPlayer() == plr) {
				return PD;
			}
		}
		return null;
	}
	
	
}