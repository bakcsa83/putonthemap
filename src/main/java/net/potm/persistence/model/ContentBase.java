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


import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "content_base", schema = "public")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ContentBase {
    private Long id;
    private Date createdOn;
    private Person owner;
    private ShareType shareType;
    private Point location;
    private Set<ContentTag> tags = new HashSet<ContentTag>(0);


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false, length = 29)
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_type", nullable = false)
    public ShareType getShareType() {
        return shareType;
    }

    public void setShareType(ShareType shareType) {
        this.shareType = shareType;
    }


    @Column(name = "location", columnDefinition = "geometry(Point,4326)", nullable = false)
    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "content_base_content_tag", schema = "public", joinColumns = {
            @JoinColumn(name = "content_base", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "content_tag", nullable = false, updatable = false)})
    public Set<ContentTag> getTags() {
        return tags;
    }

    public void setTags(Set<ContentTag> tags) {
        this.tags = tags;
    }
}
