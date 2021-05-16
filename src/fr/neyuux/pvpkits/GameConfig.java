package fr.neyuux.pvpkits;

import fr.neyuux.pvpkits.PlayerKits.Retour;
import fr.neyuux.pvpkits.enums.Gstate;
import fr.neyuux.pvpkits.enums.Kits;
import fr.neyuux.pvpkits.enums.Teams;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.*;
import java.util.Map.Entry;

public class GameConfig implements Listener {

	private final PvPKits main;
	public GameConfig(PvPKits main) {
		this.main = main;
	}
	
	private final List<Teams> TeamsOff = new ArrayList<>();
	private final List<Kits> KitsOff = new ArrayList<>();
	private int rdmNBJoueurParT = 2;
	private double StrengthPercentage = 25;
	private Boolean HideKits = Boolean.FALSE;
	private Boolean VieTab = Boolean.TRUE;
	
	public void startConfig() {
		main.setGameConfig(this);
		
		for (Player player : Bukkit.getOnlinePlayers())
			for (Entry<String, List<UUID>> en : main.getGrades().entrySet())
				if (en.getValue().contains(player.getUniqueId())) {
					player.getInventory().setItem(6, getComparator());
					player.updateInventory();
				}
		
		this.TeamsOff.clear();
	}
	
	public List<Teams> getTeamsOff() {
		return TeamsOff;
	}
	
	public List<Kits> getKitsOff() {
		return KitsOff;
	}

	public double getStrengthPercentage() {
		return StrengthPercentage;
	}
	
	public Boolean hasHideKits() {
		return HideKits;
	}
	
