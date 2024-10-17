package pl.stellarcode.stellarcodeTab.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import pl.stellarcode.stellarcodeTab.main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class tab implements CommandExecutor {
    private final main plugin;
    public tab(main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tab")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Zle uzycie komendy");
                return false;
            }
            if (args[0].equals("reload")) {
                if (sender.hasPermission("tab.reload")) {
                    plugin.reloadConfig();
                    plugin.zdobadzWiadomosci();
                    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

                    for (Team team : scoreboard.getTeams()) {
                        team.unregister();
                    }

                    plugin.updateTab();
                    sender.sendMessage(ChatColor.GREEN + "Przeladowano config!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Nie posiadasz permisji");

                }
            } else {
                sender.sendMessage(ChatColor.RED + "Zle uzycie komendy");
            }
        }
        return false;

    }
}
