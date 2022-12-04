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
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ShapedRecipe;

public class QuickBrewingStandItem extends EnergyConsumerItem {
    public QuickBrewingStandItem() {
        super(Material.BREWING_STAND, "quick_brewing_stand", "Quick Brewing Stand", 0x808080, new QuickBrewingStandBlock());

        ShapedRecipe recipe = new ShapedRecipe(getId(), this.create());

        recipe.shape("NNN", "TSM", "IRI");

        recipe.setIngredient('N', MetalsAddonSetup.COPPER_NUGGET.create());
        recipe.setIngredient('T', TechnologyAddonSetup.THIN_CABLE.create());
        recipe.setIngredient('S', Material.BREWING_STAND);
        recipe.setIngredient('M', TechnologyAddonSetup.MULTIMETER.create());
        recipe.setIngredient('I', MetalsAddonSetup.TITANIUM_INGOT.create());
        recipe.setIngredient('R', Material.REDSTONE);

        Bukkit.addRecipe(recipe);
    }

    public static class QuickBrewingStandBlock extends EnergyConsumerBlock {
        public QuickBrewingStandBlock() {
            super(Material.BREWING_STAND, "quick_brewing_stand");

            {/*setListener(new BlockEventsListener() {
                @Override
                public void onBlockTick(ModBlockTickEvent event) {
                    //System.out.println("user ---------------");

                    BrewingStand stand = (BrewingStand) event.getBlock().getState();

                    if (stand.getBrewingTime() <= 0) {
                        return;
                    }

                    ModBlock block = event.getModBlock();

                    EnergyHolderBlockData data = (EnergyHolderBlockData) block.getData();

                    //System.out.println("charge pre: " + data.getCharge());
                    //System.out.println("capacity pre: " + data.getCapacity());

                    int c = data.getCharge();
                    int t = Math.min(c, 42);

                    data.setCharge(c - t);

                    //System.out.println("charge post: " + data.getCharge());
                    //System.out.println("capacity post: " + data.getCapacity());

                    block.setData(data);
                    block.update();

                    //System.out.println(block.getData());

                    if (event.getTick() % 42 < c) {
                        stand.setBrewingTime(stand.getBrewingTime()-1);
                    }

                    stand.update();

                    //System.out.println("---------------");
                }
            });*/}
        }

        @Override
        public boolean doTick(ModBlockTickEvent event, double percent) {
            BrewingStand stand = (BrewingStand) event.getBlock().getState();

            if (stand.getBrewingTime() <= 0) {
                return false;
            }

            //if a lot of charge, then more likely to skip one tick, making it faster
            if (event.getTick() % 42 < percent*42) {
                stand.setBrewingTime(stand.getBrewingTime()-1);
            }

            stand.update();

            return true;
        }

        @Override
        public int getCapacity() {
            return 1200;
        }

        @Override
        public int getMaxUsage() {
            return 42;
        }

        @Override
        public int getCurrentUsage(Block block, ModBlock modBlock) {
            return 42;
        }

        //@Override
        public int energyHalfLife() {
            return 216000;
        }
    }
}
