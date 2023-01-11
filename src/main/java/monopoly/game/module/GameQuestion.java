package monopoly.game.module;

import java.util.ArrayList;
import java.util.List;

public class GameQuestion {
    private GameQuestionType type;
    private PropertyInformation propertyInformation;
    private List<PropertyInformation> propertiesInformation;
    private boolean choose;

    public GameQuestion() {
        propertyInformation = new PropertyInformation();
        propertiesInformation = new ArrayList<>();
    }

    public GameQuestionType getType() {
        return type;
    }

    public void setType(GameQuestionType type) {
        this.type = type;
    }

    public PropertyInformation getPropertyInformation() {
        return propertyInformation;
    }

    public void setPropertyInformation(PropertyInformation propertyInformation) {
        this.propertyInformation = propertyInformation;
    }

    public List<PropertyInformation> getPropertiesInformation() {
        return propertiesInformation;
    }

    public void setPropertiesInformation(List<PropertyInformation> propertiesInformation) {
        this.propertiesInformation = propertiesInformation;
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }
}
