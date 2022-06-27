package fly.technology.setup;

import fly.newmod.api.item.type.ModItemType;
import fly.technology.TechnologyPlugin;
import fly.technology.blocks.EnergyManagerItem;
import fly.technology.blocks.EnergyReceiverItem;
import fly.technology.blocks.EnergySenderItem;
import fly.technology.blocks.ThinCableItem;
import fly.technology.blocks.generators.SolarPanelItem;
import fly.technology.items.Multimeter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import static fly.metals.setup.MetalsAddonSetup.*;

public class TechnologyAddonSetup {
    public static void init() {
        Bukkit.addRecipe(new BlastingRecipe(new NamespacedKey(TechnologyPlugin.get(), "photovoltaic_brick"), PHOTOVOLTAIC_BRICK.create(), new RecipeChoice.ExactChoice(PHOTOVOLTAIC_POWDER.create()), 1.5f, 400));
    }

    public static final ModItemType PHOTOVOLTAIC_POWDER = ModItemType.createAndRegister(Material.REDSTONE, TechnologyPlugin.get(), "photovoltaic_powder", "Photovoltaic Powder", 0xD08070)
            .shapelessRecipe(5, BORON_PIECE.create(), CARBON_POWDER.create(), PHOSPHORUS_POWDER.create(), SULFUR_POWDER.create(), new ItemStack(Material.REDSTONE));

    public static final ModItemType PHOTOVOLTAIC_BRICK = ModItemType.createAndRegister(Material.BRICK, TechnologyPlugin.get(), "photovoltaic_brick", "Photovoltaic Brick", 0xD08070);

    public static final ThinCableItem THIN_CABLE = new ThinCableItem();
    public static final EnergyManagerItem ENERGY_MANAGER = new EnergyManagerItem();
    public static final EnergyReceiverItem ENERGY_RECEIVER = new EnergyReceiverItem();
    public static final EnergySenderItem ENERGY_SENDER = new EnergySenderItem();

    public static final SolarPanelItem SOLAR_PANEL = new SolarPanelItem();

    public static final Multimeter MULTIMETER = new Multimeter();
}
