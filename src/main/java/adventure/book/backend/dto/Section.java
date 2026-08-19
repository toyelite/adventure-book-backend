package adventure.book.backend.dto;

import adventure.book.backend.enums.SectionType;

import java.util.List;

public class Section {

    private Integer id;
    private String text;
    private SectionType type;
    private List<Option> options;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SectionType getType() {
        return type;
    }

    public void setType(SectionType type) {
        this.type = type;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}