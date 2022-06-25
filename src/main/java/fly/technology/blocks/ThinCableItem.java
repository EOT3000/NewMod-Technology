package fly.technology.blocks;

import fly.newmod.NewMod;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.item.type.ModItemType;
import fly.technology.TechnologyPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class ThinCableItem extends ModItemType {
    public ThinCableItem() {
        super(Material.END_ROD, new NamespacedKey(TechnologyPlugin.get(), "thin_cable"));

        name("Thin Cable", 0xFFFFC0);

        setBlock(new ModBlockType(Material.END_ROD, getId()));

        NewMod.get().getItemManager().registerItem(this);
        NewMod.get().getBlockManager().registerBlock(getBlock());
    }
}
