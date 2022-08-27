package fly.technology.items;

import fly.newmod.NewMod;
import fly.newmod.api.item.texture.MetaModifier;
import fly.newmod.api.item.type.ModItemType;
import fly.technology.TechnologyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class BeheadingSword extends ModItemType implements Listener {
    public BeheadingSword() {
        super(Material.IRON_SWORD, new NamespacedKey("magicmodule", "beheading_sword"));

        name("Sword of Beheading", 0xFFFF00);

        addModifier(new MetaModifier<>(null, (o, itemMeta) -> {
            itemMeta.setUnbreakable(true);

            return itemMeta;
        }));

        NewMod.get().getItemManager().registerItem(this);

        Bukkit.getPluginManager().registerEvents(this, TechnologyPlugin.get());
    }

    @EventHandler
    public void onHit(EntityDeathEvent event) {
        if(event.getEntity().getKiller() != null) {
            if(NewMod.get().getItemManager().getType(event.getEntity().getKiller().getInventory().getItemInMainHand()) == this) {
                if(event.getEntity() instanceof Player) {
                    ItemStack drop = new ItemStack(Material.PLAYER_HEAD);

                    SkullMeta meta = (SkullMeta) drop.getItemMeta();

                    meta.setPlayerProfile(((Player) event.getEntity()).getPlayerProfile());

                    drop.setItemMeta(meta);

                    event.getDrops().add(drop);
                }
            }
        }
    }
}
