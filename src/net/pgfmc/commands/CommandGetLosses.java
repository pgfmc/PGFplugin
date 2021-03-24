package net.pgfmc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Written by CrimsonDart

import net.pgfmc.database.SaveScore;

public class CommandGetLosses implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player player = (Player) sender;

		SaveScore.getScore(player, false);
		
		return true;
	}
}
