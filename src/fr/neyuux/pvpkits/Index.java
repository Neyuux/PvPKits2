package fr.neyuux.pvpkits;

import fr.neyuux.pvpkits.PlayerKits.CSState;
import fr.neyuux.pvpkits.PlayerKits.Retour;
import fr.neyuux.pvpkits.commands.CommandTeam;
import fr.neyuux.pvpkits.commands.SpecExecutor;
import fr.neyuux.pvpkits.commands.StartExecutor;
import fr.neyuux.pvpkits.enums.Gstate;
import fr.neyuux.pvpkits.enums.Kits;
import fr.neyuux.pvpkits.enums.Teams;
import fr.neyuux.pvpkits.listener.KitsDamageListener;
import fr.neyuux.pvpkits.listener.KitsListener;
import fr.neyuux.pvpkits.task.KitsAutoStart;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import org.bukkit.Material;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

public class Index extends JavaPlugin {
	
	public List<UUID> players = new ArrayList<>();
	public List<Player> spectators = new ArrayList<>();
	public HashMap<UUID, PlayerKits> playerkits = new HashMap<>();
	public List<Block> blocknomap = new ArrayList<>();
	private Gstate state;
	private static final String prefix = "§e[§4§lP§b§lv§4§lP §9§lKits§r§e]§r";
	public Map<UUID, ScoreboardSign> boards = new HashMap<>();
	public static final HashMap<String, List<UUID>> Grades = new HashMap<>();
	
	private GameConfig config = new GameConfig(this);
	public static Index instance;
	
	public String getPrefix() {
		return prefix;
	}

	public Gstate getState() {
		return this.state;
	}
	
	public boolean isState(Gstate state) {
		return this.state.equals(state);
	}
	
	public void setState(Gstate state) {
		this.state = state;
	}
	
	
	public HashMap<String, List<UUID>> getGrades() {
		return Grades;
	}
	
	public GameConfig getGameConfig() {
		return config;
	}
	
	public void setGameConfig(GameConfig config) {
		this.config = config;
	}
	
	
	@Override
	public void onEnable() {
		if (!System.getProperties().containsKey("RELOAD")) {
			Properties prop = new Properties(System.getProperties());
			prop.put("RELOAD", "FALSE");
		} else
			if (System.getProperty("RELOAD").equals("TRUE"))
				return;
		
		instance = this;
		
		System.out.println("PvPKits enabling");
		setState(Gstate.PREPARING);
		getCommand("team").setExecutor(new CommandTeam(this));
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(config, this);
		pm.registerEvents(new KitsListener(this), this);
		pm.registerEvents(new KitsDamageListener(this), this);
		pm.registerEvents(new SpecExecutor(this), this);
		pm.registerEvents(new StartExecutor(this), this);
		reloadScoreboard();
		rel();

		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		System.out.println("PvPKits disabling");
		
		super.onDisable();
	}
	
	
	public ItemStack getItem(String customName, List<String> lore, Material material, short data) {
		ItemStack it = new ItemStack(material, 1, data);
		ItemMeta itM = it.getItemMeta();
		itM.setDisplayName(customName);
		if (lore != null) itM.setLore(lore);
		it.setItemMeta(itM);		
		return it;
	}
	
