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

package net.potm.test.util;

import net.potm.geo.GeoTools;
import net.potm.persistence.model.Person;
import net.potm.persistence.model.PhotoContent;
import net.potm.persistence.model.ShareType;
import net.potm.security.SecurityUtils;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class TestUtils {
    /**
     * Workaround. https://issues.jboss.org/browse/ARQ-2144
     * The lines commented with "Workaround" shouldn't be needed
     *
     * @return
     */
    public static WebArchive prepareDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
                .withTransitivity().asFile();
        final PomEquippedResolveStage pomEquippedResolveStage = Maven.resolver().loadPomFromFile("pom.xml"); //Workaround
        return ShrinkWrap.create(WebArchive.class).addPackages(true, "net.potm")
                .addAsLibraries(pomEquippedResolveStage.resolve("org.testng:testng").withTransitivity().asFile()) //Workaround
                .addAsLibraries(files).addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsLibraries(files).addAsResource("META-INF/jboss-deployment-structure.xml", "META-INF/jboss-deployment-structure.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    public static String getRandomEmail(){
        return String.format("%s.%s@%s.net", SecurityUtils.generateRandomString(4),
                SecurityUtils.generateRandomString(5),SecurityUtils.generateRandomString(5));
    }

    public static Person getTestPerson(){
        return new Person(getRandomEmail(),
                SecurityUtils.generateRandomString(5),
                SecurityUtils.generateRandomString(5),
                SecurityUtils.generateRandomString(5),
                SecurityUtils.generateRandomString(5),
                SecurityUtils.generateRandomString(5),
                SecurityUtils.generateRandomString(5),
                Person.STATUS_NEW,
                0);
    }

    public static Point getTestPoint1(){
        return GeoTools.createPoint(46.581774 ,16.825882);
    }

    public static PhotoContent getTestPhoto1(){
        var photo=new PhotoContent();
        photo.setFile(UUID.randomUUID().toString());
        photo.setDirectory(UUID.randomUUID().toString());
        photo.setLocation(getTestPoint1());
        photo.setShareType(ShareType.GET_PRIVATE());
        photo.setTemporary(false);
        return photo;
    }

    public static PhotoContent getTestPhoto2(){
        var photo=new PhotoContent();
        photo.setFile(UUID.randomUUID().toString());
        photo.setDirectory(UUID.randomUUID().toString());
        photo.setLocation(getTestPoint2());
        photo.setShareType(ShareType.GET_PRIVATE());
        photo.setTemporary(false);
        return photo;
    }

    public static Polygon getBoundingPolygonForP1(){
        var coordinates= List.of(
                new Coordinate(46.583217, 16.805277, 0),
                new Coordinate(46.586993, 16.832558, 0),
                new Coordinate(46.572716, 16.830671, 0),
                new Coordinate(46.572480, 16.784687, 0),
                new Coordinate(46.583217, 16.805277, 0));

        return GeoTools.getBoundingPolygon(coordinates);
    }

    public static Point getTestPoint2(){
        return GeoTools.createPoint(46.585884 ,16.846554);
    }

    public static Polygon getBoundingPolygonForP2(){
        var coordinates= List.of(
                new Coordinate(46.588815, 16.842796, 0),
                new Coordinate(46.589435, 16.848973, 0),
                new Coordinate(46.584303, 16.849274, 0),
                new Coordinate(46.585070, 16.841509, 0),
                new Coordinate(46.588815, 16.842796, 0));

        return GeoTools.getBoundingPolygon(coordinates);
    }

    public static Polygon getBoundingPolygonForP1_2(){
        var coordinates= List.of(
                new Coordinate(46.588585, 16.817045, 0),
                new Coordinate(46.590060, 16.851926, 0),
                new Coordinate(46.573247, 16.850554, 0),
                new Coordinate(46.573601, 16.816752, 0),
                new Coordinate(46.588585, 16.817045, 0));

        return GeoTools.getBoundingPolygon(coordinates);
    }
}
