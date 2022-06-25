package fly.technology;

import fly.newmod.NewMod;
import fly.technology.blocks.data.EnergyHolderBlockDataImpl;
import fly.technology.setup.TechnologyAddonSetup;

public class TechnologyPlugin extends NewMod.ModExtension {
    private static TechnologyPlugin INSTANCE = null;

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
