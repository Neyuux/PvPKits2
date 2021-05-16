package fr.neyuux.pvpkits.commands;

import fr.neyuux.pvpkits.PvPKits;
import fr.neyuux.pvpkits.enums.Teams;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class CommandTeam implements CommandExecutor {
	
	private final PvPKits main;
	
	public CommandTeam(PvPKits main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		String aide = main.getPrefix() + "§bAide de la commande "+alias+" : \n§e/"+alias+" list §8(Affiche la liste des teams)\n§e/"+alias+" leave §8(Quitte la team actuelle)\n§e/"+alias+" join §c<Rouge ou Bleu ou Vert ou Jaune ou Noir ou Rose> §8(Rejoint l'équipe choisie)§r\n§e/"+alias+" info §8(Donne des informations sur votre team)";

		if (args.length < 1)
			sender.sendMessage(aide);
			
		else {
				
			if (args[0].equalsIgnoreCase("help"))
				sender.sendMessage(aide);
			
			else if (args[0].equalsIgnoreCase("list")) {
				StringBuilder message = new StringBuilder("§bListe des teams en vie : §l");
				for (Teams t : Teams.values()) {
					if (t.equals(Teams.NONE) || t.getPlayers().size() == 0) continue;
	
					message.append("\n").append("§b§l - ").append(t.getColor()).append(t.getName()).append("§7(§8").append(t.getPlayers().size()).append("§7) §f ");
				}
				if (message.toString().equals("§bListe des teams en vie : §l")) sender.sendMessage(main.getPrefix() + "§cAucune équipe de contient de joueur.");
				else sender.sendMessage(main.getPrefix() + message);
				
				
				
			} else if (args[0].equalsIgnoreCase("leave")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(main.getPrefix() + "§4[§cErreur§4] §cVous devez être un joueur pour effectuer cette commande !");
					return false;
				}
				Player p = (Player) sender;
				
				if (!main.playerkits.get(p.getUniqueId()).isTeam(Teams.NONE)) {
					Teams t = main.playerkits.get(p.getUniqueId()).getTeam();
					p.teleport(Teams.NONE.getMainSalle());
					p.getInventory().clear();
					p.getInventory().setItem(1, main.getItem("§7§lDevenir Spectateur", null, Material.GHAST_TEAR, (short)0));
					p.updateInventory();
					p.setDisplayName(p.getName());
					p.setPlayerListName(p.getName());
					main.setPlayerTeamFromGrade(p, Teams.NONE);
					for (Entry<String, List<UUID>> en : main.getGrades().entrySet())
						if (en.getValue().contains(p.getUniqueId()))
							p.getInventory().setItem(6, main.getGameConfig().getComparator());
					
					Bukkit.broadcastMessage(main.getPrefix() + p.getDisplayName() + " §fa quitté la "+t.getColor()+"§lTeam " + t.getAdjectiveName() + "§f !");
					main.playerkits.get(p.getUniqueId()).setRetour(null);
					main.playerkits.get(p.getUniqueId()).setKit(null);
					main.players.remove(p.getUniqueId());
					
					} else
						p.sendMessage(main.getPrefix() + "§4[§cErreur§4] §cVous n'êtes pas dans une team !");
	
			} else if (args[0].equalsIgnoreCase("join")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(main.getPrefix() + "§4[§cErreur§4] §cVous devez être un joueur pour effectuer cette commande !");
					return false;
				}
				Player p = (Player)sender;
				if (args.length < 2) {
					sender.sendMessage(main.getPrefix() + "§4[§cErreur§4] §cVous devez préciser la team que vous voulez rejoindre ! §e§o(Rouge, Bleu, Vert, Jaune, Rose, Noir)");
					return false;
				}
				
				Teams t = Teams.getByName(args[1]);
				if (t == null) {
					p.sendMessage(main.getPrefix() + "§4[§cErreur§4] §cLa team §4\"§e" + args[1] + "§4\" §cn'existe pas ! §e§o(Rouge, Bleu, Vert, Jaune, Rose, Noir)");
					return false;
				}
				main.players.remove(p.getUniqueId());
				main.addPlayerTeam(p, t);
				
			} else if (args[0].equalsIgnoreCase("info")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(main.getPrefix() + "§4[§cErreur§4] §cVous devez être un joueur pour effectuer cette commande !");
					return false;
				}
				Player player = (Player)sender;
				Teams t = main.playerkits.get(player.getUniqueId()).getTeam();
				if (t.equals(Teams.NONE)) {
					player.sendMessage(main.getPrefix() + "§4[§cErreur§4] §cVous devez être dans une team pour effectuer cette commande !");
					return false;
				}
				StringBuilder steam = new StringBuilder();
				
				for (Player p : t.getPlayers())
					steam.append("\n").append(" - ").append(p.getDisplayName());
				player.sendMessage(main.getPrefix() + "Informations sur la team " + t.getColor() + t.getAdjectiveName() + " §f: ");
				player.sendMessage("Joueurs §8(§7"+ t.getPlayers().size() + "§8) §f: §f§l" + steam );
				
			}
		}
		
		return true;
	}

}
