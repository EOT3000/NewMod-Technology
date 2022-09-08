package fly.technology.items.meta;

import fly.newmod.api.item.meta.ModItemMeta;

public interface PortableStorageItemMeta extends ModItemMeta {
    int getCharge();

    void setCharge(int charge);
}
