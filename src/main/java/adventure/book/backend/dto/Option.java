package adventure.book.backend.dto;

public class Option {

    private String description;
    private Integer gotoId;
    private Consequence consequence;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGotoId() {
        return gotoId;
    }

    public void setGotoId(Integer gotoId) {
        this.gotoId = gotoId;
    }

    public Consequence getConsequence() {
        return consequence;
    }

    public void setConsequence(Consequence consequence) {
        this.consequence = consequence;
    }
}
