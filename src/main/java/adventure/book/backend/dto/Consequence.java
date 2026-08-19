package adventure.book.backend.dto;

import adventure.book.backend.enums.ConsequenceType;

public class Consequence {

    private ConsequenceType type;
    private Integer value;
    private String text;

    public ConsequenceType getType() {
        return type;
    }

    public void setType(ConsequenceType type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}