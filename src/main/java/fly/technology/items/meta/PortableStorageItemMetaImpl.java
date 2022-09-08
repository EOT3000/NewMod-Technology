package fly.technology.items.meta;

import fly.newmod.NewMod;
import fly.newmod.api.item.ItemManager;
import fly.newmod.api.item.meta.DefaultModItemMeta;
import fly.newmod.api.item.meta.ModItemMeta;
import fly.newmod.api.item.meta.ModItemMetaSerializer;
import fly.newmod.api.item.type.ModItemType;
import fly.technology.TechnologyPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class PortableStorageItemMetaImpl extends ModItemMeta.AbstractModItemMeta implements PortableStorageItemMeta {
    private int charge;

    public PortableStorageItemMetaImpl(ModItemType type, int charge) {
        super(type);

        this.charge = charge;
    }

    @Override
    public int getCharge() {
        return charge;
    }

    @Override
    public void setCharge(int charge) {
        this.charge = charge;
    }

    @Override
    public ModItemMeta cloneItem() {
        return new PortableStorageItemMetaImpl(getType(), charge);
    }

    @Override
    public boolean isAcceptable(ModItemMeta modItemMeta) {
        return modItemMeta instanceof PortableStorageItemMeta && ((PortableStorageItemMeta) modItemMeta).getCharge() == charge;
    }


    public static class PortableStorageItemMetaSerializer extends ModItemMetaSerializer<PortableStorageItemMetaImpl> {
        public static final NamespacedKey CHARGE = new NamespacedKey(TechnologyPlugin.get(), "charge");
        public static final NamespacedKey RANDOM = new NamespacedKey(TechnologyPlugin.get(), "random");
        public static final NamespacedKey TIME = new NamespacedKey(TechnologyPlugin.get(), "time");

        public PortableStorageItemMetaSerializer() {
            super(PortableStorageItemMetaImpl.class);
        }

        @Override
        public PortableStorageItemMetaImpl getItemMeta(PersistentDataContainer container) {
            ItemManager manager = NewMod.get().getItemManager();

            return new PortableStorageItemMetaImpl(manager.getType(container), container.getOrDefault(CHARGE, PersistentDataType.INTEGER, 0));
        }

        @Override
        public PortableStorageItemMetaImpl defaultMeta(ModItemType type) {
            return new PortableStorageItemMetaImpl(type, 0);
        }

        @Override
        public boolean applyMeta(ItemStack stack, PortableStorageItemMetaImpl defaultModItemMeta) {
            ItemManager manager = NewMod.get().getItemManager();

            if(defaultModItemMeta.getType().equals(manager.getType(stack))) {
                return false;
            }

            PersistentDataContainer container = stack.getItemMeta().getPersistentDataContainer();

            container.set(CHARGE, PersistentDataType.INTEGER, defaultModItemMeta.charge);
            container.set(RANDOM, PersistentDataType.INTEGER, new Random().nextInt());
            container.set(TIME, PersistentDataType.LONG, System.currentTimeMillis());

            return true;
        }
    }
}
