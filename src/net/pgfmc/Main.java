package net.pgfmc;

import org.bukkit.plugin.java.JavaPlugin;

import net.pgfmc.commands.CommandGetLosses;
import net.pgfmc.commands.CommandGetWins;
import net.pgfmc.duel.DuelEvents;

// Written by CrimsonDart

public class Main extends JavaPlugin {
	
	public static Main plugin;
	
	@Override
	public  void onEnable() {
		
		// insert code here
		
		plugin = this;
		
		getCommand("Wins").setExecutor(new CommandGetWins()); // - register commands
		getCommand("Losses").setExecutor(new CommandGetLosses());
		
		getServer().getPluginManager().registerEvents(new DuelEvents(), this);
	}
}