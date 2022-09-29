package fly.technology.blocks.generators;

import fly.metals.setup.MetalsAddonSetup;
import fly.technology.setup.TechnologyAddonSetup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Random;

public class SolarPanelItem extends EnergyGeneratorItem {

    public SolarPanelItem() {
        super(Material.DAYLIGHT_DETECTOR, "solar_panel", "Solar Panel", 0x000080, new EnergyGeneratorBlock(Material.DAYLIGHT_DETECTOR, "solar_panel") {
            private final Random random = new Random();

            @Override
            public int getMaxGeneration() {
                return 12;
            }

            @Override
            public int getCurrentGeneration(Location location) {
                return location.getWorld().isDayTime() ? random.nextInt(3)+10 : 1;
            }

            @Override
            public int getCapacity() {
                return 240;
            }
        });

        ShapedRecipe recipe = new ShapedRecipe(getId(), this.create());

        recipe.shape("SSS", "SSS", "BBB");

        recipe.setIngredient('S', MetalsAddonSetup.SILICON_NUGGET.create());
        recipe.setIngredient('B', TechnologyAddonSetup.PHOTOVOLTAIC_BRICK.create());

        Bukkit.addRecipe(recipe);
    }
}
