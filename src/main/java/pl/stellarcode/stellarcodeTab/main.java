package pl.stellarcode.stellarcodeTab;
import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.LuckPerms;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pl.stellarcode.stellarcodeTab.commands.tab;

import java.util.List;
import java.util.logging.Logger;

public final class main extends JavaPlugin implements Listener {


    String header = "";
    String footer = "";

    Boolean placeholderapi = false;
    Boolean luckperms = false;

    public Logger log = Bukkit.getLogger();

    @Override
    public void onEnable() {

        Metrics metrics = new Metrics(this, 0); // bStats

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) { // Sprawdz czy jest PAPI
           placeholderapi = false;

        } else {
            placeholderapi = true;
        }

        if (Bukkit.getPluginManager().getPlugin("LuckPerms") == null) {
            luckperms = false;
        } else {
            luckperms = true;
        }

        getCommand("tab").setExecutor(new tab(this)); // Rejestrowanie komendy
        getServer().getPluginManager().registerEvents(this, this); // Rejestrowanie Eventu
        saveDefaultConfig(); // Config
        zdobadzWiadomosci();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::updateTab, 0L, 20L);
        log.info("\n\n StellarCode.pl (TAB) \n\n Dziekuje za uzywanie pluginu!\n");

    }


    @EventHandler
    public void onPlayerjoin(PlayerJoinEvent event) {
        updateTab();
        if (luckperms) {
            sortTabList.sortPlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        updateTab();
    }

    public void zdobadzWiadomosci() {
        FileConfiguration config = this.getConfig();

        List headerList = getConfig().getStringList("TAB.wiadomosci.header");
        header = String.join("\n", headerList);
        header = hex.hex(header);

        List footerList = getConfig().getStringList("TAB.wiadomosci.footer");
        footer = String.join("\n", footerList);
        footer = hex.hex(footer);

    }


    public void updateTab() {
        for (Player p : Bukkit.getOnlinePlayers()) {

            sortTabList.sortPlayer(p);

            if (placeholderapi) {
                p.setPlayerListHeaderFooter(PlaceholderAPI.setPlaceholders(p, header), PlaceholderAPI.setPlaceholders(p, footer));
            } else {
                p.setPlayerListHeaderFooter(header, footer);
            }
        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
