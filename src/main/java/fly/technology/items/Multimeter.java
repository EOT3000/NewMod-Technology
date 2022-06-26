package fly.technology.items;

import fly.newmod.NewMod;
import fly.newmod.api.block.ModBlock;
import fly.newmod.api.event.ItemEventsListener;
import fly.newmod.api.event.both.ModBlockItemUseEvent;
import fly.newmod.api.item.type.ModItemType;
import fly.technology.TechnologyPlugin;
import fly.technology.blocks.EnergyComponent;
import fly.technology.blocks.data.EnergyHolderBlockData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class Multimeter extends ModItemType {
    public Multimeter() {
        super(Material.CLOCK, new NamespacedKey(TechnologyPlugin.get(), "multimeter"));

        name("Multimeter", 0xD0B000);

        setListener(new MultimeterListener());

        NewMod.get().getItemManager().registerItem(this);
    }

    public static class MultimeterListener implements ItemEventsListener {
        @Override
        public void onItemUseLowest(ModBlockItemUseEvent event) {
            if(event.getModBlock() != null) {
                ModBlock block = event.getModBlock();

                if(block.getType() instanceof EnergyComponent) {
                    EnergyHolderBlockData data = (EnergyHolderBlockData) block.getData();

                    int bars = (int)((data.getCharge()*1.0)/data.getCapacity())*10;

                    // A fucking mess but it works (I hope)

                    event.getPlayer().sendMessage("");
                    event.getPlayer().sendMessage(block.getType().getItem().getCustomName());
                    event.getPlayer().sendMessage(Component.text("Type: ").color(TextColor.color(0x00D5FF)).append(Component.text(((EnergyComponent) block.getType()).getType().toString()).color(TextColor.color(0x00A0C0))));
                    event.getPlayer().sendMessage(Component.text("Capacity: ").color(TextColor.color(0x00A0C0)).append(Component.text(data.getCapacity()).color(TextColor.color(0x00D5FF))));
                    event.getPlayer().sendMessage(Component.text("Charge: ").color(TextColor.color(0x00D5FF)).append(Component.text(p(bars)).color(TextColor.color(0x00FF00)).append(Component.text(p(10-bars)).color(TextColor.color(0xFFC000)))));
                }
            }
        }

        private String p(int count) {
            return "|".repeat(Math.max(0, count));
        }
    }
}