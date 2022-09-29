package fly.technology.setup;

import fly.metals.setup.MetalsAddonSetup;
import fly.newmod.api.item.type.ModItemType;
import fly.technology.TechnologyPlugin;
import fly.technology.blocks.*;
import fly.technology.blocks.consumer.QuickBrewingStandItem;
import fly.technology.blocks.consumer.PoweredFurnaceItem;
import fly.technology.blocks.generators.SolarPanelItem;
import fly.technology.items.Multimeter;
import fly.technology.items.Pan;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import static fly.metals.setup.MetalsAddonSetup.*;

public class TechnologyAddonSetup {
    public static void init() {
        Bukkit.addRecipe(new BlastingRecipe(new NamespacedKey(TechnologyPlugin.get(), "photovoltaic_brick"), PHOTOVOLTAIC_BRICK.create(), new RecipeChoice.ExactChoice(PHOTOVOLTAIC_POWDER.create()), 1.5f, 400));

        new BlockPlacer();

        furnaceOne(QUICK_FURNACE_IRON, new ItemStack(Material.IRON_NUGGET), TITANIUM_INGOT.create());
        furnaceOne(QUICK_SMOKER_IRON, new ItemStack(Material.IRON_NUGGET), TITANIUM_INGOT.create());
        furnaceOne(QUICK_BLAST_FURNACE_IRON, new ItemStack(Material.IRON_NUGGET), TITANIUM_INGOT.create());

        furnaceOne(QUICK_FURNACE_TITANIUM, TITANIUM_NUGGET.create(), BORRO_NEODYMIUM_INGOT.create());
        furnaceOne(QUICK_SMOKER_TITANIUM, TITANIUM_NUGGET.create(), BORRO_NEODYMIUM_INGOT.create());
        furnaceOne(QUICK_BLAST_FURNACE_TITANIUM, TITANIUM_NUGGET.create(), BORRO_NEODYMIUM_INGOT.create());

        //furnaceOne(QUICK_FURNACE_IRON, new ItemStack(Material.IRON_NUGGET), TITANIUM_INGOT.create());
        //furnaceOne(QUICK_SMOKER_IRON, new ItemStack(Material.IRON_NUGGET), TITANIUM_INGOT.create());
        //furnaceOne(QUICK_BLAST_FURNACE_IRON, new ItemStack(Material.IRON_NUGGET), TITANIUM_INGOT.create());
    }

    private static void furnaceOne(ModItemType type, ItemStack metal1, ItemStack metal2) {
        ShapedRecipe recipe = new ShapedRecipe(type.getId(), type.create());

        recipe.shape("NNN", "TFM", "IRI");

        recipe.setIngredient('N', metal1);
        recipe.setIngredient('T', TechnologyAddonSetup.THIN_CABLE.create());
        recipe.setIngredient('F', type.create().getType());
        recipe.setIngredient('M', TechnologyAddonSetup.MULTIMETER.create());
        recipe.setIngredient('I', metal2);
        recipe.setIngredient('R', Material.REDSTONE);

        Bukkit.addRecipe(recipe);
    }

    public static final Multimeter MULTIMETER = new Multimeter();
    public static final Pan PAN = new Pan();

    public static final ModItemType PHOTOVOLTAIC_POWDER = ModItemType.createAndRegister(Material.REDSTONE, TechnologyPlugin.get(), "photovoltaic_powder", "Photovoltaic Powder", 0xD08070)
            .shapelessRecipe(5, BORON_PIECE.create(), CARBON_POWDER.create(), PHOSPHORUS_POWDER.create(), SULFUR_POWDER.create(), new ItemStack(Material.REDSTONE));

    public static final ModItemType PHOTOVOLTAIC_BRICK = ModItemType.createAndRegister(Material.BRICK, TechnologyPlugin.get(), "photovoltaic_brick", "Photovoltaic Brick", 0xD08070);

    public static final ThinCableItem THIN_CABLE = new ThinCableItem();
    public static final EnergyManagerItem ENERGY_MANAGER = new EnergyManagerItem();
    public static final EnergyReceiverItem ENERGY_RECEIVER = new EnergyReceiverItem();
    public static final EnergySenderItem ENERGY_SENDER = new EnergySenderItem();
    public static final PortableStorageItem PORTABLE_STORAGE = new PortableStorageItem();

    public static final SolarPanelItem SOLAR_PANEL = new SolarPanelItem();

    public static final PoweredFurnaceItem QUICK_FURNACE_IRON = new PoweredFurnaceItem(Material.FURNACE, "quick_furnace_iron", "Quick Furnace (Iron)", 0xA0A0A0, 1680, 18, (float) 4.0/3);
    public static final PoweredFurnaceItem QUICK_SMOKER_IRON = new PoweredFurnaceItem(Material.SMOKER, "quick_smoker_iron", "Quick Smoker (Iron)", 0xA0A0A0, 1680, 18, (float) 4.0/3);
    public static final PoweredFurnaceItem QUICK_BLAST_FURNACE_IRON = new PoweredFurnaceItem(Material.BLAST_FURNACE, "quick_blast_furnace_iron", "Quick Blast Furnace (Iron)", 0xA0A0A0, 1680, 18, (float) 4.0/3);

    public static final PoweredFurnaceItem QUICK_FURNACE_TITANIUM = new PoweredFurnaceItem(Material.FURNACE, "quick_furnace_titanium", "Quick Furnace (Titanium)", 0xC0C0C0, 2040, 36, (float) 7.0/4);
    public static final PoweredFurnaceItem QUICK_SMOKER_TITANIUM = new PoweredFurnaceItem(Material.SMOKER, "quick_smoker_titanium", "Quick Smoker (Titanium)", 0xC0C0C0, 2040, 36, (float) 7.0/4);
    public static final PoweredFurnaceItem QUICK_BLAST_FURNACE_TITANIUM = new PoweredFurnaceItem(Material.BLAST_FURNACE, "quick_blast_furnace_titanium", "Quick Blast Furnace (Titanium)", 0xC0C0C0, 2040, 36, (float) 7.0/4);

    /*public static final PoweredFurnaceItem QUICK_FURNACE_IRON = new PoweredFurnaceItem(Material.FURNACE, "quick_furnace_iron", "Quick Furnace (Iron)", 0xA0A0A0, 1680, 18, (float) 4.0/3);
    public static final PoweredFurnaceItem QUICK_SMOKER_IRON = new PoweredFurnaceItem(Material.SMOKER, "quick_smoker_iron", "Quick Smoker (Iron)", 0xA0A0A0, 1680, 18, (float) 4.0/3);
    public static final PoweredFurnaceItem QUICK_BLAST_FURNACE_IRON = new PoweredFurnaceItem(Material.BLAST_FURNACE, "quick_blast_furnace_iron", "Quick Blast Furnace (Iron)", 0xA0A0A0, 1680, 18, (float) 4.0/3);*/

    public static final QuickBrewingStandItem QUICK_BREWING_STAND = new QuickBrewingStandItem();

    //public static final PoweredMinecart POWERED_MINECART = new PoweredMinecart();
}