	public void clearArmor(Player player) {
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.updateInventory();
	}
	
	
	public void replaceBush(Player player, ItemStack now) {
		ItemStack it = player.getInventory().getItem(player.getInventory().first(Material.DEAD_BUSH));
		if (it != null) it.setItemMeta(now.getItemMeta());
	}
	
	
	public void updateGrades() {
		List<UUID> Dieux = new ArrayList<>();
		List<UUID> DieuxM = new ArrayList<>();
		List<UUID> DieuxX = new ArrayList<>();
		List<UUID> DieuxE = new ArrayList<>();
		List<UUID> Demons = new ArrayList<>();
		List<UUID> Leaders = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers())  {
		if (p.getUniqueId().toString().equals("0234db8c-e6e5-45e5-8709-ea079fa575bb")) {
			Dieux.add(p.getUniqueId());
		}
		if (p.getUniqueId().toString().equals("a9198cde-e7b0-407e-9b52-b17478e17f90")) {
			DieuxM.add(p.getUniqueId());
		}
		if (p.getUniqueId().toString().equals("290d1443-a362-4f79-b616-893bfb1361e5")) {
			DieuxX.add(p.getUniqueId());
		}
		
		if (p.getUniqueId().toString().equals("9a4d5447-13e0-43a3-87af-977ba87e77a7") || p.getUniqueId().toString().equals("cb067197-d121-4bfc-ac47-d6b4e40841b2")) {
			DieuxE.add(p.getUniqueId());
		}
												//Goteix																			//OffColor															//Newiland																			//Kaul																		//Davyre																	//Gwen / TryHardeuse													Eternel
		if (p.getUniqueId().toString().equals("ac5ec348-baf0-4ad9-b8d2-e5fc817414f7") || p.getUniqueId().toString().equals("41ba0eb8-5d55-4aaa-9a57-b6cd9ee6e301") || p.getUniqueId().toString().equals("ada86697-6253-4ccf-9121-9e19c5288cf1") || p.getUniqueId().toString().equals("e08c6ffd-f577-422d-9dcd-a8d22c20be97") || p.getUniqueId().toString().equals("9c9e3477-d951-431b-ac11-344ec8a5a919") || p.getUniqueId().toString().equals("6a29cae9-c3f0-4e9d-8249-90227a1c669a") || p.getUniqueId().toString().equals("3a8ed81d-d284-4b1c-86b9-d835c27b2a33")) {
			Demons.add(p.getUniqueId());
		}

		if (p.isOp() && !Dieux.contains(p.getUniqueId()) && !DieuxM.contains(p.getUniqueId()) && !DieuxX.contains(p.getUniqueId()) && !DieuxE.contains(p.getUniqueId()) && !Demons.contains(p.getUniqueId())) {
			Leaders.add(p.getUniqueId());
		}
		}
		Grades.put("Dieu", Dieux);
		Grades.put("DieuM", DieuxM);
		Grades.put("DieuX", DieuxX);
		Grades.put("DieuE", DieuxE);
		Grades.put("Démon", Demons);
		Grades.put("Leader", Leaders);
	}
	
	public void setPlayerTeamFromGrade(Player player, Teams te) {
		Scoreboard s = Bukkit.getScoreboardManager().getMainScoreboard();
		
		if (s.getEntryTeam(player.getName()) != null) s.getEntryTeam(player.getName()).removeEntry(player.getName());
		for (Entry<String, List<UUID>> en : getGrades().entrySet()) {
			String g = en.getKey();
			Team grade = null;
			if (g.equalsIgnoreCase("Dieu")) grade = te.getDieuTeam();
			if (g.equalsIgnoreCase("DieuM")) grade = te.getDieuMTeam();
			if (g.equalsIgnoreCase("DieuX")) grade = te.getDieuXTeam();
			if (g.equalsIgnoreCase("DieuE")) grade = te.getDieuETeam();
			if (g.equalsIgnoreCase("Démon")) grade = te.getDemonTeam();
			if (g.equalsIgnoreCase("Leader")) grade = te.getLeaderTeam();
			List<UUID> l = en.getValue();
			if (l.contains(player.getUniqueId())) {
				try {
					grade.addEntry(player.getName());
				} catch (Exception e) {
					reloadScoreboard();
					System.out.println(grade + " §e" + g + " §c" + te + " §d" + te.getTeams());
					System.out.println(player);
					if (g.equalsIgnoreCase("Dieu")) grade = te.getDieuTeam();
					if (g.equalsIgnoreCase("DieuM")) grade = te.getDieuMTeam();
					if (g.equalsIgnoreCase("DieuX")) grade = te.getDieuXTeam();
					if (g.equalsIgnoreCase("DieuE")) grade = te.getDieuETeam();
					if (g.equalsIgnoreCase("Démon")) grade = te.getDemonTeam();
					if (g.equalsIgnoreCase("Leader")) grade = te.getLeaderTeam();
				} finally {
					grade.addEntry(player.getName());
				}
			}
		}
		
		if (s.getEntryTeam(player.getName()) == null) {
			if (te.equals(Teams.NONE))
				s.getTeam("AGJoueur").addEntry(player.getName());
			else
				te.getTeam().addEntry(player.getName());
		}
		
		player.setDisplayName(s.getEntryTeam(player.getName()).getPrefix() + player.getName() + s.getEntryTeam(player.getName()).getSuffix());
		player.setPlayerListName(player.getDisplayName());
		this.playerkits.get(player.getUniqueId()).setTeam(te);
	}
	
	public void addPlayerTeam(Player player, Teams team) {
		if (team.equals(Teams.NONE)) throw new IllegalArgumentException("On peut pas rejoindre la team none");
		
		if (config.getTeamsOff().contains(team)) {
			player.sendMessage(getPrefix() + "§4[§cErreur§4]§c La team " + team.getColor() + "§l" + team.getAdjectiveName() + "§c est désactivée !");
			player.teleport(Teams.NONE.getMainSalle());
		} else {
			this.setPlayerTeamFromGrade(player, team);
		boolean isOp = Boolean.FALSE;
		for (Entry<String,List<UUID>> en : this.getGrades().entrySet())
			if (en.getValue().contains(player.getUniqueId()))
				isOp = true;
		
		player.teleport(team.getMainSalle());
		this.playerkits.get(player.getUniqueId()).setRetour(Retour.SALLE_TEAM);
		if (!isOp)
			Bukkit.broadcastMessage(getPrefix() + team.getColor() + player.getDisplayName() + "§f a rejoint la "+team.getColor()+"§lTeam "+team.getAdjectiveName()+" §r§f!");
		else
			Bukkit.broadcastMessage(getPrefix() + "§r§fLe " + player.getDisplayName() + "§r§f débarque dans la "+team.getColor()+"§lTeam "+team.getAdjectiveName()+"§r§f !");
		for (Player p : Bukkit.getOnlinePlayers())
			p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 10, 1);
		
		player.getInventory().setHeldItemSlot(0);
		player.getInventory().setItem(8, getItem("§fRetour", null, Material.BED, (short)0));
		player.getInventory().setItem(4, getItem("§6Kits " + team.getColor() + team.getName() + "s", null, Material.CHEST, (short)0));
		player.updateInventory();
		}
	}
	
	public Team getPlayerTeam(Player player, Teams team) {
		Team t = team.getTeam();
		
		for (Entry<String, List<UUID>> en : getGrades().entrySet()) {
			if (en.getValue().contains(player.getUniqueId())) {
			switch (en.getKey()) {
			case "Dieu":
				t = team.getDieuTeam();
				break;
			case "DieuM":
				t = team.getDieuMTeam();
				break;
			case "DieuX":
				t = team.getDieuXTeam();
				break;
			case "DieuE":
				t = team.getDieuETeam();
				break;
			case "Démon":
				t = team.getDemonTeam();
				break;
			case "Leader":
				t = team.getLeaderTeam();
				break;
			}
			}
		}
		
		return t;
	}
	
	public void setPlayerKit(Player player, Kits kit) {
		if (kit == null || player == null) throw new IllegalArgumentException("Le joueur ou le kit ne peut etre nul");
		
		if (!getGameConfig().getKitsOff().contains(kit)) {
			player.getInventory().remove(Material.MAGMA_CREAM);
			player.getInventory().remove(Material.CHEST);
			player.updateInventory();
			this.playerkits.get(player.getUniqueId()).setRetour(Retour.SALLE_ATTENTE);
			player.teleport(new Location(player.getWorld(), -1.539, 5.0, -43.465, -89f, 1f));
			player.playSound(player.getLocation(), Sound.NOTE_PLING, 7, 1);
			if (!this.config.hasHideKits()) {
				Bukkit.broadcastMessage(this.getPrefix() +"§4"+ player.getDisplayName() + "§f a choisi le kit " + kit.getName() + " §f!");
				player.setDisplayName("§8[§r" + kit.getName() + "§8]§r " + getPlayerTeam(player, playerkits.get(player.getUniqueId()).getTeam()).getPrefix() + player.getName() + getPlayerTeam(player, playerkits.get(player.getUniqueId()).getTeam()).getSuffix());
				player.setPlayerListName(player.getDisplayName());
			}
			this.playerkits.get(player.getUniqueId()).setKit(kit);
			this.players.add(player.getUniqueId());
			
			if (players.size() + spectators.size() == Bukkit.getOnlinePlayers().size()) {
				if (!isState(Gstate.PREPARING)) return;
				
				if (players.size() >= 2) {
					if (Teams.getAliveTeams().size() >= 2) {
						setState(Gstate.STARTING);
						KitsAutoStart start = new KitsAutoStart(this);
						start.runTaskTimer(this, 0, 20);
					} else {
						Bukkit.broadcastMessage(prefix + "§4[§cErreur§4] §c Tous les joueurs sont dans la même équipe.");
						this.rel();
						return;
					}
				} else {
					Bukkit.broadcastMessage(prefix + "§4[§cErreur§4] §c Il faut être plus que §62 joueurs§c pour commencer !");
					this.rel();
					return;
				}
			}
			System.out.println(players.size() + " + §e" + spectators.size() + " = §d" + Bukkit.getOnlinePlayers().size());
			System.out.println(players);
			System.out.println("§e" + spectators);
		} else {
			player.sendMessage(getPrefix() + "§cLe kit " + kit.getName() +"§c est §lDésactivé !");
			Teams team = playerkits.get(player.getUniqueId()).getTeam();
			
			player.teleport(team.getMainSalle());
			player.getInventory().setItem(4, getItem("§6Kits " + team.getColor() + team.getName() + "s", null, Material.CHEST, (short)0));
			player.updateInventory();
			playerkits.get(player.getUniqueId()).setRetour(Retour.SALLE_TEAM);
		}
	}
	
	public void setGameScoreboard(Player p) {
    	PlayerKits pkits = playerkits.get(p.getUniqueId());
    	if (pkits.getTeam().equals(Teams.NONE) || pkits.isKit(null) || pkits.getCSState() == null) throw new NullPointerException();
    	Teams team = pkits.getTeam();
    	Kits kit = pkits.getKit();
    	CSState csState = pkits.getCSState();
				
		ScoreboardSign scoreboard = new ScoreboardSign(p, "§4§lP§b§lv§4§lP §9§lKits");
		scoreboard.create();
		scoreboard.setLine(0, getPlayerTeam(p, team).getPrefix() + p.getName() + getPlayerTeam(p, team).getSuffix());
		scoreboard.setLine(1, "§0");
		scoreboard.setLine(2, "Équipe : " + team.getColor() + "§l" + team.getName());
		scoreboard.setLine(3, "Teams : §9§l" + Teams.getAliveTeams().size() + " §7(" + players.size() + ")");
		scoreboard.setLine(4, "§cKills : §l" + pkits.getKills());
		scoreboard.setLine(5, "§5");
		scoreboard.setLine(6, "§eTimer : 00:00");
		scoreboard.setLine(7, "Kit : " + kit.getName());
		scoreboard.setLine(8, "§dCapa.Spé. : " + csState.getScoreboardLine());
		scoreboard.setLine(9, "§9");
		scoreboard.setLine(10, "§8------------");
		scoreboard.setLine(11, "§b§oMap by §c§l§oNeyuux_");
		boards.put(p.getUniqueId(), scoreboard);
    }
	
	public Player getZombieOwner(Zombie z) {
		for (PlayerKits pk : playerkits.values())
			if (pk.getZombiesMacron().contains(z))
				return pk.getPlayer();
		return null;
	}
	
	public void delblocks() {
		for (Block b : blocknomap)
			b.setType(Material.AIR);
		blocknomap.clear();
	}

	public void rel() {
		delblocks();
		Bukkit.getScheduler().cancelTasks(this);

		players.clear();
		playerkits.clear();
		spectators.clear();
		blocknomap.clear();
		
		updateGrades();
		for (Team t : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
			if (!t.getName().startsWith("Kit") && !t.getName().equalsIgnoreCase("Teams")) {
				for(String offp : t.getEntries()) {
					t.removeEntry(offp);
				}
			}
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (boards.containsKey(p.getUniqueId())) {
				boards.get(p.getUniqueId()).destroy();
				boards.remove(p.getUniqueId());
			}
			playerkits.put(p.getUniqueId(), new PlayerKits(this, p));
			
			for (Player p2 : Bukkit.getOnlinePlayers()) {
				p.showPlayer(p2);
			}
			p.teleport(new Location(Bukkit.getWorld("PvPKits"), -6.096, 5.1, -2.486, -89.6f, -0.5f));
			p.getInventory().clear();
			p.updateInventory();
			p.setExp(0f);
			p.setLevel(0);
			p.setMaxHealth(20);
			p.setHealth(20);
			((CraftPlayer) p).getHandle().setAbsorptionHearts(0);
			for (PotionEffect pe : p.getActivePotionEffects()) {
				p.removePotionEffect(pe.getType());
			}
			PotionEffect saturation = new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, true, false);
			p.addPotionEffect(saturation);
			clearArmor(p);
			p.setDisplayName(p.getName());
			p.setPlayerListName(p.getName());
			p.setGameMode(GameMode.ADVENTURE);
			
			p.getInventory().setItem(1, getItem("§7§lDevenir Spectateur", Collections.singletonList("§b>>Clique droit pour utiliser"), Material.GHAST_TEAR, (short)0));
			p.updateInventory();
			
			try {
				Bukkit.getScoreboardManager().getMainScoreboard().getTeam("AGJoueur").addEntry(p.getName());
			} catch (NullPointerException e) {
				reloadScoreboard();
			} finally {
				Bukkit.getScoreboardManager().getMainScoreboard().getTeam("AGJoueur").addEntry(p.getName());
			}
			setPlayerTeamFromGrade(p, Teams.NONE);
		}
		
		
		setState(Gstate.PREPARING);
		config.startConfig();
	}
	
	
	private static void reloadScoreboard() {
		Scoreboard s = Bukkit.getScoreboardManager().getMainScoreboard();
		for (Team t : s.getTeams()) {
			if (!t.getDisplayName().startsWith("Kits")) {
				t.unregister();
			}
		}
		for (Objective ob : s.getObjectives()) {
				ob.unregister();
		}
		
		List<String> tc = new ArrayList<String>();
		tc.add("Rouge"); //F
		tc.add("Bleu");//C
		tc.add("Vert");//D
		tc.add("Rose");//E
		tc.add("Jaune");//G
		tc.add("Noir");//B
		tc.add("");
		
		if (!s.getTeams().isEmpty() && tc.size() != 7) while (!s.getTeams().isEmpty()) System.out.println("slt");
		else System.out.println(tc + " " + s.getTeams());
		
		for (String st : tc) {
			String tabplace = "nulltabplace";
			if (st.equalsIgnoreCase("Rouge")) tabplace = "F";
			if (st.equalsIgnoreCase("Bleu")) tabplace = "C";
			if (st.equalsIgnoreCase("Vert")) tabplace = "D";
			if (st.equalsIgnoreCase("Rose")) tabplace = "E";
			if (st.equalsIgnoreCase("Jaune")) tabplace = "G";
			if (st.equalsIgnoreCase("Noir")) tabplace = "B";
			if (st.equalsIgnoreCase("")) tabplace = "A";
			
			String color = "\u00A7f";
			if (st.equalsIgnoreCase("Rouge")) color = "\u00A74";
			if (st.equalsIgnoreCase("Bleu")) color = "\u00A71";
			if (st.equalsIgnoreCase("Vert")) color = "\u00A7a";
			if (st.equalsIgnoreCase("Rose")) color = "\u00A7d";
			if (st.equalsIgnoreCase("Jaune")) color = "\u00A7e";
			if (st.equalsIgnoreCase("Noir")) color = "\u00A78";
			
			s.registerNewTeam(tabplace + "ADieu" + st);
			s.getTeam(tabplace + "ADieu" + st).setDisplayName("Dieu" + st);
			s.getTeam(tabplace + "ADieu" + st).setPrefix("\u00A7c\u00A7lDieu. " + color + "\u00A7l");
			s.getTeam(tabplace + "ADieu" + st).setSuffix("\u00A7d\u00A7k\u00A7laa\u00A7r");
			
			s.registerNewTeam(tabplace + "BDieuM" + st);
			s.getTeam(tabplace + "BDieuM" + st).setDisplayName("DieuM" + st);
			s.getTeam(tabplace + "BDieuM" + st).setPrefix("\u00A75\u00A7lDieu. " + color + "\u00A7l");
			s.getTeam(tabplace + "BDieuM" + st).setSuffix("\u00A76\u00A7k\u00A7laa\u00A7r");
			
			s.registerNewTeam(tabplace + "CDieuX" + st);
			s.getTeam(tabplace + "CDieuX" + st).setDisplayName("DieuX" + st);
			s.getTeam(tabplace + "CDieuX" + st).setPrefix("\u00A76\u00A7lDieu. " + color + "\u00A7l");
			s.getTeam(tabplace + "CDieuX" + st).setSuffix("\u00A75\u00A7k\u00A7laa\u00A7r");
			
			s.registerNewTeam(tabplace + "DDieuE" + st);
			s.getTeam(tabplace + "DDieuE" + st).setDisplayName("DieuE" + st);
			s.getTeam(tabplace + "DDieuE" + st).setPrefix("\u00A73\u00A7lDieu. " + color + "\u00A7l");
			s.getTeam(tabplace + "DDieuE" + st).setSuffix("\u00A70\u00A7k\u00A7laa\u00A7r");
			
			s.registerNewTeam(tabplace + "EDémon" + st);
			s.getTeam(tabplace + "EDémon" + st).setDisplayName("Démon" + st);
			s.getTeam(tabplace + "EDémon" + st).setPrefix("\u00A7b\u00A7lDémon. " + color + "\u00A7l");
			s.getTeam(tabplace + "EDémon" + st).setSuffix("\u00A7c\u00A7k\u00A7laa\u00A7r");
			
			s.registerNewTeam(tabplace + "FLeader" + st);
			s.getTeam(tabplace + "FLeader" + st).setDisplayName("Leader" + st);
			s.getTeam(tabplace + "FLeader" + st).setPrefix("\u00A72Leader. " + color + "\u00A7l");
			s.getTeam(tabplace + "FLeader" + st).setSuffix("\u00A70\u00A7kaa\u00A7r");
			
			if(!st.equalsIgnoreCase("")) { 
				s.registerNewTeam(tabplace + "G" + color + st + "\u00A7r"); 
				s.getTeam(tabplace + "G" + color + st + "\u00A7r").setDisplayName(color + st + "\u00A7r");
				s.getTeam(tabplace + "G" + color + st + "\u00A7r").setPrefix(color + st + "\u00A7l ");
				s.getTeam(tabplace + "G" + color + st + "\u00A7r").setSuffix("\u00A7r");
			}
		}
		s.registerNewTeam("AGJoueur");
		s.getTeam("AGJoueur").setDisplayName("Joueur");
		s.registerNewObjective("health", "health");
		s.getObjective("health").setDisplayName("\u00A74\u2764");
		
		Teams.reloadTeams();
	}
	
	
	
	
	public static void sendActionBar(Player p, String message) {
        IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
    }
	
	public static void sendActionBarForAllPlayers(String message) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			sendActionBar(p, message);
		}
	}

	public static void setPlayerTabList(Player player,String header, String footer) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
		try {
			Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, tabFoot);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.sendPacket(headerPacket);
		}
	}
    
    private void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server."
                    + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void sendTitle(Player player, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
        try {
            Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + title + "\"}");
            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            Object packet = titleConstructor.newInstance(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
                    fadeInTime, showTime, fadeOutTime);

            Object chatsTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + subtitle + "\"}");
            Constructor<?> timingTitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            Object timingPacket = timingTitleConstructor.newInstance(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
                    fadeInTime, showTime, fadeOutTime);

            sendPacket(player, packet);
            sendPacket(player, timingPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendTitleForAllPlayers(String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
    	for (Player player : Bukkit.getOnlinePlayers())
    		sendTitle(player, title, subtitle, fadeInTime, showTime, fadeOutTime);
    }

	
}
