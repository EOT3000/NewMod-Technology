package fly.technology.blocks.data;

import fly.newmod.api.block.data.ModBlockData;
import fly.newmod.api.block.type.ModBlockType;

public interface EnergyHolderBlockData extends ModBlockData {
    int getCharge();
    void setCharge(int charge);
    int addCharge(int chargeAdd);

    int getCapacity();
    void setCapacity(int capacity);
}
