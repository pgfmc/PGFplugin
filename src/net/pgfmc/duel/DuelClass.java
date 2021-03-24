package net.pgfmc.duel;

import java.time.Clock;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.pgfmc.Main;
import net.pgfmc.PlayerData;
import net.pgfmc.database.SaveScore;

/*

Stores temporary data surrounding a duel

keeps track of players in the duel, and doesn't allow them to enter another if they are already in one

Written by CrimsonDart

 */

public class DuelClass {
	private Player provoker;
	
	private Player acceptor;
	
	private World world;
	
	private Set<PlayerData> Players = new HashSet<PlayerData>();
	
	private String time;
	
	public enum States { // state enumeration
		REQUESTPENDING,
		BATTLEPENDING,
		INBATTLE,
		TIMEOUT
	}
	
	private States state;
	
	private static Set<DuelClass> instances = new HashSet<>();
	
	public DuelClass(Player PR, Player CH) { // ---------------------- init
		
		provoker = PR;
		acceptor = CH;
		world = PR.getWorld();
		time = Clock.systemUTC().toString();
		
		Players.add(PlayerData.getPlrData(PR));
		Players.add(PlayerData.getPlrData(CH));
		
		state = States.REQUESTPENDING;
	}
	
	
	public States getState() { // returns the state of the duel
		return(state);
	}
	
	public void setState(States gimmer) { // Changes the state
		state = gimmer;
	}
	
	public static DuelClass findDuel(Player player) { // returns the duel that that player is currently dueling in
		
		for (DuelClass animemomnets : instances) {
			
			for (PlayerData planar : animemomnets.getPlayers()) {
				
				if (planar.getPlayer() == player) {
					return(animemomnets);
				}
			}
		}
		return null;
	}
	
	public PlayerData findStateInDuel(Player player) { // returns the duel that that player is currently dueling in
		
		for (PlayerData planar : this.getPlayers())
			if (planar.getPlayer() == player) {
				return planar;
			}
			
		return(null);
	}
	
	public boolean isProvoker(Player player) {
		
		if (provoker == player) {
			return(true);
			
		} else { return(false); }
		
	}
	
	public Set<PlayerData> getPlayers() {
		return(Players);
	}
	
	
	public World getWorld() {
		return(world);
	}
	
	public static void duelRequest(Player attacker, Player target) { // ----------------------------------------------------------------- Duel Requester
		attacker.sendRawMessage("§cDuel §6Request sent! Request will expire in 15 seconds."); //  sent to the sender
		target.sendRawMessage(attacker.getDisplayName() + " §6has Challenged you to a §cDuel!!"); // message sent to the target
		target.sendRawMessage("§6To accept the Challenge, hit them back!");
		target.sendRawMessage("§6The Challenge will expire in 60 seconds.");
		
		DuelClass Grequest = new DuelClass(attacker, target); // ----------------------------------- creates a new Duel instance
		instances.add(Grequest);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            
            @Override
            public void run() // 60 second long cooldown, in which the plugin will wait for 
            {
            	if (Grequest.getState() == States.REQUESTPENDING) {
            		
            		instances.remove(Grequest);
            		
            		attacker.sendRawMessage("§6The Challenge has expired!");
            	}
            }
        }, 300);
	}
	
	public void duelAccept() { // Duel Acceptor
		
		provoker.sendRawMessage(provoker.getName() + " §6has accepted your Challenge to §cDuel!");
		acceptor.sendRawMessage("§6You have accepted the Challenge!");
		Bukkit.broadcastMessage(acceptor.getDisplayName() + " and " + provoker.getDisplayName() + " are beginning to duel!!");
		
		duelStart(provoker);
		duelStart(acceptor);
		
		DuelClass billNye = this; // ---------------disables attack damage
		billNye.setState(States.BATTLEPENDING);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			@Override
			public void run() {
				billNye.setState(States.INBATTLE);
			}
		}, 60);
	}
	
	public void duelStart(Player player) { //starts the duel for each player
		
		PlayerData plr = findStateInDuel(player); // basic setup functions for the beginning of a duel :-)
		
		if (plr == null) {
			Players.add(PlayerData.getPlrData(player));
			plr = findStateInDuel(player);
		}
		
		player.setHealth(20.0); // -------------------sets health to full, restores all hunger, and increases saturation
		player.setFoodLevel(20);
		player.setSaturation(2);
		
		plr.setDuelStatus(PlayerData.DuelStatus.JOINING);
		
		player.sendTitle("§c3", "", 2, 16, 2); // ------------------------------------------------------- onscreen animations and countdown
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			@Override
			public void run() {
				player.sendTitle("§c2", "", 2, 16, 2);
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
					@Override
					public void run() {
						player.sendTitle("§c1", "", 2, 16, 4);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
        					@Override
        					public void run() {
        						
        						player.sendTitle("§6D    U    E    L    !", "", 0, 20, 4);
        						findStateInDuel(player).setDuelStatus(PlayerData.DuelStatus.DUELING); // ------------------------------------------ allows player to start dueling
        						
        						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                					@Override
                					public void run() {
                						player.sendTitle("§6D   U   E   L   !", "", 0, 20, 4);
                						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        					@Override
                        					public void run() {
                        						player.sendTitle("§6D  U  E  L  !", "", 0, 20, 4);
                        						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                                					@Override
                                					public void run() {
                                						player.sendTitle("§6D U E L !", "", 0, 20, 4);
                                						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                                        					@Override
                                        					public void run() {
                                        						player.sendTitle("§6DUEL!", "", 0, 20, 4);
                                        					}
                                        				}, 2);
                                					}
                                				}, 2);
                        					}
                        				}, 2);
                					}
                				}, 2);
        					}
        				}, 20);
					}
				}, 20);
			}
		}, 20);
	}
	
	public void duelLeave(Player simp) { // ends a player's time to duel (does NOT remove them from the DuelClass instance, and will not be able to rejoin OR enter a new duel until the old one is over)
		
		DuelClass duel = this;
		simp.setHealth(20.0);
		//SaveData.loadPlayer(simp);
		duel.findStateInDuel(simp).setDuelStatus(PlayerData.DuelStatus.KILLED);
		
		ArrayList<PlayerData> HELLOGAMERS = new ArrayList<>();
		for (PlayerData planar : duel.getPlayers()) {
			if (planar.getDuelStatus() != PlayerData.DuelStatus.KILLED) {
				
				
				HELLOGAMERS.add(planar);
			}
		}
		
		if (HELLOGAMERS.size() == 1) { // if there is only one person left alive
			
			Player Winner = HELLOGAMERS.get(0).getPlayer();
			
			Winner.setHealth(20.0);
			//SaveData.loadPlayer(Winner); // loads inventory and saves scores
			SaveScore.Scoreboard(Winner);
			
			Bukkit.broadcastMessage(Winner.getDisplayName() + " §6 has won the §cDuel!!");
			
			duel.setState(States.TIMEOUT);
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
	            
	            @Override
	            public void run()
	            {
	            	instances.remove(duel);
	            }
	        }, 200);
		}
	}
	
	public void duelKick(Player player) { // ends the duel, and restores health
		
		duelLeave(player);
		Players.remove(DuelClass.findDuel(player).findStateInDuel(player));
	}

	public String getTXT() {
		return time;
	}
}
