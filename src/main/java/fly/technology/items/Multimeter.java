package fly.technology.items;

import fly.metals.setup.MetalsAddonSetup;
import fly.newmod.NewMod;
import fly.newmod.api.block.ModBlock;
import fly.newmod.api.event.ItemEventsListener;
import fly.newmod.api.event.both.ModBlockItemUseEvent;
import fly.newmod.api.item.ModItemStack;
import fly.newmod.api.item.type.ModItemType;
import fly.technology.TechnologyPlugin;
import fly.technology.blocks.EnergyComponent;
import fly.technology.blocks.data.EnergyHolderBlockData;
import fly.technology.setup.TechnologyAddonSetup;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class Multimeter extends ModItemType {
    public Multimeter() {
        super(Material.CLOCK, new NamespacedKey(TechnologyPlugin.get(), "multimeter"));

        name("Multimeter", 0xD0B000);

        setListener(new MultimeterListener());

        NewMod.get().getItemManager().registerItem(this);

        ShapedRecipe recipe = new ShapedRecipe(getId(), new ModItemStack(this).create());

        recipe.shape(" C ", "GNG", "IRI");

        recipe.setIngredient('C', Material.COPPER_INGOT);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('N', new ModItemStack(MetalsAddonSetup.NEODYMIUM_NUGGET).create());
        recipe.setIngredient('I', Material.IRON_NUGGET);
        recipe.setIngredient('R', Material.REDSTONE);

        Bukkit.addRecipe(recipe);
    }

    public static class MultimeterListener implements ItemEventsListener {
        @Override
        public void onItemUseLowest(ModBlockItemUseEvent event) {
            if(event.getModBlock() != null) {
                ModBlock block = event.getModBlock();

                if(block.getType() instanceof EnergyComponent) {
                    EnergyHolderBlockData data = (EnergyHolderBlockData) block.getData();

                    int bars = (data.getCharge()*10)/(data.getCapacity());

                    // A fucking mess but it works (I hope)
                    // UPDATE: it didn't

                    event.getPlayer().sendMessage("");
                    event.getPlayer().sendMessage(block.getType().getItem().getCustomName());
                    event.getPlayer().sendMessage(Component.text("Type: ").color(TextColor.color(0x00D5FF)).append(Component.text(((EnergyComponent) block.getType()).getType().toString()).color(TextColor.color(0x00A0C0))));
                    event.getPlayer().sendMessage(Component.text("Capacity: ").color(TextColor.color(0x00A0C0)).append(Component.text(data.getCapacity()).color(TextColor.color(0x00D5FF))));

                    TextComponent name = Component.text("Charge: ").color(TextColor.color(0x00D5FF));
                    TextComponent filled = Component.text(p(bars)).color(TextColor.color(0x00FF00));
                    TextComponent remaining = Component.text(q(10-bars)).color(TextColor.color(0xFFC000));

                    event.getPlayer().sendMessage(name.append(filled).append(Component.text(" ")).append(remaining));
                }
            }
        }

        private String p(int count) {
            return "#".repeat(Math.max(0, count));
        }

        private String q(int count) {
            return "+".repeat(Math.max(0, count));
        }
    }
}
