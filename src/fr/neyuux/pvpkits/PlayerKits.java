package fr.neyuux.pvpkits;

import fr.neyuux.pvpkits.enums.Gstate;
import fr.neyuux.pvpkits.enums.Kits;
import fr.neyuux.pvpkits.enums.Teams;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

public class PlayerKits {

	private final PvPKits main;
	
	public PlayerKits(PvPKits main, Player player) {
		this.main = main;
		this.player = player;
	}
	
	private final Player player;
	
	private Teams team = Teams.NONE;
	
	private Kits kit;
	
	private final List<Block> blocks = new ArrayList<>();
	
	private Retour retour;
	
	private StuffLG stufflg;
	
	private CSState csstate;
	
	private int XRayTPs = 3;
	
	private Entry<Entity, Integer> lastTape = null;
	
	private int kills = 0;
	
	public int CSLGVGPLGBAA = -1;
	public int CSLGInvi = -1;
	public int CSLGInf = -1;
	public int CSLGB = -1;
	public int CSLGGML = -1;
	public int CSLGAmne = -1;
	
	private final List<Zombie> zombiesMacron = new ArrayList<>();
	
	private final List<TNTPrimed> tntAllahu = new ArrayList<>();
	
	private int timerPluieTNT = -1;
	
	private int timerPluieFleches = -1;
	
	private int timerBowSpam = -1;
	
	private int timerGiletsJaunes = -1;
	
	private Entry<List<Block>, Integer> pyroBlocks = null;
	
	private Entry<List<Block>, Integer> toadBlocks = null;
	
	private int timerGenSoso = -1;
	
	private CSPyro cspyro = null;
	
	private CSSoso csSoso = null;
	
	private CSHeal csHeal = null;
	
	private CSXRay csXRay = null;
	
	private CSMili csMili = null;
	
	
	
	public void setTeam(Teams t) {
		this.team = t;
	}
	
	public boolean isTeam(Teams t) {
		return this.team.equals(t);
	}
	
	public Teams getTeam() {
		return this.team;
	}
	
	
	public void setKit(Kits k) {
		this.kit = k;
	}
	
	public boolean isKit(Kits k) {
		if (this.kit == null) return false;
		return this.kit.equals(k);
	}
	
	public Kits getKit() {
		return this.kit;
	}
	
	
	public void GiveStuff() {
		if (getKit() == null) throw new NullPointerException("On ne peut pas give le stuff d'un joueur qui n'a pas de kit");
		PvPKits.sendActionBarForAllPlayers(main.getPrefix() + "§8Stuffing " + player.getDisplayName());
		getKit().GiveStuff(getPlayer());
	}
	
	public List<Block> getBlocks() {
		return blocks;
	}

	public Retour getRetour() {
		return this.retour;
	}
	
	public void setRetour(Retour retour) {
		this.retour = retour;
	}
	
	public StuffLG getStuffLG() {
		return stufflg;
	}
	
	public void setStuffLG(StuffLG stufflg) {
		this.stufflg = stufflg;
	}
	
	public CSState getCSState() {
		return csstate;
	}
	
	public void setCSState(CSState state) {
		this.csstate = state;
		ItemStack it = null;
		for (ItemStack its : player.getInventory().getContents())
			if (its != null)
				if (its.getType().equals(Material.DEAD_BUSH))
					it = its;
		if (it != null) {
			ItemMeta itm = it.getItemMeta();
			itm.setDisplayName(state.getItemName());
			itm.setLore(state.getItemLore());
			it.setItemMeta(itm);
			main.replaceBush(getPlayer(), it);
		}
		if (main.boards.containsKey(player.getUniqueId()) && main.isState(Gstate.PLAYING)) main.boards.get(getPlayer().getUniqueId()).setLine(8, "§dCapa.Spé. : " + state.getScoreboardLine());
	}
	
	
	public int getXRayTPs() {
		return XRayTPs;
	}

	public void removeXRayTPs() {
		XRayTPs--;
	}
	
	public int getTimerGiletsJaunes() {
		return timerGiletsJaunes;
	}
	
