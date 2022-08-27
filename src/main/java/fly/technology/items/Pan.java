package fly.technology.items;

import fly.newmod.NewMod;
import fly.newmod.api.event.ItemEventsListener;
import fly.newmod.api.event.both.ModBlockItemUseEvent;
import fly.newmod.api.item.type.ModItemType;
import fly.technology.TechnologyPlugin;
import fly.technology.setup.TechnologyAddonSetup;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Random;

public class Pan extends ModItemType {
    public Pan() {
        super(Material.BOWL, new NamespacedKey(TechnologyPlugin.get(), "pan"));

        name("Gold Pan", 0x806040);

        ShapedRecipe recipe = new ShapedRecipe(getId(), this.create());

        recipe.shape(" O ", "SAS", " O ");

        recipe.setIngredient('O', Material.BOWL);
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('A', Material.IRON_BARS);

        Bukkit.addRecipe(recipe);

        NewMod.get().getItemManager().registerItem(this);

        setListener(new ItemEventsListener() {
            private Random random = new Random();

            @Override
            public void onItemUseHighest(ModBlockItemUseEvent event) {
                if(event.getBlock() == null) {
                    return;
                }

                Material type = event.getBlock().getType();

                if(type.equals(Material.GRAVEL)) {
                    event.getBlock().setType(Material.SAND);

                    ItemStack stack = new ItemStack(randomo(), random.nextInt(1)+1);

                    event.getBlock().getWorld().dropItem(event.getBlock().getLocation().clone().add(0,1,0), stack);
                } else if(type.equals(Material.SOUL_SAND)) {
                    event.getBlock().setType(Material.AIR);

                    ItemStack stack = new ItemStack(randomn(), random.nextInt(1)+1);

                    event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), stack);

                    event.getBlock().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.EXPERIENCE_ORB, CreatureSpawnEvent.SpawnReason.COMMAND, (x) -> {
                        ExperienceOrb orb = (ExperienceOrb) x;

                        orb.setExperience(random.nextInt(3)+1);
                    });
                }
            }

            private Material randomo() {
                if(random.nextDouble() < 0.5) {
                    return Material.FLINT;
                } else if(random.nextDouble() < 0.5) {
                    return Material.IRON_NUGGET;
                } else if(random.nextDouble() < 0.5) {
                    return Material.GOLD_NUGGET;
                } else {
                    return Material.QUARTZ;
                }
            }

            private Material randomn() {
                if(random.nextDouble() < 0.5) {
                    return Material.QUARTZ;
                } else if(random.nextDouble() < 0.9) {
                    return Material.GOLD_NUGGET;
                } else if(random.nextDouble() < 0.99) {
                    return Material.BLAZE_POWDER;
                } else if(random.nextDouble() < 0.5) {
                    return Material.NETHER_WART;
                } else {
                    return Material.GHAST_TEAR;
                }
            }
        });
    }
}
