package fly.technology.blocks;

import fly.newmod.NewMod;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.item.ModItemStack;
import fly.newmod.api.item.type.ModItemType;
import fly.technology.TechnologyPlugin;
import fly.technology.blocks.data.EnergyHolderBlockDataImpl;
import fly.technology.setup.TechnologyAddonSetup;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class EnergyReceiverItem extends ModItemType {
    public EnergyReceiverItem() {
        super(Material.TARGET, new NamespacedKey(TechnologyPlugin.get(), "energy_receiver"));

        name("Energy Receiver", 0xFF8060);

        setBlock(new EnergyReceiverBlock());

        NewMod.get().getItemManager().registerItem(this);
        NewMod.get().getBlockManager().registerBlock(getBlock());

        ShapedRecipe recipe = new ShapedRecipe(getId(), new ModItemStack(this).create());

        recipe.shape("RCR", "CWC", "ROR");

        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('C', new ModItemStack(TechnologyAddonSetup.THIN_CABLE).create());
        recipe.setIngredient('W', Material.WHITE_WOOL);
        recipe.setIngredient('O', Material.GOLD_INGOT);

        Bukkit.addRecipe(recipe);
    }

    public static class EnergyReceiverBlock extends ModBlockType implements EnergyComponent {
        public EnergyReceiverBlock() {
            super(Material.TARGET, new NamespacedKey(TechnologyPlugin.get(), "energy_receiver"), EnergyHolderBlockDataImpl.class);
        }

        @Override
        public EnergyComponentType getType() {
            return EnergyComponentType.RECEIVER;
        }

        @Override
        public int getCapacity() {
            return 400;
        }
    }
}