	public void removeSecondTimerGiletsJaunes() {
		timerGiletsJaunes--;
	}
	
	public void startTimerGiletsJaunes() {
		timerGiletsJaunes = 17;
	}
	
	private void resetTimerGiletsJaunes() {
		timerGiletsJaunes = -1;
	}
	
	public Entry<List<Block>, Integer> getPyroBlocks() {
		return pyroBlocks;
	}
	
	public void removeSecondTimerPyro() {
		pyroBlocks.setValue(pyroBlocks.getValue() - 1);
	}
	
	public void startPyroBlocks() {
		pyroBlocks = new SimpleEntry<>(new ArrayList<>(), 20);
	}
	
	private void resetTimerPyro() {
		pyroBlocks.setValue(-1);
	}
	
	public void clearPyroBlocks() {
		resetTimerPyro();
		for (Block b : pyroBlocks.getKey())
			b.setType(Material.AIR);
	}
	
	public Entry<List<Block>, Integer> getToadBlocks() {
		return toadBlocks;
	}
	
	public void removeSecondTimerToad() {
		toadBlocks.setValue(toadBlocks.getValue() - 1);
	}
	
	public void startToadBlocks() {
		toadBlocks = new SimpleEntry<>(new ArrayList<>(), 30);
	}
	
	public void resetTimerToad() {
		toadBlocks.setValue(-1);
		toadBlocks = null;
	}
	
	public int getTimerGenSoso() {
		return timerGenSoso;
	}
	
	public void removeSecondTimerGenSoso() {
		timerGenSoso--;
		player.setLevel(timerGenSoso);
		player.setExp((float)timerGenSoso / 30);
	}
	
	public void startTimerGenSoso() {
		timerGenSoso = 30;
		player.setLevel(30);
		player.setExp((float)1);
	}
	
	public int getTimerPluieTNT() {
		return timerPluieTNT;
	}
	
	public void removeSecondPluieTNT() {
		this.timerPluieTNT--;
	}
	
	public void setTimerPluieTNT(int timer) {
		this.timerPluieTNT = timer;
	}
	
	public int getTimerPluieFleches() {
		return timerPluieFleches;
	}
	
	public void removeSecondPluieFleches() {
		this.timerPluieFleches--;
	}
	
	public void setTimerPluieFleches(int timer) {
		this.timerPluieFleches = timer;
	}
	
	public int getTimerBowSpam() {
		return timerBowSpam;
	}
	
	public void removeSecondBowSpam() {
		this.timerBowSpam--;
	}
	
	public void setTimerBowSpam(int timer) {
		this.timerBowSpam = timer;
	}

	public Player getPlayer() {
		return player;
	}
	
	
	public Entry<Entity, Integer> getLastTape() {
		return lastTape;
	}

	public void setLastTape(Entity damager) {
		this.lastTape = new SimpleEntry<>(damager, 10);
		if (damager == null) lastTape = null;
	}
	
	public void removeSecondLastTape() {
		this.lastTape = new SimpleEntry<Entity, Integer>(this.lastTape.getKey(), this.lastTape.getValue() - 1);
	}
	
	public int getKills() {
		return kills;
	}
	
	public void resetKills() {
		kills = 0;
	}
	
	public void addKill() {
		kills++;
		if (main.isState(Gstate.PLAYING))
			main.boards.get(player.getUniqueId()).setLine(4, "§cKills : §l" + kills);
	}
	
	
	public void startLGCS() {
		CSLGVGPLGBAA = 30;
		CSLGAmne = 30;
		CSLGB = 20;
		CSLGGML = 15;
		CSLGInf = 30;
		CSLGInvi = 5;
	}
	
	public void removeSecondLGCS() {
		if (CSLGVGPLGBAA != -1) CSLGVGPLGBAA--;
		if (CSLGAmne != -1) CSLGAmne--;
		if (CSLGB != -1) CSLGB--;
		if (CSLGGML != -1) CSLGGML--;
		if (CSLGInf != -1) CSLGInf--;
		if (CSLGInvi != -1) CSLGInvi--;
		PvPKits.sendActionBar(player, main.getPrefix() + "§b§lV§4§lG§c§lP§4§lL§8§lG§f§lB§3§lA§7§lA §fterminé dans §l" + CSLGVGPLGBAA + " secondes§f.");
	}
	
