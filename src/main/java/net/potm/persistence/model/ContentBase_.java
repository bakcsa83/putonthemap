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

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

public class ContentBase_ {
    public static final String ID = "id";
    public static final String CREATED_ON = "createdOn";
    public static final String OWNER = "owner";
    public static final String SHARE_TYPE = "shareType";
    public static final String LOCATION = "location";
    public static final String TAGS = "tags";
    public static volatile SingularAttribute<ContentBase, Long> id;
    public static volatile SingularAttribute<ContentBase, String> createdOn;
    public static volatile SingularAttribute<ContentBase, Long> owner;
    public static volatile SingularAttribute<ContentBase, ShareType> shareType;
    public static volatile SingularAttribute<ContentBase, Point> location;
    public static volatile SetAttribute<Person, ContentTag> tags;

}
