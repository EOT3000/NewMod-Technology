package fly.technology.blocks;

import fly.newmod.NewMod;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.event.BlockEventsListener;
import fly.newmod.api.item.type.ModItemType;
import fly.newmod.utils.InventoryUtils;
import fly.technology.TechnologyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;

public class BlockPlacer extends ModBlockType {
    public BlockPlacer() {
        super(Material.DROPPER, new NamespacedKey(TechnologyPlugin.get(), "block_placer"));

        ModItemType item = ModItemType.createAndRegister(Material.DROPPER, TechnologyPlugin.get(), "block_placer", "Block Placer", 0xFF4000);

        item.setBlock(this);

        setListener(new BlockEventsListener() {
            @Override
            public void onDispenseTemp(BlockDispenseEvent event) {
                if (NewMod.get().getItemManager().getType(event.getItem()) == null) {
                    if (event.getItem().getType().isBlock() && event.getItem().getType().isSolid()) {
                        Block block = event.getBlock();

                        Directional dropper = (Directional) block.getBlockData();

                        if (!event.getBlock().getLocation().add(dropper.getFacing().getModX(), dropper.getFacing().getModY(), dropper.getFacing().getModZ()).getBlock().getType().equals(Material.AIR)) {
                            return;
                        }

                        event.setCancelled(true);


                        Dropper state = (Dropper) block.getState();

                        Bukkit.getScheduler().runTaskLater(TechnologyPlugin.get(), (x) -> {
                            state.getInventory().removeItem(event.getItem());
                        }, 2L);

                        state.update();

                        event.getBlock().getLocation().add(dropper.getFacing().getModX(), dropper.getFacing().getModY(), dropper.getFacing().getModZ()).getBlock().setType(event.getItem().getType());
                    }
                }
        }
        });

        ShapedRecipe recipe = new ShapedRecipe(getId(), item.create());

        recipe.shape("III", "CP ", "IRI");

        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('C', Material.COPPER_INGOT);
        recipe.setIngredient('P', Material.PISTON);
        recipe.setIngredient('R', Material.REDSTONE_WIRE);

        NewMod.get().getBlockManager().registerBlock(this);
    }
}
