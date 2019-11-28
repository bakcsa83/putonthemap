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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "share_type", schema = "public")
public class ShareType {

    public static final long PRIVATE_ID = 0;
    public static final long FRIENDS_ID = 1;
    public static final long PUBLIC_ID = 2;

    private long id;
    private String name;

    public ShareType() {
    }

    public ShareType(long id) {
        this.id = id;
    }

    public static final ShareType GET_PRIVATE() {
        return new ShareType(PRIVATE_ID);
    }

    public static final ShareType GET_FRIENDS() {
        return new ShareType(FRIENDS_ID);
    }

    public static final ShareType GET_PUBLIC() {
        return new ShareType(PUBLIC_ID);
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShareType shareType = (ShareType) o;
        return id == shareType.id &&
                name.equals(shareType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
