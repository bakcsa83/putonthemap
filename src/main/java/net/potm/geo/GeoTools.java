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

package net.potm.geo;

import org.locationtech.jts.geom.*;

import java.util.List;

// TODO: Write unit tests
public class GeoTools {
    public static final int DEFAULT_SRID = 4326;

    public static GeometryFactory getGeometryFactory(int SRID) {
        return new GeometryFactory(new PrecisionModel(), SRID);
    }

    public static GeometryFactory getGeometryFactory() {
        return getGeometryFactory(DEFAULT_SRID);
    }

    public static Point createPoint(GeometryFactory gf, double lat, double lon) {
        return gf.createPoint(new Coordinate(lat, lon));
    }

    public static Point createPoint(double lat, double lon) {
        return getGeometryFactory().createPoint(new Coordinate(lat, lon));
    }

    public static Polygon getBoundingPolygon(List<Coordinate> coordinates) {
        var linearRing = getGeometryFactory().createLinearRing(
                coordinates.toArray(new Coordinate[coordinates.size()]));
        return getGeometryFactory().createPolygon(linearRing);
    }
}
