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

        recipe.shape("NNN", "TFM", "IRI");

        recipe.setIngredient('N', MetalsAddonSetup.COPPER_NUGGET.create());
        recipe.setIngredient('T', TechnologyAddonSetup.THIN_CABLE.create());
        recipe.setIngredient('F', Material.FURNACE);
        recipe.setIngredient('M', TechnologyAddonSetup.MULTIMETER.create());
        recipe.setIngredient('I', MetalsAddonSetup.TITANIUM_INGOT.create());
        recipe.setIngredient('R', Material.REDSTONE);

        Bukkit.addRecipe(recipe);
    }

    public static class QuickFurnaceBlock extends EnergyConsumerBlock {
        public QuickFurnaceBlock() {
            super(Material.FURNACE, "quick_furnace");

            setListener(new BlockEventsListener() {
                @Override
                public void onBlockTick(ModBlockTickEvent event) {
                    //System.out.println("user ---------------");

                    Furnace furnace = (Furnace) event.getBlock().getState();

                    if(furnace.getBurnTime() < 0) {
                        return;
                    }

                    ModBlock block = event.getModBlock();

                    EnergyHolderBlockData data = (EnergyHolderBlockData) block.getData();

                    //System.out.println("charge pre: " + data.getCharge());
                    //System.out.println("capacity pre: " + data.getCapacity());

                    int c = data.getCharge();
                    int t = Math.min(c, 24);

                    data.setCharge(c-t);

                    //System.out.println("charge post: " + data.getCharge());
                    //System.out.println("capacity post: " + data.getCapacity());

                    block.setData(data);
                    block.update();

                    //System.out.println(block.getData());

                    furnace.setCookSpeedMultiplier(t/18.0);

                    furnace.update();

                    //System.out.println("---------------");
                }
            });
        }

        @Override
        public int getCapacity() {
            return 1680;
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
