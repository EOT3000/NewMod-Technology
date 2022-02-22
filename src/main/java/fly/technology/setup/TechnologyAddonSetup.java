package fly.technology.setup;

import fly.metals.setup.MetalsAddonSetup;
import fly.newmod.bases.ModItem;
import fly.newmod.utils.Pair;
import fly.technology.TechnologyPlugin;
import fly.technology.items.power.EnergyManager;
import fly.technology.items.power.EnergyStorage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import static org.bukkit.Material.*;

public class TechnologyAddonSetup {
    public static void init() {

    }

    public static final ModItem ELECTRIC_PLATE = new ModItem(HEAVY_WEIGHTED_PRESSURE_PLATE, "Electric Plate", 0xFFFFFF, "electric_plate", 2,
            MetalsAddonSetup.TITANIUM_INGOT, MetalsAddonSetup.TITANIUM_INGOT, new ItemStack(GOLD_INGOT), new ItemStack(COPPER_INGOT));

    public static final ModItem SPEAKER = new ModItem(GRAY_DYE, "Speaker", NamedTextColor.DARK_GRAY, "speaker", 4,
            ELECTRIC_PLATE, new ItemStack(REDSTONE), new ItemStack(CLAY_BALL), MetalsAddonSetup.MAGNET, new ItemStack(GLASS_PANE));

    public static final ModItem SOUND_SCANNER = new ModItem(LIGHTNING_ROD, "Sound Scanner", NamedTextColor.DARK_GRAY, "sound_scanner", 1,
            new ItemStack(COPPER_INGOT), SPEAKER, new ItemStack(GOLD_INGOT));

    public static final ModItem PARTICLE_COUNTER = new ModItem(CLOCK, "Particle Counter", NamedTextColor.DARK_GRAY, "particle_counter", 1,
            new ItemStack(GLASS_BOTTLE), new ItemStack(REDSTONE), new ItemStack(GOLD_INGOT), MetalsAddonSetup.MAGNET, MetalsAddonSetup.CARBON_PIECE);

    public static final ModItem BATTERY = new ModItem(FIREWORK_ROCKET, "Battery", NamedTextColor.DARK_GRAY, "battery", 2, new ItemStack(RED_WOOL), MetalsAddonSetup.ZINC_INGOT, new ItemStack(COPPER_INGOT), MetalsAddonSetup.SULFUR_NUGGET, new ItemStack(REDSTONE), new ItemStack(IRON_INGOT));
    public static final ModItem MOTOR = new ModItem(FIREWORK_ROCKET, "Motor", NamedTextColor.RED, "motor", 1, MetalsAddonSetup.MAGNET, new ItemStack(COPPER_INGOT), MetalsAddonSetup.MAGNET);

    public static final ModItem LITHIUM_BATTERY = new ModItem(FIREWORK_ROCKET,"Lithium Battery", NamedTextColor.DARK_GREEN, "lithium_battery", 2, new ItemStack(RED_WOOL), MetalsAddonSetup.LITHIUM_INGOT, MetalsAddonSetup.CARBON_CHUNK, MetalsAddonSetup.SULFUR_NUGGET, new ItemStack(REDSTONE), new ItemStack(IRON_INGOT));
    public static final EnergyStorage ENERGY_STORAGE = new EnergyStorage(IRON_BLOCK, "Energy Storage", NamedTextColor.RED, "energy_storage", 2048, BATTERY);
    public static final EnergyStorage LITHIUM_ENERGY_STORAGE = new EnergyStorage(IRON_BLOCK, "Lithium Energy Storage", NamedTextColor.GREEN, "lithium_energy_storage", 8192, LITHIUM_BATTERY);

    public static final ModItem ENERGY_RECEIVER = new ModItem(TARGET, "Energy Receiver", 0xEEFFFF, "energy_receiver");
    public static final ModItem ENERGY_SENDER = new ModItem(TARGET, "Energy Sender", 0xFFEEEE, "energy_sender");
    public static final EnergyManager ENERGY_MANAGER = new EnergyManager();

    private static ModItem shapelessRecipe(ModItem item, ItemStack[] recipe, int count) {
        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(new NamespacedKey(TechnologyPlugin.getProvidingPlugin(TechnologyPlugin.class), item.getId()), item);

        for(ItemStack stack : recipe) {
            shapelessRecipe.addIngredient(stack);
        }

        Bukkit.addRecipe(shapelessRecipe);

        return item;
    }


    private static ModItem shapedRecipe(ModItem item, String[] pattern, Pair<Character, ItemStack>[] items, int count) {
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(TechnologyPlugin.getProvidingPlugin(TechnologyPlugin.class), item.getId()), item);

        shapedRecipe.shape(pattern);

        for(Pair<Character, ItemStack> i : items) {
            shapedRecipe.setIngredient(i.getKey(), i.getValue());
        }

        Bukkit.addRecipe(shapedRecipe);

        return item;
    }
}
