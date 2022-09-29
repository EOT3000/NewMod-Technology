package fly.technology.blocks.consumer;

import fly.newmod.api.block.ModBlock;
import fly.newmod.api.event.block.ModBlockTickEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;

public class PoweredFurnaceItem extends EnergyConsumerItem {
    public PoweredFurnaceItem(Material material, String id, String name, int color, int capacity, int maxUsage, float maxIncrease) {
        super(material, id, name, color, new PoweredFurnaceBlock(material, id, capacity, maxUsage, maxIncrease));
    }

    public static class PoweredFurnaceBlock extends EnergyConsumerBlock {
        private final int capacity;
        private final int maxUsage;
        private final float maxIncrease;

        public PoweredFurnaceBlock(Material material, String id, int capacity, int maxUsage, float maxIncrease) {
            super(material, id);

            this.capacity = capacity;
            this.maxUsage = maxUsage;
            this.maxIncrease = maxIncrease;

            {/*setListener(new BlockEventsListener() {
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
            });*/}
        }

        @Override
        public int getCapacity() {
            return capacity;
        }

        @Override
        public int getMaxUsage() {
            return maxUsage;
        }

        @Override
        public int getCurrentUsage(Block block, ModBlock modBlock) {
            return maxUsage;
        }

        @Override
        public boolean doTick(ModBlockTickEvent event, double percent) {
            Furnace furnace = (Furnace) event.getBlock().getState();

            if(furnace.getBurnTime() <= 0) {
                return false;
            }

            furnace.setCookSpeedMultiplier(percent*maxIncrease);

            furnace.update();

            return true;
        }
    }
}
