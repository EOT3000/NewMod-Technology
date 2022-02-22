package fly.technology.items.power;

import fly.metals.setup.MetalsAddonSetup;
import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import fly.newmod.setup.BlockStorage;
import fly.technology.TechnologyPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class EnergyStorage extends ModItem {
    private int capacity;

    public EnergyStorage(Material material, String name, TextColor color, String id, int capacity, ItemStack battery) {
        super(material, name, color, id);

        this.capacity = capacity;

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(TechnologyPlugin.getPlugin(TechnologyPlugin.class), id), this);

        recipe.shape("ARA", "BBB", "ACA");

        recipe.setIngredient('A', MetalsAddonSetup.ALUMINUM_INGOT);
        recipe.setIngredient('R', new ItemStack(Material.REDSTONE));
        recipe.setIngredient('B', battery);
        recipe.setIngredient('C', new ItemStack(Material.COPPER_INGOT));

        addRecipe(recipe);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCharge(Location location) {
        BlockStorage storage = NewMod.get().getBlockStorage();

        return Integer.parseInt(storage.getData(location, "charge"));
    }
}
