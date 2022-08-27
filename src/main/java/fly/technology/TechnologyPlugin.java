package fly.technology;

import fly.metals.MetalsPlugin;
import fly.newmod.NewMod;
import fly.technology.blocks.data.EnergyHolderBlockDataImpl;
import fly.technology.items.BeheadingSword;
import fly.technology.setup.TechnologyAddonSetup;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class TechnologyPlugin extends NewMod.ModExtension {
    private static TechnologyPlugin INSTANCE = null;

    public TechnologyPlugin() {
        INSTANCE = this;
    }

    public static TechnologyPlugin get() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        new BeheadingSword();

        System.out.println("onebabel");

        //Bukkit.getPluginManager().registerEvents(TechnologyAddonSetup.POWERED_MINECART, this);
    }

    @Override
    public void load() {
        TechnologyAddonSetup.init();

        new EnergyHolderBlockDataImpl.EnergyHoldBlockDataSerializer();
    }

    @Override
    public List<NewMod.ModExtension> requirements() {
        List<NewMod.ModExtension> r = new ArrayList<>();

        r.add(MetalsPlugin.get());

        return r;
    }
}
