package fly.technology.blocks.generators;

import fly.newmod.NewMod;
import fly.newmod.api.block.BlockManager;
import fly.newmod.api.block.ModBlock;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.event.BlockEventsListener;
import fly.newmod.api.event.block.ModBlockTickEvent;
import fly.newmod.api.item.ModItemStack;
import fly.newmod.api.item.type.ModItemType;
import fly.newmod.utils.BlockUtils;
import fly.technology.TechnologyPlugin;
import fly.technology.blocks.EnergyComponent;
import fly.technology.blocks.data.EnergyHolderBlockData;
import fly.technology.blocks.data.EnergyHolderBlockDataImpl;
import fly.technology.setup.TechnologyAddonSetup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.Vector;

import java.util.List;

public class EnergyGeneratorItem extends ModItemType {
    public EnergyGeneratorItem(Material material, String id, String name, int color, EnergyGeneratorBlock block) {
        super(material, new NamespacedKey(TechnologyPlugin.get(), id));

        name(name, color);

        setBlock(block.setListener(new EnergyGeneratorListener()));

        NewMod.get().getItemManager().registerItem(this);
        NewMod.get().getBlockManager().registerBlock(getBlock());
    }

    public static abstract class EnergyGeneratorBlock extends ModBlockType implements EnergyComponent {
        public EnergyGeneratorBlock(Material material, String id) {
            super(material, new NamespacedKey(TechnologyPlugin.get(), id), EnergyHolderBlockDataImpl.class);
        }

        @Override
        public EnergyComponentType getType() {
            return EnergyComponentType.PRODUCER;
        }

        public abstract int getMaxGeneration();

        public abstract int getCurrentGeneration(Location location);
    }

    public static class EnergyGeneratorListener implements BlockEventsListener {
        @Override
        public void onBlockTick(ModBlockTickEvent event) {
            int gen = ((EnergyGeneratorBlock) event.getModBlock().getType()).getCurrentGeneration(event.getBlock().getLocation());
            BlockManager manager = NewMod.get().getBlockManager();

            if(TechnologyAddonSetup.ENERGY_MANAGER.getBlock().equals(manager.getType(event.getBlock().getRelative(0,-1,0)))) {
                ModBlock block = new ModBlock(event.getBlock().getRelative(0, -1, 0));
                EnergyHolderBlockData data = (EnergyHolderBlockData) block.getData();

                gen = data.addCharge(gen);


                ModBlock gblock = event.getModBlock();
                EnergyHolderBlockData gdata = (EnergyHolderBlockData) gblock.getData();

                if(gen == 0) {
                    gdata.setCharge(data.addCharge(gdata.getCharge()));
                } else {
                    gdata.addCharge(gen);
                }

                block.setData(data);
                gblock.setData(gdata);

                block.update();
                gblock.update();
            } else {
                ModBlock gblock = event.getModBlock();
                EnergyHolderBlockData gdata = (EnergyHolderBlockData) gblock.getData();

                gdata.addCharge(gen);

                gblock.setData(gdata);
                gblock.update();
            }
        }
    }
}
