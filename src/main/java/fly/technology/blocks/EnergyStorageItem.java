package fly.technology.blocks;

import fly.newmod.api.block.ModBlock;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.event.BlockEventsListener;
import fly.newmod.api.event.block.ModBlockTickEvent;
import fly.newmod.api.item.type.ModItemType;
import fly.newmod.utils.BlockUtils;
import fly.technology.TechnologyPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.List;

public abstract class EnergyStorageItem extends ModItemType {
    public EnergyStorageItem(EnergyStorageBlock block) {
        super(block.getDefaultMaterial(), block.getId());

        name("Energy Storage", 0xFF8060);

        setBlock(block.setListener(new EnergyStorageListener()));
    }

    public static abstract class EnergyStorageBlock extends ModBlockType implements EnergyComponent {
        public EnergyStorageBlock(Material block, String id) {
            super(block, new NamespacedKey(TechnologyPlugin.get(), id));
        }

        @Override
        public final EnergyComponentType getType() {
            return EnergyComponentType.STORAGE;
        }
    }

    public static class EnergyStorageListener implements BlockEventsListener {
        @Override
        public void onBlockTick(ModBlockTickEvent event) {
            
        }
    }
}
