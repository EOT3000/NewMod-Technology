package fly.technology.blocks;

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
import fly.technology.blocks.data.EnergyHolderBlockData;
import fly.technology.blocks.data.EnergyHolderBlockDataImpl;
import fly.technology.setup.TechnologyAddonSetup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnergySenderItem extends ModItemType {
    public EnergySenderItem() {
        super(Material.TARGET, new NamespacedKey(TechnologyPlugin.get(), "energy_sender"));

        name("Energy Sender", 0xFF8060);

        setBlock(new EnergySenderBlock().setListener(new EnergySenderListener()));

        NewMod.get().getItemManager().registerItem(this);
        NewMod.get().getBlockManager().registerBlock(getBlock());

        ShapedRecipe recipe = new ShapedRecipe(getId(), new ModItemStack(this).create());

        recipe.shape("RCR", "CWC", "ROR");

        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('C', new ModItemStack(TechnologyAddonSetup.THIN_CABLE).create());
        recipe.setIngredient('W', Material.WHITE_WOOL);
        recipe.setIngredient('O', Material.COPPER_INGOT);

        Bukkit.addRecipe(recipe);
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
            return 400;
        }
    }

    public static class EnergySenderListener implements BlockEventsListener {
        @Override
        public void onBlockTick(ModBlockTickEvent event) {
            List<ModBlock> wires = BlockUtils.getAllBlocks((x) -> x.equals(TechnologyAddonSetup.THIN_CABLE.getBlock())
                    , event.getBlock().getLocation(), false);

            List<ModBlock> receivers = new ArrayList<>();

            ModBlock b = event.getModBlock();
            EnergyHolderBlockData sdata = (EnergyHolderBlockData) b.getData();

            for(ModBlock wire : wires) {
                ModBlock end = end(event.getBlock().getLocation(), wire.create(null).getLocation(), 50);

                if(end.getType() instanceof EnergyComponent && ((EnergyComponent) end.getType()).getType().equals(EnergyComponent.EnergyComponentType.RECEIVER)) {
                    EnergyHolderBlockData data = (EnergyHolderBlockData) end.getData();

                    if(data.getCapacity()-data.getCharge() > 0) {
                        receivers.add(end);
                    }
                }
            }

            receivers.addAll(BlockUtils.getAllBlocks((x) -> x.equals(TechnologyAddonSetup.ENERGY_RECEIVER.getBlock())
                    , event.getBlock().getLocation(), false));

            receivers.addAll(BlockUtils.getAllBlocks((x) -> {
                        if(x instanceof EnergyComponent) {
                            return ((EnergyComponent) x).getType().equals(EnergyComponent.EnergyComponentType.CONSUMER);
                        }

                        return false;
                    }
                    , event.getBlock().getLocation(), false));

            if(receivers.size() == 0) {
                return;
            }

            int each = sdata.getCharge()/receivers.size();

            List<ModBlock> nreceivers = new ArrayList<>();

            for(ModBlock receiver : receivers) {
                /*ModBlock b = event.getModBlock();

                EnergyHolderBlockData data = (EnergyHolderBlockData) receiver.getData();
                EnergyHolderBlockData sdata = (EnergyHolderBlockData) b.getData();

                sdata.setCharge(data.addCharge(sdata.getCharge()));

                receiver.setData(data);
                receiver.update();

                b.setData(sdata);
                b.update();*/

                EnergyHolderBlockData data = (EnergyHolderBlockData) receiver.getData();

                int r = data.getCapacity()-data.getCharge();

                if(r <= each) {
                    sdata.setCharge(data.addCharge(sdata.getCharge()));
                } else {
                    nreceivers.add(receiver);
                }

                receiver.setData(data);
                receiver.update();
            }

            if(nreceivers.size() == 0) {
                return;
            }

            each = sdata.getCharge()/nreceivers.size();

            for(ModBlock receiver : nreceivers) {
                EnergyHolderBlockData data = (EnergyHolderBlockData) receiver.getData();

                sdata.removeCharge(each-data.addCharge(each));

                receiver.setData(data);
                receiver.update();
            }

            b.setData(sdata);
            b.update();
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
