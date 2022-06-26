package fly.technology.blocks;

import fly.newmod.NewMod;
import fly.newmod.api.block.BlockManager;
import fly.newmod.api.block.ModBlock;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.event.BlockEventsListener;
import fly.newmod.api.event.block.ModBlockTickEvent;
import fly.newmod.api.item.type.ModItemType;
import fly.newmod.utils.BlockUtils;
import fly.technology.TechnologyPlugin;
import fly.technology.blocks.data.EnergyHolderBlockData;
import fly.technology.blocks.data.EnergyHolderBlockDataImpl;
import fly.technology.setup.TechnologyAddonSetup;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.util.Vector;

import java.util.List;

public class EnergySenderItem extends ModItemType {
    public EnergySenderItem() {
        super(Material.TARGET, new NamespacedKey(TechnologyPlugin.get(), "energy_sender"));

        name("Energy Sender", 0xFF8060);

        setBlock(new EnergySenderBlock().setListener(new EnergySenderListener()));

        NewMod.get().getItemManager().registerItem(this);
        NewMod.get().getBlockManager().registerBlock(getBlock());
    }

    public static class EnergySenderBlock extends ModBlockType implements EnergyComponent {
        public EnergySenderBlock() {
            super(Material.TARGET, new NamespacedKey(TechnologyPlugin.get(), "energy_sender"), EnergyHolderBlockDataImpl.class);
        }

        @Override
        public EnergyComponentType getType() {
            return EnergyComponentType.SENDER;
        }

        @Override
        public int getCapacity() {
            return 50;
        }
    }

    public static class EnergySenderListener implements BlockEventsListener {
        @Override
        public void onBlockTick(ModBlockTickEvent event) {
            List<ModBlock> wires = BlockUtils.getAllBlocks((x) -> x.equals(TechnologyAddonSetup.THIN_CABLE.getBlock())
                    , event.getBlock().getLocation(), false);

            for(ModBlock wire : wires) {
                ModBlock end = end(event.getBlock().getLocation(), wire.create(null).getLocation(), 5);

                if(end.getType() instanceof EnergyComponent && ((EnergyComponent) end.getType()).getType().equals(EnergyComponent.EnergyComponentType.RECEIVER)) {
                    ModBlock b = event.getModBlock();

                    EnergyHolderBlockData data = (EnergyHolderBlockData) end.getData();
                    EnergyHolderBlockData sdata = (EnergyHolderBlockData) b.getData();

                    sdata.setCharge(data.addCharge(sdata.getCharge()));

                    end.setData(data);
                    end.update();

                    b.setData(sdata);
                    b.update();
                }
            }
        }

        private ModBlock end(Location node, Location wire, int length) {
            Vector direction = wire.toVector().subtract(node.toVector());

            return end0(direction, wire, length);
        }

        private ModBlock end0(Vector direction, Location init, int remaining) {
            BlockManager manager = NewMod.get().getBlockManager();

            Location newLoc = init.clone().add(direction);

            ModBlockType wireType = manager.getType(init.getBlock());
            ModBlockType nextType = manager.getType(newLoc.getBlock());

            if(nextType == null || remaining <= 0) {
                return null;
            }

            if(wireType.equals(nextType)) {
                return end0(direction, newLoc, remaining-1);
            } else return new ModBlock(newLoc.getBlock());
        }
    }
}
