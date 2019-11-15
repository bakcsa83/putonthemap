package net.potm.business.model;

import javax.persistence.*;

@Entity
@Table(name = "text", schema = "public")
public class Text implements java.io.Serializable {

    private static final long serialVersionUID = 6987623483540642253L;
    private TextId id;
    private String text;

    public Text() {
    }

    public Text(TextId id, String text) {
        this.id = id;
        this.text = text;
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "key", column = @Column(name = "key", nullable = false, length = 50)),
            @AttributeOverride(name = "language", column = @Column(name = "language", nullable = false, length = 2))})
    public TextId getId() {
        return this.id;
    }

    public void setId(TextId id) {
        this.id = id;
    }

    @Column(name = "text", nullable = false, length = 500)
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
