package fly.technology.setup;

import fly.technology.blocks.EnergyManagerItem;
import fly.technology.blocks.EnergyReceiverItem;
import fly.technology.blocks.EnergySenderItem;
import fly.technology.blocks.ThinCableItem;
import fly.technology.items.Multimeter;

public class TechnologyAddonSetup {
    public static void init() {

    }

    public static final ThinCableItem THIN_CABLE = new ThinCableItem();
    public static final EnergyManagerItem ENERGY_MANAGER = new EnergyManagerItem();
    public static final EnergyReceiverItem ENERGY_RECEIVER = new EnergyReceiverItem();
    public static final EnergySenderItem ENERGY_SENDER = new EnergySenderItem();

    public static final Multimeter MULTIMETER = new Multimeter();
}
