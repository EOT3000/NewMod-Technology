package fly.technology.blocks;

import fly.newmod.NewMod;
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
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;

public class EnergyManagerItem extends ModItemType {
    public EnergyManagerItem() {
        super(Material.REDSTONE_LAMP, new NamespacedKey(TechnologyPlugin.get(), "energy_manager"));

        name("Energy Manager", 0xFF8060);

        setBlock(new EnergyManagerBlock().setListener(new EnergyManagerListener()));

        NewMod.get().getItemManager().registerItem(this);
        NewMod.get().getBlockManager().registerBlock(getBlock());

        ShapedRecipe recipe = new ShapedRecipe(getId(), new ModItemStack(this).create());

        recipe.shape("RCR", "CWC", "ROR");

        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('C', new ModItemStack(TechnologyAddonSetup.THIN_CABLE).create());
        recipe.setIngredient('W', Material.REDSTONE_LAMP);
        recipe.setIngredient('O', Material.COPPER_INGOT);

        Bukkit.addRecipe(recipe);
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
            return 320;
        }
    }

    public static class EnergyManagerListener implements BlockEventsListener {
        @Override
        public void onBlockTick(ModBlockTickEvent event) {
            List<ModBlock> receivers = BlockUtils.getAllBlocks(
                    (x) -> x instanceof EnergyComponent && ((EnergyComponent) x).getType().equals(EnergyComponent.EnergyComponentType.RECEIVER)
                    , event.getBlock().getLocation(), true);

            List<ModBlock> senders = BlockUtils.getAllBlocks(
                    (x) -> x instanceof EnergyComponent && ((EnergyComponent) x).getType().equals(EnergyComponent.EnergyComponentType.SENDER)
                    , event.getBlock().getLocation(), true);

            ModBlock b = event.getModBlock();

            if(receivers.size() == 1) {
                if(senders.size() == 1) {
                    ModBlock receiver = receivers.get(0);
                    ModBlock sender = senders.get(0);

                    EnergyHolderBlockData r = (EnergyHolderBlockData) receiver.getData();
                    EnergyHolderBlockData s = (EnergyHolderBlockData) sender.getData();
                    EnergyHolderBlockData m = (EnergyHolderBlockData) b.getData();

                    r.setCharge(m.addCharge(s.addCharge(r.getCharge())));

                    m.setCharge(s.addCharge(m.getCharge()));

                    receiver.setData(r);
                    receiver.update();

                    sender.setData(s);
                    sender.update();

                    b.setData(m);
                    b.update();
                } else {
                    ModBlock receiver = receivers.get(0);

                    EnergyHolderBlockData r = (EnergyHolderBlockData) receiver.getData();
                    EnergyHolderBlockData m = (EnergyHolderBlockData) b.getData();

                    r.setCharge(m.addCharge(r.getCharge()));

                    receiver.setData(r);
                    receiver.update();

                    b.setData(m);
                    b.update();
                }
            } else {
                if(senders.size() == 1) {
                    ModBlock sender = senders.get(0);
                    EnergyHolderBlockData s = (EnergyHolderBlockData) sender.getData();
                    EnergyHolderBlockData m = (EnergyHolderBlockData) b.getData();

                    m.setCharge(s.addCharge(m.getCharge()));

                    sender.setData(s);
                    sender.update();

                    b.setData(m);
                    b.update();
                }
            }
        }
    }
}
