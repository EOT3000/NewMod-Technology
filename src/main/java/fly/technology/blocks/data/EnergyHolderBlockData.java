package fly.technology.blocks.data;

import fly.newmod.api.block.data.ModBlockData;

public interface EnergyHolderBlockData extends ModBlockData {
    int getCharge();
    void setCharge(int charge);
    int addCharge(int chargeAdd);
    int removeCharge(int charge);

    int getCapacity();
    void setCapacity(int capacity);
}
