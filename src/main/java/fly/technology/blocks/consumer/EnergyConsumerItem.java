package fly.technology.blocks.consumer;

import fly.newmod.NewMod;
import fly.newmod.api.block.BlockManager;
import fly.newmod.api.block.ModBlock;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.event.BlockEventsListener;
import fly.newmod.api.event.block.ModBlockTickEvent;
import fly.newmod.api.item.type.ModItemType;
import fly.technology.TechnologyPlugin;
import fly.technology.blocks.EnergyComponent;
import fly.technology.blocks.data.EnergyHolderBlockData;
import fly.technology.blocks.data.EnergyHolderBlockDataImpl;
import fly.technology.setup.TechnologyAddonSetup;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;

public class EnergyConsumerItem extends ModItemType {
    public EnergyConsumerItem(Material material, String id, String name, int color, EnergyConsumerBlock block) {
        super(material, new NamespacedKey(TechnologyPlugin.get(), id));

        name(name, color);

        setBlock(block/*.setListener(new EnergyConsumerListener())*/);

        NewMod.get().getItemManager().registerItem(this);
        NewMod.get().getBlockManager().registerBlock(getBlock());
    }

    public static abstract class EnergyConsumerBlock extends ModBlockType implements EnergyComponent {
        public EnergyConsumerBlock(Material material, String id) {
            super(material, new NamespacedKey(TechnologyPlugin.get(), id), EnergyHolderBlockDataImpl.class);

            setListener(new BlockEventsListener() {
                @Override
                public void onBlockTick(ModBlockTickEvent event) {
                    ModBlock block = event.getModBlock();

                    EnergyHolderBlockData data = (EnergyHolderBlockData) block.getData();

                    EnergyConsumerBlock type = ((EnergyConsumerBlock) data.getType());

                    int c = data.getCharge();
                    int t = Math.min(c, type.getMaxUsage());

                    boolean ret = doTick(event, (type.getMaxUsage()*1.0)/t);

                    if(!ret) {
                        return;
                    }

                    data.setCharge(c - t);

                    block.setData(data);
                    block.update();
                }
            });
        }

        @Override
        public EnergyComponentType getType() {
            return EnergyComponentType.CONSUMER;
        }

        public abstract boolean doTick(ModBlockTickEvent event, double percent);

        public abstract int getMaxUsage();

        public abstract int getCurrentUsage(Block block, ModBlock modBlock);
    }
}
