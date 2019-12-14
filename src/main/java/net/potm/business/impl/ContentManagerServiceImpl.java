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

package net.potm.business.impl;

import net.potm.business.api.ContentManagerService;
import net.potm.business.util.ContentFolderService;
import net.potm.persistence.model.ContentBase;
import net.potm.persistence.model.Person;
import net.potm.persistence.model.PhotoContent;
import net.potm.persistence.service.PhotoContentService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.InputStream;

@Stateless
public class ContentManagerServiceImpl implements ContentManagerService {

    @Inject
    PhotoContentService photoContentService;

    @Inject
    ContentFolderService contentFolderService;

    @Override
    public PhotoContent saveTempPhoto(Person user, InputStream stream) {
        PhotoContent pc=new PhotoContent();
        pc.setOwner(user);
        pc.setDirectory(contentFolderService.getPhotoDir());

        return null;
    }

    @Override
    public ContentBase updateContent(Person user, ContentBase content) {
        return null;
    }

    @Override
    public void deleteContent(ContentBase contentBase) {

    }
}
