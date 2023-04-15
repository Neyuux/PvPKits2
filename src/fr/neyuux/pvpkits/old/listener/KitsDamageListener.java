package fr.neyuux.pvpkits.old.listener;

import fr.neyuux.pvpkits.old.PvPKits;
import fr.neyuux.pvpkits.old.PlayerKits;
import fr.neyuux.pvpkits.old.PlayerKits.CSPyro;
import fr.neyuux.pvpkits.old.ScoreboardSign;
import fr.neyuux.pvpkits.old.enums.Gstate;
import fr.neyuux.pvpkits.old.enums.Kits;
import fr.neyuux.pvpkits.old.enums.Teams;
import fr.neyuux.pvpkits.old.task.KitsAutoStop;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Map;
import java.util.UUID;

public class KitsDamageListener implements Listener {

	private final PvPKits main;
	public KitsDamageListener(PvPKits main) {
		this.main = main;
	}
	
	
	@EventHandler
	public void onDamage(EntityDamageEvent ev) {
		Entity e = ev.getEntity();
		DamageCause dc = ev.getCause();
		double fdamage = ev.getFinalDamage();
		
		if (!main.isState(Gstate.PLAYING)) {
			ev.setCancelled(true);
			return;
		}
		
		
		if (e instanceof Zombie) {
			Zombie z = (Zombie)e;
			Player macron = null;
			for (PlayerKits pk : main.playerkits.values())
				if (pk.getZombiesMacron().contains(z) && pk.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
					macron = pk.getPlayer();
			if (macron != null)
				macron.setHealth(Math.min(macron.getHealth() + fdamage, macron.getMaxHealth()));
		}
		
		if (e instanceof Player)
			for (UUID id : main.players) {
				Player p = Bukkit.getPlayer(id);
				if (main.playerkits.get(p.getUniqueId()).isKit(Kits.ALLAHU_AKBAR) && !main.playerkits.get(e.getUniqueId()).isKit(Kits.ALLAHU_AKBAR)) {
					double added = fdamage / 6;
					p.setHealth(Math.min(added + p.getHealth(), p.getMaxHealth()));
				}
			}
		
		switch (dc) {
		case FALL:
			if (e instanceof Player) {
				Player player = (Player)e;
				PlayerKits playerkits = main.playerkits.get(player.getUniqueId());
				if (player.getHealth() <= fdamage) {
					
					if (playerkits.getLastTape() != null) {
						Entity elt = playerkits.getLastTape().getKey();
						System.out.println(elt);
						if (elt.getType().equals(EntityType.ARROW)) {
							Player killer = ((Player)((Arrow) elt).getShooter());
							eliminate(main.getPrefix() + player.getDisplayName() + " §cs'est éclaté au sol à cause de la flèche de " + killer.getDisplayName(), player, killer, ev);
						}
						
						else if (elt.getType().equals(EntityType.ZOMBIE)) {
							Zombie z = (Zombie) elt;
							Player killer = main.getZombieOwner(z);
							if (z.getCustomName().contains("§eGilet Jaune"))
								eliminate(main.getPrefix() + player.getDisplayName() + " §cs'est éclaté au sol à cause du §eGilet Jaune§c de " + killer.getDisplayName(), player, killer, ev);
							else
								eliminate(main.getPrefix() + player.getDisplayName() + " §cs'est éclaté au sol à cause du §9§lC.R.S.§c de " + killer.getDisplayName(), player, killer, ev);
						}
						
						else if (elt.getType().equals(EntityType.PLAYER)) {
							Player killer = (Player)elt;
							if (!checkInfect(killer, player))
								eliminate(main.getPrefix() + player.getDisplayName() + " §cs'est éclaté au sol à cause de " + killer.getDisplayName(), player, killer, ev);
						}
						
						else
							eliminate(main.getPrefix() + player.getDisplayName() + " §cs'est éclaté au sol.", player, null, ev);
					} else
						eliminate(main.getPrefix() + player.getDisplayName() + " §cs'est éclaté au sol.", player, null, ev);
				}
			}
			break;
		case FIRE:
			if (e instanceof Player) {
				Player player = (Player)e;
				PlayerKits playerkits = main.playerkits.get(player.getUniqueId());
				
				if (playerkits.isKit(Kits.PYROMANE) && playerkits.getCSPyro() != null)
					if (playerkits.getCSPyro().equals(CSPyro.INFLAMMATION_VAMPIRIQUE)) {
						double damage = ev.getDamage();
						ev.setCancelled(true);
						player.setHealth(Math.min((player.getHealth() + damage / 20), player.getMaxHealth()));
						player.playSound(player.getLocation(), Sound.PISTON_EXTEND, 1, 1);
					}
				
				if (player.getHealth() <= fdamage) {
					
					if (playerkits.getLastTape() != null) {
						Entity elt = playerkits.getLastTape().getKey();
						if (elt.getType().equals(EntityType.ARROW)) {
							Player killer = ((Player)((Arrow) elt).getShooter());
							eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c à cause de la flèche de " + killer.getDisplayName(), player, killer, ev);
						}
						
						else if (elt.getType().equals(EntityType.ZOMBIE)) {
							Zombie z = (Zombie) elt;
							Player killer = main.getZombieOwner(z);
							if (z.getCustomName().contains("§eGilet Jaune"))
								eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c à cause du §eGilet Jaune §cde " + killer.getDisplayName(), player, killer, ev);
							else
								eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c à cause du §9C.R.S.§c de " + killer.getDisplayName(), player, killer, ev);
						}
						
						else if (elt.getType().equals(EntityType.PLAYER)) {
							Player killer = (Player)elt;
							if (!checkInfect(killer, player))
								eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c à cause de " + killer.getDisplayName(), player, killer, ev);
						}
						
						else
							eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c!", player, null, ev);
					} else
						eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c!", player, null, ev);
				}
			}
			break;
		case FIRE_TICK:
			if (e instanceof Player) {
				Player player = (Player)e;
				PlayerKits playerkits = main.playerkits.get(player.getUniqueId());
				
				if (playerkits.isKit(Kits.PYROMANE) && playerkits.getCSPyro() != null)
					if (playerkits.getCSPyro().equals(CSPyro.INFLAMMATION_VAMPIRIQUE)) {
						double damage = ev.getDamage();
						ev.setCancelled(true);
						player.setHealth(Math.min((player.getHealth() + damage / 10), player.getMaxHealth()));
						player.playSound(player.getLocation(), Sound.PISTON_EXTEND, 3, 1);
					}
				
				if (player.getHealth() <= fdamage) {
					
					if (playerkits.getLastTape() != null) {
						Entity elt = playerkits.getLastTape().getKey();
						if (elt.getType().equals(EntityType.ARROW)) {
							Player killer = ((Player)((Arrow) elt).getShooter());
							eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c à cause de la flèche de " + killer.getDisplayName(), player, killer, ev);
						}
						
						else if (elt.getType().equals(EntityType.ZOMBIE)) {
							Zombie z = (Zombie) elt;
							Player killer = main.getZombieOwner(z);
							if (z.getCustomName().contains("§eGilet Jaune"))
								eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c à cause du §eGilet Jaune §cde " + killer.getDisplayName(), player, killer, ev);
							else
								eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c à cause du §9C.R.S.§c de " + killer.getDisplayName(), player, killer, ev);
						}
						
						else if (elt.getType().equals(EntityType.PLAYER)) {
							Player killer = (Player)elt;
							if (!checkInfect(killer, player))
								eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c à cause de " + killer.getDisplayName(), player, killer, ev);
						}
						
						else
							eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c!", player, null, ev);
					} else
						eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c!", player, null, ev);
				}
			}
			break;
		case LAVA:
			if (e instanceof Player) {
				Player player = (Player)e;
				PlayerKits playerkits = main.playerkits.get(player.getUniqueId());
				
				if (playerkits.isKit(Kits.PYROMANE) && playerkits.getCSPyro() != null)
					if (playerkits.getCSPyro().equals(CSPyro.INFLAMMATION_VAMPIRIQUE)) {
						double damage = ev.getDamage();
						ev.setCancelled(true);
						player.setHealth(Math.min((player.getHealth() + damage / 9), player.getMaxHealth()));
						player.playSound(player.getLocation(), Sound.PISTON_EXTEND, 3, 1);
					}
				
				if (player.getHealth() <= fdamage) {
					
					if (playerkits.getLastTape() != null) {
						Entity elt = playerkits.getLastTape().getKey();
						if (elt.getType().equals(EntityType.ARROW)) {
							Player killer = ((Player)((Arrow) elt).getShooter());
							eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c à cause de la flèche de " + killer.getDisplayName(), player, killer, ev);
						}
						
						else if (elt.getType().equals(EntityType.ZOMBIE)) {
							Zombie z = (Zombie) elt;
							Player killer = main.getZombieOwner(z);
							if (z.getCustomName().contains("§eGilet Jaune"))
								eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c à cause du §eGilet Jaune §cde " + killer.getDisplayName(), player, killer, ev);
							else
								eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c à cause du §9C.R.S.§c de " + killer.getDisplayName(), player, killer, ev);
						}
						
						else if (elt.getType().equals(EntityType.PLAYER)) {
							Player killer = (Player)elt;
							if (!checkInfect(killer, player))
								eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c à cause de " + killer.getDisplayName(), player, killer, ev);
						}
						
						else
							eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c!", player, null, ev);
					} else
						eliminate(main.getPrefix() + player.getDisplayName() + " §ca échoué au : §nLe Sol C'est De La Lave §c!", player, null, ev);
				}
			}
			break;
			case SUICIDE:
			if (e instanceof Player) {
				Player player = (Player)e;
				eliminate(main.getPrefix() + player.getDisplayName() + " §cs'est fait /kill, EZ !", player, null, ev);
			}
			break;
		case THORNS:
			if (e instanceof Player) {
				Player player = (Player)e;
				PlayerKits playerkits = main.playerkits.get(player.getUniqueId());
				if (player.getHealth() <= fdamage)
					eliminate(main.getPrefix() + player.getDisplayName() + " §cs'est fait tué par le karma de " + ((Player) playerkits.getLastTape().getKey()).getDisplayName() + " §c.", player, (Player) playerkits.getLastTape().getKey(), ev);
			}
			break;
		case WITHER:
			if (e instanceof Player) {
				Player player = (Player)e;
				if (player.getHealth() <= fdamage)
					eliminate(main.getPrefix() + player.getDisplayName() + " §cest mort d'un effet de potion mdr", player, null, ev);
			}
			break;
			case POISON:

			default:
			break;
		}
	}
	
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent ev) {
		Entity e = ev.getEntity();
		double fdamage = ev.getFinalDamage();
		Entity d = ev.getDamager();
		
		if (e instanceof Player) {
			Player player = (Player)e;
			PlayerKits playerkits = main.playerkits.get(player.getUniqueId());
			
			switch (d.getType()) {
			case ARROW:
				Arrow arrow = (Arrow)d;
				
				if (arrow.getShooter() instanceof Player) {
					Player killer = (Player)arrow.getShooter();
					if (playerkits.isTeam(main.playerkits.get(killer.getUniqueId()).getTeam())) {
						ev.setCancelled(true);
						return;
					}
					
					if (main.playerkits.get(killer.getUniqueId()).getTimerPluieFleches() != -1) {
						if (killer.hasPotionEffect(PotionEffectType.SPEED)) {
							int s = 5;
							for (PotionEffect pe : killer.getActivePotionEffects())
								if (pe.getType().equals(PotionEffectType.SPEED))
									s = s + pe.getDuration() / 20;
							killer.removePotionEffect(PotionEffectType.SPEED);
							killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * s, 1));
							
						} else
							killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 2, 1));
					}
					
