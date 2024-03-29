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

package net.potm.test.service;


import net.potm.persistence.model.ContentTag;
import net.potm.persistence.service.ContentService;
import net.potm.persistence.service.ContentTagService;
import net.potm.persistence.service.PersonService;
import net.potm.test.util.TestUtils;
import net.potm.util.security.SecurityUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.List;

public class ContentServiceTest extends Arquillian {

    @Inject
    ContentService contentService;

    @Inject
    ContentTagService contentTagService;

    @Inject
    PersonService personService;

    @Deployment
    public static Archive<?> createTestArchive() {
        return TestUtils.prepareDeployment();
    }

    @Test
    public void persistenceTest() {
        var user1 = TestUtils.getTestPerson();
        personService.createPerson(user1);

        var photo1 = TestUtils.getTestPhoto1();
        photo1.setOwner(user1);
        contentService.createContent(photo1);
        Assert.assertNotNull(photo1.getId());

        contentService.deleteContent(photo1);
        var content = contentService.findContentById(photo1.getId());
        Assert.assertNull(content);

        personService.deletePerson(user1);
    }

    @Test(dependsOnMethods = {"persistenceTest"})
    public void contentTagTest() {
        var user1 = TestUtils.getTestPerson();
        personService.createPerson(user1);

        var photo1 = TestUtils.getTestPhoto1();
        photo1.setOwner(user1);

        var tag = new ContentTag();
        tag.setName(SecurityUtils.generateRandomString(8));
        contentTagService.createTag(tag);

        contentService.createContent(photo1);

        photo1.getTags().add(tag);
        photo1 = contentService.updateContent(photo1);
    }

    @Test(dependsOnMethods = {"contentTagTest"})
    public void searchTest() {
        //Remove old test data
        var contentList = contentService.findContent(null, null, null, TestUtils.getBoundingPolygonForP1_2(), null, 0, 1000);
        contentList.forEach(e -> contentService.deleteContent(e));

        //Create test data
        var user1 = TestUtils.getTestPerson();
        personService.createPerson(user1);

        var user2 = TestUtils.getTestPerson();
        personService.createPerson(user2);

        var photo1 = TestUtils.getTestPhoto1();
        photo1.setOwner(user1);
        contentService.createContent(photo1);

        var photo2 = TestUtils.getTestPhoto2();
        photo2.setOwner(user2);
        contentService.createContent(photo2);

        contentList = contentService.findContent(null, null, null, TestUtils.getBoundingPolygonForP1(), null, 0, 1000);
        Assert.assertEquals(contentList.size(), 1);
        Assert.assertEquals(contentService.fetchOwner(contentList.get(0)).getOwner().getId(), user1.getId());

        contentList = contentService.findContent(null, null, null, TestUtils.getBoundingPolygonForP2(), null, 0, 1000);
        Assert.assertEquals(contentList.size(), 1);
        Assert.assertEquals(contentService.fetchOwner(contentList.get(0)).getOwner().getId(), user2.getId());

        contentList = contentService.findContent(null, null, null, TestUtils.getBoundingPolygonForP1_2(), null, 0, 1000);
        Assert.assertEquals(contentList.size(), 2);

        contentList = contentService.findContent(user1, null, null, null, null, 0, 1000);
        Assert.assertEquals(contentList.size(), 1);
        Assert.assertEquals(contentService.fetchOwner(contentList.get(0)).getOwner().getId(), user1.getId());

        contentList = contentService.findContent(user2, null, null, null, null, 0, 1000);
        Assert.assertEquals(contentList.size(), 1);
        Assert.assertEquals(contentService.fetchOwner(contentList.get(0)).getOwner().getId(), user2.getId());

        contentList = contentService.findContent(user2, null, null, TestUtils.getBoundingPolygonForP2(), null, 0, 1000);
        Assert.assertEquals(contentList.size(), 1);

        contentList = contentService.findContent(user1, null, null, TestUtils.getBoundingPolygonForP2(), null, 0, 1000);
        Assert.assertEquals(contentList.size(), 0);

        var tag1 = new ContentTag();
        tag1.setName(SecurityUtils.generateRandomString(8));
        contentTagService.createTag(tag1);

        var tag2 = new ContentTag();
        tag2.setName(SecurityUtils.generateRandomString(8));
        contentTagService.createTag(tag2);

        photo1.getTags().add(tag1);
        contentService.updateContent(photo1);

        photo2.getTags().add(tag2);
        contentService.updateContent(photo2);

        contentList = contentService.findContent(user1, null, null, TestUtils.getBoundingPolygonForP1_2(), List.of(tag1), 0, 1000);
        Assert.assertEquals(contentList.size(), 1);

        contentList = contentService.findContent(user2, null, null, TestUtils.getBoundingPolygonForP1_2(), List.of(tag2), 0, 1000);
        Assert.assertEquals(contentList.size(), 1);

        contentList = contentService.findContent(user2, null, null, TestUtils.getBoundingPolygonForP1_2(), List.of(tag1), 0, 1000);
        Assert.assertEquals(contentList.size(), 0);

        personService.deletePerson(user1);
        personService.deletePerson(user2);
    }
}
