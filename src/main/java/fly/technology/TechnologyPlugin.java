package fly.technology;

import fly.metals.MetalsPlugin;
import fly.newmod.NewMod;
import fly.technology.setup.TechnologyAddonSetup;

import java.util.ArrayList;
import java.util.List;

public class TechnologyPlugin extends NewMod.ModExtension {
    @Override
    public void load() {
        TechnologyAddonSetup.init();
    }

    @Override
    public List<NewMod.ModExtension> requirements() {
        ArrayList<NewMod.ModExtension> list = new ArrayList<>();

        list.add(MetalsPlugin.getPlugin(MetalsPlugin.class));

        return list;
    }
}