	public void resetLGCS() {
		CSLGVGPLGBAA = -1;
		CSLGAmne = -1;
		CSLGB = -1;
		CSLGGML = -1;
		CSLGInf = -1;
		CSLGInvi = -1;
		
		player.setDisplayName("§8[" + Kits.LG.getName() + "§8] " + main.getPlayerTeam(player, getTeam()).getPrefix() + player.getName() + main.getPlayerTeam(getPlayer(), getTeam()).getSuffix());
		player.setPlayerListName(player.getDisplayName());
	}
	
	public void resetLGInfCS() {
		CSLGInf = -1;
	}
	
	
	public final List<Zombie> getZombiesMacron() {
		return zombiesMacron;
	}
	
	public final List<TNTPrimed> getTNTAllahu() {
		return tntAllahu;
	}
	
	public void clearGiletsJaunes() {
		for (Zombie z : zombiesMacron)
			if (z.getCustomName().contains("§eGilet Jaune"))
				z.remove();
		resetTimerGiletsJaunes();
	}
	
	public CSPyro getCSPyro() {
		return cspyro;
	}
	
	public void setCSPyro(CSPyro cspyro) {
		this.cspyro = cspyro;
	}
	
	public CSHeal getCSHeal() {
		return csHeal;
	}
	
	public void setCSHeal(CSHeal csHeal) {
		this.csHeal = csHeal;
	}
	
	public CSSoso getCSSoso() {
		return csSoso;
	}
	
	public void setCSSoso(CSSoso csSoso) {
		this.csSoso = csSoso;
	}
	
	public CSXRay getCSXRay() {
		return csXRay;
	}
	
	public void setCSXRay(CSXRay csXRay) {
		this.csXRay = csXRay;
	}
	
	public CSMili getCSMili() {
		return csMili;
	}
	
	public void setCSMili(CSMili csMili) {
		this.csMili = csMili;
	}
	
	

	public enum Retour {
		SALLE_KITS,
		SALLE_TEAM,
		SALLE_ATTENTE;
	}
	
	public enum CSState {
		READY("§8[§2§l\u2714§8] §eCapacité spéciale §8[§2§l\u2714§8]", Arrays.asList("§aStatut de la capacité spéciale \u21D2 §r§2§l\u2714>§r", "§2§l\u21D2 §a§lCapacité spéciale, §nCHARGÉE §2§l\u2714"), "§a§lChargée"),
		USED("§8[§4§l?§8] §eCapacité spéciale §8[§4§l\u2716§8]", Arrays.asList("§cStatut de la capacité spéciale \u21D2 §r§4§l\u2716§r", "§4§l\u21D2 §c§lCapacité spéciale, §4§nUTILISÉE §4§l?"), "§4§lUtilisée"),
		WAITING("§8[§f§l?§8] §eCapacité spéciale §8[§f§l?§8]", Arrays.asList("§7Statut de la capacité spéciale \u21D2 §r§f§l\u221E§r", "§f§l\u21D2 §7§lCapacité spéciale, §f§nEN ATTENTE §f§l?"), "§f§lEn Attente"),
		NOT_EXIST("§8[§6§l?§8] §eCapacité spéciale §8[§6§l?§8]", Arrays.asList("§r§eStatut de la capacité spéciale \u21D2 §r§6§l?§r", "§6§l\u21D2 §e§lCapacité spéciale, §6§nEN DÉVELOPPEMENT §6§l?"), "§6§lEn Dév");
		
		CSState(String itemName, List<String> itemLore, String scoreboardLine) {
			this.itemName = itemName;
			this.itemLore = itemLore;
			this.scoreboardLine = scoreboardLine;
		}
		
		private final String itemName;
		
		private final List<String> itemLore;
		
		private final String scoreboardLine;
		
		
		public String getItemName() {
			return itemName;
		}
		
