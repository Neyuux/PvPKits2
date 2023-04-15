package fr.neyuux.pvpkits.refont;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Properties;
import java.util.logging.Level;

public class PvPKits extends JavaPlugin {

    private static final String PREFIX = "§b§lPvPKits §8§l» §r";

    private static PvPKits INSTANCE;


    private GameKits game;


    public static String getPrefix() {
        return PREFIX;
    }

    public static PvPKits getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {

        if (System.getProperties().containsKey("RELOAD")) {
            if (System.getProperty("RELOAD").equals("TRUE"))
                return;
        } else {
            Properties prop = new Properties(System.getProperties());
            prop.put("RELOAD", "FALSE");
        }

        Bukkit.getLogger().log(Level.INFO, "PvPKits enabling");

        INSTANCE = this;

        super.onEnable();
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.INFO, "LG disabling");
        super.onDisable();
    }


    public GameKits getGame() {
        return game;
    }
}
