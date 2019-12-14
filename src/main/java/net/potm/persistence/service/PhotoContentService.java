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

package net.potm.persistence.service;

import net.potm.business.util.ContentFolderService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Stateless
public class PhotoContentService {

    @Inject
    ContentFolderService contentFolderService;


    /**
     * @param stream
     * @param path     Absolute path
     * @param filename
     */
    public void savePhoto(InputStream stream, @NotNull String path, @NotNull String filename) {
        var file=new File(path+filename);
        if (file.exists()) throw new PhotoContentServiceException("File already exists");
        if (file.isDirectory()) throw new PhotoContentServiceException("File path points to a directory");

        try {
            byte[] buffer = new byte[stream.available()];
            OutputStream outStream = new FileOutputStream(file);
            outStream.write(buffer);
        } catch (Exception e) {
            throw new PhotoContentServiceException("File could not be saved.", e);
        }
    }

    public static class PhotoContentServiceException extends RuntimeException {
        public PhotoContentServiceException(String msg) {
            super(msg);
        }

        public PhotoContentServiceException(String msg, Exception e) {
            super(msg, e);
        }
    }
}
