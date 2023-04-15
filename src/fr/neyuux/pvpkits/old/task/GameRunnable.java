package fr.neyuux.pvpkits.old.task;

import fr.neyuux.pvpkits.old.PvPKits;
import fr.neyuux.pvpkits.old.PlayerKits;
import fr.neyuux.pvpkits.old.PlayerKits.CSMili;
import fr.neyuux.pvpkits.old.ScoreboardSign;
import fr.neyuux.pvpkits.old.enums.Gstate;
import fr.neyuux.pvpkits.old.enums.Kits;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class GameRunnable extends BukkitRunnable {

	private static int timer = 0;
	private final PvPKits main;
	public GameRunnable(PvPKits main) {
		this.main = main;
		timer = 0;
	}
	
	@Override
	public void run() {
		
		if (!main.isState(Gstate.PLAYING)) {
			cancel();
			return;
		}
		
		
		for (UUID uuid : main.players) {
			Player player = Bukkit.getPlayer(uuid);
			
			if (player != null) {
				PlayerKits playerkits = main.playerkits.get(player.getUniqueId());
				
				if (playerkits.isKit(Kits.BISCUIT) || playerkits.isKit(Kits.MILITAIRE)) {
					if (player.hasPotionEffect(PotionEffectType.SATURATION))
						player.removePotionEffect(PotionEffectType.SATURATION);
					player.setFoodLevel(19);
				}
				
				else if (playerkits.isKit(Kits.MACRON)) {
					for (Zombie z : playerkits.getZombiesMacron()) {
						List<Entity> list = z.getNearbyEntities(72, 72, 72);
						Player target = null;
						if (list != null)
							if (!list.isEmpty())
								for (Entity n : list)
									if (target == null) {
										if (n.getType().equals(EntityType.PLAYER)) {
											Player np = (Player)n;
											if (!playerkits.getTeam().contains(np) && np.getGameMode().equals(GameMode.SURVIVAL))
												target = np;
										}
									}
						z.setTarget(target);
					}
				}
				
				
				
				if (playerkits.getTimerGiletsJaunes() != -1 && playerkits.isKit(Kits.MACRON)) {
					int timer = playerkits.getTimerGiletsJaunes();
					
					if (timer != 0)
						playerkits.removeSecondTimerGiletsJaunes();
					else {
						Bukkit.broadcastMessage(main.getPrefix() + "§fLes §e§nGilets Jaunes§f de " + player.getDisplayName() + "§f sont morts !");
						playerkits.clearGiletsJaunes();
					}
				}
				
				if (playerkits.getPyroBlocks() != null)
					if (playerkits.getPyroBlocks().getValue() != -1 && playerkits.isKit(Kits.PYROMANE)) {
						int timer = playerkits.getPyroBlocks().getValue();
						
						if (timer != 0)
							playerkits.removeSecondTimerPyro();
						else
							playerkits.clearPyroBlocks();
					}
				
				if (playerkits.CSLGVGPLGBAA != -1 && playerkits.isKit(Kits.LG)) {
					int timerCS = playerkits.CSLGVGPLGBAA;
					int timerInvi = playerkits.CSLGInvi;
					int timerGML = playerkits.CSLGGML;
					int timerLGB = playerkits.CSLGB;
					
					if (timerInvi == 0) {
						PvPKits.sendActionBar(player, main.getPrefix() + "§bVous êtes de nouveau visible !");
						for (Player p : Bukkit.getOnlinePlayers())
							p.showPlayer(player);
						playerkits.CSLGInvi = -1;
					}
					
					if (timerGML != 0)
						if (!player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * timerGML, 0));
					
					if (timerLGB == 0) {
						double health = player.getHealth() / 30.0;
						health = health * 100;
						player.setHealth(health / 5);
						player.setMaxHealth(15);
					}
					
					if (timerCS != 0)
						playerkits.removeSecondLGCS();
					else
						playerkits.resetLGCS();
				}
				
				if (playerkits.getTimerBowSpam() != -1 && playerkits.isKit(Kits.MILITAIRE)) {
					int timer = playerkits.getTimerBowSpam();
					if (timer != 1)
						PvPKits.sendActionBar(player, main.getPrefix() + CSMili.BOWSPAM.getName() + " §fdure encore §l" + timer + "§f secondes.");
					else PvPKits.sendActionBar(player, main.getPrefix() + CSMili.BOWSPAM.getName() + " §fdure encore §l" + timer + "§f seconde.");
					
					if (timer != 0)
						playerkits.removeSecondBowSpam();
					else {
						playerkits.setTimerBowSpam(-1);
						player.getInventory().setHelmet(Kits.MILITAIRE.getHelmet());
						player.getInventory().setLeggings(Kits.MILITAIRE.getLeggings());
						player.getInventory().setBoots(Kits.MILITAIRE.getBoots());
					}
				}
				
				if (playerkits.getTimerGenSoso() != -1 && playerkits.isKit(Kits.SORCIERE))
					playerkits.removeSecondTimerGenSoso();
				else
					if (playerkits.isKit(Kits.SORCIERE))
						if (player.getLevel() != 0) {
							player.setLevel(0);
							player.setExp(0f);
						}
				
				if (playerkits.getLastTape() != null)
					if (playerkits.getLastTape().getValue() == 0)
						playerkits.setLastTape(null);
					else
						playerkits.removeSecondLastTape();
			}
		}
		
		
		for (Entry<UUID, ScoreboardSign> en : main.boards.entrySet())
			en.getValue().setLine(6, "§eTimer : " + new SimpleDateFormat("mm:ss").format(timer * 1000));
		
		timer++;
	}

}
