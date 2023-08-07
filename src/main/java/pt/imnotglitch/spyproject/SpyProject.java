package pt.imnotglitch.spyproject;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import pt.imnotglitch.spyproject.listeners.PlayerEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class SpyProject extends Plugin {

    private static SpyProject instance;
    private Configuration config;

    @Override
    public void onEnable() {
        instance = this;
        reloadConfig();
        getProxy().getPluginManager().registerListener(this, new PlayerEvent(this));

        getLogger().info(ChatColor.YELLOW + "                                             ");
        getLogger().info(ChatColor.YELLOW + "       _____         _____           _         _       ");
        getLogger().info(ChatColor.YELLOW + "      |   __|___ _ _|  _  |___ ___  |_|___ ___| |_     ");
        getLogger().info(ChatColor.YELLOW + "      |__   | . | | |   __|  _| . | | | -_|  _|  _|    ");
        getLogger().info(ChatColor.YELLOW + "      |_____|  _|_  |__|  |_| |___|_| |___|___|_|      ");
        getLogger().info(ChatColor.YELLOW + "            |_| |___|             |___|            ");
        getLogger().info(ChatColor.YELLOW + "                                             ");
        getLogger().info(ChatColor.YELLOW + "         SpyProject loaded with successfully       ");
        getLogger().info(ChatColor.YELLOW + "                                             ");


    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "                                             ");
        getLogger().info(ChatColor.RED + "       _____         _____           _         _       ");
        getLogger().info(ChatColor.RED + "      |   __|___ _ _|  _  |___ ___  |_|___ ___| |_     ");
        getLogger().info(ChatColor.RED + "      |__   | . | | |   __|  _| . | | | -_|  _|  _|    ");
        getLogger().info(ChatColor.RED + "      |_____|  _|_  |__|  |_| |___|_| |___|___|_|      ");
        getLogger().info(ChatColor.RED + "            |_| |___|             |___|            ");
        getLogger().info(ChatColor.RED + "                                             ");
        getLogger().info(ChatColor.RED + "          SpyProject stopped with successfully   ");
        getLogger().info(ChatColor.RED + "                                             ");

    }

    public static SpyProject getInstance() {
        return instance;
    }

    public Configuration getConfig() {
        return config;
    }

    public void reloadConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