					NumberFormat format = NumberFormat.getInstance();
					format.setRoundingMode(RoundingMode.DOWN);
					format.setMaximumFractionDigits(1);

					String formattedPlayerHealth = format.format(player.getHealth() - fdamage);
					
					killer.sendMessage(main.getPrefix() + " " + player.getDisplayName() + "§c est maintenant à §4§l" + formattedPlayerHealth + " coeurs");
					if (player.getHealth() <= fdamage)
						
						if (!checkInfect(killer, player))
							eliminate(main.getPrefix() + player.getDisplayName() + " §cs'est fait tué par la flèche de " + killer.getDisplayName(), player, killer, ev);
				}
				break;
			case PLAYER:
				Player killer = (Player)d;
				PlayerKits killerKits = main.playerkits.get(killer.getUniqueId());
				
				if (!main.isState(Gstate.PLAYING)) {
					PvPKits.sendActionBar(killer, main.getPrefix() + "§cLe PvP n'est pas activé !");
					killer.playSound(player.getLocation(), Sound.ITEM_BREAK, 5, 1);
					return;
				}
				
				if (playerkits.isTeam(killerKits.getTeam())) {
					ev.setCancelled(true);
					return;
				}
				
				if (playerkits.CSLGAmne != -1 && killerKits.isKit(Kits.LG) && playerkits.isKit(Kits.LG)) {
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 3, 1));
				}
				
				if (killerKits.CSLGInvi != -1 && killerKits.isKit(Kits.LG)) {
					for (Player p : Bukkit.getOnlinePlayers())
						p.showPlayer(killer);
					PvPKits.sendActionBar(killer, main.getPrefix() + "§7Vous n'êtes plus invisible.");
					killerKits.CSLGInvi = -1;
				}
				
				if (killerKits.CSLGGML != -1 && killerKits.isKit(Kits.LG)) {
					double i = fdamage / 10;
					if ((killer.getHealth() + i) < killer.getMaxHealth())
						killer.setHealth(killer.getHealth() + i);
				}
				
				if (playerkits.getTimerBowSpam() != -1) {
					ev.setCancelled(true);
					return;
				}
				
				if (killerKits.getTimerBowSpam() != -1) {
					ev.setDamage(ev.getDamage() / 4);
					fdamage = ev.getFinalDamage();
				}
				
				if (player.getHealth() <= fdamage) {
					if (!checkInfect(killer, player))
						eliminate(main.getPrefix() + player.getDisplayName() + " §cwas rekt by " + killer.getDisplayName(), player, killer, ev);
				}
				break;
			case PRIMED_TNT:
				TNTPrimed tnt = (TNTPrimed)d;
				if (tnt.getSource() != null) {
					if (tnt.getSource().getType().equals(EntityType.PLAYER)) {
						Player killer2 = (Player)tnt.getSource();
						if (playerkits.isTeam(main.playerkits.get(killer2.getUniqueId()).getTeam())) {
							ev.setCancelled(true);
							return;
						}
						playerkits.setLastTape(killer2);
						if (player.getHealth() <= fdamage)
							
							eliminate(main.getPrefix() + killer2.getDisplayName() + " §ca arabé " + player.getDisplayName() + " §c.", player, killer2, ev);
					}
				} else {
					Player killer2 = null;
					for (UUID uuid : main.players)
						if (main.playerkits.get(Bukkit.getPlayer(uuid).getUniqueId()).getTNTAllahu().contains(tnt))
							killer2 = Bukkit.getPlayer(uuid);
					if (killer2 != null)
						if (playerkits.isTeam(main.playerkits.get(killer2.getUniqueId()).getTeam())) {
							ev.setCancelled(true);
							return;
						}
				}
				break;
			case SPLASH_POTION:
				ThrownPotion pot = (ThrownPotion)d;
				
				if (playerkits.isKit(Kits.SORCIERE))
					for (PotionEffect pe : pot.getEffects())
						if (pe.getType().equals(PotionEffectType.HARM)) {
							ev.setCancelled(true);
							double h = player.getHealth();
							double mh = player.getMaxHealth();
							player.setHealth(Math.min(fdamage + h, mh));
						}
				
				if (pot.getShooter() != null)
					if (pot.getShooter() instanceof Player) {
						Player killer2 = (Player)pot.getShooter();
						if (playerkits.isTeam(main.playerkits.get(killer2.getUniqueId()).getTeam())) {
							ev.setCancelled(true);
							return;
						}
						if (player.getHealth() <= fdamage)
							eliminate(main.getPrefix() + player.getDisplayName() + " §cs'est fait potté par " + killer2.getDisplayName(), player, killer2, ev);
					}
				break;
			case ZOMBIE:
				Zombie z = (Zombie)d;
				Player killer2 = null;
				
				for (PlayerKits pk : main.playerkits.values())
					if (pk.getZombiesMacron().contains(z))
						killer2 = pk.getPlayer();
				if (playerkits.isTeam(main.playerkits.get(killer2.getUniqueId()).getTeam())) {
					ev.setCancelled(true);
					return;
				}
				
				if (player.getHealth() <= fdamage) {
					if (z.getCustomName().contains("§eGilet Jaune"))
						eliminate(main.getPrefix() + player.getDisplayName() + " §ca été réformé par les §eGilets Jaunes§c de " + killer2.getDisplayName() + "§c.", player, killer2, ev);
					else
						eliminate(main.getPrefix() + player.getDisplayName() + " §ca été emmené au poste par les C.R.S. de " + killer2.getDisplayName() + "§c.", player, killer2, ev);
				}
				break;
				
			default:
				ev.setCancelled(true);
				break;
			}
			if (!ev.isCancelled() && ev.getDamage() != 0) playerkits.setLastTape(d);
		}
	}
	
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void ForceModifier(EntityDamageEvent event) {
        if (event instanceof EntityDamageByEntityEvent) {
            Entity e = ((EntityDamageByEntityEvent) event).getDamager();
            if (e instanceof Player) {
                Player damager = (Player) e;
                if (damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                    int amplifier = -1;

                    for (PotionEffect potionEffect : damager.getActivePotionEffects())
                        if (potionEffect.getType().equals(PotionEffectType.INCREASE_DAMAGE))
                            amplifier = potionEffect.getAmplifier();
                    amplifier++;
                    event.setDamage(10 * event.getDamage() / (10.0 + 13.0 * (double) amplifier) + 13.0 * event.getDamage() * (double) amplifier * main.getGameConfig().getStrengthPercentage() / 100.0 / (10.0 + 13.0 * (double) amplifier));
                }
            }
        }
    }
	
	
	private boolean checkInfect(Player lg, Player died) {
		PlayerKits diedkits = main.playerkits.get(died.getUniqueId());
		Teams diedTeam = diedkits.getTeam();
		PlayerKits lgkits = main.playerkits.get(lg.getUniqueId());
		Teams lgTeam = lgkits.getTeam();
		
		if (!lgkits.isKit(Kits.LG)) return false;
		if (lgkits.CSLGInf == -1) return false;
		
		
		if (diedTeam.getPlayers().size() == 1)
			Bukkit.broadcastMessage(main.getPrefix() + "§eLa team " + diedTeam.getColor() + "§l" + diedTeam.getAdjectiveName() + "§e a été éliminée...");
			
		diedkits.setTeam(lgTeam);
		main.setPlayerTeamFromGrade(died, lgTeam);
		
		died.teleport(lgTeam.getSpawnIle());
		died.setFallDistance(0);
		Bukkit.broadcastMessage(main.getPrefix() + lg.getDisplayName() + " §c§la infecté " + died.getDisplayName() + " §c§l !");
		lgkits.resetLGInfCS();
		for (Player p : Bukkit.getOnlinePlayers())
			p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 10, 1);
		
		died.setHealth(died.getMaxHealth());
		died.setDisplayName(!main.getGameConfig().hasHideKits() ? "§8[§r" + diedkits.getKit().getName() + "§8]§r " + main.getPlayerTeam(died, lgTeam).getPrefix() + died.getName() + main.getPlayerTeam(died, lgTeam).getSuffix() : main.getPlayerTeam(died, lgTeam).getPrefix() + died.getName() + main.getPlayerTeam(died, lgTeam).getSuffix());
		died.setPlayerListName(died.getDisplayName());

		checkWin();
		
		return true;
	}
	
	
	public void eliminate(String deathMessage, Player died, Player killer, EntityDamageEvent ev) {
		PlayerKits diedkits = main.playerkits.get(died.getUniqueId());
		Teams diedTeam = diedkits.getTeam();
		
		ev.setDamage(0);
		
		Bukkit.broadcastMessage("§c================================================");
		Bukkit.broadcastMessage(deathMessage);
		Bukkit.broadcastMessage("§c================================================");
		
		if (Teams.getAliveTeams().size() != 2 && diedTeam.getPlayers().size() != 1)
			for (Player p : Bukkit.getOnlinePlayers())
				p.playSound(p.getLocation(), Sound.WITHER_DEATH, 5, 1);
		

		main.players.remove(died.getUniqueId());
		main.spectators.add(died);
		Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(died.getName()).removeEntry(died.getName());
		
		died.sendMessage(main.getPrefix() + "§cVous êtes MORT. §7Vous avez été placé en mode spectateur.");
		died.sendMessage(main.getPrefix() + "§7Nombre de vos teammates restants : §5§l" + (diedTeam.getPlayers().size()) + "§7.");
		died.setGameMode(GameMode.SPECTATOR);
		if (diedTeam.getPlayers().size() == 0)
			Bukkit.broadcastMessage(main.getPrefix() + "§eLa team " + diedTeam.getColor() + "§l" + diedTeam.getAdjectiveName() + "§e a été éliminée...");
		
		for (Map.Entry<UUID, ScoreboardSign> sign : main.boards.entrySet())
			sign.getValue().setLine(3, "Teams : §9§l" + Teams.getAliveTeams().size() + " §7(" + main.players.size() + ")");
		
		died.setDisplayName("§8[§7Spectateur§8] §7" + died.getName());
		died.setPlayerListName("§8[§7Spectateur§8] §7" + died.getName());
		main.getPlayerTeam(died, diedTeam).removeEntry(died.getName());
		died.getInventory().clear();
		main.clearArmor(died);
		died.resetMaxHealth();
		died.setHealth(died.getMaxHealth());
		for (PotionEffect pe : died.getActivePotionEffects())
			if (!pe.getType().equals(PotionEffectType.SATURATION))
				died.removePotionEffect(pe.getType());
		
		if (killer != null)
			main.playerkits.get(killer.getUniqueId()).addKill();
		
		checkWin();
	}
	
	
	private void checkWin() {
		if (Teams.getAliveTeams().size() == 1 || Teams.getAliveTeams().size() == 0) {
			main.setState(Gstate.FINISHED);
			new KitsAutoStop(main).runTaskTimer(main, 0, 20);
			if (Teams.getAliveTeams().size() == 1) {
				Teams team = Teams.getAliveTeams().get(0);
				Bukkit.broadcastMessage(main.getPrefix() + "§eLa Team " + team.getColor() + team.getAdjectiveName() + " §ea gagné !");
				Bukkit.broadcastMessage(main.getPrefix() + "§eNombre de gagnants : " + main.players.size() + " §7:");
				for (UUID id : main.players)
					Bukkit.broadcastMessage(main.getPrefix() + Bukkit.getPlayer(id).getDisplayName());
				for (Player p : Bukkit.getOnlinePlayers())
					p.playSound(p.getLocation(), Sound.ZOMBIE_REMEDY, 9, 1);
				PvPKits.sendTitleForAllPlayers("§c§l§n§kaa §e§l§nVictoire de la team " + team.getColor() + team.getAdjectiveName() + " §c§l§n§kaa§r", "§6§l§nNombre de Survivants §7§l: §f" + main.players.size(), 20, 180, 20);
				
			} else if (Teams.getAliveTeams().size() == 0) {
				Bukkit.broadcastMessage(main.getPrefix() + "§eAucune Team ne s'en est sortie vivante. §cÉGALITÉE PARFAITE.");
				for (Player p : Bukkit.getOnlinePlayers())
					p.playSound(p.getLocation(), Sound.ZOMBIE_REMEDY, 8, 1);
				PvPKits.sendTitleForAllPlayers("§5§kaa §c§l§nÉgalité §5§kaa", "§cAucun survivant.", 20, 120, 20);
			}
		}
	}
	
}
