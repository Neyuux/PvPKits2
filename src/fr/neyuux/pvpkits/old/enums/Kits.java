package fr.neyuux.pvpkits.old.enums;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Kits {

	TOAD("§4§lT§2§lo§3§la§e§ld", "§7§lN§5uage §2§lT§aoxique", getEnchantedItem(Material.LEATHER_HELMET, "§2Casque §4§lT§2§lo§3§la§e§ld", Color.fromBGR(0x335625), new SimpleEntry<>(Enchantment.DURABILITY, 1), new SimpleEntry<>(Enchantment.THORNS, 6), new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 6)),
			getEnchantedItem(Material.IRON_CHESTPLATE, "§1Plastron §4§lT§2§lo§3§la§e§ld", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			getEnchantedItem(Material.IRON_LEGGINGS, "§bPantalon §4§lT§2§lo§3§la§e§ld", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			getEnchantedItem(Material.IRON_BOOTS, "§aBottes §4§lT§2§lo§3§la§e§ld", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			Arrays.asList(getInventoryItem(Material.DIAMOND_SWORD, 1, (short) 0, "§4Épée §4§lT§2§lo§3§la§e§ld", null, null, new SimpleEntry<>(Enchantment.DAMAGE_ALL, 1), null),
				getPotion(PotionType.POISON, PotionEffectType.POISON, 20*33, 0, true, true, Material.POTION, 1, (short)0, "§2Poison §833s", null, null, null, null),
				getPotion(PotionType.WEAKNESS, PotionEffectType.WITHER, 20*20, 0, true, true, Material.POTION, 1, (short)0, "§8Wither §820s", null, null, null, null),
				getEnchantedItem(Material.LEATHER_HELMET, "§2Casque §4§lT§2§lo§3§la§e§ld", Color.fromBGR(0x335625), new SimpleEntry<>(Enchantment.DURABILITY, 1), new SimpleEntry<>(Enchantment.THORNS, 6), new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 6))
			)
		),
	PYROMANE("§c§lP§6§ly§c§lr§6§lo§c§lm§6§la§c§ln§6§le", null, getEnchantedItem(Material.CHAINMAIL_HELMET, "§2Casque §c§lP§6§ly§c§lr§6§lo§c§lm§6§la§c§ln§6§le", null, new SimpleEntry<>(Enchantment.PROTECTION_FIRE, 2), new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 1), null),
			getEnchantedItem(Material.CHAINMAIL_CHESTPLATE, "§1Plastron §c§lP§6§ly§c§lr§6§lo§c§lm§6§la§c§ln§6§le", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), new SimpleEntry<>(Enchantment.PROTECTION_FIRE, 2), null),
			getEnchantedItem(Material.CHAINMAIL_LEGGINGS, "§bPantalon §c§lP§6§ly§c§lr§6§lo§c§lm§6§la§c§ln§6§le", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 4), new SimpleEntry<>(Enchantment.PROTECTION_FIRE, 3), null),
			getEnchantedItem(Material.CHAINMAIL_BOOTS, "§aBottes §c§lP§6§ly§c§lr§6§lo§c§lm§6§la§c§ln§6§le", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 4), new SimpleEntry<>(Enchantment.PROTECTION_FIRE, 4), new SimpleEntry<>(Enchantment.PROTECTION_PROJECTILE, 2)),
			Arrays.asList(getInventoryItem(Material.STONE_SWORD, 1, (short) 0, "§4Épée §c§lP§6§ly§c§lr§6§lo§c§lm§6§la§c§ln§6§le", null, new SimpleEntry<>(Enchantment.FIRE_ASPECT, 1), new SimpleEntry<>(Enchantment.DAMAGE_ALL, 2), null),
				getInventoryItem(Material.BOW, 1, (short) 0, "§cArc §c§lP§6§ly§c§lr§6§lo§c§lm§6§la§c§ln§6§le", null, new SimpleEntry<>(Enchantment.ARROW_FIRE, 1), null, null),
				getInventoryItem(Material.FLINT_AND_STEEL, 1, (short) 0, "§6Briquet §c§lP§6§ly§c§lr§6§lo§c§lm§6§la§c§ln§6§le", null, null, null, null),
				getInventoryItem(Material.ARROW, 16, (short) 0, null, null, null, null, null)
			)
		),
	LG("§4§lL§8§lG", "§b§lV§4§lG§c§lP§4§lL§8§lG§f§lB§3§lA§7§lA", null, null, null, null, null),
	SORCIERE("§5§lSorcière", null, getEnchantedItem(Material.LEATHER_HELMET, "§2Casque §5§lSorcière", Color.fromBGR(0x84538b), null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 3), null),
			getEnchantedItem(Material.DIAMOND_CHESTPLATE, "§1Plastron §5§lSorcière", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			getEnchantedItem(Material.LEATHER_LEGGINGS, "§bPantalon §5§lSorcière", Color.fromBGR(0x84538b), new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 5), null, null),
			getEnchantedItem(Material.DIAMOND_BOOTS, "§aBottes §5§lSorcière", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			Arrays.asList(getInventoryItem(Material.WOOD_SWORD, 1, (short) 0, "§4Épée §5§lSorcière", null, null, new SimpleEntry<>(Enchantment.DAMAGE_ALL, 4), null),
				getInventoryItem(Material.DISPENSER, 1, (short) 0, "§6Générateur de §c§lPotions §5§lSorcière", Arrays.asList("§aDonne une potion de §4dégâts instantanés", "§aau joueur.", "§e§l30 sec §cde cooldown.", "§7§oAffichés au niveau des levels"), new SimpleEntry<>(Enchantment.DURABILITY, 1), null, Collections.singletonList(ItemFlag.HIDE_ENCHANTS)),
				getPotion(PotionType.WEAKNESS, PotionEffectType.WEAKNESS, 400, 0, true, true, Material.POTION, 1, (short)0, "§8Weakness §820s", null, null, null, null),
				getPotion(PotionType.SLOWNESS, PotionEffectType.SLOW, 400, 0, true, true, Material.POTION, 1, (short)0, "§7Slowness §820s", null, null, null, null),
				getPotion(PotionType.INSTANT_DAMAGE, PotionEffectType.HARM, 0, 0, true, true, Material.POTION, 1, (short)16398, "§4Instant damage §f1", null, null, null, null),
				getPotion(PotionType.INSTANT_DAMAGE, PotionEffectType.HARM, 0, 1, true, true, Material.POTION, 1, (short)16460, "§4Instant damage §c2", null, null, null, null)
			)
		),
	HEALER("§d§lH§f§le§d§la§f§ll§d§le§f§lr", null, getEnchantedItem(Material.IRON_HELMET, "§2Casque §d§lH§f§le§d§la§f§ll§d§le§f§lr", null, null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null),
			getEnchantedItem(Material.IRON_CHESTPLATE, "§1Plastron §d§lH§f§le§d§la§f§ll§d§le§f§lr", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 1), null, null),
			getEnchantedItem(Material.IRON_LEGGINGS, "§bPantalon §d§lH§f§le§d§la§f§ll§d§le§f§lr", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 1), null, null),
			getEnchantedItem(Material.IRON_BOOTS, "§aBottes §d§lH§f§le§d§la§f§ll§d§le§f§lr", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			Arrays.asList(getInventoryItem(Material.IRON_SWORD, 1, (short) 0, "§4Épée §d§lH§f§le§d§la§f§ll§d§le§f§lr", null, new SimpleEntry<>(Enchantment.KNOCKBACK, 1), new SimpleEntry<>(Enchantment.DAMAGE_ALL, 1), null),
				getPotion(PotionType.REGEN, PotionEffectType.REGENERATION, 20*16, 1, true, true, Material.POTION, 1, (short)0, "§dRégénération §c2 §816s", null, null, null, null),
				getPotion(PotionType.REGEN, PotionEffectType.REGENERATION, 20*33, 0, true, true, Material.POTION, 1, (short)0, "§dRégénération §f1 §833s", null, null, null, null),
				getPotion(PotionType.INSTANT_HEAL, PotionEffectType.HEAL, 0, 0, true, true, Material.POTION, 1, (short)16453, "§dInstant health §f1", null, null, null, null),
				getPotion(PotionType.INSTANT_HEAL, PotionEffectType.HEAL, 0, 1, true, true, Material.POTION, 1, (short)16421, "§dInstant health §c2", null, null, null, null)
			)
		),
	XRAYEUR("§b§lXRayeur", null, getEnchantedItem(Material.DIAMOND_HELMET, "§2Casque §b§lXRayeur", null, null, null, null),
			getEnchantedItem(Material.DIAMOND_CHESTPLATE, "§1Plastron §b§lXRayeur", null, null, null, null),
			getEnchantedItem(Material.DIAMOND_LEGGINGS, "§bPantalon §b§lXRayeur", null, null, null, null),
			getEnchantedItem(Material.DIAMOND_BOOTS, "§aBottes §b§lXRayeur", null, null, null, null),
			Arrays.asList(getInventoryItem(Material.DIAMOND_SWORD, 1, (short) 0, "§4Épée §b§lXRayeur", null, null, null, null),
				getInventoryItem(Material.DIAMOND_PICKAXE, 1, (short) 0, "§dPioche §b§lXRayeur", null, new SimpleEntry<>(Enchantment.DIG_SPEED, 3), null, null),
				getInventoryItem(Material.GOLDEN_APPLE, 5, (short) 0, "§ePomme en Or §b§lXRayeur", null, null, null, null),
				getInventoryItem(Material.DIAMOND_BLOCK, 16, (short) 0, null, null, null, null, null),
				getInventoryItem(Material.GOLD_BLOCK, 32, (short) 0, null, null, null, null, null)
			)
		),
	BISCUIT("§f§lBiscuit", "§c§lM§f§lÉ§c§lG§f§lA §f§lBiscuit", getEnchantedItem(Material.IRON_HELMET, "§2Casque §f§lBiscuit", null, null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null),
			getEnchantedItem(Material.IRON_CHESTPLATE, "§1Plastron §f§lBiscuit", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			getEnchantedItem(Material.IRON_LEGGINGS, "§bPantalon §f§lBiscuit", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 1), null, null),
			getEnchantedItem(Material.IRON_BOOTS, "§aBottes §f§lBiscuit", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			Arrays.asList(getInventoryItem(Material.IRON_SWORD, 1, (short) 0, "§4Épée §f§lBiscuit", null, null, new SimpleEntry<>(Enchantment.DAMAGE_ALL, 2), null),
				getInventoryItem(Material.STONE_PICKAXE, 1, (short) 0, "§dPioche §f§lBiscuit", null, new SimpleEntry<>(Enchantment.DIG_SPEED, 1), null, null),
				getInventoryItem(Material.GOLDEN_APPLE, 3, (short) 0, "§ePomme en Or §f§lBiscuit", null, null, null, null),
				getInventoryItem(Material.COOKIE, 5, (short) 0, "§c§lSuper §6§lBiscuit", Arrays.asList("§f\u2B07Attribue les effets suivants \u2B07", "§4§lF§corce §8§l1 §2§l11s", "§b§lS§fpeed §8§l1 §2§l14s", "§8§lR§7ésistance §7§l1 §a9s"), null, new SimpleEntry<>(Enchantment.DURABILITY, 1), Collections.singletonList(ItemFlag.HIDE_ENCHANTS)),
				getInventoryItem(Material.IRON_BLOCK, 8, (short) 0, null, null, null, null, null),
				getInventoryItem(Material.COAL_BLOCK, 16, (short) 0, null, null, null, null, null)
			)
		),
	MACRON("§e§lM§6§la§e§lc§6§lr§e§lo§6§ln", "§e§lGilets Jaunes", getEnchantedItem(Material.GOLD_HELMET, "§2Casque §e§lM§6§la§e§lc§6§lr§e§lo§6§ln", null, null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 3), null),
			getEnchantedItem(Material.GOLD_CHESTPLATE, "§1Plastron §e§lM§6§la§e§lc§6§lr§e§lo§6§ln", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 3), null, null),
			getEnchantedItem(Material.GOLD_LEGGINGS, "§bPantalon §e§lM§6§la§e§lc§6§lr§e§lo§6§ln", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 4), null, null),
			getEnchantedItem(Material.GOLD_BOOTS, "§aBottes §e§lM§6§la§e§lc§6§lr§e§lo§6§ln", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			Arrays.asList(getInventoryItem(Material.GOLD_SWORD, 1, (short) 0, "§4Épée §e§lM§6§la§e§lc§6§lr§e§lo§6§ln", null, null, new SimpleEntry<>(Enchantment.DAMAGE_ALL, 3), null),
				getInventoryItem(Material.MONSTER_EGG, 2, (short) 94, "§9§lCRS", null, new SimpleEntry<>(Enchantment.DURABILITY, 1), null, Collections.singletonList(ItemFlag.HIDE_ENCHANTS)),
				getPotion(PotionType.SPEED, PotionEffectType.SPEED, 20*60, 0, true, false, Material.POTION, 1, (short)0, "§bSpeed §860s", null, null, null, null)
			)
		),
	MILITAIRE("§2§lMilit§a§laire", null, getEnchantedItem(Material.LEATHER_HELMET, "§2Casque §2§lMilit§a§laire", Color.fromBGR(0x335625), null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null),
			getEnchantedItem(Material.CHAINMAIL_CHESTPLATE, "§1Plastron §2§lMilit§a§laire", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 5), new SimpleEntry<>(Enchantment.PROTECTION_PROJECTILE, 4), null),
			getEnchantedItem(Material.LEATHER_LEGGINGS, "§bPantalon §2§lMilit§a§laire", Color.fromBGR(0x335625), new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			getEnchantedItem(Material.LEATHER_BOOTS, "§aBottes §2§lMilit§a§laire", Color.fromBGR(0x335625), new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			Arrays.asList(getInventoryItem(Material.IRON_SWORD, 1, (short) 0, "§4Épée §2§lMilit§a§laire", null, null, new SimpleEntry<>(Enchantment.DAMAGE_ALL, 1), null),
				getInventoryItem(Material.BOW, 1, (short) 0, "§cArc §2§lMilit§a§laire", null, new SimpleEntry<>(Enchantment.ARROW_DAMAGE, 1), null, null),
				getInventoryItem(Material.BREAD, 15, (short) 0, "§6§lPains §2§lMilit§a§laire", Collections.singletonList("§dVous régénére §4§l0.5\u2764"), new SimpleEntry<>(Enchantment.DURABILITY, 1), null, Collections.singletonList(ItemFlag.HIDE_ENCHANTS)),
				getInventoryItem(Material.ARROW, 20, (short) 0, null, null, null, null, null)
			)
		),
	ALLAHU_AKBAR("§4§lAllahu §6§lAkbar","§9§lPluie §cde §4§lT§f§lN§4§lT" , getEnchantedItem(Material.CHAINMAIL_HELMET, "§2Casque §4§lAllahu §6§lAkbar", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 4), new SimpleEntry<>(Enchantment.PROTECTION_EXPLOSIONS, 4), null),
			getEnchantedItem(Material.CHAINMAIL_CHESTPLATE, "§1Plastron §4§lAllahu §6§lAkbar", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 1), new SimpleEntry<>(Enchantment.PROTECTION_EXPLOSIONS, 2), null),
			getEnchantedItem(Material.CHAINMAIL_LEGGINGS, "§bPantalon §4§lAllahu §6§lAkbar", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 1), new SimpleEntry<>(Enchantment.PROTECTION_EXPLOSIONS, 2), null),
			getEnchantedItem(Material.CHAINMAIL_BOOTS, "§aBottes §4§lAllahu §6§lAkbar", null, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 3), new SimpleEntry<>(Enchantment.PROTECTION_EXPLOSIONS, 4), null),
			Arrays.asList(getInventoryItem(Material.GOLD_SWORD, 1, (short) 0, "§4Épée §4§lAllahu §6§lAkbar", null, new SimpleEntry<>(Enchantment.KNOCKBACK, 2), new SimpleEntry<>(Enchantment.DAMAGE_ALL, 3), null),
				getInventoryItem(Material.FLINT_AND_STEEL, 1, (short) 0, "§6Briquet §4§lAllahu §6§lAkbar", null, null, null, null),
				getInventoryItem(Material.TNT, 8, (short) 0, null, null, null, null, null),
				getInventoryItem(Material.DIRT, 16, (short) 0, null, null, null, null, null)
			)
		)
	;
	//MAGE, epee nulle mais des batons lancant des choses pour se battre / ulti : tp qqn sur lui ou ulti de janna / caque plastron cuir reste fer support

	Kits(String name, String capa, ItemStack helmet, ItemStack chestplate, ItemStack legs, ItemStack boots, List<ItemStack> inventory) {
		this.name = name;
		this.capa = capa;
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.legs = legs;
		this.boots = boots;
		this.inventory = inventory;
	}
	
	private final String name;
	
	private final String capa;
	
	private final ItemStack helmet;
	
	private final ItemStack chestplate;
	
	private final ItemStack legs;
	
	private final ItemStack boots;
	
	private final List<ItemStack> inventory;
	
	
	public String getName() {
		return name;
	}
	
	public String getCapa() {
		return capa;
	}
	
	public ItemStack getHelmet() {
		return helmet;
	}
	
	public ItemStack getChestplate() {
		return chestplate;
	}
	
	public ItemStack getLeggings() {
		return legs;
	}
	
	public ItemStack getBoots() {
		return boots;
	}
	
	public List<ItemStack> getInventory() {
		return inventory;
	}
	
	
	public void GiveStuff(Player player) {
		PlayerInventory pinv = player.getInventory();
		
		if (getHelmet() != null)pinv.setHelmet(getHelmet());
		if (getChestplate() != null)pinv.setChestplate(getChestplate());
		if (getLeggings() != null)pinv.setLeggings(getLeggings());
		if (getBoots() != null)pinv.setBoots(getBoots());
		
		if (getInventory() != null)
			for (ItemStack it : getInventory())
				if (it != null)
					pinv.addItem(it);
		player.updateInventory();
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
	
	
	
	public static Kits getByName(String name) {
		Kits k = null;
		
		for (Kits kit : Kits.values())
			if (kit.getName().equals(name))
				k = kit;
		
		return k;
	}
	
}
