package fly.technology.blocks;

import fly.newmod.NewMod;
import fly.newmod.api.block.BlockManager;
import fly.newmod.api.block.ModBlock;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.event.BlockEventsListener;
import fly.newmod.api.event.block.ModBlockBreakEvent;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class PortableStorageItem extends ModItemType {
    public PortableStorageItem() {
        super(Material.TARGET, new NamespacedKey(TechnologyPlugin.get(), "portable_storage"));

        name("Portable Energy Storage", 0xFF8060);

        setBlock(new PortableStorageBlock().setListener(new PortableStorageListener()));

        NewMod.get().getItemManager().registerItem(this);
        NewMod.get().getBlockManager().registerBlock(getBlock());

        ShapedRecipe recipe = new ShapedRecipe(getId(), new ModItemStack(this).create());

        recipe.shape("RRR", "RRR", "WCO");

        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('C', new ModItemStack(TechnologyAddonSetup.THIN_CABLE).create());
        recipe.setIngredient('W', Material.WHITE_WOOL);
        recipe.setIngredient('O', Material.COPPER_INGOT);

        Bukkit.addRecipe(recipe);
    }

    public static class PortableStorageBlock extends ModBlockType implements EnergyComponent {
        public PortableStorageBlock() {
            super(Material.TARGET, new NamespacedKey(TechnologyPlugin.get(), "portable_storage"), EnergyHolderBlockDataImpl.class);
        }

        @Override
        public EnergyComponentType getType() {
            return EnergyComponentType.SENDER_RECEIVER;
        }

        @Override
        public int getCapacity() {
            return 120000;
        }

        @Override
        public ItemStack getDropStack(ModBlockBreakEvent ne) {
            return super.getDropStack(ne);
        }
    }

    public static class PortableStorageListener implements BlockEventsListener {
        @Override
        public void onBlockTick(ModBlockTickEvent event) {
            //System.out.println("sender ---------------");

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

            int total = 0;

            List<ModBlock> list = new ArrayList<>();

            for(ModBlock block : receivers) {
                EnergyHolderBlockData ndata = (EnergyHolderBlockData) block.getData();

                if(ndata.getCapacity()-ndata.getCharge() > 0) {
                    total+=ndata.getCapacity()-ndata.getCharge();

                    list.add(block);
                }
            }

            double percent = Math.min((sdata.getCharge()*1.0)/total, 1.0);

            for(ModBlock block : list) {
                //System.out.println("-");

                //System.out.println(block.create(null).getLocation());

                EnergyHolderBlockData ndata = (EnergyHolderBlockData) block.getData();

                //System.out.println("user charge pre: " + ndata.getCharge());
                //System.out.println("user capacity pre: " + ndata.getCapacity());

                //System.out.println("sender charge pre: " + sdata.getCharge());
                //System.out.println("sender capacity pre: " + sdata.getCapacity());

                int s = (int)((ndata.getCapacity()-ndata.getCharge())*percent);

                ndata.addCharge(s);

                sdata.removeCharge(s);

                //System.out.println("user charge post: " + ndata.getCharge());
                //System.out.println("user capacity post: " + ndata.getCapacity());

                //System.out.println("sender charge post: " + sdata.getCharge());
                //System.out.println("sender capacity post: " + sdata.getCapacity());

                block.setData(ndata);
                block.update();

                //System.out.println(block.getData());
            }

            //System.out.println("sender charge last: " + sdata.getCharge());
            //System.out.println("sender capacity last: " + sdata.getCapacity());

            b.setData(sdata);
            b.update();

            //System.out.println(b.getData());

            //System.out.println("---------------");
        }

        @Override
        public void onBlockBreakMonitor(ModBlockBreakEvent event) {
            BlockEventsListener.super.onBlockBreakMonitor(event);
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
