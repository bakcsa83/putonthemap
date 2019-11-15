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
// Generated May 16, 2019 10:46:13 PM by Hibernate Tools 5.2.11.Final

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Privilege generated by hbm2java
 */
@Entity
@Table(name = "privilege", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Privilege implements java.io.Serializable {

    /**
     * Can do ANYTHING. Including division by zero.
     */
    public static final long DO_ANYTHING = 0;

    /**
     * Can create, update, disable or delete users
     */
    public static final long MANAGE_USER = 1;

    /**
     * Can upload, edit and delete photos
     */
    public static final long MANAGE_PHOTOS = 2;

    /**
     * Can upload, edit and delete places. (e.g. Church, City hall, Railway station etc)
     */
    public static final long MANAGE_PLACES = 3;

    /**
     * Can upload, edit and delete shops. (e.g. Restaurants, bars, cafe, other shops)
     */
    public static final long MANAGE_SHOPS = 4;

    /**
     * Can upload, edit and delete events.
     */
    public static final long MANAGE_EVENTS = 5;

    private static final long serialVersionUID = -1946844855423531554L;
    private long id;
    private String name;
    private Set<Role> roles = new HashSet<>(0);

    public Privilege() {
    }

    public Privilege(long id) {
        this.id = id;
    }

    public Privilege(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Privilege(long id, String name, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }

    @Id

    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name", unique = true, nullable = false, length = 250)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_privilege", schema = "public", joinColumns = {
            @JoinColumn(name = "privilege", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "role", nullable = false, updatable = false)})
    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}