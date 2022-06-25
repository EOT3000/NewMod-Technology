package fly.technology.blocks;

import fly.newmod.NewMod;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.item.type.ModItemType;
import fly.technology.TechnologyPlugin;
import fly.technology.blocks.data.EnergyHolderBlockDataImpl;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class EnergyReceiverItem extends ModItemType {
    public EnergyReceiverItem() {
        super(Material.TARGET, new NamespacedKey(TechnologyPlugin.get(), "energy_receiver"));

        name("Energy Receiver", 0xFF8060);

        setBlock(new EnergyManagerItem.EnergyManagerBlock());

        NewMod.get().getItemManager().registerItem(this);
        NewMod.get().getBlockManager().registerBlock(getBlock());
    }

    public static class EnergyReceiverBlock extends ModBlockType implements EnergyComponent {
        public EnergyReceiverBlock() {
            super(Material.REDSTONE_LAMP, new NamespacedKey(TechnologyPlugin.get(), "energy_receiver"), EnergyHolderBlockDataImpl.class);
        }

        @Override
        public EnergyComponentType getType() {
            return EnergyComponentType.RECEIVER;
        }

        @Override
        public int getCapacity() {
            return 50;
        }
    }
}
