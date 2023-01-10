package monopoly.game.model;

public class Property {
    private final PropertyInformation propertyInformation;

    public Property(PropertyInformation propertyInformation) {
        this.propertyInformation = propertyInformation;
    }

    public PropertyInformation getPropertyInformation() {
        return propertyInformation;
    }
}
