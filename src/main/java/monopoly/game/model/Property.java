package monopoly.game.model;

import monopoly.game.module.PropertyInformation;

public class Property {
    private final PropertyInformation propertyInformation;
    private int amount;

    public Property(PropertyInformation propertyInformation) {
        this.propertyInformation = propertyInformation;
    }

    public PropertyInformation getPropertyInformation() {
        return propertyInformation;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
