package fr.neyuux.pvpkits.enums;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Teams {
	
	RED("Rouge", "Rouge", "§4", "§c", new Location(Bukkit.getWorld("PvPKits"), 58.854, 5.0, -49.787, 0.1f, 2.4f), new Location(Bukkit.getWorld("PvPKits"), -484, 95.5, -266, -90f, 0f), "FG§4Rouge§r", "FADieuRouge", "FBDieuMRouge", "FCDieuXRouge", "FDDieuERouge", "FEDémonRouge", "FFLeaderRouge"),
	BLUE("Bleu", "Bleue", "§1", "§b", new Location(Bukkit.getWorld("PvPKits"), 58.323, 5.01, -32.289, 179.9f, 1.6f), new Location(Bukkit.getWorld("PvPKits"), -557, 95.5, -198, 0f, 0f), "CG§1Bleu§r", "CADieuBleu", "CBDieuMBleu", "CCDieuXBleu", "CDDieuEBleu", "CEDémonBleu", "CFLeaderBleu"),
	GREEN("Vert", "Verte", "§a", "§2", new Location(Bukkit.getWorld("PvPKits"), 58.323, 5.01, -12.289, 179.9f, 1.6f), new Location(Bukkit.getWorld("PvPKits"), -298, 95.5, -268, 90f, 0f), "DG§aVert§r", "DADieuVert", "DBDieuMVert", "DCDieuXVert", "DDDieuEVert", "DEDémonVert", "DFLeaderVert"),
	PINK("Rose", "Rose", "§d", "§5", new Location(Bukkit.getWorld("PvPKits"), 44.094, 5.01, 27.115, 179.9f, 1.6f), new Location(Bukkit.getWorld("PvPKits"), -230, 95.5, -8, 180f, 0f), "EG§dRose§r", "EADieuRose", "EBDieuMRose", "ECDieuXRose", "EDDieuERose", "EEDémonRose", "EFLeaderRose"),
	BLACK("Noir", "Noire", "§8", "§7", new Location(Bukkit.getWorld("PvPKits"), 58.323, 5.01, 27.115, 179.9f, 1.6f), new Location(Bukkit.getWorld("PvPKits"), -232, 95.5, -194, 0f, 0f), "BG§8Noir§r", "BADieuNoir", "BBDieuMNoir", "BCDieuXNoir", "BDDieuENoir", "BEDémonNoir", "BFLeaderNoir"),
	YELLOW("Jaune", "Jaune", "§e", "§6", new Location(Bukkit.getWorld("PvPKits"), 58.716, 5.01, 5.266, 179.1f, 1.6f), new Location(Bukkit.getWorld("PvPKits"), -558, 95.5, -12, 180f, 0f), "GG§eJaune§r", "GADieuJaune", "GBDieuMJaune", "GCDieuXJaune", "GDDieuEJaune", "GEDémonJaune", "GFLeaderJaune"),
	NONE("", "", "", "", new Location(Bukkit.getWorld("PvPKits"), -8, 6, -2), null, "AGJoueur", "AADieu", "ABDieuM", "ACDieuX", "ADDieuE", "AEDémon", "AFLeader");


	Teams(String name, String adjectiveName, String color, String secondColor, Location mainSalle, Location spawnIle, String team, String DieuTeam, String DieuMTeam, String DieuXTeam, String DieuETeam, String DémonTeam, String LeaderTeam) {
		this.name = name;
		this.adjectiveName = adjectiveName;
		this.secondColor = secondColor;
		this.color = color;
		this.mainSalle = mainSalle;
		this.spawnIle = spawnIle;
		this.team = s.getTeam(team);
		this.steam = team;
		this.DieuTeam = s.getTeam(DieuTeam);
		this.sDieuteam = DieuTeam;
		this.DieuMTeam = s.getTeam(DieuMTeam);
		this.sDieuMteam = DieuMTeam;
		this.DieuXTeam = s.getTeam(DieuXTeam);
		this.sDieuXteam = DieuXTeam;
		this.DieuETeam = s.getTeam(DieuETeam);
		this.sDieuEteam = DieuETeam;
		this.DémonTeam = s.getTeam(DémonTeam);
		this.sDemonteam = DémonTeam;
		this.LeaderTeam = s.getTeam(LeaderTeam);
		this.sLeaderteam = LeaderTeam;
	}

	Scoreboard s = Bukkit.getScoreboardManager().getMainScoreboard();

	private final String name;

	private final String adjectiveName;

	private final String color;

	private final String secondColor;

	private Location mainSalle;

	private Location spawnIle;

	private Team team;
	private final String steam;

	private Team DieuTeam;
	private final String sDieuteam;

	private Team DieuMTeam;
	private final String sDieuMteam;

	private Team DieuXTeam;
	private final String sDieuXteam;

	private Team DieuETeam;
	private final String sDieuEteam;

	private Team DémonTeam;
	private final String sDemonteam;

	private Team LeaderTeam;
	private final String sLeaderteam;


	public String getName() {
		return name;
	}

	public String getAdjectiveName() {
		return adjectiveName;
	}

	public String getColor() {
		return color;
	}

	public String getSecondColor() {
		return secondColor;
	}

	public Location getMainSalle() {
		try {
			if (!mainSalle.getChunk().isLoaded()) mainSalle.getChunk().load();
		} catch (Exception e) {
			System.out.println(mainSalle);
			e.printStackTrace();
			reloadLocations();
		} finally {
			if (!mainSalle.getChunk().isLoaded()) mainSalle.getChunk().load();
		}
		return mainSalle;
	}

	public Location getSpawnIle() {
		try {
			if (!spawnIle.getChunk().isLoaded()) spawnIle.getChunk().load();
		} catch (Exception e) {
			System.out.println(spawnIle);
			e.printStackTrace();
			reloadLocations();
		} finally {
			if (spawnIle == null) throw new NullPointerException("SpawnIle est nul sur none");
			if (!spawnIle.getChunk().isLoaded()) spawnIle.getChunk().load();
		}
		return spawnIle;
	}

	public Team getTeam() {
		return team;
	}

	public Team getDieuTeam() {
		return DieuTeam;
	}

	public Team getDieuMTeam() {
		return DieuMTeam;
	}

	public Team getDieuXTeam() {
		return DieuXTeam;
	}

	public Team getDieuETeam() {
		return DieuETeam;
	}

	public Team getDemonTeam() {
		return DémonTeam;
	}

	public Team getLeaderTeam() {
		return LeaderTeam;
	}


	public List<Team> getTeams() {
		_reloadTeams();

		List<Team> list = new ArrayList<>();
		list.add(getTeam());
		list.add(getDieuTeam());
		list.add(getDieuMTeam());
		list.add(getDieuXTeam());
		list.add(getDieuETeam());
		list.add(getDemonTeam());
		list.add(getLeaderTeam());
		return list;
	}

	public List<Player> getPlayers() {
		List<Player> list = new ArrayList<>();
		for (Team team : getTeams()) {
			if (team.getSize() != 0)
				for (String s : team.getEntries())
					if (Bukkit.getPlayer(s) != null)
						list.add(Bukkit.getPlayer(s));
		}
		return list;
	}


	public boolean contains(Player player) {
		boolean b = Boolean.FALSE;

		for (Team t : getTeams())
			if (t.hasEntry(player.getName()))
				b = true;

		return b;
	}



	public static Teams getByColor(String color) {
		Teams t = null;

		for (Teams team : Teams.values())
			if (team.getColor().equals(color))
				t = team;

		return t;
	}

	public static Teams getByName(String name) {
		Teams t = null;

		for (Teams team : Teams.values())
			if (team.getName().equals(name))
				t = team;

		return t;
	}

	public static List<Teams> getAliveTeams() {
		List<Teams> teams = new ArrayList<Teams>();

		for (Teams t : Teams.values())
			if (!t.equals(Teams.NONE))
				if (t.getPlayers().size() != 0)
					teams.add(t);
		return teams;
	}





	private void _reloadTeams() {
		this.team = s.getTeam(steam);
		this.DieuTeam = s.getTeam(sDieuteam);
		this.DieuMTeam = s.getTeam(sDieuMteam);
		this.DieuXTeam = s.getTeam(sDieuXteam);
		this.DieuETeam = s.getTeam(sDieuEteam);
		this.DémonTeam = s.getTeam(sDemonteam);
		this.LeaderTeam = s.getTeam(sLeaderteam);
	}
	
	public static void reloadTeams() {
		for (Teams t : Teams.values())
			t._reloadTeams();
	}
	
	
	private static void reloadLocations() {
		List<Teams> teams = new ArrayList<Teams>();
		Collections.addAll(teams, Teams.values());
		
		for (Teams t : teams)
			switch (t) {
			case BLACK:
				t.mainSalle = new Location(Bukkit.getWorld("PvPKits"), 58.323, 5.01, 27.115, 179.9f, 1.6f);
				t.spawnIle = new Location(Bukkit.getWorld("PvPKits"), -232, 95.5, -194, 0f, 0f);
				break;
			case BLUE:
				t.mainSalle = new Location(Bukkit.getWorld("PvPKits"), 58.323, 5.01, -32.289, 179.9f, 1.6f);
				t.spawnIle = new Location(Bukkit.getWorld("PvPKits"), -557, 95.5, -198, 0f, 0f);
				break;
			case GREEN:
				t.mainSalle = new Location(Bukkit.getWorld("PvPKits"), 58.323, 5.01, -12.289, 179.9f, 1.6f);
				t.spawnIle = new Location(Bukkit.getWorld("PvPKits"), -298, 95.5, -268, 90f, 0f);
				break;
			case NONE:
				t.mainSalle = new Location(Bukkit.getWorld("PvPKits"), -8, 6, -2);
				t.spawnIle = null;
				break;
			case PINK:
				t.mainSalle = new Location(Bukkit.getWorld("PvPKits"), 44.094, 5.01, 27.115, 179.9f, 1.6f);
				t.spawnIle = new Location(Bukkit.getWorld("PvPKits"), -230, 95.5, -8, 180f, 0f);
				break;
			case RED:
				t.mainSalle = new Location(Bukkit.getWorld("PvPKits"), 58.854, 5.0, -49.787, 0.1f, 2.4f);
				t.spawnIle = new Location(Bukkit.getWorld("PvPKits"), -484, 95.5, -266, -90f, 0f);
				break;
			case YELLOW:
				t.mainSalle = new Location(Bukkit.getWorld("PvPKits"), 58.716, 5.01, 5.266, 179.1f, 1.6f);
				t.spawnIle = new Location(Bukkit.getWorld("PvPKits"), -558, 95.5, -12, 180f, 0f);
				break;
			}
	}
}
