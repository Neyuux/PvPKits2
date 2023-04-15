package fr.neyuux.pvpkits.old.listener;

import com.google.common.collect.ImmutableList;
import fr.neyuux.pvpkits.old.PvPKits;
import fr.neyuux.pvpkits.old.PlayerKits;
import fr.neyuux.pvpkits.old.PlayerKits.*;
import fr.neyuux.pvpkits.old.enums.Gstate;
import fr.neyuux.pvpkits.old.enums.Kits;
import fr.neyuux.pvpkits.old.enums.Teams;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;

public class KitsListener implements Listener {
	
	private final PvPKits main;
	private final HashMap<Kits, List<String>> kitsLores = new HashMap<>();
	public KitsListener(PvPKits main) {
		this.main = main;
		kitsLores.put(Kits.TOAD, Arrays.asList("§6§l§nCapacité Spéciale§r §7\"§7§lN§5uage §2§lT§aoxique§7\" §6§l§n:§e Fait", "§eapparaître un nuage toxique qui donne Wither", "§eà toutes les personnes qui y passent.", "§b§nPassif§r §b§n:§f Obtient Régénération à côté de ses alliés §o(5blocks)", "§2Casque §6en §lCuir§7(§8Prot §46§7, §3Ub §f1§7, §5Thorns §46§7) §fx2", "§1Plastron §fen §lFer§7(§8Protection §a2§7)", "§bPantalon §fen §lFer§7(§8Protection §a2§7)", "§aBottes §fen §lFer§7(§8Protection §a2§7)", "§4Épée §ben §lDiamant§7(§4Sharpness §f1§7)", "§2Potion de Poison §833s", "§8Potion de Wither 20s", "§9§nClasse§r §9§n:§1 Combattant"));
		kitsLores.put(Kits.PYROMANE, Arrays.asList("§6§l§nCapacité Spéciale§r §7\"§6§lT§eourbillion de §6§lL§6ave§7\" §6§l§n:§e Fait", "§eapparaître du feu et de lave autour du joueur.", "§f§lOU", "§6§l§nCapacité Spéciale 2§r §7\"§6§lI§enflammation §4§lV§5ampirique§7\" : §eLe", "§efeu régénére le joueur, mais la durabilité du briquet", "§epasse à 10.", "§2Casque §7en §lMaille§7(§8Protection §f1, §6Fire§8Prot §a2§7)", "§1Plastron §7en §lMaille§7(§8Protection §a2, §6Fire§8Prot §a2§7)", "§bPantalon §7en §lMaille§7(§8Protection §c4, §6Fire§8Prot §e3§7)", "§aBottes §7en §lMaille§7(§8Protection §c4, §6Fire§8Prot §c4§7, §cProj§8Prot §a2§7)", "§4Épée §7en §lPierre§7(§4Sharpness §a2, §6Fire Aspect §f1§7)", "§cArc§7(§6Flame§7) §fFlèches x16", "§6Briquet", "§9§nClasse§r §9§n:§1 Tireur"));
		kitsLores.put(Kits.LG, Arrays.asList("§6§l§nCapacité Spéciale§r §7\"§b§lV§4§lG§c§lP§4§lL§8§lG§f§lB§3§lA§7§lA§7\" §6§l§n:§e Transforme le joueur ", "§een §7\"§b§lV§4§lG§c§lP§4§lL§8§lG§f§lB§3§lA§7§lA§7\" §e§oplus d'infos dans le chat au clic...", "§b§nPassif§r §b§n:§f A le choix entre le stuff §a§lChien §fou §c§lLoup", "§b§nPassif§r §b§n:§f Possède seulement 7.5 coeurs", "", "§cStuff §lLoup", "§7§l\u21D2 §2Casque §fen §lFer§7(§8Protection §f1§7)", "§7§l\u21D2 §1Plastron §ben §lDiamant§7(§8Protection §f1§7)", "§7§l\u21D2 §bPantalon §fen §lFer§7(§8Protection §f1§7)", "§7§l\u21D2 §aBottes §fen §lFer", "§7§l\u21D2 §4Épée §ben §lDiamant§7(§4Sharpness §f1§7)", "§7§l\u21D2 §e§lG§eolden §lA§epples §fx2", "§7§l\u21D2 §4Potion de Force §830s", "§aStuff §lChien", "§7§l\u21D2 §2Casque §fen §lFer§7(§8Protection §a2§7)", "§7§l\u21D2 §1Plastron §ben §lDiamant§7(§8Protection §f1§7)", "§7§l\u21D2 §bPantalon §fen §lFer§7(§8Protection §a2§7)", "§7§l\u21D2 §aBottes §ben §lDiamant§7(§8Protection §f1§7)", "§7§l\u21D2 §4Épée §fen §lFer", "§7§l\u21D2 §e§lG§eolden §lA§epples §fx3", "§7§l\u21D2 §7Potion de Résistance §820s", "§9§nClasse§r §9§n:§1 Assassin"));
		kitsLores.put(Kits.SORCIERE, Arrays.asList("§6§l§nCapacité Spéciale§r §7\"§5§lC§c§lorbeau §5§lN§c§liqueur§7\" §6§l§n:", "§eRetire 3 coeurs d'un seul coup à tous les joueurs", "§eadverses. §e§o(Ne tue pas)", "§f§lOU", "§6§l§nCapacité Spéciale 2§r §7\"§2§lE§5mpoisonnement§7\" §6§l§n:", "§eRetire 2 coeurs permanant et donne Poison pendant 10s", "§eau joueur sélectionné.", "§2Casque §6en §lCuir§7(§8Protection §e3§7)", "§1Plastron §ben §lDiamant§7(§8Protection §a2§7)", "§bPantalon §6en §lCuir§7(§8Protection §45§7)", "§aBottes §ben §lDiamant§7(§8Protection §a2§7)", "§4Épée §6en §lBois§7(§4Sharpness §c4§7)", "§5Potion de §cdégâts §5instantanés §f1", "§5Potion de §cdégâts §5instantanés §c2", "§8Potion de Slowness 20s", "§7Potion de Weakness §820s", "§9§nClasse§r §9§n:§1 Mage de Combat"));
		kitsLores.put(Kits.HEALER, Arrays.asList("§6§l§nCapacité Spéciale§r §7\"§5§lR§d§légénération§7\" §6§l§n:§e Donne", "§erégénération 3 pendant 7 secondes à toute", "§el'équipe du joueur.", "§f§lOU", "§6§l§nCapacité Spéciale 2§7 \"§c§lR§6§léanimation§7\" §6§l§n:§e Permet", "§ede réssuciter un membre de l'équipe", "§edu joueur mort.", "§2Casque §fen §lFer§7(§8Protection §a2§7)", "§1Plastron §fen §lFer§7(§8Protection §f1§7)", "§bPantalon §fen §lFer§7(§8Protection §f1§7)", "§aBottes §fen §lFer§7(§8Protection §a2§7)", "§4Épée §fen §lFer§7(§4Shaprness §f1, §dKB §f1§7)", "§5Potion de §dsoin §5instantané §f1", "§5Potion de §dsoin §5instantané §c2", "§dPotion de régénération §833s", "§dPotion de régénération §c2 §816s", "§9§nClasse§r §9§n:§1 Soutien"));
		kitsLores.put(Kits.XRAYEUR, Arrays.asList("§6§l§nCapacité Spéciale§r §7\"§b§lT§e§lP §4Aura§7\" §6§l§n:§e Permet de se téléporter", "§eà un joueur aléatoire de la partie. §oPossède 3 utilisations", "§f§lOU", "§6§l§nCapacité Spéciale 2§7 \"§b§lT§e§le§b§ll§e§le§b§lp§e§lo§b§lr§e§lt§7\" §6§l§n:§e Téléporte tous les", "§ejoueurs de l'équipe actuelle sur un autre joueur.", "§b§nPassif§r §b§n:§f Possède Weakness et Slowness", "§2Casque §ben §lDiamant", "§1Plastron §ben §lDiamant", "§bPantalon en §lDiamant", "§aBottes §ben §lDiamant", "§4Épée §ben §lDiamant", "§dPioche §ben §lDiamant§7(§dEfficiency §e3§7)", "§e§lG§eolden §lA§epples §fx5", "§932 §7Blocks §ed'§lOr", "§916 §7Blocks §bde §lDiamant", "§9§nClasse§r §9§n:§1 Tank"));
		kitsLores.put(Kits.BISCUIT, Arrays.asList("§6§l§nCapacité Spéciale§r §7\"§c§lM§f§lÉ§c§lG§f§lA§7 " + Kits.BISCUIT.getName() + "§7\" §6§l§n:§e Donne", "§eun §7\"§c§lM§f§lÉ§c§lG§f§lA§7 " + Kits.BISCUIT.getName() + "§7\"§e.", "  §e=> (§4Force §811s§e)", "  §e=> (§7Résistance §89s§e)", "  §e=> (§bSpeed 2 §814s§e)", "§2Casque §fen §lFer§7(§8Protection §a2§7)", "§1Plastron §fen §lFer§7(§8Protection §a2§7)", "§bPantalon §fen §lFer§7(§8Protection §f1§7)", "§aBottes §fen §lFer§7(§8Protection §a2§7)", "§4Épée §fen §lFer(§4Sharpness §a2§7)", "§dPioche §7en §lPierre§7(§cEfficiency §f1§7)", "§e§lG§eolden §lA§epples §fx3", "§c§lSuper §f§lBiscuits§7(§e§oPlus d'infos au clic§7)§fx5", "§916 §7Blocks §8de §lCharbon", "§98 §7Blocks §fde §lfer"));
		kitsLores.put(Kits.MACRON, Arrays.asList("§6§l§nCapacité Spéciale§r §7\"§e§lGilets Jaunes§7\" §6§l§n:§e Fait", "§eapparaître des Gilets Jaunes à toutes", "§eles intersections de la map. §oDure 17secs.", "§b§nPassif§r §b§n:§f Obtient Régénération lorsqu'aucun", "§fennemi ne se trouve à moins de 30 blocks.", "§2Casque §een §lOr§7(§8Protection §e3§7)", "§1Plastron §een §lOr§7(§8Protection §e3§7)", "§bPantalon §een §lOr§7(§8Protection §c4§7)", "§aBottes §een §lOr§7(§8Protection §a2§7)", "§4Épée §een §lOr§7(§4Sharpness §e3§7)", "§bPotion de Speed §81 min", "§9Oeuf de §lC.R.S §fx2", "§9§nClasse§r §9§n:§1 Combattant défensif"));
		kitsLores.put(Kits.MILITAIRE, Arrays.asList("§6§l§nCapacité Spéciale§r §7\"§7§lArr§b§lain§7\" §6§l§n:§e Fait tomber", "§edes flèches autours de tous les ennemis. §oDure 12 secondes.", "§eChaque flèche touchée donne 5s de speed 2 cumulable pendant ce temps.", "§f§lOU", "§6§l§nCapacité Spéciale 2§7 \"§c§lBow§nSPAM§7\" §6§l§n:§e Rend invulnérable", "§eaux coups en mélée et donne speed 2 pendant 20s.", "§2Casque §6en §lCuir§7(§8Protection §a2§7)", "§1Plastron §7en §lMaille§7(§8Protection §45§7, §cProj§8Prot §c4§7)", "§bPantalon §6en §lCuir§7(§8Protection §a2§7)", "§aBottes §6en §lCuir§7(§8Protection §a2§7)", "§4Épée §fen §lFer§7(§4Sharpness §f1§7)", "§cArc§7(§bPower §f1§7)", "§6§lPains " + Kits.MILITAIRE.getName() + "§7(§e§oPlus d'infos au clic§7) §fx15", "§f§lFlèches §fx20", "§9§nClasse§r §9§n:§1 Tireur"));
		kitsLores.put(Kits.ALLAHU_AKBAR, Arrays.asList("§6§l§nCapacité Spéciale§r §7\"§9§lPluie §cde §4§lT§f§lN§4§lT§7\" §6§l§n:§e Fait", "§epleuvoir des tnt aléatoirement sur la map pendant 20s.", "§2Casque §7en §lMaille§7(§8Protection §c4, §4Explo§8Prot §c4§7)", "§1Plastron §7en §lMaille§7(§8Protection §f1, §4Explo§8Prot §a2§7)", "§bPantalon §7en §lMaille§7(§8Protection §f1, §4Explo§8Prot §a2§7)", "§aBottes §7en §lMaille§7(§8Protection §c4, §4Explo§8Prot §e3§7)", "§4Épée §een §lOr§7(§4Sharpness §e3, §dKnockback §a2§7)", "§6Briquet", "§4T§fN§4T §fx8", "§6§lDirt §fx16", "§9§nClasse§r §9§n:§1 Arabe"));
	}
	

