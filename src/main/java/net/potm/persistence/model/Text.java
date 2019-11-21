/*
 *     (C) 2019 by Zoltan Bakcsa (zoltan@bakcsa.hu)
 *     This file is part of "putonthemap".
 *
 *     putonthemap is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     putonthemap is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with putonthemap.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.potm.persistence.model;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text1 = (Text) o;
        return id.equals(text1.id) &&
                text.equals(text1.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }
}