		public List<String> getItemLore() {
			return itemLore;
		}
		
		public String getScoreboardLine() {
			return scoreboardLine;
		}
	}
	
	public enum CSPyro {
		INFLAMMATION_VAMPIRIQUE("§6§lI§enflammation §4§lV§5ampirique"),
		TOURBILLION_DE_LAVE("§6§lT§eourbillion de §6§lL§6ave");
		
		CSPyro(String name) {
			this.name = name;
		}
		
		private final String name;
		
		public String getName() {
			return name;
		}
	}
	
	public enum CSSoso {
		CORBEAU_NIQUEUR("§5§lC§c§lorbeau §5§lN§c§liqueur"),
		EMPOISONNEMENT("§2§lE§5mpoisonnement");
		
		CSSoso(String name) {
			this.name = name;
		}
		
		private final String name;
		
		public String getName() {
			return name;
		}
	}
	
	public enum CSHeal {
		REGENERATION("§5§lR§d§légénération"),
		REANIMATION("§c§lR§6§léanimation");
		
		CSHeal(String name) {
			this.name = name;
		}
		
		private final String name;
		
		public String getName() {
			return name;
		}
	}
	
	public enum CSXRay {
		TELEPORT("§b§lT§e§le§b§ll§e§le§b§lp§e§lo§b§lr§e§lt"),
		TP_AURA("§b§lT§e§lP §4Aura");
		
		CSXRay(String name) {
			this.name = name;
		}
		
		private final String name;
		
		public String getName() {
			return name;
		}
	}
	
	public enum CSMili {
		ARRAIN("§7§lArr§b§lain"),
		BOWSPAM("§c§lBow§nSPAM");
		
		CSMili(String name) {
			this.name = name;
		}
		
		private final String name;
		
		public String getName() {
			return name;
		}
	}
	