	@EventHandler
	public void onResetGameInv(InventoryOpenEvent ev) {
		if (ev.getInventory().getName().equals("resetlamap")) {
			Bukkit.broadcastMessage(main.getPrefix() + "§b" + ev.getInventory().getItem(0).getItemMeta().getDisplayName() + " §ea reset la map !");
			main.rel();
		}
	}
	
	
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent ev) {
		if (Bukkit.getOnlinePlayers().size() == 1) return;
		
		Player player = ev.getPlayer();
		boolean isPlayerPlaying = main.players.contains(player.getUniqueId());
		
		main.updateGrades();
		if (!main.playerkits.containsKey(player.getUniqueId())) main.playerkits.put(player.getUniqueId(), new PlayerKits(main, player));
		PvPKits.setPlayerTabList(player, main.getPrefix() + "\n" + "§fBienvenue sur la map de §c§lNeyuux_" + "\n", "\n" + "§fMerci à §emini0x_ §fet §expbush §f les builders !");
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, true, false));
		player.setGameMode(GameMode.ADVENTURE);
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 4, 250, true));
		player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 4, 250, true));
		if (main.isState(Gstate.PLAYING) || main.isState(Gstate.FINISHED)) {
			if (!isPlayerPlaying) {
				player.setGameMode(GameMode.SPECTATOR);
				player.getInventory().clear();
				main.clearArmor(player);
				player.setMaxHealth(20);
				player.setHealth(20.0);
				player.setFoodLevel(20);
				player.sendMessage(main.getPrefix() + "§9 Le jeu a déjà démarré !");
				player.sendMessage(main.getPrefix() + "§9Votre mode de jeu a été établi en spectateur.");
				main.spectators.add(player);
			} else {
				Kits Kit = main.playerkits.get(player.getUniqueId()).getKit();
				Team t = main.getPlayerTeam(player, main.playerkits.get(player.getUniqueId()).getTeam());
				if (!main.getGameConfig().hasHideKits()) {
					player.setDisplayName("§8[§r" + Kit.getName() + "§8]§r " +t.getPrefix()+ player.getName()+t.getSuffix());
					player.setPlayerListName("§8[§r" + Kit.getName() + "§8]§r " +t.getPrefix()+ player.getName()+t.getSuffix());
				} else {
					player.setDisplayName(t.getPrefix()+ player.getName()+t.getSuffix());
					player.setPlayerListName(t.getPrefix()+ player.getName()+t.getSuffix());
				}
				player.setGameMode(GameMode.SURVIVAL);
			}
			
		} else {
			player.teleport(Teams.NONE.getMainSalle());
			player.getInventory().clear();
			main.clearArmor(player);
			player.getInventory().setItem(1, main.getItem("§7§lDevenir Spectateur", null, Material.GHAST_TEAR, (short)0));
			Teams.NONE.getTeam().addEntry(player.getName());
			main.setPlayerTeamFromGrade(player, Teams.NONE);
			for (Entry<String, List<UUID>> en : main.getGrades().entrySet())
				if (en.getValue().contains(player.getUniqueId()))
					player.getInventory().setItem(6, main.getGameConfig().getComparator());
			player.updateInventory();
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent ev) {
		Player player = ev.getPlayer();
		main.updateGrades();
		main.spectators.remove(player);
	}
	
	
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent ev) {
		Block block = ev.getBlockPlaced();
		Player player = ev.getPlayer();
		
		if (player.getGameMode() != GameMode.CREATIVE && block.getType() != Material.FIRE && block.getType() != Material.IRON_BLOCK && block.getType() != Material.DIAMOND_BLOCK && block.getType() != Material.GOLD_BLOCK && block.getType() != Material.COAL_BLOCK && block.getType() != Material.DIRT && block.getType() != Material.TNT)
			ev.setCancelled(true);
		else {
			if (!player.getGameMode().equals(GameMode.CREATIVE)) {
				if (player.getInventory().getItem(player.getInventory().getHeldItemSlot()).getType().equals(Material.FLINT_AND_STEEL) && !block.getType().equals(Material.FIRE)) return;
				if (main.blocknomap.contains(block)) return;
				main.blocknomap.add(block);
				main.playerkits.get(player.getUniqueId()).getBlocks().add(block);
			}
		}
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent ev) {
		Block block = ev.getBlock();
		Player player = ev.getPlayer();
		
		if (player.getGameMode() != GameMode.CREATIVE && block.getType() != Material.IRON_BLOCK && block.getType() != Material.DIAMOND_BLOCK && block.getType() != Material.GOLD_BLOCK && block.getType() != Material.COAL_BLOCK && block.getType() != Material.DIRT && block.getType() != Material.TNT)
			ev.setCancelled(true);
		if (!main.blocknomap.contains(block) && !player.getGameMode().equals(GameMode.CREATIVE)) {
			ev.setCancelled(true);
			PvPKits.sendActionBar(player, main.getPrefix() + "§c Vous ne pouvez pas cassez un block §4qui n'a pas été posé par un joueur !");
		}
		main.blocknomap.remove(block);
	}
	
	@EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        Material type = event.getBlock().getType();
        if (type.equals(Material.STATIONARY_LAVA) || type.equals(Material.STATIONARY_WATER))
            event.setCancelled(true);
    }
	
	@EventHandler
	public void onBoom(EntityExplodeEvent event) {
		event.blockList().clear();
	}
	
	
	@EventHandler
	public void onBurnBlock(BlockBurnEvent e) {
		Block block = e.getBlock();
		if (!main.blocknomap.contains(block))
			e.setCancelled(true);
	}
	
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent ev) {
		Player player = ev.getPlayer();
		Item item = ev.getItem();
		ItemStack it = item.getItemStack();
		
		if (player.getInventory().contains(it.getType()))
			for (ItemStack i : player.getInventory().getContents())
				if (i != null)
					if (i.getType().equals(it.getType())) {
						ItemMeta im = i.getItemMeta();
						im.spigot().setUnbreakable(false);
						i.setItemMeta(im);
					}
	}

	@EventHandler
	public void onInv(InventoryDragEvent ev) {
		if (ev.getInventory().getName().equals("container.inventory") && ev.getWhoClicked() instanceof Player) {
			((Player)ev.getWhoClicked()).updateInventory();
			Bukkit.getLogger().info(ev.getWhoClicked().getName() + " " + Arrays.toString(ev.getWhoClicked().getInventory().getContents()));
		}
	}
	
	
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player sender = event.getPlayer();
		PlayerKits senderkits = main.playerkits.get(sender.getUniqueId());
		String msg = event.getMessage().trim();
		event.setCancelled(false);
		
		if (!senderkits.isTeam(Teams.NONE) && !main.spectators.contains(sender)) {
			if (msg.startsWith("!")) {
				String[] with = msg.split("!");
				event.setFormat("§8[§7Global§8] " + sender.getDisplayName() + "§r §7»§f " + with[1].trim());
			} else {
				List<Player> teammates = new ArrayList<>();
				event.setCancelled(true);
				for(Player p : Bukkit.getOnlinePlayers())
					if (main.playerkits.get(p.getUniqueId()).getTeam().equals(senderkits.getTeam()))
						teammates.add(p);
				
				for (Player tm : teammates)
					tm.sendMessage(senderkits.getTeam().getSecondColor() + "[" + senderkits.getTeam().getColor() +"Team"+senderkits.getTeam().getSecondColor()+"] " + sender.getDisplayName() + "§r §7»§f " + msg);
			}
		} else
			event.setFormat("§8[§7Global§8] " + sender.getDisplayName() + "§r §7»§f " + msg);
		
	}
	
	
	@EventHandler
	public void onEat(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();		
		
		if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lSuper §6§lBiscuit")) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 14, 0));
			player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 9, 0));
		} else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lM§f§lÉ§c§lG§f§lA §f§lBiscuit")) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 11, 0));
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 14, 1));
			player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 9, 0));
		} else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lPains "+ Kits.MILITAIRE.getName())) {
			if (player.getHealth() <= (player.getMaxHealth() - 1))
				player.setHealth(player.getHealth() + (double)1);
			else
				player.setHealth(player.getMaxHealth());
			((CraftPlayer) player).getHandle().setAbsorptionHearts(2);
		}
	}
	
	
	
	@EventHandler
	public void onSpawnItemInteract(PlayerInteractEvent ev) {
		Player player = ev.getPlayer();
		PlayerKits playerkits = main.playerkits.get(player.getUniqueId());
		ItemStack current = player.getItemInHand();
		
		if (current.getType().equals(Material.GHAST_TEAR)) {
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
		} else if (current.getType().equals(Material.BED)) {
			player.getInventory().setHeldItemSlot(0);
			player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);
			
			if (playerkits.getRetour().equals(Retour.SALLE_TEAM)) {
				Teams team = playerkits.getTeam();
				player.teleport(Teams.NONE.getMainSalle());
				player.getInventory().remove(Material.BED);
				player.getInventory().remove(Material.CHEST);
				player.sendMessage(main.getPrefix() + "§9Tu as bien quitté ta team.");
				
				Bukkit.broadcastMessage(main.getPrefix() + player.getDisplayName() + " §fa quitté la " + team.getColor() + "§lTeam " + team.getAdjectiveName() + "§f !");
				main.players.remove(player.getUniqueId());
				playerkits.setTeam(Teams.NONE);
				main.setPlayerTeamFromGrade(player, Teams.NONE);
				
			} else if (playerkits.getRetour().equals(Retour.SALLE_KITS)) {
				Teams team = playerkits.getTeam();
				
				player.teleport(team.getMainSalle());
				player.getInventory().setItem(4, main.getItem("§6Kits " + team.getColor() + team.getName() + "s", null, Material.CHEST, (short)0));
				playerkits.setRetour(Retour.SALLE_TEAM);
				
			} else if (playerkits.getRetour().equals(Retour.SALLE_ATTENTE)) {
				Teams team = playerkits.getTeam();
				
				player.setDisplayName(main.getPlayerTeam(player, team).getPrefix() + player.getName() + main.getPlayerTeam(player, team).getSuffix());
				player.setPlayerListName(player.getDisplayName());
				player.teleport(team.getMainSalle());
				player.getInventory().setItem(4, main.getItem("§6Kits " + team.getColor() + team.getName() + "s", null, Material.CHEST, (short)0));

				Kits kit = playerkits.getKit();
				
				if (!main.getGameConfig().hasHideKits()) Bukkit.broadcastMessage(main.getPrefix() + player.getDisplayName() + " §fn'a plus le kit " + kit.getName()+ " §f!");
				playerkits.setKit(null);
				main.players.remove(player.getUniqueId());
				playerkits.setRetour(Retour.SALLE_TEAM);
			}
			player.updateInventory();
		}
	}
	
	@EventHandler
	public void onSignClick(PlayerInteractEvent ev) {
		Player player = ev.getPlayer();
		Action action = ev.getAction();
		Block cblock = ev.getClickedBlock();
		
		if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
			if (cblock == null) return;
			BlockState bs = cblock.getState();
			
			if(bs instanceof Sign) {
				Sign sign = (Sign)bs;
				
				if (sign.getLine(0).contains("Rejoindre")) {
					Teams team = Teams.getByColor(sign.getLine(2).trim().substring(0, 2));
					main.addPlayerTeam(player, team);
				}
			}
		}
	}
	
	@EventHandler
	public void onKitsInteract(PlayerInteractEvent ev) {
		Player player = ev.getPlayer();
		ItemStack current = player.getItemInHand();
		
		if (current.getType().equals(Material.CHEST)) {
			Inventory invKits = Bukkit.createInventory(null, 27, current.getItemMeta().getDisplayName());
			
			invKits.setItem(1, main.getItem(Kits.TOAD.getName(), kitsLores.get(Kits.TOAD), Material.RED_MUSHROOM, (short)0));
			invKits.setItem(11, main.getItem(Kits.PYROMANE.getName(), kitsLores.get(Kits.PYROMANE), Material.BLAZE_POWDER, (short)0));
			invKits.setItem(3, main.getItem(Kits.LG.getName(), kitsLores.get(Kits.LG), Material.MONSTER_EGG, (short)0));
			invKits.setItem(13, main.getItem(Kits.SORCIERE.getName(), kitsLores.get(Kits.SORCIERE), Material.POTION, (short)0));
			invKits.setItem(5, main.getItem(Kits.HEALER.getName(), kitsLores.get(Kits.HEALER), Material.GOLDEN_APPLE, (short)0));
			invKits.setItem(15, main.getItem(Kits.XRAYEUR.getName(), kitsLores.get(Kits.XRAYEUR), Material.DIAMOND_ORE, (short)0));
			invKits.setItem(7, main.getItem(Kits.BISCUIT.getName(), kitsLores.get(Kits.BISCUIT), Material.COOKIE, (short)0));
			invKits.setItem(17, main.getItem(Kits.MACRON.getName(), kitsLores.get(Kits.MACRON), Material.GOLD_INGOT, (short)0));
			invKits.setItem(9, main.getItem(Kits.MILITAIRE.getName(), kitsLores.get(Kits.MILITAIRE), Material.BOW, (short)0));
			invKits.setItem(22, main.getItem(Kits.ALLAHU_AKBAR.getName(), kitsLores.get(Kits.ALLAHU_AKBAR), Material.TNT, (short)0));
			
			player.openInventory(invKits);
		} else if (current.getType().equals(Material.MAGMA_CREAM)) {
			Kits kit = null;
			for (Kits k : Kits.values()) {
				try {
					if (k.getName().equals(current.getItemMeta().getDisplayName().substring(27, 27 + k.getName().length())))
						kit = k;
				} catch (StringIndexOutOfBoundsException ignored){}
			}
			main.setPlayerKit(player, kit);
		}
	}
	
	@EventHandler
	public void onInvKit(InventoryClickEvent ev) {
		Player player = (Player) ev.getWhoClicked();
		PlayerKits playerkits = main.playerkits.get(player.getUniqueId());
		ItemStack current = ev.getCurrentItem();
		
		if (current == null) return;
		if (current.getType().equals(Material.AIR)) return;
		
		if (ev.getInventory().getName().startsWith("§6Kits "))
			tpSalleKit(player, Kits.getByName(current.getItemMeta().getDisplayName()));
		
		if (ev.getInventory().getName().equals("§4§lL§8§lG"))
			if (current.getType().equals(Material.BOWL)) {
				playerkits.setStuffLG(StuffLG.CHIEN);
				player.playSound(player.getLocation(), Sound.WOLF_WHINE, 10, 1);
			} else if (current.getType().equals(Material.RABBIT_STEW)) {
				playerkits.setStuffLG(StuffLG.LOUP);
				player.playSound(player.getLocation(), Sound.WOLF_HOWL, 8, 1);
			}
		if (playerkits.getStuffLG() != null) {
			playerkits.getStuffLG().GiveStuff(player);
			player.closeInventory();
		}
	}
	
	@EventHandler
	public void onChooseKitArmorStandClick(PlayerInteractAtEntityEvent ev) {
		Entity e = ev.getRightClicked();
		
		if (e.getType().equals(EntityType.ARMOR_STAND))
			tpSalleKit(ev.getPlayer(), Kits.getByName(e.getCustomName()));
	}
	
	
	
	@EventHandler
	public void onMoove(PlayerMoveEvent ev) {
		try {
			Player player = ev.getPlayer();
			PlayerKits playerkits = main.playerkits.get(player.getUniqueId());
			if (playerkits == null) {
				main.playerkits.put(player.getUniqueId(), new PlayerKits(main, player));
			}
			Location to = ev.getTo();
			
			if (main.isState(Gstate.PLAYING)) {
				for (UUID id : main.players) {
					Player p = Bukkit.getPlayer(id);
					PlayerKits pkits = main.playerkits.get(p.getUniqueId());
					if (pkits.isKit(Kits.TOAD))
						if (playerkits.getTeam() != null && !playerkits.getTeam().contains(p))
							if (pkits.getToadBlocks() != null)
								if (pkits.getToadBlocks().getKey().contains(to.getBlock()))
									if (!player.hasPotionEffect(PotionEffectType.WITHER)) {
											player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 8, 1));
											p.sendMessage(main.getPrefix() + player.getDisplayName() + " §2a été touché par vos spores !");
											p.playSound(p.getLocation(), Sound.DRINK, 10, 1);
											player.playSound(p.getLocation(), Sound.HORSE_BREATHE, 10, 1);
										}
				}
			
			
				if (playerkits.isKit(Kits.LG) && playerkits.getStuffLG() == null) {
					Inventory CSLGC = Bukkit.createInventory(null, InventoryType.HOPPER, "§4§lL§8§lG");
					
					CSLGC.setItem(0, main.getItem("§fStuff §a§lCHIEN", Arrays.asList("§2Casque §fen §lFer§7(§8Protection §a2§7)", "§1Plastron §ben §lDiamant§7(§8Protection §f1§7)", "§bPantalon §fen §lFer§7(§8Protection §a2§7)", "§aBottes §ben §lDiamant§7(§8Protection §f1§7)", "§4Épée §fen §lFer", "§e§lG§eolden §lA§epples §fx3", "§7Potion de Résistance §820s"), Material.BOWL, (short)0));
		
					CSLGC.setItem(2, main.getItem("§6§lChoisissez le stuff de votre " + "§4§lL§8§lG", Arrays.asList("§fLe kit " + "§4§lL§8§lG" + " §fdispose d'un choix de stuff.", "§fLe stuff \"§a§lCHIEN§f\" : qui", "§fest un kit plus orienté tank.", "§f§lOU", "§fLe stuff \"§c§lLOUP§f\" qui", "§fest un kit plus orienté assassin.", "§9§lPOUR CHOISIR SON STUFF,", "§9§lCLIQUEZ SUR L'ITEM SU STUFF VOULU !"), Material.BOOK_AND_QUILL, (short)0));
		
					CSLGC.setItem(4, main.getItem("§fStuff §c§lLOUP", Arrays.asList("§2Casque §fen §lFer§7(§8Protection §f1§7)", "§1Plastron §ben §lDiamant§7(§8Protection §f1§7)", "§bPantalon §fen §lFer§7(§8Protection §f1§7)", "§aBottes §fen §lFer", "§4Épée §ben §lDiamant§7(§4Sharpness §f1§7)", "§e§lG§eolden §lA§epples §fx2", "§4Potion de Force §830s"), Material.RABBIT_STEW, (short)0));
					
					player.getPlayer().openInventory(CSLGC);
				}
				
				if (playerkits.isKit(Kits.TOAD))
					if (playerkits.getTeam().getPlayers().size() >= 2) {
						List<Player> proches = new ArrayList<>();
						
						if (player.getGameMode().equals(GameMode.SURVIVAL))
							for (Entity ne : player.getNearbyEntities(3, 3, 3))
								if (ne.getType().equals(EntityType.PLAYER)) {
									Player pe = (Player)ne;
									if (pe.getGameMode().equals(GameMode.SURVIVAL))
										if (playerkits.getTeam().contains(pe)) {
											player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
											proches.add(pe);
										}
								}
						if (proches.isEmpty())
							if (player.hasPotionEffect(PotionEffectType.REGENERATION))
								player.removePotionEffect(PotionEffectType.REGENERATION);
					}
				
				if (playerkits.isKit(Kits.MACRON)) {
					List<Player> proches = new ArrayList<>();
					
					if (player.getGameMode().equals(GameMode.SURVIVAL))
						for (Entity ne : player.getNearbyEntities(30, 30, 30))
							if (ne.getType().equals(EntityType.PLAYER)) {
								Player pe = (Player) ne;
								if (pe.getGameMode().equals(GameMode.SURVIVAL))
									if (!playerkits.getTeam().contains(pe))
										proches.add(pe);
							}
						
						if (proches.isEmpty())
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
				}
			}
		} catch (NullPointerException ignored) {}
	}
	
	
	@EventHandler
	public void onGameInteract(PlayerInteractEvent ev) {
		Player player = ev.getPlayer();
		ItemStack current = player.getItemInHand();
		Block cblock = ev.getClickedBlock();
		
		if (current == null) return;
		
		if (current.getType().equals(Material.MONSTER_EGG) || current.getType().equals(Material.MONSTER_EGGS)) {
			if (cblock == null) return;
			if (cblock.getType().equals(Material.AIR) || current.getType().equals(Material.AIR)) return;
			ev.setCancelled(true);
			
			Zombie z = (Zombie) player.getWorld().spawnEntity(new Location(cblock.getWorld(), cblock.getX(), cblock.getY() + 1, cblock.getZ()), EntityType.ZOMBIE);
			EntityEquipment zee = z.getEquipment();
			zee.setHelmet(getZombieArmor(Material.LEATHER_HELMET, Color.fromBGR(0x0c0e32), Collections.emptyList()));
			zee.setChestplate(getZombieArmor(Material.IRON_CHESTPLATE, null, Collections.singletonList(new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 4))));
			zee.setLeggings(getZombieArmor(Material.LEATHER_LEGGINGS, Color.fromBGR(0x0c0e32), ImmutableList.of()));
			zee.setBoots(getZombieArmor(Material.LEATHER_BOOTS, Color.fromBGR(0x0c0e32), ImmutableList.of()));
			zee.setItemInHand(getZombieItem(Material.STICK, Arrays.asList(new SimpleEntry<>(Enchantment.DAMAGE_ALL, 4), new SimpleEntry<>(Enchantment.KNOCKBACK, 1))));
			z.setCustomName("§9§lCRS " + main.getPlayerTeam(player, main.playerkits.get(player.getUniqueId()).getTeam()).getPrefix() + player.getName() + main.getPlayerTeam(player, main.playerkits.get(player.getUniqueId()).getTeam()).getSuffix());
			main.playerkits.get(player.getUniqueId()).getZombiesMacron().add(z);
			
			if (current.getAmount() == 1)
				player.getInventory().remove(current);
			else
				current.setAmount(current.getAmount() - 1);
			player.updateInventory();
		}
		
		else if (current.getType().equals(Material.DISPENSER) && player.getLevel() == 0) {
			ev.setCancelled(true);
			
			Potion pot = new Potion(PotionType.INSTANT_DAMAGE, 1);
			pot.setSplash(true);
			ItemStack it = pot.toItemStack(1);
			ItemMeta itm = it.getItemMeta();
			itm.setDisplayName("§4Instant damage §f1");
			it.setItemMeta(itm);
			player.getInventory().addItem(it);
			player.updateInventory();
			player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 8, 1);
			main.playerkits.get(player.getUniqueId()).startTimerGenSoso();
		}
	}
	
	
	@EventHandler
	public void onZombieTarget(EntityTargetLivingEntityEvent ev) {
		if (!ev.getEntity().getType().equals(EntityType.ZOMBIE)) return;
		if (ev.getTarget() == null) return;
		if (!ev.getTarget().getType().equals(EntityType.PLAYER)) return;
		Zombie z = (Zombie)ev.getEntity();
		if (main.getZombieOwner(z) == null) return;
		
		if (main.playerkits.get(main.getZombieOwner(z).getUniqueId()).getTeam().contains((Player)ev.getTarget())) ev.setCancelled(true);
	}
	
	
	@EventHandler
	public void onCSInteract(PlayerInteractEvent ev) {
		Player player = ev.getPlayer();
		PlayerKits playerkits = main.playerkits.get(player.getUniqueId());
		Teams team = playerkits.getTeam();
		Kits kit = playerkits.getKit();
		ItemStack current = player.getItemInHand();
		
		if (current == null) return;
		
		if (current.getType().equals(Material.DEAD_BUSH)) {
			CSState state = playerkits.getCSState();
			String capa = kit.getCapa();
			
			if (state.equals(CSState.READY)) {
				if (kit.equals(Kits.ALLAHU_AKBAR)) {
					playerkits.setTimerPluieTNT(20);
					BukkitRunnable r = CSAllahuAkbar(player, playerkits);
					r.runTaskTimer(main, 0, 20);
					
					Bukkit.broadcastMessage(main.getPrefix() + " §e§lLes TNT de " + player.getDisplayName() + " §e§lcommencent à pleuvoir !");
				}
				else if (kit.equals(Kits.BISCUIT)) {
					ItemStack c = new ItemStack(Material.COOKIE);
					ItemMeta cm = c.getItemMeta();
					cm.setDisplayName(capa);
					cm.addEnchant(Enchantment.DURABILITY, 1, true);
					cm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					cm.setLore(Arrays.asList("§f\u21D3Attribue les effets suivants \u21D3", "§4§lF§corce §2§l11s", "§b§lS§fpeed §8§l2 §2§l14s", "§0§lR§8ésistance §8§l2 §2§l9s", "§6§lMiam Miam " + player.getDisplayName()));
					c.setItemMeta(cm);
					player.getInventory().addItem(c);
					player.updateInventory();
				}
				else if (kit.equals(Kits.HEALER)) {
					capa = playerkits.getCSHeal().getName();
					if (playerkits.getCSHeal().equals(CSHeal.REANIMATION)) {
						Inventory InvMorts = Bukkit.createInventory(null, 27, "§7Coéquipiers §4§lMORTS");
						
						for (PlayerKits pkits : main.playerkits.values())
							if (pkits.getTeam().contains(player))
								if (Bukkit.getOnlinePlayers().contains(pkits.getPlayer()) && pkits.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
									ItemStack it = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
									SkullMeta itm = (SkullMeta)it.getItemMeta();
									itm.setOwner(pkits.getPlayer().getName());
									itm.setDisplayName(team.getColor() + pkits.getPlayer().getName());
									itm.setLore(Arrays.asList("§7Réssucite le joueur §d" + pkits.getPlayer().getName() + "§7.", "§7Il apparaîtra au l'île du spawn de votre île."));
									it.setItemMeta(itm);
									InvMorts.addItem(it);
								}
							
						player.openInventory(InvMorts);
						
					} else if (playerkits.getCSHeal().equals(CSHeal.REGENERATION)) {
						 PotionEffect pe = new PotionEffect(PotionEffectType.REGENERATION, 20 * 7, 2);
						 
						 for (Player p : team.getPlayers())
							if (p.getGameMode().equals(GameMode.SURVIVAL)) {
								p.addPotionEffect(pe);
								p.sendMessage(main.getPrefix() + player.getDisplayName() + " §dest entrain d'utiliser sa capacité spéciale §7\""+capa+"§7\" §d sur vous !");
								p.playSound(p.getLocation(), Sound.DONKEY_IDLE, 9, 1);
								player.sendMessage(main.getPrefix() + p.getDisplayName() + " §dest entrain de se faire §l§nHEAL §r§d !");
							}
					}
					
				}
				else if (kit.equals(Kits.LG)) {
					//VGPLGBAA = Vilain Grand-Père des Loups-Garou Blancs Amnesiques Anonymes
					//Vilain petit loup = Speed 19s
					//Grand méchant loup = Force 15s + Régén 1/10 des dégâts qu'il met
					//Infect père des loups = Peut mettre quelqu'un dans son équipe. pendant 30s
					//Loup-Garou Blanc = Possède 15 coeurs pendant 20s
					//Amnesique = Si un autre lg le tape il obtient force 2 pendant 3s pendant 30s
					//Anonyme = Est invisible pendant 5s
					
					playerkits.startLGCS();
					
					player.setMaxHealth(30);
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 19, 0));
					player.setHealth(player.getHealth() + 10);
					
					for (Player p : Bukkit.getOnlinePlayers())
						p.hidePlayer(player);
					player.setDisplayName("§8[§r" + "§b§lV§4§lG§c§lP§4§lL§8§lG§f§lB§3§lA§7§lA" + "§8]§r " +main.getPlayerTeam(player, team).getPrefix()+ player.getName()+main.getPlayerTeam(player, team).getSuffix());
					player.setPlayerListName(player.getDisplayName());
					player.getWorld().strikeLightningEffect(player.getLocation());
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(player.getLocation(), Sound.WOLF_GROWL, 10, 1);
						p.sendMessage(main.getPrefix() + player.getDisplayName() + " §e§lSE TRANSFORME EN VILAIN GRAND-PERE DES LOUPS-GAROU BLANCS AMNESIQUES ANONYMES !");
					}
					
				}
				else if (kit.equals(Kits.MACRON)) {
					Location PontNoir = new Location(player.getWorld(), -231.5, 94, -159.5, 180f, 0f);
					Location PontRose = new Location(player.getWorld(), -230.5, 94, -41.5, 0f, 0f);
					Location IntermNoir = new Location(player.getWorld(), -233.5, 102, -111.5, 0f, 0f);
					Location IntermRose = new Location(player.getWorld(), -230.5, 102, -89.5, 180f, 0f);	
					Location IntermRoseNoirCentre = new Location(player.getWorld(), -243.5, 102, -102.5, 90f, 0f);
					Location CentreRoseNoir = new Location(player.getWorld(), -339.5, 118, -102.5, 90f, 0f);
					
					Location PontRouge = new Location(player.getWorld(),  -449.5, 94, -264.5, 90f, 0f);
					Location PontVert = new Location(player.getWorld(), -331.5, 94, -266.5, -90f, 0f);
					Location IntermRouge = new Location(player.getWorld(), -401.5, 102, -264.5, -90f, 0f);
					Location IntermVert = new Location(player.getWorld(), -379.5, 102, -266.5, 90f, 0f);
					Location IntermRougeVertCentre = new Location(player.getWorld(), -392.5, 102, -253.5, 180f, 0f);
					Location CentreRougeVert = new Location(player.getWorld(), -392.5, 118, -157.5, 0f, 0f);
					
					Location PontBleu = new Location(player.getWorld(), -557.5, 94, -163.5, 180f, 0f);
					Location PontJaune = new Location(player.getWorld(), -556.5, 94, -45.5, 0f, 0f);
					Location IntermBleu = new Location(player.getWorld(), -557.5, 102, -115.5, 0f, 0f);
					Location IntermJaune = new Location(player.getWorld(), -556.5, 102, -93.5, 180f, 0f);
					Location IntermBleuJauneCentre = new Location(player.getWorld(), -544.5, 102, -102.5, 90f, 0f);
					Location CentreBleuJaune = new Location(player.getWorld(), -447.5, 120, -102.5, -90f, 0f);
					
					ArrayList<Location> locs = new ArrayList<>();
					locs.add(PontNoir);
					locs.add(PontBleu);
					locs.add(PontRouge);
					locs.add(PontJaune);
					locs.add(PontRose);
					locs.add(PontVert);
					locs.add(IntermNoir);
					locs.add(IntermBleu);
					locs.add(IntermRouge);
					locs.add(IntermJaune);
					locs.add(IntermRose);
					locs.add(IntermVert);
					locs.add(IntermRoseNoirCentre);
					locs.add(IntermRougeVertCentre);
					locs.add(IntermBleuJauneCentre);
					locs.add(CentreBleuJaune);
					locs.add(CentreRoseNoir);
					locs.add(CentreRougeVert);
					
					SpawnGiletsJaunes(player, locs);
					playerkits.startTimerGiletsJaunes();
					
					Bukkit.broadcastMessage(main.getPrefix() + "Les §e§nGilets Jaunes §f de " + player.getDisplayName() + " §fbloquent les ponts !");
					for (Player p : Bukkit.getOnlinePlayers())
						if (!p.equals(player))
							p.playSound(p.getLocation(), Sound.IRONGOLEM_DEATH, 8, 1);
					
				}
				else if (kit.equals(Kits.MILITAIRE)) {
					capa = playerkits.getCSMili().getName();
					if (playerkits.getCSMili().equals(CSMili.ARRAIN)) {
						playerkits.setTimerPluieFleches(12);
						BukkitRunnable r = CSMiliArrain(player, playerkits);
						r.runTaskTimer(main, 0, 20);
						
						Bukkit.broadcastMessage(main.getPrefix() + " §f§lLes Flèches de " + player.getDisplayName() + " §f§lcommencent à pleuvoir !");
					} else if (playerkits.getCSMili().equals(CSMili.BOWSPAM)) {
						
						for (Player p : Bukkit.getOnlinePlayers())
							if (!p.equals(player))
								p.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 8, 1);
						
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 1));
						playerkits.setTimerBowSpam(20);
						
						ItemStack it = player.getInventory().getHelmet();
						LeatherArmorMeta itm = (LeatherArmorMeta) it.getItemMeta();
						itm.setColor(Color.fromRGB(0x832E2E));
						it.setItemMeta(itm);
						player.getInventory().setHelmet(it);
						
						ItemStack it2 = player.getInventory().getLeggings();
						LeatherArmorMeta itm2 = (LeatherArmorMeta) it2.getItemMeta();
						itm2.setColor(Color.fromRGB(0x832E2E));
						it2.setItemMeta(itm2);
						player.getInventory().setLeggings(it2);
						
						ItemStack it3 = player.getInventory().getBoots();
						LeatherArmorMeta itm3 = (LeatherArmorMeta) it3.getItemMeta();
						itm3.setColor(Color.fromRGB(0x832E2E));
						it3.setItemMeta(itm3);
						player.getInventory().setBoots(it3);
					}
					
				}
				else if (kit.equals(Kits.PYROMANE)) {
					capa = playerkits.getCSPyro().getName();
					if (playerkits.getCSPyro().equals(CSPyro.TOURBILLION_DE_LAVE)) {
						
						int PositionX = player.getLocation().getBlockX();
						int PositionY = player.getLocation().getBlockY();
						int PositionZ = player.getLocation().getBlockZ();
						player.teleport(new Location(player.getWorld(), PositionX + 0.5, PositionY, PositionZ + 0.5));
						playerkits.startPyroBlocks();
						
						SetPyroBlock(playerkits, PositionX + 1, PositionY, PositionZ, Material.STATIONARY_LAVA);
						SetPyroBlock(playerkits, PositionX - 1, PositionY, PositionZ, Material.STATIONARY_LAVA);
						SetPyroBlock(playerkits, PositionX, PositionY, PositionZ + 1, Material.STATIONARY_LAVA);
						SetPyroBlock(playerkits, PositionX, PositionY, PositionZ - 1, Material.STATIONARY_LAVA);
						SetPyroBlock(playerkits, PositionX + 1, PositionY + 1, PositionZ, Material.STATIONARY_LAVA);
						SetPyroBlock(playerkits, PositionX - 1, PositionY + 1, PositionZ, Material.STATIONARY_LAVA);
						SetPyroBlock(playerkits, PositionX, PositionY + 1, PositionZ + 1, Material.STATIONARY_LAVA);
						SetPyroBlock(playerkits, PositionX, PositionY + 1, PositionZ - 1, Material.STATIONARY_LAVA);
						SetPyroBlock(playerkits, PositionX, PositionY + 2, PositionZ, Material.STATIONARY_LAVA);
						SetPyroBlock(playerkits, PositionX, PositionY + 2, PositionZ, Material.STATIONARY_LAVA);
						SetPyroBlock(playerkits, PositionX + 2, PositionY, PositionZ, Material.FIRE);
						SetPyroBlock(playerkits, PositionX - 2, PositionY, PositionZ, Material.FIRE);
						SetPyroBlock(playerkits, PositionX, PositionY, PositionZ + 2, Material.FIRE);
						SetPyroBlock(playerkits, PositionX, PositionY, PositionZ - 2, Material.FIRE);
						SetPyroBlock(playerkits, PositionX + 1, PositionY, PositionZ + 1, Material.FIRE);
						SetPyroBlock(playerkits, PositionX - 1, PositionY, PositionZ - 1, Material.FIRE);
						SetPyroBlock(playerkits, PositionX + 3, PositionY, PositionZ, Material.FIRE);
						SetPyroBlock(playerkits, PositionX - 3, PositionY, PositionZ, Material.FIRE);
						SetPyroBlock(playerkits, PositionX, PositionY, PositionZ + 3, Material.FIRE);
						SetPyroBlock(playerkits, PositionX, PositionY, PositionZ - 3, Material.FIRE);
						SetPyroBlock(playerkits, PositionX + 2, PositionY, PositionZ + 2, Material.FIRE);
						SetPyroBlock(playerkits, PositionX - 2, PositionY, PositionZ - 2, Material.FIRE);
						SetPyroBlock(playerkits, PositionX + 4, PositionY, PositionZ, Material.FIRE);
						SetPyroBlock(playerkits, PositionX - 4, PositionY, PositionZ, Material.FIRE);
						SetPyroBlock(playerkits, PositionX, PositionY, PositionZ + 4, Material.FIRE);
						SetPyroBlock(playerkits, PositionX, PositionY, PositionZ - 4, Material.FIRE);
						SetPyroBlock(playerkits, PositionX + 3, PositionY, PositionZ + 3, Material.FIRE);
						SetPyroBlock(playerkits, PositionX - 3, PositionY, PositionZ - 3, Material.FIRE);
						SetPyroBlock(playerkits, PositionX + 5, PositionY, PositionZ, Material.FIRE);
						SetPyroBlock(playerkits, PositionX - 5, PositionY, PositionZ, Material.FIRE);
						SetPyroBlock(playerkits, PositionX, PositionY, PositionZ + 5, Material.FIRE);
						SetPyroBlock(playerkits, PositionX, PositionY, PositionZ - 5, Material.FIRE);
						SetPyroBlock(playerkits, PositionX + 4, PositionY, PositionZ + 4, Material.FIRE);
						SetPyroBlock(playerkits, PositionX - 4, PositionY, PositionZ - 4, Material.FIRE);
						SetPyroBlock(playerkits, PositionX + 6, PositionY, PositionZ, Material.FIRE);
						SetPyroBlock(playerkits, PositionX - 6, PositionY, PositionZ, Material.FIRE);
						SetPyroBlock(playerkits, PositionX, PositionY, PositionZ + 6, Material.FIRE);
						SetPyroBlock(playerkits, PositionX, PositionY, PositionZ - 6, Material.FIRE);
						SetPyroBlock(playerkits, PositionX + 5, PositionY, PositionZ + 5, Material.FIRE);
						SetPyroBlock(playerkits, PositionX - 5, PositionY, PositionZ - 5, Material.FIRE);
						
						Location loc = player.getLocation();
		                for(int i = 0; i <360; i+=5){
							loc.setZ(loc.getZ() + Math.cos(i)*5);
		                    loc.setX(loc.getX() + Math.sin(i)*5);
		                    loc.getWorld().playEffect(loc, Effect.STEP_SOUND, 51);
		                }
						
						player.playSound(player.getLocation(), Sound.LAVA, 10, 1);
						
					}
					
				}
				else if (kit.equals(Kits.SORCIERE)) {
					capa = playerkits.getCSSoso().getName();
					if (playerkits.getCSSoso().equals(CSSoso.CORBEAU_NIQUEUR)) {
						List<Player> hits = new ArrayList<>();
						
						for (UUID id : main.players) {
							Player p = Bukkit.getPlayer(id);
						
							if (!playerkits.isTeam(main.playerkits.get(p.getUniqueId()).getTeam()) && p.getGameMode().equals(GameMode.SURVIVAL))
								hits.add(p);
						}
						for (Player p : hits) {
							if (p.getHealth() < 6)
								p.setHealth(0.00001);
							else
								p.setHealth(p.getHealth() - 6);
							p.sendMessage(main.getPrefix() + player.getDisplayName() + " §ca utilisé sa capacité spéciale §r\"§5§lC§c§lorbeau §5§lN§c§liqueur§r\" §c sur vous !");
							p.playSound(p.getLocation(), Sound.SPLASH, 10, 1);
							
							NumberFormat format = NumberFormat.getInstance();
							format.setRoundingMode(RoundingMode.DOWN);
							format.setMaximumFractionDigits(1);
							
							player.sendMessage(main.getPrefix() + p.getDisplayName() + " §cest maintenant à §4§l" + format.format(p.getHealth()) + " \u2764");
						}
					} else if (playerkits.getCSSoso().equals(CSSoso.EMPOISONNEMENT)) {
						Inventory InvTeams = Bukkit.createInventory(null, InventoryType.HOPPER, "§fTeam du §cjoueur");
						
						for (Teams t : Teams.getAliveTeams())
							if (!t.getName().equalsIgnoreCase(team.getName())) {
								String tname = t.getColor() + t.getName();
								ItemStack titem = new ItemStack(Material.BARRIER);
								ItemMeta tmeta = titem.getItemMeta();
								tmeta.setDisplayName(tname);
								
								if (tname.equalsIgnoreCase("§4Rouge"))
									titem = new ItemStack(Material.WOOL, 1, (short)14);
								else if (tname.equalsIgnoreCase("§1Bleu"))
									titem = new ItemStack(Material.WOOL, 1, (short)11);
								else if (tname.equalsIgnoreCase("§aVert"))
									titem = new ItemStack(Material.WOOL, 1, (short)5);
								else if (tname.equalsIgnoreCase("§eJaune"))
									titem = new ItemStack(Material.WOOL, 1, (short)4);
								else if (tname.equalsIgnoreCase("§8Noir"))
									titem = new ItemStack(Material.WOOL, 1, (short)15);
								else if (tname.equalsIgnoreCase("§dRose"))
									titem = new ItemStack(Material.WOOL, 1, (short)6);
								
								titem.setItemMeta(tmeta);
								InvTeams.addItem(titem);
							}
						player.openInventory(InvTeams);
						
					}
					
				}
				else if (kit.equals(Kits.TOAD)) {
					//Obtient regen proche de ses alités (3b)
					Location plocation = player.getLocation();
					double x = plocation.getX();
					double y = plocation.getY();
					double z = plocation.getZ();
					
					playerkits.startToadBlocks();
					SetToadBlockH(player, x, y, z);
					SetToadBlockH(player, x + 1, y, z);
					SetToadBlockH(player, x + 2, y, z);
					SetToadBlockH(player, x, y, z + 1);
					SetToadBlockH(player, x, y, z + 2);
					SetToadBlockH(player, x - 1, y, z);
					SetToadBlockH(player, x - 2, y, z);
					SetToadBlockH(player, x, y, z - 1);
					SetToadBlockH(player, x, y, z - 2);
					SetToadBlockH(player, x + 1, y, z + 1);
					SetToadBlockH(player, x+ 2, y, z + 2);
					SetToadBlockH(player, x - 1, y, z - 1);
					SetToadBlockH(player, x- 2, y, z - 2);
					BukkitRunnable nuage = CSToad(player, playerkits);
					nuage.runTaskTimer(main, 0, 10);

				}
				else if (kit.equals(Kits.XRAYEUR)) {
					capa = playerkits.getCSXRay().getName();
					if (playerkits.getCSXRay().equals(CSXRay.TELEPORT)) {
						Inventory InvTeams = Bukkit.createInventory(null, InventoryType.HOPPER, "§fÉquipe de la §btéléportation");
						
						for (Teams t : Teams.getAliveTeams())
							if (!t.getName().equalsIgnoreCase(team.getName())) {
								String tname = t.getColor() + t.getName();
								ItemStack titem = new ItemStack(Material.BARRIER);
								ItemMeta tmeta = titem.getItemMeta();
								tmeta.setDisplayName(tname);
								
								if (tname.equalsIgnoreCase("§4Rouge"))
									titem = new ItemStack(Material.WOOL, 1, (short)14);
								else if (tname.equalsIgnoreCase("§1Bleu"))
									titem = new ItemStack(Material.WOOL, 1, (short)11);
								else if (tname.equalsIgnoreCase("§aVert"))
									titem = new ItemStack(Material.WOOL, 1, (short)5);
								else if (tname.equalsIgnoreCase("§eJaune"))
									titem = new ItemStack(Material.WOOL, 1, (short)4);
								else if (tname.equalsIgnoreCase("§8Noir"))
									titem = new ItemStack(Material.WOOL, 1, (short)15);
								else if (tname.equalsIgnoreCase("§dRose"))
									titem = new ItemStack(Material.WOOL, 1, (short)6);
								
								titem.setItemMeta(tmeta);
								InvTeams.addItem(titem);
							}
						player.openInventory(InvTeams);
					} else if (playerkits.getCSXRay().equals(CSXRay.TP_AURA)) {
						if (playerkits.getXRayTPs() > 0) {
							int rdm = new Random().nextInt(main.players.size());
							Player p = Bukkit.getPlayer(main.players.get(rdm));
							
							player.teleport(p);
							player.sendMessage(main.getPrefix() + "§fVous avez été §btéléporté§f à " + p.getDisplayName());
							p.sendMessage(main.getPrefix() + player.getDisplayName() + " §fs'est §btéléporté§f à vous !");
							p.playSound(player.getLocation(), Sound.NOTE_BASS, 10, 1);
							playerkits.removeXRayTPs();
							player.setLevel(playerkits.getXRayTPs());
							player.sendMessage(main.getPrefix() + "§cVous pouvez vous téléporter encore §e" + player.getLevel() + " §cfois !");
							playerkits.setCSState(CSState.READY);
							
						if (playerkits.getXRayTPs() == 0) {
							player.sendMessage(main.getPrefix() + "§cVOUS AVEZ UTILISÉ TOUTES VOS TÉLÉPORTATIONS !");
							playerkits.setCSState(CSState.USED);
						}
						
						} else {
							player.sendMessage(main.getPrefix() + "§cVous avez déjà utilisé toutes vos téléportations !");
							player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 7, 1);
						}
					}
				}
			} else if (state.equals(CSState.WAITING)) {
				
				if (kit.equals(Kits.HEALER)) {
					Inventory CSHealC = Bukkit.createInventory(null, InventoryType.HOPPER, kit.getName());

					CSHealC.setItem(0, main.getItem("§5§lR§d§légénération", Arrays.asList("§fAttribue l'effet", "§5§lR§dénération §0§l3", "§fpendant 7 secondes", "§fà toute votre équipe"), Material.GOLDEN_APPLE, (short)0));

					CSHealC.setItem(2, main.getItem("§6§lCapacité spéciale de votre " + kit.getName(), Arrays.asList("§fLe Kit " + kit.getName() + "§r §fdispose d'un choix de capacité spéciale.", "§fLa capacité de donner §5§lR§dénération §fà toute l'équipe", "§f§lOU§r§f de réssuiter un coéquipier mort.", "§c§lATTENTION, §4 si vous ne choisissez pas", "§4 la capacité spéciale, ce menu se réouvrira à", "§fla prochaine utlisation.", "§9§l CLIQUEZ SUR L'OBJET VOULU ", "§9§lPOUR CHOISIR LA CAPACITÉ SPÉCIALE !"), Material.BOOK_AND_QUILL, (short)0));

					CSHealC.setItem(4, main.getItem("§c§lR§6§léanimation", Arrays.asList("§fPermet de", "§c§lR§6éssuciter§f un membre de votre équipe mort.", "§fSi vous choisissez cette capacité, lors du", "§f prochain clic sur cet objet", "§fUn menu s'affichera avec la liste de vos", " §fcoéquipiers morts", "§fIl vous suffira de cliquer sur celui que", "§f vous voulez sauver."), Material.NETHER_STAR, (short)0));
					
					player.openInventory(CSHealC);
				 
				} else if (kit.equals(Kits.MILITAIRE)) {
					Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, kit.getName());

					inv.setItem(0, main.getItem(CSMili.ARRAIN.getName(), Arrays.asList("§fFait pleuvoir des flèches", "§fautour des ennemis", "§fpendant 12 secondes", "§fchaque flèche que vous touchez vous", "§fdonne 5 secondes de speed 2 (cumulable)."), Material.ARROW, (short)0));

					inv.setItem(2, main.getItem("§6§lCapacité spéciale de votre " + kit.getName(), Arrays.asList("§fLe Kit " + kit.getName() + "§r §fdispose d'un choix de", "§fcapacité spéciale. La capacité de faire pleuvoir des", "§fflèches autour des ennemis. ", "§f§lOU§r§f d'être impossible à mélée.", "§c§lATTENTION, §4 si vous ne choisissez pas", "§4 la capacité spéciale, ce menu se réouvrira à", "§fla prochaine utlisation.", "§9§l CLIQUEZ SUR L'OBJET VOULU ", "§9§lPOUR CHOISIR LA CAPACITÉ SPÉCIALE !"), Material.BOOK_AND_QUILL, (short)0));

					inv.setItem(4, main.getItem(CSMili.BOWSPAM.getName(), Arrays.asList("§fRend impossible", "§fd'être frappé en mélée pendant 20 secondes.", "§fVous obtiendrez également speed pendant ce temps."), Material.GOLDEN_APPLE, (short)1));
					
					player.openInventory(inv);
				 
				} else if (kit.equals(Kits.PYROMANE)) {
					Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, kit.getName().replaceAll("§l", ""));

					inv.setItem(0, main.getItem(CSPyro.TOURBILLION_DE_LAVE.getName(), Arrays.asList("§fFait apparaître de la lave autour", "§fdu joueur. Du feu spawn aussi", "§fDans les environs proches du joueur.", "§fLe tout s'enlève 12s après l'activation de la", "§fCapacité Spéciale."), Material.BLAZE_ROD, (short)0));

					inv.setItem(2, main.getItem("§6§lCapacité spéciale de votre " + kit.getName(), Arrays.asList("§fLe Kit " + kit.getName() + "§r §fdispose d'un choix de capacité spéciale.", "§fLa capacité \"§6§lT§eourbillion de §6§lL§6ave§f\" qui", "§ffait apparaître de la lave et du feu", "§fautour du joueur.", "§f§lOU", "§fLa Capacité : \"§6§lI§enflammation §4§lV§5ampirique§f\" qui retire", "§fde la durabilité au briquet du joueur", "§fet fait en sorte que la lave régénére le joueur.", "§c§lATTENTION, §4 si vous ne choisissez pas", "§4 la capacité spéciale, ce menu se réouvrira à", "§fla prochaine utlisation.", "§9§l CLIQUEZ SUR L'OBJET VOULU ", "§9§lPOUR CHOISIR LA CAPACITÉ SPÉCIALE !"), Material.BOOK_AND_QUILL, (short)0));
					
					ItemStack pot = new ItemStack(Material.POTION);
					Potion pot2 =  new Potion(PotionType.FIRE_RESISTANCE);
					pot2.setSplash(false);
					ItemMeta potMeta = pot.getItemMeta();
					potMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
					potMeta.setDisplayName("§6§lI§enflammation §4§lV§5ampirique");
					pot.setItemMeta(potMeta);
					pot2.apply(pot);
					ItemStack CSpyrovamp = new ItemStack(pot);
					ItemMeta CSpyrovampmeta = CSpyrovamp.getItemMeta();
					CSpyrovampmeta.setDisplayName("§6§lI§enflammation §4§lV§5ampirique");
					CSpyrovampmeta.setLore(Arrays.asList("§fMet la durabilité du briquet du joueur à §e10§f.",  "§fSi le joueur prend des dégâts", "§fde feu, ils seront divisés", "§fet ajoutés à la santé du joueur."));
					CSpyrovamp.setItemMeta(CSpyrovampmeta);
					inv.setItem(4, CSpyrovamp);
					
					player.openInventory(inv);
				 
				} else if (kit.equals(Kits.SORCIERE)) {
					Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, kit.getName());

					inv.setItem(0, main.getItem(CSSoso.CORBEAU_NIQUEUR.getName(), Arrays.asList("§fInflige §4§l3\u2764§f d'un seul coup", "§fà tous les joueurs ennemis.", "§fSi le joueur à moins de §4§l3\u2764§f, il ne mourra pas."), Material.POISONOUS_POTATO, (short)0));

					inv.setItem(2, main.getItem("§6§lCapacité spéciale de votre " + kit.getName(), Arrays.asList("§fLe Kit " + kit.getName() + "§r §fa un choix de capacité spéciale.", "§fLa capacité \"§5§lC§c§lorbeau §5§lN§c§liqueur§f\" qui", "§fretire §4§l3\u2764§f à tous les joueurs adverses.", "§f§lOU", "§fLa Capacité : \"§2§lE§5mpoisonnement§f\" qui retire", "§4§l2\u2764 permanant §f et donne §2poison §710s", "§f à un joueur adverse.", "§c§lATTENTION, §4 si vous ne choisissez pas", "§4 la capacité spéciale, ce menu se réouvrira à", "§fla prochaine utlisation.", "§9§l CLIQUEZ SUR L'OBJET VOULU ", "§9§lPOUR CHOISIR LA CAPACITÉ SPÉCIALE !"), Material.BOOK_AND_QUILL, (short)0));

					inv.setItem(4, main.getItem(CSSoso.EMPOISONNEMENT.getName(), Arrays.asList("§fRetire §4§l2\u2764 permanant§f et attribue l'effet",  "§2Poison §7pendant 10s§f à un joueur adverse choisi.", "§fSi vous choisissez cette capactié spéciale :", "§fun menu s'ouvrira où choisir la team du joueur", "§fvoulu au prochain clic.", "§fPuis, vous pourrez choisir le joueur voulu", "§fen cliquant sur sa tête."), Material.RAW_FISH, (short)0));
					
					player.openInventory(inv);
					
				} else if (kit.equals(Kits.XRAYEUR)) {
					Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, kit.getName());

					inv.setItem(0, main.getItem(CSXRay.TELEPORT.getName(), Arrays.asList("§fTéléporte tous les membres", "§fde l'équipe à un", "§fjoueur précis."), Material.ENDER_PEARL, (short)0));

					inv.setItem(2, main.getItem("§6§lCapacité spéciale de votre " + kit.getName(), Arrays.asList("§fLe Kit " + kit.getName() + "§r §fdispose d'un choix de capacité spéciale.", "§fLa capacité \"§b§lT§e§le§b§ll§e§le§b§lp§e§lo§b§lr§e§lt\"§f qui", "§ftéléporte tous les membres de l'équipe", "§fà un joueur précis.", "§f§lOU", "§fLa Capacité : \"§b§lT§e§lP §4Aura\"§f qui téléporte", "§fle joueur à un autre aléatoirement.", "§cPeut-être utilisé trois fois.", "", "§c§lATTENTION, §4 si vous ne choisissez pas", "§4 la capacité spéciale, ce menu se réouvrira à", "§fla prochaine utlisation.", "§9§l CLIQUEZ SUR L'OBJET VOULU ", "§9§lPOUR CHOISIR LA CAPACITÉ SPÉCIALE !"), Material.BOOK_AND_QUILL, (short)0));

					inv.setItem(4, main.getItem(CSXRay.TP_AURA.getName(), Arrays.asList("§fVous téléporte aléatoirement", "§fà un joueur de la partie.", "§cPeut-être utilisé trois fois !"), Material.ENDER_PORTAL_FRAME, (short)0));
					
					player.openInventory(inv);
					
				}
				
			}
			
			
			if (state.equals(CSState.READY)) {
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 9, 1);
				player.sendMessage(main.getPrefix() + "§dCapacité Spéciale §5\"" + capa + "§5\" §d: §c§lUTILISÉE");
				if (playerkits.isKit(Kits.XRAYEUR)) {
					if (playerkits.getCSXRay() != null)
						return;
				} else if (playerkits.isKit(Kits.SORCIERE))
					if (playerkits.getCSSoso() != null)
						if (playerkits.getCSSoso().equals(CSSoso.EMPOISONNEMENT))
							return;
				  else if (playerkits.isKit(Kits.HEALER))
					  if (playerkits.getCSHeal() != null)
						  if (playerkits.getCSHeal().equals(CSHeal.REANIMATION))
							  return;
				playerkits.setCSState(CSState.USED);
			} else if (state.equals(CSState.USED) || state.equals(CSState.NOT_EXIST)) {
				player.sendMessage(main.getPrefix() + "§4[§cErreur§4]§c Le statut de votre §r" + current.getItemMeta().getDisplayName() + "§r§c est §r" + current.getItemMeta().getLore().get(1));
				player.playSound(player.getLocation(), Sound.ITEM_BREAK, 9, 1);
			}
		}
	}
	
	@EventHandler
	public void onInvCS(InventoryClickEvent ev) {
		Player player = (Player)ev.getWhoClicked();
		Inventory inv = ev.getInventory();
		ItemStack current = ev.getCurrentItem();
		
		if (current == null) return;
		if (!main.players.contains(player.getUniqueId())) return;
		PlayerKits playerkits = main.playerkits.get(player.getUniqueId());
		
		if (inv.getName().equals(Kits.HEALER.getName())) {
			ev.setCancelled(true);
			if (current.getType().equals(Material.GOLDEN_APPLE)) {
				playerkits.setCSHeal(CSHeal.REGENERATION);
				playerkits.setCSState(CSState.READY);
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
				PvPKits.sendActionBar(player, main.getPrefix() + "La capacité " + CSHeal.REGENERATION.getName() + " §fa été choisie !");
			} else if (current.getType().equals(Material.NETHER_STAR)) {
				playerkits.setCSHeal(CSHeal.REANIMATION);
				playerkits.setCSState(CSState.READY);
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
				PvPKits.sendActionBar(player, main.getPrefix() + "§fLa capacité " + CSHeal.REANIMATION.getName() + " §fa été choisie !");
			}
			player.closeInventory();
		} else if (inv.getName().equals(Kits.SORCIERE.getName())) {
			ev.setCancelled(true);
			if (current.getType().equals(Material.POISONOUS_POTATO)) {
				playerkits.setCSSoso(CSSoso.CORBEAU_NIQUEUR);
				playerkits.setCSState(CSState.READY);
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
				PvPKits.sendActionBar(player, main.getPrefix() + "La capacité " + CSSoso.CORBEAU_NIQUEUR.getName() + " §fa été choisie !");
			} else if (current.getType().equals(Material.RAW_FISH)) {
				playerkits.setCSSoso(CSSoso.EMPOISONNEMENT);
				playerkits.setCSState(CSState.READY);
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
				PvPKits.sendActionBar(player, main.getPrefix() + "§fLa capacité " + CSSoso.EMPOISONNEMENT.getName() + " §fa été choisie !");
			}
			player.closeInventory();
		} else if (inv.getName().equals(Kits.XRAYEUR.getName())) {
			ev.setCancelled(true);
			if (current.getType().equals(Material.ENDER_PEARL)) {
				playerkits.setCSXRay(CSXRay.TELEPORT);
				playerkits.setCSState(CSState.READY);
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
				PvPKits.sendActionBar(player, main.getPrefix() + "La capacité " + CSXRay.TELEPORT.getName() + " §fa été choisie !");
			} else if (current.getType().equals(Material.ENDER_PORTAL_FRAME)) {
				playerkits.setCSXRay(CSXRay.TP_AURA);
				playerkits.setCSState(CSState.READY);
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
				PvPKits.sendActionBar(player, main.getPrefix() + "§fLa capacité " + CSXRay.TP_AURA.getName() + " §fa été choisie !");
			}
			player.closeInventory();
		} else if (inv.getName().equals(Kits.PYROMANE.getName().replaceAll("§l", ""))) {
			ev.setCancelled(true);
			if (current.getType().equals(Material.BLAZE_ROD)) {
				playerkits.setCSPyro(CSPyro.TOURBILLION_DE_LAVE);
				playerkits.setCSState(CSState.READY);
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
				PvPKits.sendActionBar(player, main.getPrefix() + "La capacité " + CSPyro.TOURBILLION_DE_LAVE.getName() + " §fa été choisie !");
			} else if (current.getType().equals(Material.POTION)) {
				playerkits.setCSPyro(CSPyro.INFLAMMATION_VAMPIRIQUE);
				playerkits.setCSState(CSState.USED);
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
				PvPKits.sendActionBar(player, main.getPrefix() + "§fLa capacité " + CSPyro.INFLAMMATION_VAMPIRIQUE.getName() + " §fa été choisie !");
				for(int i = 1; i <= 35; i++) {
					ItemStack item = player.getInventory().getItem(i);
					if (!(item == null))
						if (item.getType().equals(Material.FLINT_AND_STEEL)) {
							player.getInventory().remove(item);
							ItemStack br = new ItemStack(Material.FLINT_AND_STEEL);
							ItemMeta brm = br.getItemMeta();
							brm.setDisplayName("§6Briquet "+ Kits.PYROMANE.getName());
							br.setDurability((short)54);
							br.setItemMeta(brm);
							player.getInventory().addItem(br);
							player.updateInventory();
						}
				}	
					for (Block b : playerkits.getBlocks())
							b.setType(Material.AIR);
			}
			player.closeInventory();
		} else if (inv.getName().equals(Kits.MILITAIRE.getName())) {
			ev.setCancelled(true);
			if (current.getType().equals(Material.ARROW)) {
				playerkits.setCSMili(CSMili.ARRAIN);
				playerkits.setCSState(CSState.READY);
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
				PvPKits.sendActionBar(player, main.getPrefix() + "La capacité " + CSMili.ARRAIN.getName() + " §fa été choisie !");
			} else if (current.getType().equals(Material.GOLDEN_APPLE)) {
				playerkits.setCSMili(CSMili.BOWSPAM);
				playerkits.setCSState(CSState.READY);
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
				PvPKits.sendActionBar(player, main.getPrefix() + "§fLa capacité " + CSMili.BOWSPAM.getName() + " §fa été choisie !");
			}
			player.closeInventory();
		}
		
		
		if (inv.getName().equals("§fTeam du §cjoueur")) {
			ev.setCancelled(true);
			Teams t = Teams.getByColor(current.getItemMeta().getDisplayName().substring(0, 2));
			Inventory invTeam = Bukkit.createInventory(null, 27, t.getColor() + "Équipe " + t.getAdjectiveName());
			
			for (Player p : t.getPlayers()) {
				ItemStack it = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
				SkullMeta itm = (SkullMeta)it.getItemMeta();
				itm.setOwner(p.getName());
				itm.setDisplayName(p.getDisplayName());
				itm.setLore(Arrays.asList("§fFait perdre à " + t.getSecondColor() + p.getName() + " §f2 coeurs", "§fpermanent et lui donne poison pendant 10 secondes."));
				it.setItemMeta(itm);
				invTeam.addItem(it);
			}
			player.openInventory(invTeam);
		}
		
		if (inv.getName().equals("§fÉquipe de la §btéléportation")) {
			ev.setCancelled(true);
			Teams t = Teams.getByColor(current.getItemMeta().getDisplayName().substring(0, 2));
			Inventory invTeam = Bukkit.createInventory(null, 27, t.getColor() + "Équipe " + t.getAdjectiveName());
			
			for (Player p : t.getPlayers()) {
				ItemStack it = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
				SkullMeta itm = (SkullMeta)it.getItemMeta();
				itm.setOwner(p.getName());
				itm.setDisplayName(p.getDisplayName());
				itm.setLore(Collections.singletonList("§fFait téléporter toute votre équipe à " + t.getSecondColor() + p.getName() + "§f."));
				it.setItemMeta(itm);
				invTeam.addItem(it);
			}
			player.openInventory(invTeam);
		}
		
		if (inv.getName().equals("§7Coéquipiers §4§lMORTS")) {
			ev.setCancelled(true);
			Player p = Bukkit.getPlayer(((SkullMeta)current.getItemMeta()).getOwner());
			PlayerKits pkits = main.playerkits.get(p.getUniqueId());
			PvPKits.sendActionBarForAllPlayers(main.getPrefix() + "§8Scattering " + p.getDisplayName());
			p.teleport(playerkits.getTeam().getSpawnIle());
			p.setFallDistance(0f);
			
			p.setGameMode(GameMode.SURVIVAL);
			main.getPlayerTeam(p, playerkits.getTeam()).addEntry(p.getName());
			main.players.add(p.getUniqueId());
			main.spectators.remove(p);
			p.setHealth(20.0);
			p.setFoodLevel(20);
			p.setDisplayName("§8[§r" + pkits.getKit().getName() + "§8]§r " + Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(p.getName()).getPrefix() + p.getName() + Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(p.getName()).getSuffix());
			p.setPlayerListName(p.getDisplayName());
			if (main.getGameConfig().hasHideKits()) {
				p.setDisplayName(Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(p.getName()).getPrefix() + p.getName() + Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(p.getName()).getSuffix());
				p.setPlayerListName(p.getDisplayName());
			}
			pkits.GiveStuff();
			if (pkits.isKit(Kits.LG)) {
				pkits.getStuffLG().GiveStuff(p);
				p.setMaxHealth(15);
			} else if (pkits.isKit(Kits.XRAYEUR)) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0, false, false));
				p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 0, false, false));
			}
			
			player.sendMessage(main.getPrefix() + "§c Grâce à vos pouvoirs, vous avez §c§lR§6§léssucité §r " + p.getDisplayName());
			Bukkit.broadcastMessage(main.getPrefix() + player.getDisplayName() + "§r§e a §c§lR§6§léssucité §r" + p.getDisplayName());
			player.sendMessage(main.getPrefix() + "§fCapacité spéciale§r \"§c§lR§6§léanimation§r\" §c§lUTILISÉE");
			
			for (Player p2 : Bukkit.getOnlinePlayers())
				if (p2.equals(p))
					p.playSound(p.getLocation(), Sound.ZOMBIE_REMEDY, 10, 1);
				else if (p2.equals(player))
					player.playSound(player.getLocation(), Sound.ARROW_HIT, 8, 1);
				else
					p2.playSound(p2.getLocation(), Sound.ANVIL_LAND, 10, 1);

			
			pkits.setCSState(CSState.USED);
			player.closeInventory();
	}
		
		for (Teams t : Teams.values())
			if (!t.equals(Teams.NONE))
				if (inv.getName().equals(t.getColor() + "Équipe " + t.getAdjectiveName()))
					if (playerkits.isKit(Kits.SORCIERE)) {
						Player p = Bukkit.getPlayer(((SkullMeta)current.getItemMeta()).getOwner());

						p.setMaxHealth(p.getMaxHealth() - 4);
						p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*10, 0));
						p.sendMessage(main.getPrefix() + player.getDisplayName() + " §ca utilisé sa capacité spéciale : \"§2§lE§5mpoisonnement§c\" sur vous ! Vous perdez §42\u2764§c permanants et obtenez §2poison §7pendant 10s§c.");
						player.sendMessage(main.getPrefix() + p.getDisplayName() + " §aa été §2§lE§5mpoisonné §a!");
						p.playSound(p.getLocation(), Sound.VILLAGER_HIT, 10, 1);
						player.playSound(player.getLocation(), Sound.VILLAGER_YES, 10, 1);
						playerkits.setCSState(CSState.USED);
						player.closeInventory();
					} else if (playerkits.isKit(Kits.XRAYEUR)) {
						Player p = Bukkit.getPlayer(((SkullMeta)current.getItemMeta()).getOwner());
						
						p.sendMessage(main.getPrefix() + player.getDisplayName() + " §ba utilisé sa Capacité Spéciale " + CSXRay.TELEPORT.getName() + " §bsur vous.");
						p.playSound(p.getLocation(), Sound.GHAST_SCREAM, 7, 1.3f);
						BukkitRunnable r = CSXRayTP(player, playerkits, p);
						r.runTaskTimer(main, 0, 10);
						
						player.sendMessage(main.getPrefix() + "§fCapacité Spéciale \"" + CSXRay.TELEPORT.getName() + "§f\" §cUTILISÉE");
						player.closeInventory();
						playerkits.setCSState(CSState.USED);
					}
	}
	
	
	
	private void tpSalleKit(Player player, Kits kit) {
		Teams t = main.playerkits.get(player.getUniqueId()).getTeam();
		Location Salle;

		double x;
		double z;
		
		int addx = 0;
		int addz = 0;
		switch (kit) {
		case ALLAHU_AKBAR:
			addx = 68;
			addz = -6;
			break;
		case BISCUIT:
			addx = 48;
			addz = 4;
			break;
		case HEALER:
			addx = 33;
			addz = 4;
			break;
		case LG:
			addx = 18;
			addz = 4;
			break;
		case MACRON:
			addx = 53;
			addz = -6;
			break;
		case MILITAIRE:
			addx = 63;
			addz = 4;
			break;
		case PYROMANE:
			addx = 8;
			addz = -6;
			break;
		case SORCIERE:
			addx = 23;
			addz = -6;
			break;
		case TOAD:
			addx = 3;
			addz = 4;
			break;
		case XRAYEUR:
			addx = 38;
			addz = -6;
			break;
		}
		
		double CentreZ = 0;
		double DebutX = 0;
		
		switch (t) {
		case BLACK:
			CentreZ = 28.5;
			DebutX = 71.5;
			break;
		case BLUE:
			CentreZ = -38.5;
			DebutX = 84.5;
			break;
		case GREEN:
			CentreZ = -18.5;
			DebutX = 71.5;
			break;
		case PINK:
			CentreZ = 58.5;
			DebutX = 71.5;
			break;
		case RED:
			CentreZ = -60.5;
			DebutX = 71.5;
			break;
		case YELLOW:
			CentreZ = 4.5;
			DebutX = 81.5;
			break;
			
		default:
			break;
		}
		x = DebutX + addx;
		z = CentreZ + addz;
		
		Salle = new Location(Bukkit.getWorld("PvPKits"), x, 5.2, z, 90f, 0f);
	
		
		player.teleport(Salle);
		main.playerkits.get(player.getUniqueId()).setRetour(Retour.SALLE_KITS);
		player.getInventory().setItem(4, main.getItem("§a§lChoisir le Kit §7 : §b["+kit.getName()+"§r§b]", null, Material.MAGMA_CREAM, (short)0));
		player.updateInventory();
		player.playSound(Salle, Sound.NOTE_PLING, 6, 1);
		PvPKits.sendTitle(player, "§a§lConfirmez votre §6kit", "§2Voulez-vous choisir le §6kit " + kit.getName() + "§2 ?", 10, 30, 10);
		
		for (String s : kitsLores.get(kit))
			player.sendMessage(s);
		switch (kit) {
		case BISCUIT:
			player.sendMessage(main.getPrefix() + "§eInfos sur le §c§lSuper §f§lBiscuit §e: §r\n§eDonne les effets : §bspeed §ependant 14 secondes§e et §7résistance §ependant 9 secondes§e.");
			break;
		case LG:
			player.sendMessage(main.getPrefix() + "§eInfos sur la Transformation en §b§lV§4§lG§c§lP§4§lL§8§lG§f§lB§3§lA§7§lA §e: §r\n§eNom complet : §c§lVilain Grand-Père des Loups-Garous Blanc Amnesiques Anonymes§e. Pendant 30 secondes, le joueur se verra attribuer les effets suivants : Obtient Speed pendant 19 secondes, obtient force pendant 15 secondes, régénére de 10% des dégâts qu'il inflige pendant 15 secondes, peut infecter quelqu'un (le met dans son équipe), obtient 15 coeurs pendant 20 secondes, s'il est frappé par un autre LG il obtient force 2 pendant 3 secondes et devient indétectable pendant 5 secondes (sauf s'il tape).");
			break;
		case MILITAIRE:
			player.sendMessage(main.getPrefix() + "§eInfos sur les §6§lPains " + Kits.MILITAIRE.getName() + " §e: §r\n§eRégénére un demi-coeur et donne un coeur d'abosorbtion lorsqu'il est mangé.");
			break;
		
		default:
			break;
		}
		player.sendMessage(main.getPrefix() + "§a§lClique sur la Magma Cream pour valider ton kit !");
	}
	
	
	
	private static ItemStack getZombieArmor(Material type, Color color, List<Entry<Enchantment, Integer>> enchants) {
		ItemStack it = new ItemStack(type);
		ItemMeta itm = it.getItemMeta();
		if (color != null) {
			LeatherArmorMeta lm = (LeatherArmorMeta) itm;
			lm.setColor(color);
			for (Entry<Enchantment, Integer> en : enchants)
				lm.addEnchant(en.getKey(), en.getValue(), true);
			it.setItemMeta(lm);
			return it;
		}
		for (Entry<Enchantment, Integer> en : enchants)
			itm.addEnchant(en.getKey(), en.getValue(), true);
		itm.spigot().setUnbreakable(true);
		it.setItemMeta(itm);
		return it;
	}
	
	private static ItemStack getZombieItem(Material type, List<Entry<Enchantment, Integer>> enchants) {
		ItemStack it = new ItemStack(type);
		ItemMeta itm = it.getItemMeta();
		for (Entry<Enchantment, Integer> en : enchants)
			itm.addEnchant(en.getKey(), en.getValue(), true);
		
		itm.spigot().setUnbreakable(true);
		it.setItemMeta(itm);
		return it;
	}
	
	
	private void SpawnGiletsJaunes(Player player, ArrayList<Location> locs) {
		for (Location loc : locs) {
			char xz = 'a';
			if (loc.getYaw() == 180f) {
				xz = 'x';
				SpawnGiletJaune(player, loc);
				SpawnGiletJaune(player, new Location(loc.getWorld(), (loc.getX() + 1), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));
				SpawnGiletJaune(player, new Location(loc.getWorld(), (loc.getX() - 1), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));
			} else if (loc.getYaw() == 90f) {
				xz = 'z';
				SpawnGiletJaune(player, loc);
				SpawnGiletJaune(player, new Location(loc.getWorld(), loc.getX(), loc.getY(), (loc.getZ() - 1), loc.getYaw(), loc.getPitch()));
				SpawnGiletJaune(player, new Location(loc.getWorld(), loc.getX(), loc.getY(), (loc.getZ() + 1), loc.getYaw(), loc.getPitch()));
			} else if (loc.getYaw() == -90f) {
				xz = 'z';
				SpawnGiletJaune(player, loc);
				SpawnGiletJaune(player, new Location(loc.getWorld(), loc.getX(), loc.getY(), (loc.getZ() - 1), loc.getYaw(), loc.getPitch()));
				SpawnGiletJaune(player, new Location(loc.getWorld(), loc.getX(), loc.getY(), (loc.getZ() + 1), loc.getYaw(), loc.getPitch()));
			} else if (loc.getYaw() == -180f) {
				xz = 'x';
				SpawnGiletJaune(player, loc);
				SpawnGiletJaune(player, new Location(loc.getWorld(), (loc.getX() + 1), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));
				SpawnGiletJaune(player, new Location(loc.getWorld(), (loc.getX() - 1), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));
			} else if (loc.getYaw() == 0f) {
				xz = 'x';
				SpawnGiletJaune(player, loc);
				SpawnGiletJaune(player, new Location(loc.getWorld(), (loc.getX() + 1), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));
				SpawnGiletJaune(player, new Location(loc.getWorld(), (loc.getX() - 1), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));
			}
				
			if (xz == 'a')
				throw new NullPointerException("xz est null ou a");
		}
	}

	private void SpawnGiletJaune(Player player, Location loc) {
		Entity e = player.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
		Zombie e1 = (Zombie) e;
		EntityEquipment ee = e1.getEquipment();
		
		ee.setChestplate(getZombieArmor(Material.GOLD_CHESTPLATE, null, Arrays.asList(new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 6), new SimpleEntry<>(Enchantment.PROTECTION_PROJECTILE, 6))));
		
		ItemStack phead = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		SkullMeta pheadmeta = (SkullMeta) phead.getItemMeta();
		pheadmeta.setOwner(Bukkit.getPlayer(main.players.get(new Random().nextInt(main.players.size()))).getName());
		phead.setItemMeta(pheadmeta);
		ee.setHelmet(phead);
		
		e1.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
		e1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
		
		loc.getChunk().load();
		e1.setCustomName("§eGilet Jaune " + player.getDisplayName());
		PvPKits.sendActionBar(player, main.getPrefix() + e1.getCustomName() + " §eest apparu !");
		main.playerkits.get(player.getUniqueId()).getZombiesMacron().add(e1);
	}
	
	
	private void SetPyroBlock(PlayerKits playerkits, int PositionX, int PositionY, int PositionZ, Material material) {
		Block b = Bukkit.getWorld("PvPKits").getBlockAt(PositionX, PositionY, PositionZ);
		
		if (b.getType().equals(Material.AIR))
			if (!Bukkit.getWorld("PvPKits").getBlockAt(PositionX, PositionY - 1, PositionZ).getType().equals(Material.AIR)) {
				b.setType(material);
				playerkits.getPyroBlocks().getKey().add(b);
				main.blocknomap.add(b);
			}
	}
	
	
	private void SetToadBlockH(Player player, double x, double y, double z) {
		SetToadBlock(player, x, y, z);
		SetToadBlock(player, x, y + 1, z);
		SetToadBlock(player, x, y + 2, z);
	}
	
	private void SetToadBlock(Player player, double x, double y, double z) {
		main.playerkits.get(player.getUniqueId()).getToadBlocks().getKey().add(player.getWorld().getBlockAt((int)x, (int)y, (int)z));
	}
	
	
	private BukkitRunnable CSAllahuAkbar(Player player, PlayerKits playerkits) {

		return new BukkitRunnable() {

			@Override
			public void run() {
				int i = 10;

				while (i != 0) {
					int x = -(new Random().nextInt(386) + 1) - 200;
					int z = -(new Random().nextInt(296) + 1) + 3;
					Location l = new Location(player.getWorld(), x, player.getWorld().getHighestBlockYAt(x, z) + 5, z);
					if (l.getBlockY() < 46)
						while (l.getBlockY() < 46) {
							x = -(new Random().nextInt(386) + 1) - 200;
							z = -(new Random().nextInt(296) + 1) + 3;
							l = new Location(player.getWorld(), x, player.getWorld().getHighestBlockYAt(x, z) + 5, z);
						}
					TNTPrimed tnt = (TNTPrimed) player.getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
					tnt.getLocation().getChunk().load();
					tnt.setFuseTicks(20 * 2);
					playerkits.getTNTAllahu().add(tnt);
					i--;
				}

				if (playerkits.getTimerPluieTNT() == 0) {
					playerkits.setTimerPluieTNT(-1);
					cancel();
					Bukkit.broadcastMessage(main.getPrefix() + "§e§lFin de la §9§lPluie §cde §4§lT§f§lN§4§lT §e§l!");
					return;
				}
				PvPKits.sendActionBar(player, main.getPrefix() + "§9§lPluie §cde §4§lT§f§lN§4§lT §edure encore " + playerkits.getTimerPluieTNT() + " §esecondes.");
				playerkits.removeSecondPluieTNT();

			}
		};
	}
	
	private BukkitRunnable CSMiliArrain(Player player, PlayerKits playerkits) {

		return new BukkitRunnable() {

			@Override
			public void run() {

				for (UUID uuid : main.players) {
					Player p = Bukkit.getPlayer(uuid);
					if (p.getGameMode().equals(GameMode.SURVIVAL))
						if (!playerkits.getTeam().equals(main.playerkits.get(p.getUniqueId()).getTeam())) {

							Random r1 = new Random();
							double x = r1.nextInt(6) + r1.nextDouble();
							double z = r1.nextInt(6) + r1.nextDouble();
							if (r1.nextBoolean())
								x = -x;
							if (r1.nextBoolean())
								z = -z;

							double x2 = r1.nextInt(6) + r1.nextDouble();
							double z2 = r1.nextInt(6) + r1.nextDouble();
							if (r1.nextBoolean())
								x2 = -x2;
							if (r1.nextBoolean())
								z2 = -z2;

							double x3 = r1.nextInt(6) + r1.nextDouble();
							double z3 = r1.nextInt(6) + r1.nextDouble();
							if (r1.nextBoolean())
								x3 = -x3;
							if (r1.nextBoolean())
								z3 = -z3;

							Arrow a = (Arrow) p.getWorld().spawnEntity(new Location(p.getWorld(), p.getLocation().getX() + x, p.getEyeLocation().getBlockY() + 3, p.getLocation().getZ() + z), EntityType.ARROW);
							Arrow a2 = (Arrow) p.getWorld().spawnEntity(new Location(p.getWorld(), p.getLocation().getX() + x2, p.getEyeLocation().getBlockY() + 3, p.getLocation().getZ() + z2), EntityType.ARROW);
							Arrow a3 = (Arrow) p.getWorld().spawnEntity(new Location(p.getWorld(), p.getLocation().getX() + x3, p.getEyeLocation().getBlockY() + 3, p.getLocation().getZ() + z3), EntityType.ARROW);
							a.setShooter(player);
							a2.setShooter(player);
							a3.setShooter(player);
						}
				}


				if (playerkits.getTimerPluieFleches() == 0) {
					playerkits.setTimerPluieFleches(-1);
					cancel();
					Bukkit.broadcastMessage(main.getPrefix() + "§f§lFin de l'"+CSMili.ARRAIN.getName()+" §f§l!");
					return;
				}
				PvPKits.sendActionBar(player, main.getPrefix() + CSMili.ARRAIN.getName() + " §fdure encore " + playerkits.getTimerPluieFleches() + " §fsecondes.");
				playerkits.removeSecondPluieFleches();
			}
		};
	}
	
	
	private BukkitRunnable CSToad(Player player, PlayerKits playerkits) {

		return new BukkitRunnable() {

			@Override
			public void run() {

				for (Block b : playerkits.getToadBlocks().getKey())
					player.getWorld().spigot().playEffect(b.getLocation(), Effect.CLOUD, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 32, 5);

				if (playerkits.getToadBlocks().getValue() == 0) {
					playerkits.resetTimerToad();
					cancel();
					return;
				}
				PvPKits.sendActionBar(player, main.getPrefix() + "§fFin de §7§lN§5uage §2§lT§aoxique §f dans §f§l" + playerkits.getToadBlocks().getValue() + "§fs !");
				playerkits.removeSecondTimerToad();
			}
		};
	}
	
	
	private BukkitRunnable CSXRayTP(Player player, PlayerKits playerkits, Player toP) {
		List<Player> teammates = new ArrayList<>(playerkits.getTeam().getPlayers());

		return new BukkitRunnable() {

			@Override
			public void run() {
				if (teammates.isEmpty()) {
					cancel();
					return;
				}


				Player pl = teammates.get(0);

				pl.teleport(toP);
				pl.sendMessage(main.getPrefix() + player.getDisplayName() + " §bvous a téléporté à " + toP.getDisplayName());
				pl.playSound(toP.getLocation(), Sound.ENDERMAN_TELEPORT, 6, 1);

				teammates.remove(pl);
			}
		};
	}
	
	
}
