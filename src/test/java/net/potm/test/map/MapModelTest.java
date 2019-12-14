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

package net.potm.test.map;

import net.potm.web.jsf.map.ContentModel;
import net.potm.web.jsf.map.MapStateModel;
import org.testng.annotations.Test;

import java.io.IOException;

@Test
public class MapModelTest {

    public void ModelTest() throws IOException {
        MapStateModel m=new MapStateModel();
        m.setNewContentLat(1.11);
        m.setNewContentLong(2.23);
        m.setNewContentMode(true);

        ContentModel cm=new ContentModel("zb",22.33,44.55,"Http:thumb","www");
        cm.getTagList().add("tag1");
        cm.getTagList().add("tag2");

        m.getContentList().add(cm);

        System.out.println(m.toJSON());
    }
}
