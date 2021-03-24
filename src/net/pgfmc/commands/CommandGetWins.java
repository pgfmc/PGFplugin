package net.pgfmc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.pgfmc.database.SaveScore;

// Written by CrimsonDart

public class CommandGetWins implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player player = (Player) sender;

		SaveScore.getScore(player, true); // umm yeah
		
		return true;
	}
}
