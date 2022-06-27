package fly.technology.blocks;

import fly.metals.setup.MetalsAddonSetup;
import fly.newmod.NewMod;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.item.ModItemStack;
import fly.newmod.api.item.type.ModItemType;
import fly.technology.TechnologyPlugin;
import fly.technology.setup.TechnologyAddonSetup;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class ThinCableItem extends ModItemType {
    public ThinCableItem() {
        super(Material.END_ROD, new NamespacedKey(TechnologyPlugin.get(), "thin_cable"));

        name("Thin Cable", 0xFFFFC0);

        setBlock(new ModBlockType(Material.END_ROD, getId()));

        NewMod.get().getItemManager().registerItem(this);
        NewMod.get().getBlockManager().registerBlock(getBlock());

        ShapedRecipe recipe = new ShapedRecipe(getId(), new ModItemStack(this).create());

        recipe.shape("FFF", "CRC", "FWF");

        recipe.setIngredient('F', new ModItemStack(MetalsAddonSetup.HARD_CARBON_CHUNK).create());
        recipe.setIngredient('C', Material.COPPER_INGOT);
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('W', Material.WHITE_WOOL);

        Bukkit.addRecipe(recipe);
    }
}
