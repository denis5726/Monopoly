package monopoly.ux.model;

import monopoly.game.model.PropertyInformation;

public class GameQuestion {
    private QuestionType type;
    private PropertyInformation propertyInformation;

    private String propertyName;
    private boolean choose;

    public GameQuestion() {
        propertyInformation = new PropertyInformation();
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

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }
}