	public enum StuffLG {
		CHIEN(getEnchantedItem(Material.IRON_HELMET, "§2Casque §8§lL§4§lG", null, null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null),
				getEnchantedItem(Material.DIAMOND_CHESTPLATE, "§1Plastron §8§lL§4§lG", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 1), null, null),
				getEnchantedItem(Material.IRON_LEGGINGS, "§bPantalon §8§lL§4§lG", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
				getEnchantedItem(Material.DIAMOND_BOOTS, "§aBottes §8§lL§4§lG", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 1), null, null),
				Arrays.asList(getInventoryItem(Material.IRON_SWORD, 1, (short) 0, "§4Épée §8§lL§4§lG", null, null, null, null),
					getInventoryItem(Material.GOLDEN_APPLE, 3, (short) 0, "§e§lG§eolden §e§lA§epple", null, null, null, null),
					getPotion(PotionType.FIRE_RESISTANCE, PotionEffectType.DAMAGE_RESISTANCE, 20*20, 0, true, false, Material.POTION, 1, (short)0, "§7Résistance §820s", null, null, null, null)
				)
			),
		LOUP(getEnchantedItem(Material.IRON_HELMET, "§2Casque §8§lL§4§lG", null, null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 1), null),
				getEnchantedItem(Material.DIAMOND_CHESTPLATE, "§1Plastron §8§lL§4§lG", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 1), null, null),
				getEnchantedItem(Material.IRON_LEGGINGS, "§bPantalon §8§lL§4§lG", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 1), null, null),
				getEnchantedItem(Material.IRON_BOOTS, "§aBottes §8§lL§4§lG", null, null, null, null),
				Arrays.asList(getInventoryItem(Material.DIAMOND_SWORD, 1, (short) 0, "§4Épée §8§lL§4§lG", null, null, new SimpleEntry<>(Enchantment.DAMAGE_ALL, 1), null),
					getInventoryItem(Material.GOLDEN_APPLE, 2, (short) 0, "§e§lG§eolden §e§lA§epple", null, null, null, null),
					getPotion(PotionType.STRENGTH, PotionEffectType.INCREASE_DAMAGE, 20*30, 0, true, false, Material.POTION, 1, (short)0, "§4Force §830s", null, null, null, null)
				)
			);
		
		StuffLG(ItemStack helmet, ItemStack chest, ItemStack legs, ItemStack boots, List<ItemStack> inventory) {
			this.helmet = helmet;
			this.chest = chest;
			this.legs = legs;
			this.boots = boots;
			this.inventory = inventory;
		}
		
		private final ItemStack helmet;
		
		private final ItemStack chest;
		
		private final ItemStack legs;
		
		private final ItemStack boots;
		
		private final List<ItemStack> inventory;
		
		
		public void GiveStuff(Player player) {
			PlayerInventory pinv = player.getInventory();
			
			if (helmet != null)pinv.setHelmet(helmet);
			if (chest != null)pinv.setChestplate(chest);
			if (legs != null)pinv.setLeggings(legs);
			if (boots != null)pinv.setBoots(boots);
			
			if (inventory != null)
				for (ItemStack it : inventory)
					if (it != null)
						pinv.addItem(it);
		}
		
		
		private static ItemStack getEnchantedItem(Material material, String customName, Color leatherColor, SimpleEntry<Enchantment, Integer> enchant1, SimpleEntry<Enchantment, Integer> enchant2, SimpleEntry<Enchantment, Integer> enchant3) {
			ItemStack it = new ItemStack(material);
			ItemMeta itm = it.getItemMeta();
			itm.setDisplayName(customName);
			if (enchant1 != null)itm.addEnchant(enchant1.getKey(), enchant1.getValue(), true);
			if (enchant2 != null)itm.addEnchant(enchant2.getKey(), enchant2.getValue(), true);
			if (enchant3 != null)itm.addEnchant(enchant3.getKey(), enchant3.getValue(), true);
			if (!itm.hasEnchant(Enchantment.DURABILITY))
				itm.spigot().setUnbreakable(true);
			if (material.equals(Material.LEATHER_HELMET) || material.equals(Material.LEATHER_CHESTPLATE) || material.equals(Material.LEATHER_LEGGINGS) || material.equals(Material.LEATHER_BOOTS)) {
				LeatherArmorMeta lam = (LeatherArmorMeta) itm;
				if (leatherColor != null) lam.setColor(leatherColor);
			}
			it.setItemMeta(itm);
			
			return it;
		}
		
		private static ItemStack getInventoryItem(Material material, int amount, short durability, String customName, List<String> lore, SimpleEntry<Enchantment, Integer> enchant1, SimpleEntry<Enchantment, Integer> enchant2, List<ItemFlag> lit) {
			ItemStack it = new ItemStack(material, amount, durability);
			ItemMeta itm = it.getItemMeta();
			itm.setDisplayName(customName);
			if (lore != null) itm.setLore(lore);
			if (enchant1 != null) itm.addEnchant(enchant1.getKey(), enchant1.getValue(), true);
			if (enchant2 != null) itm.addEnchant(enchant2.getKey(), enchant2.getValue(), true);
			if (!itm.hasEnchant(Enchantment.DURABILITY))
				itm.spigot().setUnbreakable(true);
			if (lit != null)
				for (ItemFlag If : lit)
					itm.addItemFlags(If);
			it.setItemMeta(itm);
			
			return it;
		}
		
		private static ItemStack getPotion(PotionType pt, PotionEffectType pet, int duration, int amplifier, boolean particles, boolean isSplash, Material material, int amount, short durability, String customName, List<String> lore, SimpleEntry<Enchantment, Integer> enchant1, SimpleEntry<Enchantment, Integer> enchant2, List<ItemFlag> lit) {
			ItemStack it = getInventoryItem(material, amount, durability, customName, lore, enchant1, enchant2, lit);
			Potion p = new Potion(pt);
			PotionMeta pm = (PotionMeta) it.getItemMeta();
			pm.clearCustomEffects();
			pm.addCustomEffect(new PotionEffect(pet, duration, amplifier, particles, particles), true);
			p.setSplash(isSplash);
			it.setItemMeta(pm);
			p.apply(it);
			
			return it;
		}
	}
}
