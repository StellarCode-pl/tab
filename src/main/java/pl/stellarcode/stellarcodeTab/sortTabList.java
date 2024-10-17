package pl.stellarcode.stellarcodeTab;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import java.util.List;
public class sortTabList {

    private final main plugin;

    public sortTabList(main plugin) {
        this.plugin = plugin;
    }



    public static void sortPlayer(Player p) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        String playerGroup = getPlayerGroup(p);

        Team team = scoreboard.getTeam(getGroupPriority(playerGroup) + playerGroup);
        if (team == null) {
            team = scoreboard.registerNewTeam(getGroupPriority(playerGroup) + playerGroup);
        }

        team.addEntry(p.getName());
        p.setPlayerListName(hex.hex(getRankPrefix(p) + p.getName()));


    }

    public static String getRankPrefix(Player player) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
        String prefix = user.getCachedData().getMetaData().getPrefix();

        if (prefix == null) {
            return "";
        }
        return prefix;
    }

    private static String getPlayerGroup(Player player) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
        return user.getPrimaryGroup();
    }

    private static int getGroupPriority(String groupName) {
        List<String> grupy = main.getPlugin(main.class).getConfig().getStringList("sortowanie.grupy");

        int priority = grupy.indexOf(groupName.toLowerCase());
        if (priority != -1) {
            return priority;
        } else {
            return grupy.size();
        }
    }

}


