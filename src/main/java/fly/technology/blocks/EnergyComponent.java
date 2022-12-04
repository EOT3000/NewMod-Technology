package fly.technology.blocks;

public interface EnergyComponent {
    EnergyComponentType getType();

    int getCapacity();



    enum EnergyComponentType {
        SENDER,
        RECEIVER,
        CONSUMER,
        PRODUCER,
        STORAGE,
        MANAGER,
        SENDER_RECEIVER
    }
}
