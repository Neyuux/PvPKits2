package fr.neyuux.pvpkits.task;

import fr.neyuux.pvpkits.PvPKits;
import fr.neyuux.pvpkits.PlayerKits;
import fr.neyuux.pvpkits.PlayerKits.CSState;
import fr.neyuux.pvpkits.enums.Gstate;
import fr.neyuux.pvpkits.enums.Kits;
import fr.neyuux.pvpkits.enums.Teams;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.UUID;

public class KitsAutoStart extends BukkitRunnable {

	private static int timer = 11;
	private final PvPKits main;
	public KitsAutoStart(PvPKits main) {
		this.main = main;
		timer = 11;
	}
	
	@Override
	public void run() {
		
		if (main.players.size() + main.spectators.size() != Bukkit.getOnlinePlayers().size()) {
			main.setState(Gstate.PREPARING);
			main.rel();
		}
		
		if (!main.isState(Gstate.STARTING)) {
			cancel();
			return;
		}
			
		for(Player pls : Bukkit.getOnlinePlayers()) {
			pls.setLevel(timer);
			pls.setExp((float)timer / 10);
			if (pls.getInventory().getContents().length != 0) pls.getInventory().clear();
		}
			
		if (timer != 11 && timer != 0)
			if (timer == 1)
				Bukkit.broadcastMessage(main.getPrefix() + "§eLancement du jeu dans §c§l" + timer + "§c seconde !");
			else
				Bukkit.broadcastMessage(main.getPrefix() + "§eLancement du jeu dans §c§l" + timer + "§c secondes !");

		timer--;
		if (timer==-1) {
			main.setState(Gstate.PLAYING);
			Bukkit.broadcastMessage(main.getPrefix() + "§9Nombre de teams : §6" + Teams.getAliveTeams().size() + "§7:");
			for (Teams t : Teams.getAliveTeams())
				Bukkit.broadcastMessage(main.getPrefix() + t.getColor() + t.getName() + " §7: §c" + t.getPlayers().size() + " joueur");
			cancel();
			Bukkit.broadcastMessage(main.getPrefix() + "§eLancement du jeu !");
			main.sendTitleForAllPlayers("§b§lGO !", "", 20, 20, 20);
			if (main.getGameConfig().hasVieTab())
				Bukkit.getScoreboardManager().getMainScoreboard().getObjective("health").setDisplaySlot(DisplaySlot.PLAYER_LIST);
			else
				Bukkit.getScoreboardManager().getMainScoreboard().getObjective("health").setDisplaySlot(null);
			
			for (Player p : Bukkit.getOnlinePlayers()) p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 10, 1);
			for (UUID uuid : main.players) {
				Player player = Bukkit.getPlayer(uuid);
				PlayerKits playerkits = main.playerkits.get(uuid);
				Kits kit = playerkits.getKit();

				player.updateInventory();
				playerkits.GiveStuff();
				player.updateInventory();
				
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
				if (playerkits.isKit(Kits.SORCIERE))
					playerkits.startTimerGenSoso();
				
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
				} else if (kit.equals(Kits.LG))
					player.setMaxHealth(15);
				
				if (kit.equals(Kits.XRAYEUR) || kit.equals(Kits.HEALER) || kit.equals(Kits.SORCIERE) || kit.equals(Kits.PYROMANE) || kit.equals(Kits.MILITAIRE))
					playerkits.setCSState(CSState.WAITING);
				
				main.setGameScoreboard(player);
				if (playerkits.getCSState().equals(CSState.WAITING)) {
					CSM.setLore(CSState.WAITING.getItemLore());
					CSM.setDisplayName(CSState.WAITING.getItemName());
					CS.setItemMeta(CSM);
					main.replaceBush(player, CS);
				}
			}
			
			GameRunnable game = new GameRunnable(main);
			game.runTaskTimer(main, 0, 20);
		}
		
		
		if (timer==9) {
		main.sendTitleForAllPlayers("§4§l10", "§c§oPréparation...", 20, 30, 20);
		for (Player p : Bukkit.getOnlinePlayers()) p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 8, 1);
		} else if (timer==4) {
			main.sendTitleForAllPlayers("§6§l5", "§cAttention !", 5, 10, 5);
			for (Player p : Bukkit.getOnlinePlayers()) p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 8, 1.1f);
		} else if (timer==3) {
			main.sendTitleForAllPlayers("§e§l4", "§cAttention !", 5, 10, 5);
			for (Player p : Bukkit.getOnlinePlayers()) p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 8, 1.2f);
		} else if (timer==2) {
			main.sendTitleForAllPlayers("§2§l3", "§6A vos marques...", 5, 10, 5);
			for (Player p : Bukkit.getOnlinePlayers()) p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 8, 1.5f);
		} else if (timer==1) {
			main.sendTitleForAllPlayers("§a§l2", "§ePrêts ?", 5, 10, 5);
			for (Player p : Bukkit.getOnlinePlayers()) p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 8, 1.7f);
		} else if (timer==0) {
			main.sendTitleForAllPlayers("§f§l1", "§aDécollage...", 5, 10, 5);
			for (Player p : Bukkit.getOnlinePlayers()) p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 8, 2);
		}
			
		
	}

}
