package fly.technology.blocks;

import fly.newmod.NewMod;
import fly.newmod.api.block.ModBlock;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.event.BlockEventsListener;
import fly.newmod.api.event.block.ModBlockTickEvent;
import fly.newmod.api.item.ItemManager;
import fly.newmod.api.item.type.ModItemType;
import fly.newmod.utils.BlockUtils;
import fly.technology.TechnologyPlugin;
import fly.technology.blocks.data.EnergyHolderBlockData;
import fly.technology.blocks.data.EnergyHolderBlockDataImpl;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class EnergyManagerItem extends ModItemType {
    public EnergyManagerItem() {
        super(Material.REDSTONE_LAMP, new NamespacedKey(TechnologyPlugin.get(), "energy_manager"));

        name("Energy Manager", 0xFF8060);

        setBlock(new EnergyManagerBlock().setListener(new EnergyManagerListener()));

        NewMod.get().getItemManager().registerItem(this);
        NewMod.get().getBlockManager().registerBlock(getBlock());
    }

    public static class EnergyManagerBlock extends ModBlockType implements EnergyComponent {
        public EnergyManagerBlock() {
            super(Material.REDSTONE_LAMP, new NamespacedKey(TechnologyPlugin.get(), "energy_manager"), EnergyHolderBlockDataImpl.class);
        }

        @Override
        public EnergyComponentType getType() {
            return EnergyComponentType.MANAGER;
        }

        @Override
        public int getCapacity() {
            return 50;
        }
    }

    public static class EnergyManagerListener implements BlockEventsListener {
        @Override
        public void onBlockTick(ModBlockTickEvent event) {
            ModBlock receiver = BlockUtils.getAllBlocks(
                    (x) -> x instanceof EnergyComponent && ((EnergyComponent) x).getType().equals(EnergyComponent.EnergyComponentType.RECEIVER)
                    , event.getBlock().getLocation(), true).get(0);

            ModBlock sender = BlockUtils.getAllBlocks(
                    (x) -> x instanceof EnergyComponent && ((EnergyComponent) x).getType().equals(EnergyComponent.EnergyComponentType.SENDER)
                    , event.getBlock().getLocation(), true).get(0);

            ModBlock b = event.getModBlock();

            EnergyHolderBlockData r = (EnergyHolderBlockData) receiver.getData();
            EnergyHolderBlockData s = (EnergyHolderBlockData) sender.getData();
            EnergyHolderBlockData m = (EnergyHolderBlockData) b.getData();

            r.setCharge(m.addCharge(s.addCharge(r.getCharge())));

            receiver.setData(r);
            receiver.update();

            sender.setData(s);
            sender.update();

            b.setData(m);
            b.update();
        }
    }
}
