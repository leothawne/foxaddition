package zoruafan.foxaddition.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import zoruafan.foxaddition.FoxAdditionAPI;

public class GeyserManager extends FoxPlayer {
    FoxAdditionAPI api = FoxAdditionAPI.INSTANCE;
    JavaPlugin plugin = api.getPlugin();
    private Boolean floodgate = cF();
    
    public GeyserManager() { this.floodgate = cF(); }
    
    public boolean iB(Player e) {
        boolean isBedrock = false;
        this.floodgate = cF();
        ConfigurationSection l = api.getFiles().getST().getConfigurationSection("bedrock.modules");
        if (l.getBoolean("prefix.enable", false)) {
            isBedrock = e.getName().startsWith(l.getString("prefix.value", "."));
            if (isBedrock) return true;
        }
        if (l.getBoolean("uuid.enable", true)) {
            isBedrock = e.getUniqueId().toString().startsWith("000000");
            if (isBedrock) return true;
        }
        if (l.getBoolean("brand.enable", false)) {
            isBedrock = this.player_bedrock;
            if (player_bedrock) return true;
        }
        if(floodgate == true && l.getBoolean("floodgate.enable", true)) {
            try { isBedrock = Floodgate.INSTANCE.isBedrockUser(e); if (isBedrock) return true; } catch (Exception ignored) {}
        }
        return isBedrock;
    }
    
    public String getDevice(Player e) {
        String de = "Java";
        ConfigurationSection l = api.getFiles().getST().getConfigurationSection("bedrock.modules.floodgate");
        if(floodgate == true && (l.getBoolean("device", true) && l.getBoolean("enable", true))) {
            try { de = Floodgate.INSTANCE.getDevice(e); return de; } catch (Exception ignored) {}
        }
        return de;
    }

    private Boolean cF() {
        return Bukkit.getPluginManager().isPluginEnabled("Floodgate");
    }
}