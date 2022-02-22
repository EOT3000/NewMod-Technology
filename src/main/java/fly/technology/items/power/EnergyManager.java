package fly.technology.items.power;

import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import fly.newmod.setup.BlockStorage;
import fly.technology.TechnologyPlugin;
import fly.technology.setup.TechnologyAddonSetup;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class EnergyManager extends ModItem {
    public EnergyManager() {
        super(Material.REDSTONE_LAMP, "Energy Manager", 0xFF8000, "energy_manager");

        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(TechnologyPlugin.getProvidingPlugin(TechnologyPlugin.class), getId()), this);

        shapedRecipe.shape("GEG", "RPR", "GCG");

        shapedRecipe.setIngredient('G', new ItemStack(Material.GLOWSTONE_DUST));
        shapedRecipe.setIngredient('E', TechnologyAddonSetup.ELECTRIC_PLATE);
    }

    @Override
    public void tick(Location location, int count) {

    }

    public static int getRequested(Location location) {
        BlockStorage storage = NewMod.get().getBlockStorage();

        return Integer.parseInt(storage.getData(location, "requested"));
    }
}
