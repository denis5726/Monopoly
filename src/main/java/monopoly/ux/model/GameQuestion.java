package monopoly.ux.model;

import monopoly.game.model.PropertyInformation;

import java.util.ArrayList;
import java.util.List;

public class GameQuestion {
    private QuestionType type;
    private PropertyInformation propertyInformation;
    private List<PropertyInformation> propertiesInformation;
    private String propertyName;
    private boolean choose;

    public GameQuestion() {
        propertyInformation = new PropertyInformation();
        propertiesInformation = new ArrayList<>();
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public PropertyInformation getPropertyInformation() {
        return propertyInformation;
    }

    public void setPropertyInformation(PropertyInformation propertyInformation) {
        this.propertyInformation = propertyInformation;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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
