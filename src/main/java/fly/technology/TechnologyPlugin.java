package fly.technology;

import fly.newmod.NewMod;
import fly.technology.blocks.data.EnergyHolderBlockDataImpl;
import fly.technology.setup.TechnologyAddonSetup;
import org.bukkit.plugin.java.JavaPlugin;

public class TechnologyPlugin extends NewMod.ModExtension {
    private static TechnologyPlugin INSTANCE = new TechnologyPlugin();

    public TechnologyPlugin() {
        INSTANCE = this;
    }

    public static TechnologyPlugin get() {
        return INSTANCE;
    }

    @Override
    public void load() {
        TechnologyAddonSetup.init();

        new EnergyHolderBlockDataImpl.EnergyHoldBlockDataSerializer();
    }
}
