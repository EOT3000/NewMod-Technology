package fly.technology.blocks.consumer;

import fly.metals.setup.MetalsAddonSetup;
import fly.newmod.api.block.ModBlock;
import fly.newmod.api.event.BlockEventsListener;
import fly.newmod.api.event.block.ModBlockTickEvent;
import fly.technology.blocks.data.EnergyHolderBlockData;
import fly.technology.setup.TechnologyAddonSetup;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ShapedRecipe;

public class QuickFurnaceItem extends EnergyConsumerItem {
    public QuickFurnaceItem() {
        super(Material.FURNACE, "quick_furnace", "Quick Furnace", 0x808080, new QuickFurnaceBlock());

        ShapedRecipe recipe = new ShapedRecipe(getId(), this.create());

        recipe.shape("NNN", "TFM", "RRR");

        recipe.setIngredient('N', MetalsAddonSetup.COPPER_NUGGET.create());
        recipe.setIngredient('T', TechnologyAddonSetup.THIN_CABLE.create());
        recipe.setIngredient('F', Material.FURNACE);
        recipe.setIngredient('M', TechnologyAddonSetup.MULTIMETER.create());
        recipe.setIngredient('R', Material.REDSTONE);

        Bukkit.addRecipe(recipe);
    }

    public static class QuickFurnaceBlock extends EnergyConsumerBlock {
        public QuickFurnaceBlock() {
            super(Material.FURNACE, "quick_furnace");

            setListener(new BlockEventsListener() {
                @Override
                public void onBlockTick(ModBlockTickEvent event) {
                    Furnace furnace = (Furnace) event.getBlock().getState();

                    ModBlock block = event.getModBlock();

                    EnergyHolderBlockData data = (EnergyHolderBlockData) block.getData();

                    int c = data.getCharge();
                    int t = Math.min(c, 24);

                    data.setCharge(c-t);

                    block.setData(data);

                    block.update();

                    furnace.setCookSpeedMultiplier(t/18.0);

                    furnace.update();
                }
            });
        }

        @Override
        public int getCapacity() {
            return 280;
        }

        @Override
        public int getMaxUsage() {
            return 24;
        }

        @Override
        public int getCurrentUsage(Block block, ModBlock modBlock) {
            return 24;
        }
    }
}
