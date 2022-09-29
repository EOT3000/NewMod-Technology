package fly.technology.listeners;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import fly.newmod.NewMod;
import fly.newmod.api.block.BlockManager;
import fly.newmod.api.block.ModBlock;
import fly.newmod.api.block.type.ModBlockType;
import fly.technology.blocks.EnergyComponent;
import fly.technology.blocks.data.EnergyHolderBlockData;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DebugListener implements Listener {
    @EventHandler
    public void tick(ServerTickStartEvent event) {
        //System.out.println("tick");

        if(event.getTickNumber() % 20 != 0) {
            return;
        }

        BlockManager manager = NewMod.get().getBlockManager();

        int total = 0;

        for(ModBlockType type : manager.getBlocks()) {
            //System.out.println(type);

            if(type instanceof EnergyComponent) {
                //System.out.println("good: " + type);

                for(Location location : manager.getAllBlocksOfType(type.getId().asString())) {
                    ModBlock block = new ModBlock(location.getBlock());

                    total+=((EnergyHolderBlockData) block.getData()).getCharge();
                }
            }
        }

        System.out.println(total);
    }
}
