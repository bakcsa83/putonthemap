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
import net.potm.persistence.service.ContentTagService;
import net.potm.security.SecurityUtils;
import net.potm.test.util.TestUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;

public class ContentTagServiceTest extends Arquillian {
    @Inject
    ContentTagService contentTagService;

    @Deployment
    public static Archive<?> createTestArchive() {
        return TestUtils.prepareDeployment();
    }

    @Test
    public void persistenceTest(){
        var myTagName= SecurityUtils.generateRandomString(8);
        var tag=new ContentTag(myTagName);
        contentTagService.createTag(tag);

        Assert.assertNotNull(tag.getId());

        var tags=contentTagService.getTagsByName(myTagName);
        Assert.assertEquals(tags.size(),1);

        contentTagService.deleteTag(tag);

        tags=contentTagService.getTagsByName(myTagName);
        Assert.assertEquals(tags.size(),0);
    }
}