	public Boolean hasVieTab() {
		return VieTab;
	}
	
	
	@EventHandler
	public void onInteractComparator(PlayerInteractEvent ev) {
		Player player = ev.getPlayer();
		ItemStack current = player.getItemInHand();
		Action action = ev.getAction();
		
		if (current == null) return;
		
		if (current.hasItemMeta())
			if (current.getItemMeta().hasDisplayName())
				if (current.getItemMeta().getDisplayName().equals(getComparator().getItemMeta().getDisplayName()))
						if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))
								player.openInventory(getGameConfigInv(player));
	}
	
	@EventHandler
	public void onClickComparator(InventoryClickEvent ev) {
		Player player = (Player) ev.getWhoClicked();
		ItemStack current = ev.getCurrentItem();
		
		if (current == null) return;
		
		if (current.equals(getComparator()))
			player.openInventory(getGameConfigInv(player));
	}
	
	
	@EventHandler
	public void onGameConfigInv(InventoryClickEvent ev) {
		Player player = (Player)ev.getWhoClicked();
		Inventory inv = ev.getInventory();
		ItemStack current = ev.getCurrentItem();
		
		if (current == null) return;
		
		if (inv.getName().equals(getGameConfigInv(player).getName())) {
			ev.setCancelled(true);
			
			switch (current.getType()) {
			case BANNER:
				player.openInventory(getTeamInv());
				break;
			case APPLE:
				player.openInventory(getParamInv());
				break;
			case COMMAND:
				player.openInventory(getKitsInv());
				break;
			case BARRIER:
				Inventory invReset = Bukkit.createInventory(null, InventoryType.HOPPER, "§aConfirmation §b§lReset");
				invReset.setItem(0, main.getItem("§a§lConfirmer", Arrays.asList("§fConfirme le", "§freset de la map."), Material.STAINED_CLAY, (short)5));
				invReset.setItem(4, main.getItem("§c§lRefuser", Arrays.asList("§fRefuse le", "§freset de la map."), Material.STAINED_CLAY, (short)14));
				player.openInventory(invReset);
				break;
			case SKULL_ITEM:
				player.openInventory(getJoueursInv());
				break;

			default:
				break;
			}
		}
	}
	
	
	@EventHandler
	public void onInvTeam(InventoryClickEvent ev) {
		Player player = (Player)ev.getWhoClicked();
		Inventory inv = ev.getInventory();
		ItemStack current = ev.getCurrentItem();
		
		if (current == null) return;
		
		if (inv.getName().equals(getTeamInv().getName())) {
			ev.setCancelled(true);
			if (current.getType().equals(Material.BANNER)) {
				Teams t = Teams.getByColor(current.getItemMeta().getDisplayName().substring(0, 2));
				
				if (this.TeamsOff.contains(t))
					this.TeamsOff.remove(t);
				else
					this.TeamsOff.add(t);
				ItemMeta itm = current.getItemMeta();
				itm.setLore(getTeamInvItemLore(t));
				current.setItemMeta(itm);
				
			} else if (current.getType().equals(Material.BEDROCK)) {
				player.openInventory(getTeamRandomInv());
				
			} else if (current.getType().equals(Material.ARROW)) {
				player.openInventory(getGameConfigInv(player));
			}
			
		} else if (inv.getName().equals(getTeamRandomInv().getName())) {
			ev.setCancelled(true);
			
			if (current.getType().equals(Material.BANNER)) {
				Inventory invChangeRDMJPT = Bukkit.createInventory(null, 9, "§c§lMenu §eJoueurs par Teams");
				
				invChangeRDMJPT.setItem(0, getNumberAddOrRemoveBanner(1, Arrays.asList("§fAjoute §c" + 1, "§fau nombre de", "§fjoueurs par team.")));
				invChangeRDMJPT.setItem(1, getNumberAddOrRemoveBanner(2, Arrays.asList("§fAjoute §c" + 2, "§fau nombre de", "§fjoueurs par team.")));
				invChangeRDMJPT.setItem(2, getNumberAddOrRemoveBanner(3, Arrays.asList("§fAjoute §c" + 3, "§fau nombre de", "§fjoueurs par team.")));
				invChangeRDMJPT.setItem(6, getNumberAddOrRemoveBanner(-3, Arrays.asList("§fRetire §c" + 3, "§fau nombre de", "§fjoueurs par team.")));
				invChangeRDMJPT.setItem(7, getNumberAddOrRemoveBanner(-2, Arrays.asList("§fRetire §c" + 2, "§fau nombre de", "§fjoueurs par team.")));
				invChangeRDMJPT.setItem(8, getNumberAddOrRemoveBanner(-1, Arrays.asList("§fRetire §c" + 1, "§fau nombre de", "§fjoueurs par team.")));
				
				invChangeRDMJPT.setItem(5, getFlècheRetour());
				invChangeRDMJPT.setItem(4, getJParTeamItem());
				
				player.openInventory(invChangeRDMJPT);
				
			} else if (current.getType().equals(Material.ARROW)) {
				player.openInventory(getTeamInv());
				
			} else if (current.getType().equals(Material.STAINED_CLAY)) {
				List<Teams> TeamsOn = new ArrayList<>();
				for (Teams t : Teams.values())
					if (!this.TeamsOff.contains(t) && !t.equals(Teams.NONE))
						TeamsOn.add(t);
				ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
					
				for(Player p : new ArrayList<>(players)) {
					if (main.spectators.contains(p))
						players.remove(p);
					main.players.remove(p.getUniqueId());
				}
				
				if (((double)players.size() / (double)TeamsOn.size()) > (double)this.rdmNBJoueurParT) {
					player.sendMessage(main.getPrefix() + "§4[§cErreur§4] §cIl y a trop de joueurs pour le nombre d'équipe !");
					player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 10, 1);
					return;
				}
				
				for (Player p : players) {
					int rdm = new Random().nextInt(TeamsOn.size());
					boolean teamok = Boolean.FALSE;
					Teams team = TeamsOn.get(rdm);
					while(!teamok) {
						if (TeamsOn.size() == 1) {
							teamok = true;
							team = TeamsOn.get(0);
						} else {
						if (team.getPlayers().size() < this.rdmNBJoueurParT) {
							teamok = true;
							if (team.getPlayers().size() == (this.rdmNBJoueurParT - 1)) TeamsOn.remove(team);
						} else {
							TeamsOn.remove(team);
							rdm = new Random().nextInt(TeamsOn.size());
							team = TeamsOn.get(rdm);
							}
						}
					}
					main.addPlayerTeam(p, team);
				}
				for (Teams t : Teams.values())
					if (t.getPlayers().size() > 0 && !t.equals(Teams.NONE)) {
						String PlayersInTeam = "\n";
						for (Player p : t.getPlayers())
							PlayersInTeam = PlayersInTeam + "\n" + "§f- " + p.getDisplayName();
						Bukkit.broadcastMessage("§9Membres de la team " + t.getColor() + t.getAdjectiveName() + "§f : " +PlayersInTeam);
					}
				
			}
			
		} else if (inv.getName().equals("§c§lMenu §eJoueurs par Teams")) {	
			ev.setCancelled(true);
			
			if (current.getType().equals(Material.ARROW))
				player.openInventory(getTeamRandomInv());
			
			if (current.getType().equals(Material.BANNER)) {
				if (current.getItemMeta().getDisplayName().startsWith("§c- §l")) {
					int retired = current.getItemMeta().getDisplayName().charAt(current.getItemMeta().getDisplayName().length() - 1);
					this.rdmNBJoueurParT = this.rdmNBJoueurParT - retired;
				} else if (current.getItemMeta().getDisplayName().startsWith("§a+ §l")) {
					int added = current.getItemMeta().getDisplayName().charAt(current.getItemMeta().getDisplayName().length() - 1) - 48;
					this.rdmNBJoueurParT = this.rdmNBJoueurParT + added;
				}
				if (this.rdmNBJoueurParT <= 0)
					this.rdmNBJoueurParT = 1;
				if (this.rdmNBJoueurParT > Bukkit.getOnlinePlayers().size())
					this.rdmNBJoueurParT = Bukkit.getOnlinePlayers().size();
				inv.setItem(4, getJParTeamItem());
			}
			
		
		}
	}
	
	@EventHandler
	public void onParamInv(InventoryClickEvent ev) {
		Player player = (Player) ev.getWhoClicked();
		ItemStack current = ev.getCurrentItem();
		Inventory inv = ev.getInventory();
		
		if (current == null) return;
		
		if (inv.getName().equals(getParamInv().getName())) {
			ev.setCancelled(true);
			
			if (current.getType().equals(Material.POTION)) {
				Inventory InvForce = Bukkit.createInventory(null, 9, "§f§lMenu §4Force");
				
				InvForce.setItem(0, getNumberAddOrRemoveBanner(1, Arrays.asList("§fAjoute §c" + 1, "§fau pourcentage", "§fde §4force§f.")));
				InvForce.setItem(1, getNumberAddOrRemoveBanner(5, Arrays.asList("§fAjoute §c" + 5, "§fau pourcentage", "§fde §4force§f.")));
				InvForce.setItem(2, getNumberAddOrRemoveBanner(10, Arrays.asList("§fAjoute §c" + 10, "§fau pourcentage", "§fde §4force§f.")));
				InvForce.setItem(6, getNumberAddOrRemoveBanner(-10, Arrays.asList("§fRetire §c" + 10, "§fau pourcentage", "§fde §4force§f.")));
				InvForce.setItem(7, getNumberAddOrRemoveBanner(-5, Arrays.asList("§fRetire §c" + 5, "§fau pourcentage", "§fde §4force§f.")));
				InvForce.setItem(8, getNumberAddOrRemoveBanner(-1, Arrays.asList("§fRetire §c" + 1, "§fau pourcentage", "§fde §4force§f.")));
				
				InvForce.setItem(5, getFlècheRetour());
				InvForce.setItem(4, current);
				
				player.openInventory(InvForce);
				
			} else if (current.getType().equals(Material.SKULL_ITEM)) {
				this.HideKits = !this.HideKits;
				
				ItemStack HideKits = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
				SkullMeta hkm = (SkullMeta)HideKits.getItemMeta();
				String hkbv = "§cNon";
				if (this.HideKits) hkbv = "§aOui";
				hkm.setOwner("Anonymous2");
				hkm.setDisplayName("§6§lKits §7cachés");
				hkm.setLore(Arrays.asList("§9Valeur : " + hkbv, "", "§fSi cette option est activée, les", "§fkits de tous les joueurs ne seront", "§fpas affichés dans le chat ou le tab."));
				HideKits.setItemMeta(hkm);
				
				inv.setItem(13, HideKits);
				
			} else if (current.getType().equals(Material.GOLDEN_APPLE)) {
				this.VieTab = !this.VieTab;
				
				String vtbv = "§cNon";
				if (this.VieTab) vtbv = "§aOui";
				
				inv.setItem(15, main.getItem("§d§lVie §edans le §7tab", Arrays.asList("§9Valeur : " + vtbv, "", "§fPermet d'afficher ou non", "§fla §dvie §fdans le §7tab§f."), Material.GOLDEN_APPLE, (short)0));
			} else if (current.getType().equals(Material.ARROW)) {
				player.openInventory(getGameConfigInv(player));
				
			}
			
		} else if (inv.getName().equals("§f§lMenu §4Force")) {
			ev.setCancelled(true);
			
			if (current.getType().equals(Material.ARROW))
				player.openInventory(getParamInv());
			
			if (current.getType().equals(Material.BANNER)) {
				if (current.getItemMeta().getDisplayName().startsWith("§c- §l")) {
					if (current.getItemMeta().getDisplayName().endsWith("1")) {
						this.StrengthPercentage = this.StrengthPercentage - 1;
					} else if (current.getItemMeta().getDisplayName().endsWith("5")) {
						this.StrengthPercentage = this.StrengthPercentage - 5;
					}else if (current.getItemMeta().getDisplayName().endsWith("10")) {
						this.StrengthPercentage = this.StrengthPercentage - 10;
					}
				} else if (current.getItemMeta().getDisplayName().startsWith("§a+ §l")) {
					if (current.getItemMeta().getDisplayName().endsWith("1")) {
						this.StrengthPercentage = this.StrengthPercentage + 1;
					} else if (current.getItemMeta().getDisplayName().endsWith("5")) {
						this.StrengthPercentage = this.StrengthPercentage + 5;
					} else if (current.getItemMeta().getDisplayName().endsWith("10")) {
						this.StrengthPercentage = this.StrengthPercentage + 10;
					}
				}
				if (this.StrengthPercentage <= 0)
					this.StrengthPercentage = 1;
				
				if (this.StrengthPercentage > 130)
					this.StrengthPercentage = 130;
				
				Potion strength = new Potion(PotionType.STRENGTH);
				ItemStack si = strength.toItemStack(1);
				ItemMeta sm = si.getItemMeta();
				sm.setDisplayName("§7Pourcentage de la §4Force");
				sm.setLore(Arrays.asList("§9Valeur : §c" + this.StrengthPercentage, 
				"", "§fPermet de changer le taux de", "§fdégâts de l'effet de force."));
				sm.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				si.setItemMeta(sm);
				inv.setItem(4, si);
			}
			
		
		}
	}
	
	@EventHandler
	public void onKitsInv(InventoryClickEvent ev) {
		Player player = (Player) ev.getWhoClicked();
		ItemStack current = ev.getCurrentItem();
		Inventory inv = ev.getInventory();
		
		if (current == null) return;
		
		if (inv.getName().equals(getKitsInv().getName())) {
			ev.setCancelled(true);
			
			if (current.getType().equals(Material.ARROW))
				player.openInventory(getGameConfigInv(player));
		
			if (!current.getType().equals(Material.STAINED_GLASS_PANE) && !current.getType().equals(Material.ARROW) && !current.getType().equals(Material.AIR) && !current.getType().equals(Material.BEDROCK)) {
				Kits k = Kits.getByName(current.getItemMeta().getDisplayName());
				
				if (this.KitsOff.contains(k)) {
					ItemMeta m = current.getItemMeta();
					List<String> l = m.getLore();
					m.setLore(Arrays.asList(l.get(0), "§eÉtat : §aActivé"));
					current.setItemMeta(m);
					this.KitsOff.remove(k);
				} else {
					ItemMeta m = current.getItemMeta();
					List<String> l = m.getLore();
					m.setLore(Arrays.asList(l.get(0), "§eÉtat : §cDésactivé"));
					current.setItemMeta(m);
					this.KitsOff.add(k);
				}
			}
			
			if (current.getType().equals(Material.BEDROCK))
				player.openInventory(getKitsRandomInv());

		} else if (inv.getName().equals(getKitsRandomInv().getName())) {
			ev.setCancelled(true);
			
			if (current.getType().equals(Material.STAINED_CLAY)) {
				List<Kits> KitsOn = new ArrayList<Kits>();
				for (Kits t : Kits.values())
					if (!this.KitsOff.contains(t))
						KitsOn.add(t);
				ArrayList<Player> players = new ArrayList<Player>(Bukkit.getOnlinePlayers());
					
				for(Player p : new ArrayList<Player>(players)) {
					if (main.spectators.contains(p) || main.playerkits.get(p.getUniqueId()).isTeam(Teams.NONE))
						players.remove(p);
					main.players.remove(p.getUniqueId());
				}
				
				for (Player p : players)
					main.setPlayerKit(p, KitsOn.get(new Random().nextInt(KitsOn.size())));
			} else if (current.getType().equals(Material.ARROW))
				player.openInventory(getKitsInv());
		}
	}
	
	@EventHandler
	public void onResetInv(InventoryClickEvent ev) {
		Player player = (Player) ev.getWhoClicked();
		ItemStack current = ev.getCurrentItem();
		Inventory inv = ev.getInventory();
		
		if (current == null) return;
		
		if (inv.getName().equals("§aConfirmation §b§lReset")) {
			ev.setCancelled(true);
			if (current.getType().equals(Material.STAINED_CLAY)) {
				
				if (current.getDurability() == 14)
					player.closeInventory();
				else if (current.getDurability() == 5) {
					player.sendMessage(main.getPrefix() + "§b" + player.getName() + " §ea reset la map !");
					main.rel();
				}
				
			}
		}
	}
	
	@EventHandler
	public void onJoueursInv(InventoryClickEvent ev) {
		Player player = (Player) ev.getWhoClicked();
		ItemStack current = ev.getCurrentItem();
		Inventory inv = ev.getInventory();
		
		if (current == null) return;
		
		if (inv.getName().equals(getJoueursInv().getName())) {
			ev.setCancelled(true);
			
			if (current.getType().equals(Material.ARROW))
				player.openInventory(getGameConfigInv(player));
			else if (current.getType().equals(Material.SKULL_ITEM))
				player.openInventory(getConfigJoueurInv(Bukkit.getPlayer(((SkullMeta) current.getItemMeta()).getOwner())));
		}
		else if (inv.getName().startsWith("§6§lMenu ")) {
			ev.setCancelled(true);
			Player p = Bukkit.getPlayer(((SkullMeta) inv.getItem(3).getItemMeta()).getOwner());
			
			if (current.getType().equals(Material.ARROW))
				player.openInventory(getJoueursInv());
			else if (current.getType().equals(Material.WOOL) && current.getDurability() == 8) {
				Inventory invChangeTeam = null;
				if (p.getDisplayName().length() <= (32 - 8))
					invChangeTeam = Bukkit.createInventory(null, 45, "§e§lTeams " + p.getDisplayName());
					else
						invChangeTeam = Bukkit.createInventory(null, 45, "§e§lTeams §b" + p.getName());
				setInvCoin(invChangeTeam, (byte)4);
				
				ItemStack phead = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
				SkullMeta pheadmeta = (SkullMeta) phead.getItemMeta();
				pheadmeta.setOwner(p.getName());
				pheadmeta.setDisplayName(p.getDisplayName());
				pheadmeta.setLore(current.getItemMeta().getLore());
				phead.setItemMeta(pheadmeta);
				invChangeTeam.setItem(4, phead);
				
				invChangeTeam.setItem(11, main.getItem("§cTeam §4§lRouge", getTeamChangeItemLore(Teams.RED), Material.BANNER, (short)1));
				invChangeTeam.setItem(13, main.getItem("§bTeam §1§lBleue", getTeamChangeItemLore(Teams.BLUE), Material.BANNER, (short)4));
				invChangeTeam.setItem(15, main.getItem("§2Team §a§lVerte", getTeamChangeItemLore(Teams.GREEN), Material.BANNER, (short)10));
				invChangeTeam.setItem(29, main.getItem("§5Team §d§lRose", getTeamChangeItemLore(Teams.PINK), Material.BANNER, (short)9));
				invChangeTeam.setItem(31, main.getItem("§7Team §8§lNoire", getTeamChangeItemLore(Teams.BLACK), Material.BANNER, (short)0));
				invChangeTeam.setItem(33, main.getItem("§6Team §e§lJaune", getTeamChangeItemLore(Teams.YELLOW), Material.BANNER, (short)11));
				
				invChangeTeam.setItem(41, main.getItem("§f§lReset la team", Arrays.asList("§fRetire " + p.getName(), "§fde sa team."), Material.WOOL, (short)0));
				
				invChangeTeam.setItem(44, getFlècheRetour());
				player.openInventory(invChangeTeam);
			}
			else if (current.getType().equals(Material.NETHER_STAR)) {
				if (!main.playerkits.get(p.getUniqueId()).getTeam().equals(Teams.NONE)) {
					Inventory invChangeKit = null;
					if (p.getDisplayName().length() <= (32 - 8))
						invChangeKit = Bukkit.createInventory(null, 54, "§6§lKits " + p.getDisplayName());
						else
							invChangeKit = Bukkit.createInventory(null, 54, "§6§lKits §b" + p.getName());
				
					setInvCoin(invChangeKit, (byte)12);
					invChangeKit.setItem(53, getFlècheRetour());
				
					ItemStack phead = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
					SkullMeta pheadmeta = (SkullMeta) phead.getItemMeta();
					pheadmeta.setOwner(p.getName());
					pheadmeta.setLore(current.getItemMeta().getLore());
					pheadmeta.setDisplayName(p.getDisplayName());
					phead.setItemMeta(pheadmeta);
					invChangeKit.setItem(4, phead);
				
					invChangeKit.setItem(20, main.getItem(Kits.TOAD.getName(), Arrays.asList("§fMet le kit du joueur", "§fà " + Kits.TOAD.getName()), Material.RED_MUSHROOM, (short)0));
					invChangeKit.setItem(22, main.getItem(Kits.PYROMANE.getName(), Arrays.asList("§fMet le kit du joueur", "§fà " + Kits.PYROMANE.getName()), Material.BLAZE_POWDER, (short)0));
					invChangeKit.setItem(24, main.getItem(Kits.LG.getName(), Arrays.asList("§fMet le kit du joueur", "§fà " + Kits.LG.getName()), Material.MONSTER_EGG, (short)0));
					invChangeKit.setItem(30, main.getItem(Kits.SORCIERE.getName(), Arrays.asList("§fMet le kit du joueur", "§fà " + Kits.SORCIERE.getName()), Material.POTION, (short)0));
					invChangeKit.setItem(32, main.getItem(Kits.HEALER.getName(), Arrays.asList("§fMet le kit du joueur", "§fà " + Kits.HEALER.getName()), Material.GOLDEN_APPLE, (short)0));
					invChangeKit.setItem(38, main.getItem(Kits.XRAYEUR.getName(), Arrays.asList("§fMet le kit du joueur", "§fà " + Kits.XRAYEUR.getName()), Material.DIAMOND_ORE, (short)0));
					invChangeKit.setItem(40, main.getItem(Kits.BISCUIT.getName(), Arrays.asList("§fMet le kit du joueur", "§fà " + Kits.BISCUIT.getName()), Material.COOKIE, (short)0));
					invChangeKit.setItem(42, main.getItem(Kits.MACRON.getName(), Arrays.asList("§fMet le kit du joueur", "§fà " + Kits.MACRON.getName()), Material.GOLD_INGOT, (short)0));
					invChangeKit.setItem(48, main.getItem(Kits.MILITAIRE.getName(), Arrays.asList("§fMet le kit du joueur", "§fà " + Kits.MILITAIRE.getName()), Material.BOW, (short)0));
					invChangeKit.setItem(50, main.getItem(Kits.ALLAHU_AKBAR.getName(), Arrays.asList("§fMet le kit du joueur", "§fà " + Kits.ALLAHU_AKBAR.getName()), Material.TNT, (short)0));
					invChangeKit.setItem(14, main.getItem("§f§lReset le kit du joueur", null, Material.NETHER_STAR, (short)0));
				
					player.openInventory(invChangeKit);
				} else {
					player.sendMessage(main.getPrefix() + "§4[§cErreur§4] §c" + p.getName() + " doit être dans une team avant de pouvoir choisir son kit.");
					player.playSound(player.getLocation(), Sound.ENDERMAN_SCREAM, 10f, 1f);
				}
			}
			else if (current.getType().equals(Material.BEDROCK)) {
				Inventory rdmmenu = Bukkit.createInventory(null, InventoryType.HOPPER, "§7Random §6§lMenu §b" + p.getName());
				rdmmenu.setItem(4, getFlècheRetour());
				
				rdmmenu.setItem(1, main.getItem("§7Random §e§lTeam", Arrays.asList("§fMet aléatoirement", "§e" + p.getName() + "§f dans", "§fune team activée."), Material.WOOL, (short)7));
				rdmmenu.setItem(2, main.getItem("§7Random §6§lKit", Arrays.asList("§fDonne aléatoirement", "§fun kit à §e" + p.getName(), "§fparmis les kits activés."), Material.NETHER_STAR, (short)0));
				
				player.openInventory(rdmmenu);
			}
			else if (current.getType().equals(Material.SUGAR)) {
				
				if (!main.spectators.contains(p)) {

					main.players.remove(p.getUniqueId());
					
					main.spectators.add(p);
					p.setGameMode(GameMode.SPECTATOR);
					p.getInventory().remove(Material.GHAST_TEAR);
					p.updateInventory();
					p.setDisplayName("§8[§7Spectateur§8] §7" + p.getName());
					p.setPlayerListName("§8[§7Spectateur§8] §7" + p.getName());
					if (Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(p.getName()) != null)Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(p.getName()).removeEntry(p.getName());
					main.playerkits.get(p.getUniqueId()).setTeam(Teams.NONE);
					p.sendMessage(main.getPrefix() + "§7Vous avez été mis en spectateur par " + player.getDisplayName() + "§7.");
					player.sendMessage(main.getPrefix() + "§b" + p.getName() + "§7 a bien été mis en spectateur.");
					
					String spec = "§7Spectateur : §cNon";
					if (main.spectators.contains(p))
						spec = "§7Spectateur : §aOui";
					inv.setItem(24, main.getItem(current.getItemMeta().getDisplayName(), Arrays.asList("", spec), Material.SUGAR, (short)0));
					
					ItemStack phead = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
					SkullMeta pheadmeta = (SkullMeta) phead.getItemMeta();
					pheadmeta.setOwner(p.getName());
					pheadmeta.setLore(Arrays.asList("", spec, inv.getItem(3).getItemMeta().getLore().get(2)));
					phead.setItemMeta(pheadmeta);
					inv.setItem(3, phead);
					
					
				} else {
					p.teleport(Teams.NONE.getMainSalle());
					p.setGameMode(GameMode.ADVENTURE);
					p.getInventory().clear();
					main.clearArmor(player);
					p.getInventory().setItem(1, main.getItem("§7§lDevenir Spectateur", null, Material.GHAST_TEAR, (short)0));
					p.setDisplayName(player.getName());
					p.setPlayerListName(p.getName());
					
					main.setPlayerTeamFromGrade(p, Teams.NONE);
					for (Entry<String, List<UUID>> en : main.getGrades().entrySet())
						if (en.getValue().contains(p.getUniqueId()))
							p.getInventory().setItem(6, getComparator());
					main.spectators.remove(p);
					main.setState(Gstate.PREPARING);
					player.sendMessage(main.getPrefix() + p.getDisplayName() + "§7 n'est plus en spectateur.");
					p.sendMessage(main.getPrefix() + player.getDisplayName() + " §7vous a enlevé du mode spectateur.");
					String spec = "§7Spectateur : §cNon";
					if (main.spectators.contains(p))
						spec = "§7Spectateur : §aOui";
					inv.setItem(24, main.getItem(current.getItemMeta().getDisplayName(), Arrays.asList("", spec), Material.SUGAR, (short)0));
					
					ItemStack phead = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
					SkullMeta pheadmeta = (SkullMeta) phead.getItemMeta();
					pheadmeta.setOwner(p.getName());
					pheadmeta.setLore(Arrays.asList("", spec, inv.getItem(3).getItemMeta().getLore().get(2)));
					phead.setItemMeta(pheadmeta);
					inv.setItem(3, phead);
				}
			}
		} else if (inv.getName().startsWith("§7Random §6§lMenu ")) {
			Player p = Bukkit.getPlayer(inv.getName().split("§b")[1]);
			ev.setCancelled(true);
			
			if (current.getType().equals(Material.WOOL)) {
			List<Teams> TeamsOn = new ArrayList<Teams>();
			for (Teams t : Teams.values())
				if (!t.equals(Teams.NONE) && !getTeamsOff().contains(t))
					TeamsOn.add(t);
			

			int rdm = new Random().nextInt(TeamsOn.size());
			Boolean teamok = Boolean.FALSE;
			Teams team = TeamsOn.get(rdm);
			while(!teamok) {
				if (TeamsOn.size() == 1) {
					teamok = true;
					team = TeamsOn.get(0);
				} else {
				if (team.getPlayers().size() < this.rdmNBJoueurParT) {
					teamok = true;
					if (team.getPlayers().size() == (this.rdmNBJoueurParT - 1)) TeamsOn.remove(team);
				} else {
					TeamsOn.remove(team);
					rdm = new Random().nextInt(TeamsOn.size());
					team = TeamsOn.get(rdm);
					}
				}
			}
			main.addPlayerTeam(p, team);
			p.sendMessage(main.getPrefix() + player.getDisplayName() + " §fa randomizé votre team !");
			player.sendMessage(main.getPrefix() + "§fVous avez bien randomizé la team de " + player.getDisplayName());
			player.playSound(player.getLocation(), Sound.NOTE_PLING, 6, 1);
			p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 7, 1);

			} else if (current.getType().equals(Material.NETHER_STAR)) {
				
				if (!main.playerkits.get(p.getUniqueId()).isTeam(Teams.NONE)) {
					List<Kits> KitsOn = new ArrayList<Kits>();
					for (Kits t : Kits.values())
						if (!this.KitsOff.contains(t))
							KitsOn.add(t);
					main.setPlayerKit(p, KitsOn.get(new Random().nextInt(KitsOn.size())));
					p.sendMessage(main.getPrefix() + player.getDisplayName() + " §fa randomizé votre kit !");
					player.sendMessage(main.getPrefix() + "§fVous avez bien randomizé le kit de " + player.getDisplayName());
					player.playSound(player.getLocation(), Sound.NOTE_PLING, 6, 1);
					p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 7, 1);
				} else {
					player.sendMessage(main.getPrefix() + "§4[§cErreur§4] §c" + p.getName() + " doit être dans une team avant de pouvoir choisir son kit.");
					player.playSound(player.getLocation(), Sound.ENDERMAN_SCREAM, 10f, 1f);
				}
				
			} else if (current.getType().equals(Material.ARROW))
				player.openInventory(getConfigJoueurInv(p));
			
		} else if (inv.getName().startsWith("§e§lTeams ")) {
			ev.setCancelled(true);
			Player p = Bukkit.getPlayer(((SkullMeta) inv.getItem(4).getItemMeta()).getOwner());
			
			if (current.getType().equals(Material.BANNER)) {
				Teams t = Teams.getByColor(current.getItemMeta().getDisplayName().substring(7, 9));
				main.addPlayerTeam(p, t);
				player.sendMessage(main.getPrefix() + p.getDisplayName() + "§9 a bien été mis dans l'équipe "+t.getColor()+"§l"+t.getAdjectiveName()+" §9!"); 
				player.playSound(player.getLocation(), Sound.GLASS, 10, 1);
				p.sendMessage(main.getPrefix() + player.getDisplayName() + "§9 vous a mis dans l'équipe "+t.getColor()+"§l"+t.getAdjectiveName()+" §9!");
			}
			else if (current.getType().equals(Material.WOOL)) {
				if (!main.playerkits.get(p.getUniqueId()).isTeam(Teams.NONE)) {
					Teams t = main.playerkits.get(p.getUniqueId()).getTeam();
					p.teleport(Teams.NONE.getMainSalle());
					p.getInventory().clear();
					p.getInventory().setItem(1, main.getItem("§7§lDevenir Spectateur", null, Material.GHAST_TEAR, (short)0));
					p.setDisplayName(p.getName());
					p.setPlayerListName(p.getName());
					main.setPlayerTeamFromGrade(p, Teams.NONE);
					p.sendMessage(main.getPrefix() + player.getDisplayName() + "§9 a reset votre team.");
					player.sendMessage(main.getPrefix() + "§9Vous avez bien reset la team de " + p.getDisplayName());
					for (Entry<String, List<UUID>> en : main.getGrades().entrySet())
						if (en.getValue().contains(p.getUniqueId()))
							p.getInventory().setItem(6, getComparator());
					p.updateInventory();
					
					Bukkit.broadcastMessage(main.getPrefix() + p.getDisplayName() + " §fa quitté la "+t.getColor()+"§lTeam " + t.getAdjectiveName() + "§f !");
					main.playerkits.get(p.getUniqueId()).setRetour(null);
					main.playerkits.get(p.getUniqueId()).setKit(null);
					main.players.remove(p.getUniqueId());
					
					} else
						player.sendMessage(main.getPrefix() + "§4[§cErreur§4] §c"+p.getName()+" n'est pas dans une team !");

			}
			
			else if (current.getType().equals(Material.ARROW))
				player.openInventory(getConfigJoueurInv(p));
		} else if (inv.getName().startsWith("§6§lKits ")) {
			ev.setCancelled(true);
			Player p = Bukkit.getPlayer(((SkullMeta) inv.getItem(4).getItemMeta()).getOwner());
			
			if (!current.getType().equals(Material.NETHER_STAR) && !current.getType().equals(Material.STAINED_GLASS_PANE) && !current.getType().equals(Material.ARROW) && !current.getType().equals(Material.AIR) && !current.getType().equals(Material.SKULL_ITEM)) {
				Kits k = Kits.getByName(current.getItemMeta().getDisplayName());
				//checkStart
				main.setPlayerKit(p, k);
				p.playSound(p.getLocation(), Sound.GHAST_SCREAM, 10, 1);
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
				player.sendMessage(main.getPrefix() + "§9Vous avez mis le kit de " + p.getDisplayName().substring(11 + k.getName().length()) + " à " + current.getItemMeta().getDisplayName());
			}
			
			else if (current.getType().equals(Material.NETHER_STAR)) {
				if (main.playerkits.get(p.getUniqueId()).getKit() != null) {
					Kits kit = main.playerkits.get(p.getUniqueId()).getKit();
					Teams team = main.playerkits.get(p.getUniqueId()).getTeam();
					
					player.getInventory().setItem(4, main.getItem("§6Kits " + team.getColor() + team.getName() + "s", null, Material.CHEST, (short)0));
					player.updateInventory();
					main.playerkits.get(player.getUniqueId()).setRetour(Retour.SALLE_TEAM);
					player.teleport(team.getMainSalle());
					if (!hasHideKits()) {
						Bukkit.broadcastMessage(main.getPrefix() + p.getDisplayName().substring(11 + kit.getName().length()) + " §fn'a plus le kit " + kit.getName() + " §f!");
						player.setDisplayName(player.getDisplayName().substring(11 + kit.getName().length()));
						player.setPlayerListName(player.getDisplayName());
					}
					main.playerkits.get(player.getUniqueId()).setKit(null);
					main.players.remove(player.getUniqueId());
					
					player.sendMessage(main.getPrefix() + "§9Vous avez bien reset le kit de " + p.getDisplayName());
					player.playSound(player.getLocation(), Sound.ARROW_HIT, 10, 1);
					p.sendMessage(main.getPrefix() + player.getDisplayName() + " §9a reset votre kit.");
				} else {
					player.sendMessage(main.getPrefix() + "§4[§cErreur§4] §4" + p.getName() + "§c n'a pas de kit !");
					player.playSound(player.getLocation(), Sound.ENDERMAN_SCREAM, 10f, 1f);
				}
			}
			else if (current.getType().equals(Material.ARROW))
				player.openInventory(getConfigJoueurInv(p));
		}
	}
	
	
	
	public ItemStack getComparator() {
		ItemStack c = new ItemStack(Material.REDSTONE_COMPARATOR);
		ItemMeta cm = c.getItemMeta();
		cm.setDisplayName("§c§lConfiguration de la partie");
		cm.addEnchant(Enchantment.DURABILITY, 9, true);
		cm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		c.setItemMeta(cm);
		
		return c;
	}
	
	private ItemStack getTeamInvItem(Teams team) {
		ItemStack i = new ItemStack(Material.BANNER);
		ItemMeta im = i.getItemMeta();
		
		
		if (team.equals(Teams.RED)) {
			i.setDurability((short)1);
		} else if (team.equals(Teams.YELLOW)) {
			i.setDurability((short)11);
			
		} else if (team.equals(Teams.BLUE)) {
			i.setDurability((short)4);		
			
		} else if (team.equals(Teams.PINK)) {
			i.setDurability((short)9);
		
		} else if (team.equals(Teams.GREEN)) {
			i.setDurability((short)10);
		}
		
		im.setDisplayName(team.getColor() + "Team §l" + team.getAdjectiveName());
		im.setLore(getTeamInvItemLore(team));
		i.setItemMeta(im);
		return i;
	}
	
	private List<String> getTeamInvItemLore(Teams team) {
		List<String> lores = new ArrayList<String>();
		List<String> players = new ArrayList<String>();
		for (Player p : team.getPlayers())
			players.add(p.getDisplayName());
		String Etat = "§aActivé";
		if (this.TeamsOff.contains(team))
			Etat = "§cDésactivé";

		lores.add(team.getSecondColor() + "Joueurs (" + team.getColor() + players.size() + team.getSecondColor() + ") : ");
		for (String s : players) {
			if (lores.size() < 19) {
				lores.add(s);
			}
		}
		lores.add(" ");
		lores.add("§bÉtat : " + Etat);
		
		return lores;
	}
	
	private List<String> getTeamChangeItemLore(Teams team) {
		List<String> lores = new ArrayList<String>();
		
		lores.add(team.getSecondColor() + "Joueurs (" + team.getColor() + team.getPlayers().size() + team.getSecondColor() + ") : ");
		for (Player p : team.getPlayers())
			if (lores.size() < 17)
				lores.add(p.getDisplayName());
		
		lores.add("");
		lores.add(team.getSecondColor() + "Mettre le joueur dans la " + team.getColor() + "§l" + "Team " + team.getAdjectiveName());
		
		return lores;
	}
	
	private ItemStack getJParTeamItem() {
		ItemStack JParTeams = new ItemStack(Material.BANNER, this.rdmNBJoueurParT);
		BannerMeta jptm = (BannerMeta) JParTeams.getItemMeta();
		jptm.setDisplayName("§6Nombre de joueurs max par teams");
		jptm.setLore(Arrays.asList("", "§eNombre de joueurs max : §c" + this.rdmNBJoueurParT, "§fDéfinit le nombre de", "§fjoueurs max par team."));
		jptm.setBaseColor(DyeColor.WHITE);
		jptm.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_SMALL));
		jptm.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT));
		jptm.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT));
		jptm.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP));
		jptm.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
		jptm.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_MIDDLE));
		jptm.addPattern(new Pattern(DyeColor.WHITE, PatternType.BORDER));
		jptm.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		JParTeams.setItemMeta(jptm);
		return JParTeams;
	}
	
	private ItemStack getRandomedPlayersKitsItem() {
		List<Player> players = new ArrayList<Player>();
		for (Teams t : Teams.values())
			if (!t.equals(Teams.NONE))
				players.addAll(t.getPlayers());
		
		ItemStack JParTeams = new ItemStack(Material.BANNER, players.size());
		BannerMeta jptm = (BannerMeta) JParTeams.getItemMeta();
		jptm.setDisplayName("§6Joueurs qui vont avoir un kit aléatoire");
		List<String> lore = new ArrayList<String>();
		for (Player p : players) lore.add(p.getDisplayName());
		jptm.setLore(lore);
		jptm.setBaseColor(DyeColor.WHITE);
		jptm.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_SMALL));
		jptm.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT));
		jptm.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT));
		jptm.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP));
		jptm.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
		jptm.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_MIDDLE));
		jptm.addPattern(new Pattern(DyeColor.WHITE, PatternType.BORDER));
		jptm.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		JParTeams.setItemMeta(jptm);
		return JParTeams;
	}
	
	private ItemStack getNumberAddOrRemoveBanner(int nbmoinsplus, List<String> lore) {
		ItemStack b = new ItemStack(Material.BANNER);
		BannerMeta bm = (BannerMeta) b.getItemMeta();
		bm.setBaseColor(DyeColor.WHITE);
		bm.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		
		if (nbmoinsplus > 0) {
			bm.addPattern(new Pattern(DyeColor.LIME, PatternType.STRIPE_CENTER));
			bm.addPattern(new Pattern(DyeColor.LIME, PatternType.STRIPE_MIDDLE));
			bm.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP));
			bm.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM));
			bm.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP));
			bm.addPattern(new Pattern(DyeColor.WHITE, PatternType.BORDER));
			b.setAmount(nbmoinsplus);
			bm.setDisplayName("§a+ §l" + nbmoinsplus);
		} else {
			bm.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_MIDDLE));
			bm.addPattern(new Pattern(DyeColor.WHITE, PatternType.BORDER));
			b.setAmount((nbmoinsplus));
			bm.setDisplayName("§c- §l" + -nbmoinsplus);
		}
		bm.setLore(lore);
		b.setItemMeta(bm);
		return b;
	}
	
	private ItemStack getKitsInvItem(Material type, Kits kit) {
		ItemStack it = new ItemStack(type);
		ItemMeta itm = it.getItemMeta();
		itm.setDisplayName(kit.getName());
		
		Boolean etat = true;
		String setat = "§aActivé";
		if (this.KitsOff.contains(kit)) etat = false;
		if (!etat) setat = "§cDésactivé";
		itm.setLore(Arrays.asList("", "§eÉtat : " + setat));
		
		it.setItemMeta(itm);
		
		
		return it;
	}
	
	
	
	public Inventory getGameConfigInv(Player player) {
		Inventory inv = Bukkit.createInventory(null, 45, "§c§lConfiguration");
		inv.setItem(11, main.getItem("§e§lTeams", Arrays.asList("§fConfiguration des", "§fteams de la partie."), Material.BANNER, (byte)15));
		inv.setItem(15, main.getItem("§6§lKits", Arrays.asList("§fConfiguration des", "§fkits de la partie."), Material.COMMAND, (short)0));
		inv.setItem(13, main.getItem("§f§lParamètres de la partie", Arrays.asList("§fConfiguration", "§fprincipale de la partie."), Material.APPLE, (short)0));
		
		List<String> ops = new ArrayList<String>();
		
		for (Entry<String, List<UUID>> en : main.getGrades().entrySet())
			for (UUID uuid : en.getValue())
				ops.add(Bukkit.getPlayer(uuid).getDisplayName());
		inv.setItem(38, main.getItem("§cGens pouvant modifier la configuration", ops, Material.SIGN, (short)0));
		inv.setItem(32, main.getItem("§b§lReset la map", Collections.singletonList("§fRedémarre la map."), Material.BARRIER, (short)0));
		
		ItemStack phead = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		SkullMeta pheadmeta = (SkullMeta) phead.getItemMeta();
		pheadmeta.setOwner(player.getName());
		pheadmeta.setLore(Arrays.asList("§fPermet de gérer", "§fles joueurs", "§f§o(leurs team, etc)"));
		pheadmeta.setDisplayName("§6Joueurs");
		phead.setItemMeta(pheadmeta);
		inv.setItem(30, phead);
		setInvCoin(inv, (byte)0);
		
		
		return inv;
	}
	
	private Inventory getTeamInv() {
		Inventory inv = Bukkit.createInventory(null, 36, "§c§lConfiguration §e§lTeams");
		setInvCoin(inv, (byte)4);
		inv.setItem(11, getTeamInvItem(Teams.RED));
		inv.setItem(20, getTeamInvItem(Teams.GREEN));
		inv.setItem(13, getTeamInvItem(Teams.BLACK));
		inv.setItem(22, getTeamInvItem(Teams.PINK));
		inv.setItem(15, getTeamInvItem(Teams.BLUE));
		inv.setItem(24, getTeamInvItem(Teams.YELLOW));
		
		ItemStack r = new ItemStack(Material.BEDROCK);
		ItemMeta rm = r.getItemMeta();
		rm.setDisplayName("§c§lRandomizer");
		rm.setLore(Arrays.asList("§fRépartit aléatoirement", "§fles joueurs dans", "§fles teams activées."));
		rm.addEnchant(Enchantment.SILK_TOUCH, 1, true);
		rm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		r.setItemMeta(rm);
		inv.setItem(33, r);
		
		inv.setItem(35, getFlècheRetour());
		
		return inv;
	}
	
	private Inventory getTeamRandomInv() {
		Inventory inv = Bukkit.createInventory(null, 9, "§c§lMenu §e§lTeams §7Random");
		List<String> TeamsOn = new ArrayList<String>();
		for (Teams t : Teams.values())
			if (!this.TeamsOff.contains(t) && !t.equals(Teams.NONE))
				TeamsOn.add(t.getColor() + t.getAdjectiveName());
		
		inv.setItem(8, getFlècheRetour());
		
		inv.setItem(0, getJParTeamItem());
		
		ItemStack TA = new ItemStack(Material.WOOL, TeamsOn.size(), (short)7);
		ItemMeta tam = TA.getItemMeta();
		tam.setDisplayName("§eTeams Activées");
		tam.setLore(TeamsOn);
		TA.setItemMeta(tam);
		inv.setItem(1, TA);

		inv.setItem(4, main.getItem("§a§lValider le random", Arrays.asList("§fRépartit tous les", "§fjoueurs en équipe de §e" + this.rdmNBJoueurParT , "§fdans les équipes activées."), Material.STAINED_CLAY, (short)5));
		
		return inv;
	}
	
	private Inventory getKitsRandomInv() {
		Inventory inv = Bukkit.createInventory(null, 9, "§c§lMenu §6§lKits §7Random");
		List<String> KitsOn = new ArrayList<String>();
		for (Kits k : Kits.values())
			if (!this.KitsOff.contains(k))
				KitsOn.add(k.getName());
		
		inv.setItem(8, getFlècheRetour());
		
		inv.setItem(0, getRandomedPlayersKitsItem());
		
		ItemStack TA = new ItemStack(Material.WOOL, KitsOn.size(), (short)7);
		ItemMeta tam = TA.getItemMeta();
		tam.setDisplayName("§6Kits Activés");
		tam.setLore(KitsOn);
		TA.setItemMeta(tam);
		inv.setItem(1, TA);

		inv.setItem(4, main.getItem("§a§lValider le random", Arrays.asList("§fDonne à tous les", "§fjoueurs en équipe un des §6" + KitsOn.size() , "§fkits activés."), Material.STAINED_CLAY, (short)5));
		
		return inv;
	}
	
	private Inventory getParamInv() {
		Inventory inv = Bukkit.createInventory(null, 45, "§c§lConfiguration §f§lPartie");
		setInvCoin(inv, (byte)8);
		inv.setItem(44, getFlècheRetour());
		
		Potion strength = new Potion(PotionType.STRENGTH);
		ItemStack si = strength.toItemStack(1);
		ItemMeta sm = si.getItemMeta();
		sm.setDisplayName("§7Pourcentage de la §4Force");
		sm.setLore(Arrays.asList("§9Valeur : §c" + this.StrengthPercentage, 
		"", "§fPermet de changer le taux de", "§fdégâts de l'effet de force."));
		sm.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		si.setItemMeta(sm);
		
		ItemStack HideKits = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
		SkullMeta hkm = (SkullMeta)HideKits.getItemMeta();
		String hkbv = Boolean.valueOf(this.HideKits).toString();
		if (this.HideKits) hkbv = "§aOui";
		else hkbv = "§cNon";
		hkm.setOwner("DKTroy");
		hkm.setDisplayName("§6§lKits §7cachés");
		hkm.setLore(Arrays.asList("§9Valeur : " + hkbv, "", "§fSi cette option est activée, les", "§fkits de tous les joueurs ne seront", "§fpas affichés dans le chat ou le tab."));
		HideKits.setItemMeta(hkm);
		
		ItemStack VieTab = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta vtm = VieTab.getItemMeta();
		String vtbv = Boolean.valueOf(this.VieTab).toString();
		if (this.VieTab) vtbv = "§aOui";
		else vtbv = "§cNon";
		vtm.setDisplayName("§d§lVie §edans le §7tab");
		vtm.setLore(Arrays.asList("§9Valeur : " + vtbv, "", "§fPermet d'afficher ou non", "§fla §dvie §fdans le §7tab§f."));
		VieTab.setItemMeta(vtm);
		
		inv.setItem(11, si);
		inv.setItem(13, HideKits);
		inv.setItem(15, VieTab);

		return inv;
	}
	
	private Inventory getKitsInv() {
		Inventory inv = Bukkit.createInventory(null, 54, "§c§lConfiguration §6§lKits");
		setInvCoin(inv, (byte)12);
		inv.setItem(53, getFlècheRetour());
		
		inv.setItem(11, getKitsInvItem(Material.RED_MUSHROOM, Kits.TOAD));
		inv.setItem(13, getKitsInvItem(Material.BLAZE_POWDER, Kits.PYROMANE));
		inv.setItem(15, getKitsInvItem(Material.MONSTER_EGG, Kits.LG));
		inv.setItem(21, getKitsInvItem(Material.POTION, Kits.SORCIERE));
		inv.setItem(23, getKitsInvItem(Material.GOLDEN_APPLE, Kits.HEALER));
		inv.setItem(29, getKitsInvItem(Material.DIAMOND_ORE, Kits.XRAYEUR));
		inv.setItem(31, getKitsInvItem(Material.COOKIE, Kits.BISCUIT));
		inv.setItem(33, getKitsInvItem(Material.GOLD_INGOT, Kits.MACRON));
		inv.setItem(39, getKitsInvItem(Material.BOW, Kits.MILITAIRE));
		inv.setItem(41, getKitsInvItem(Material.TNT, Kits.ALLAHU_AKBAR));
		inv.setItem(49, main.getItem("§c§lRandomizer", Arrays.asList("§fRépartit aléatoirement", "§fà tous les joueurs dans une team", "§fun kit aléatoire."), Material.BEDROCK, (short)0));
		return inv;
	}
	
	private Inventory getJoueursInv() {
		Inventory inv = null;

		int nb = Bukkit.getOnlinePlayers().size();
		if (nb <= 4)
			inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§c§lConfiguration §6Joueurs");
		else if (nb <= 8 && nb > 4)
			inv = Bukkit.createInventory(null, 9, "§c§lConfiguration §6Joueurs");
		else if (nb <= 17 && nb > 8)
			inv = Bukkit.createInventory(null, 18, "§c§lConfiguration §6Joueurs");
		else if (nb <= 26 && nb > 17)
			inv = Bukkit.createInventory(null, 27, "§c§lConfiguration §6Joueurs");
		else if (nb <= 35 && nb > 26)
			inv = Bukkit.createInventory(null, 36, "§c§lConfiguration §6Joueurs");
		else if (nb <= 44 && nb > 35)
			inv = Bukkit.createInventory(null, 45, "§c§lConfiguration §6Joueurs");
		else if (nb <= 53 && nb > 44)
			inv = Bukkit.createInventory(null, 54, "§c§lConfiguration §6Joueurs");
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			PlayerKits pk = main.playerkits.get(p.getUniqueId());
			ItemStack phead = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
			SkullMeta pheadmeta = (SkullMeta) phead.getItemMeta();
			pheadmeta.setOwner(p.getName());
			pheadmeta.setDisplayName(p.getDisplayName());
			
			List<String> lores = new ArrayList<String>();
			String team = "§fTeam : §cAucune";
			if (!pk.getTeam().equals(Teams.NONE))
				team = "§fTeam : " + pk.getTeam().getColor() + "§l" + pk.getTeam().getAdjectiveName();
			lores.add(team);
			String kit = "§fKit : §cAucun";
			if (pk.getKit() != null)
				kit = "§fKit : " + pk.getKit().getName();
			lores.add(kit);
			String spec = "§7Spectateur : §cNon";
			if (main.spectators.contains(p))
				spec = "§7Spectateur : §aOui";
			lores.add("");
			lores.add(spec);
			String op = "§aPossède §caucun grade";
			for (Entry<String, List<UUID>> en : main.getGrades().entrySet())
				if (en.getValue().contains(p.getUniqueId())) {
					String g = en.getKey();
					String gradeplace = "ABCDE";
					if (g.equalsIgnoreCase("Dieu")) gradeplace = "A";
					if (g.equalsIgnoreCase("DieuM")) gradeplace = "B";
					if (g.equalsIgnoreCase("DieuX")) gradeplace = "C";
					if (g.equalsIgnoreCase("DieuE")) gradeplace = "D";
					if (g.equalsIgnoreCase("Démon")) gradeplace = "E";
					if (g.equalsIgnoreCase("Leader")) gradeplace = "F";
					op = "§aPossède le grade " + Bukkit.getScoreboardManager().getMainScoreboard().getTeam("A" + gradeplace + g).getPrefix();
				}
			lores.add(op);
			
			pheadmeta.setLore(lores);
			
			phead.setItemMeta(pheadmeta);
			inv.addItem(phead);
		}
		inv.setItem(inv.getSize() - 1, getFlècheRetour());
		return inv;
	}
	
	private Inventory getConfigJoueurInv(Player target) {
		PlayerKits targetkits = main.playerkits.get(target.getUniqueId());
		Inventory inv = null;
		if (target.getDisplayName().length() <= (32 - 8))
		 inv = Bukkit.createInventory(null, 45, "§6§lMenu " + target.getDisplayName());
		else
			inv = Bukkit.createInventory(null, 45, "§6§lMenu §b" + target.getName());
		setInvCoin(inv, (byte)1);
		
		ItemStack phead = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		SkullMeta pheadmeta = (SkullMeta) phead.getItemMeta();
		List<String> lores = new ArrayList<String>();
		pheadmeta.setOwner(target.getName());
		pheadmeta.setDisplayName(target.getDisplayName());
		String spec = "§7Spectateur : §cNon";
		if (main.spectators.contains(target))
			spec = "§7Spectateur : §aOui";
		lores.add("");
		lores.add(spec);
		String op = "§aPossède §caucun grade";
		for (Entry<String, List<UUID>> en : main.getGrades().entrySet())
			if (en.getValue().contains(target.getUniqueId())) {
				String g = en.getKey();
				String gradeplace = "ABCDE";
				if (g.equalsIgnoreCase("Dieu")) gradeplace = "A";
				if (g.equalsIgnoreCase("DieuM")) gradeplace = "B";
				if (g.equalsIgnoreCase("DieuX")) gradeplace = "C";
				if (g.equalsIgnoreCase("DieuE")) gradeplace = "D";
				if (g.equalsIgnoreCase("Démon")) gradeplace = "E";
				if (g.equalsIgnoreCase("Leader")) gradeplace = "F";
				op = "§aPossède le grade " + Bukkit.getScoreboardManager().getMainScoreboard().getTeam("A" + gradeplace + g).getPrefix();
			}
		lores.add(op);
		
		pheadmeta.setLore(lores);
		phead.setItemMeta(pheadmeta);
		inv.setItem(3, phead);
		
		String team = "§fTeam : §cAucune";
		if (!targetkits.getTeam().equals(Teams.NONE))
			team = "§fTeam : " + targetkits.getTeam().getColor() + "§l" + targetkits.getTeam().getAdjectiveName();
		inv.setItem(4, main.getItem(team, null, Material.WOOL, (short)7));
		
		String kit = "§fKit : §cAucun";
		if (targetkits.getKit() != null)
			kit = "§fKit : " + targetkits.getKit().getName();
		inv.setItem(5, main.getItem(kit, null, Material.ANVIL, (short)0));
			
		inv.setItem(20, main.getItem("§f§lChanger le joueur de §e§lteam", Arrays.asList("", team), Material.WOOL, (short)8));
		
		inv.setItem(22, main.getItem("§f§lChanger le §6§lkit§f§l du joueur", Arrays.asList("", kit), Material.NETHER_STAR, (short)0));
		
		inv.setItem(24, main.getItem("§7§lMettre le joueur en spectateur", Arrays.asList("", spec), Material.SUGAR, (short)0));
		
		ItemStack r = new ItemStack(Material.BEDROCK);
		ItemMeta rm = r.getItemMeta();
		rm.setDisplayName("§c§lRandomizer");
		rm.setLore(Arrays.asList("§fMettre aléatoirement", "§fle joueur dans une team", "§f§lOU", "§fChoisir aléatoirement le kit du joueur"));
		rm.addEnchant(Enchantment.SILK_TOUCH, 1, true);
		rm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		r.setItemMeta(rm);
		inv.setItem(40, r);
		
		inv.setItem(44, getFlècheRetour());
		return inv;
	}
	
	
	private void setInvCoin(Inventory inv, short color) {
		ItemStack verre = new ItemStack(Material.STAINED_GLASS_PANE, 1, color);
		int slot1 = 0;
		int slot2 = 8;
		int slot3 = inv.getSize() - 9;
		int slot4 = inv.getSize() - 1;
		inv.setItem((slot1), verre);
		inv.setItem((slot1 + 9), verre);
		inv.setItem((slot1 + 1), verre);
		inv.setItem((slot2), verre);
		inv.setItem((slot2 + 9), verre);
		inv.setItem((slot2 - 1), verre);
		inv.setItem((slot3), verre);
		inv.setItem((slot3 - 9), verre);
		inv.setItem((slot3 + 1), verre);
		inv.setItem((slot4), verre);
		inv.setItem((slot4 - 9), verre);
		inv.setItem((slot4 - 1), verre);
	}
	
	private ItemStack getFlècheRetour() {
		ItemStack it = new ItemStack(Material.ARROW);
		ItemMeta itm = it.getItemMeta();
		itm.setDisplayName("§cRetour");
		itm.setLore(Arrays.asList("§fRetourner au", "§fmenu précédent."));
		it.setItemMeta(itm);
		return it;
	}
	
}
