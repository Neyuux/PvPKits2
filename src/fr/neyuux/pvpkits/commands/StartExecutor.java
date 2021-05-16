package fr.neyuux.pvpkits.commands;

import fr.neyuux.pvpkits.PvPKits;
import fr.neyuux.pvpkits.PlayerKits;
import fr.neyuux.pvpkits.PlayerKits.CSState;
import fr.neyuux.pvpkits.enums.Gstate;
import fr.neyuux.pvpkits.enums.Kits;
import fr.neyuux.pvpkits.enums.Teams;
import fr.neyuux.pvpkits.task.GameRunnable;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.UUID;

public class StartExecutor implements Listener {
	
	private final PvPKits main;
	public StartExecutor(PvPKits main) {
		this.main = main;
	}

	@EventHandler
	public void onStart(InventoryOpenEvent ev) {
		Inventory inv = ev.getInventory();
		if (inv.getName().equals("startlamap")) {
			String ssender = inv.getItem(0).getItemMeta().getDisplayName();
			
			if (main.isState(Gstate.STARTING)) {
				
				main.setState(Gstate.PLAYING);
				Bukkit.broadcastMessage(main.getPrefix() + "§9Nombre de teams : §6" + Teams.getAliveTeams().size() + "§7:");
				for (Teams t : Teams.getAliveTeams())
					Bukkit.broadcastMessage(main.getPrefix() + t.getColor() + t.getName() + " §7: §c" + t.getPlayers().size() + " joueur");
				ev.getPlayer().closeInventory();
				Bukkit.broadcastMessage(main.getPrefix() + "§eLancement du jeu !");
				PvPKits.sendTitleForAllPlayers("§b§lGO !", "", 20, 20, 20);
				if (main.getGameConfig().hasVieTab())
					Bukkit.getScoreboardManager().getMainScoreboard().getObjective("§4♥").setDisplaySlot(DisplaySlot.PLAYER_LIST);
				else
					Bukkit.getScoreboardManager().getMainScoreboard().getObjective("§4♥").setDisplaySlot(null);
				
				for (Player p : Bukkit.getOnlinePlayers()) p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 10, 1);
				for (UUID uuid : main.players) {
					Player player = Bukkit.getPlayer(uuid);
					PlayerKits playerkits = main.playerkits.get(uuid);
					Kits kit = playerkits.getKit();
					
					playerkits.GiveStuff();
					
					ItemStack CS = new ItemStack(Material.DEAD_BUSH);
					ItemMeta CSM = CS.getItemMeta();
					CSM.setDisplayName(CSState.READY.getItemName());
					CSM.addEnchant(Enchantment.DURABILITY, 3, true);
					CSM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					CSM.setLore(CSState.READY.getItemLore());
					CS.setItemMeta(CSM);
					player.getInventory().addItem(CS);
					player.updateInventory();
					playerkits.setCSState(CSState.READY);
					playerkits.resetKills();
					playerkits.setLastTape(null);
					
					PvPKits.sendActionBarForAllPlayers(main.getPrefix() + "§8Scattering " + player.getDisplayName());
					player.teleport(playerkits.getTeam().getSpawnIle());
					
					player.setGameMode(GameMode.SURVIVAL);
					player.damage(1);
					player.resetMaxHealth();
					player.setHealth(20.0);
					player.setFoodLevel(20);
					player.setLevel(0);
					player.setExp(0f);
					for (PotionEffect pe : player.getActivePotionEffects())
						if (!pe.getType().equals(PotionEffectType.SATURATION))
							player.removePotionEffect(pe.getType());
						
					if (kit.equals(Kits.XRAYEUR)) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0));
						player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 0));
					}
					
					if (kit.equals(Kits.XRAYEUR) || kit.equals(Kits.HEALER) || kit.equals(Kits.SORCIERE) || kit.equals(Kits.PYROMANE) || kit.equals(Kits.MILITAIRE))
						playerkits.setCSState(CSState.WAITING);
					
					main.setGameScoreboard(player);
					if (playerkits.getCSState().equals(CSState.WAITING)) {
						CSM.setLore(CSState.WAITING.getItemLore());
						CSM.setDisplayName(CSState.WAITING.getItemName());
						CS.setItemMeta(CSM);
					}
				}
				
				GameRunnable game = new GameRunnable(main);
				game.runTaskTimer(main, 0, 20);
			} else
				if (ssender.equals("CONSOLE")) Bukkit.getConsoleSender().sendMessage(main.getPrefix() + "§4[§cErreur§4] §cMINUTE PAPILLION ! Vous ne pouvez pas lancer la partie si tous les joueurs ne sont pas prêts !");
				else Bukkit.getPlayer(ssender).sendMessage(main.getPrefix() + "§4[§cErreur§4] §cMINUTE PAPILLION ! Vous ne pouvez pas lancer la partie si tous les joueurs ne sont pas prêts !");
			
		}
	}
	
}
