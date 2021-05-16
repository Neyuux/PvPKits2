package fr.neyuux.pvpkits.commands;

import fr.neyuux.pvpkits.PlayerKits;
import fr.neyuux.pvpkits.PvPKits;
import fr.neyuux.pvpkits.enums.Gstate;
import fr.neyuux.pvpkits.enums.Teams;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class SpecExecutor implements Listener {

	private final PvPKits main;
	
	public SpecExecutor(PvPKits main) {
		this.main = main;
	}
	
	@EventHandler
	public void onSpecInv(InventoryOpenEvent ev) {
		Inventory inv = ev.getInventory();
		if (inv.getName().equals("commandespec")) {
			Player player = Bukkit.getPlayer(inv.getItem(0).getItemMeta().getDisplayName());
			PlayerKits playerkits = main.playerkits.get(player.getUniqueId());
			String arg0 = inv.getItem(1).getItemMeta().getDisplayName();
			
			if (arg0.equalsIgnoreCase("help"))
				player.sendMessage(main.getPrefix() + "§7Aide de la commande spec : \n§e/spec list §8(Affiche la liste des spectateurs)\n§e/spec off §8(Enlève le mode spectateur)\n§e/spec on §8(Active le mode spectateur)");
				
			else if (arg0.equalsIgnoreCase("off")) {
				if (!main.isState(Gstate.PLAYING) && !main.isState(Gstate.FINISHED)) {
					if (player.getGameMode().equals(GameMode.SPECTATOR) && main.spectators.contains(player)) {
					
					player.teleport(Teams.NONE.getMainSalle());
					player.setGameMode(GameMode.ADVENTURE);
					player.getInventory().clear();
					main.clearArmor(player);
					player.getInventory().setItem(1, main.getItem("§7§lDevenir Spectateur", null, Material.GHAST_TEAR, (short)0));
					
					player.setDisplayName(player.getName());
					player.setPlayerListName(player.getName());
					main.setPlayerTeamFromGrade(player, Teams.NONE);
					for (Entry<String, List<UUID>> en : main.getGrades().entrySet())
						if (en.getValue().contains(player.getUniqueId()))
							player.getInventory().setItem(6, main.getGameConfig().getComparator());
					main.spectators.remove(player);
					main.setState(Gstate.PREPARING);
					} else
						player.sendMessage(main.getPrefix() + "§4[§cErreur§4] §cVous n'êtes pas un spectateur !");
				} else
					player.sendMessage(main.getPrefix() + "§4[§cErreur§4] §cVous ne pouvez pas quitter le mode spectateur pendant une partie !");
				
			} else if (arg0.equalsIgnoreCase("on")) {
				if (!main.isState(Gstate.PLAYING) && !main.isState(Gstate.FINISHED)) {
					if (!player.getGameMode().equals(GameMode.SPECTATOR) && !main.spectators.contains(player)) {
					
						player.setGameMode(GameMode.SPECTATOR);
						player.getInventory().remove(Material.GHAST_TEAR);
						player.getInventory().clear();
						main.clearArmor(player);
						player.setMaxHealth(20);
						player.setHealth(20.0);
						player.setFoodLevel(20);
						player.setDisplayName("§8[§7Spectateur§8] §7" + player.getName());
						player.setPlayerListName("§8[§7Spectateur§8] §7" + player.getName());
						if (!playerkits.isTeam(Teams.NONE) || !playerkits.isKit(null)) {
							if (!playerkits.isTeam(Teams.NONE)) main.getPlayerTeam(player, playerkits.getTeam()).removeEntry(player.getName());
							playerkits.setTeam(Teams.NONE);
							playerkits.setKit(null);
						}
						player.sendMessage(main.getPrefix() + "§9Votre mode de jeu a été établi en spectateur.");
						player.sendMessage(main.getPrefix() + "§c§lPour revenir au mode non-spectateur : utilisez la commande : §7/spec off §c.");
						main.spectators.add(player);
						main.players.remove(player.getUniqueId());
				} else
					player.sendMessage(main.getPrefix() + "§4[§cErreur§4] §cVous êtes déjà un spectateur !");
					
				} else
					player.sendMessage(main.getPrefix() + "§4[§cErreur§4] §cVous ne pouvez pas activer le mode spectateur pendant une partie !");
				
			} else if (arg0.equalsIgnoreCase("list")) {
				
				StringBuilder specs = new StringBuilder("\n");
				for (Player p : main.spectators) {
					specs.append("\n").append(" - ").append(p.getName());
				}
				player.sendMessage(main.getPrefix() + "§7Liste des spectateurs : §f§l" + specs);
				
			} else
				player.sendMessage(main.getPrefix() + "§7Aide de la commande spec : \n§e/spec list §8(Affiche la liste des spectateurs)\n§e/spec off §8(Enlève le mode spectateur)\n§e/spec on §8(Active le mode spectateur)");
		}
		
	
	}
}
