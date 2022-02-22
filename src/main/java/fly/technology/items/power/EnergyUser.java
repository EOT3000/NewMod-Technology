package fly.technology.items.power;

import fly.newmod.bases.ModItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public abstract class EnergyUser extends ModItem {
    public EnergyUser(Material material, String name, String id) {
        super(material, name, id);
    }

    public EnergyUser(Material material, Component name, String id) {
        super(material, name, id);
    }
}
