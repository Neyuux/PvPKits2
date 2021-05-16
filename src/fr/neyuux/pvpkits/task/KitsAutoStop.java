package fr.neyuux.pvpkits.task;

import fr.neyuux.pvpkits.PvPKits;
import fr.neyuux.pvpkits.PlayerKits;
import fr.neyuux.pvpkits.ScoreboardSign;
import fr.neyuux.pvpkits.enums.Gstate;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;

public class KitsAutoStop extends BukkitRunnable {
	
	private static int timer = 30;
	private final PvPKits main;
	public KitsAutoStop(PvPKits main) {
		this.main = main;
		timer = 30;
	}

	@Override
	public void run() {
		
		if (!main.isState(Gstate.FINISHED))
			cancel();
		
		if (timer == 30)
			Bukkit.broadcastMessage(main.getPrefix() + "§eLa map va se reset dans §c§l" + timer + " §r§csecondes §e!");
		
		if (timer == 27) {
			for (Player p : Bukkit.getOnlinePlayers()) {
			if (main.boards.containsKey(p.getUniqueId()))
				main.boards.get(p.getUniqueId()).destroy();
			
			ScoreboardSign scoreboard = new ScoreboardSign(p, "§4§lKills");
			scoreboard.create();
			int limit = 0;
				
				Comparator<Entry<UUID, Integer>> valueComparator = (e1, e2) -> {
					Integer v1 = e1.getValue();
					Integer v2 = e2.getValue();
					return v1.compareTo(v2);
				};
		        List<Entry<UUID, Integer>> listOfEntries = new ArrayList<>();
		        for (PlayerKits pk : main.playerkits.values())
		        	listOfEntries.add(new SimpleEntry<>(pk.getPlayer().getUniqueId(), pk.getKills()));
		        
		        listOfEntries.sort(valueComparator.reversed());
				for(Entry<UUID, Integer> en : listOfEntries) {
					if (limit <= 15) {
						String name;
						try {
							name = main.getPlayerTeam(Bukkit.getPlayer(en.getKey()), main.playerkits.get(Bukkit.getPlayer(en.getKey()).getUniqueId()).getTeam()).getPrefix() + Bukkit.getPlayer(en.getKey()).getName() + main.getPlayerTeam(Bukkit.getPlayer(en.getKey()), main.playerkits.get(Bukkit.getPlayer(en.getKey()).getUniqueId()).getTeam()).getSuffix();
						} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | NullPointerException e) { name = Bukkit.getPlayer(en.getKey()).getName();}
						if (en.getValue() != 0)scoreboard.setLine(limit, name + " §7: §e" + en.getValue());
					}
					limit++;
				}
				
				main.boards.put(p.getUniqueId(), scoreboard);
			}
		}
		
		if (timer == 15)
			Bukkit.broadcastMessage(main.getPrefix() + "§eLa map va se reset dans §c§l" + timer + " §r§csecondes §e!");
		
		if (timer == 10)
			Bukkit.broadcastMessage(main.getPrefix() + "§eLa map va se reset dans §c§l" + timer + " §r§csecondes §e!");
		
		if (timer <= 5 && timer > 1)
			Bukkit.broadcastMessage(main.getPrefix() + "§eLa map va se reset dans §c§l" + timer + " §r§csecondes §e!");
		
		if (timer == 1)
			Bukkit.broadcastMessage(main.getPrefix() + "§eLa map va se reset dans §c§l" + timer + " §r§cseconde §e!");
		
		
		if (timer == 0) {
			for (Entity entity : Bukkit.getWorld("PvPKits").getEntities())
                if (entity instanceof Arrow)
                    entity.remove();
                else if (entity instanceof Zombie)
                	entity.remove();
                else if (entity instanceof TNTPrimed)
                	entity.remove();
			main.rel();
			cancel();
		}
		
		timer--;
	}

}
