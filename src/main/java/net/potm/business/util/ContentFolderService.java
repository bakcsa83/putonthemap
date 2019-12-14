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

package net.potm.business.util;

import net.potm.persistence.model.PhotoContent;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Singleton
@Startup
public class ContentFolderService implements Serializable {
    private static final int MAX_FILES_PER_DIR = 8500;
    private static final String DIRECTORY_NAME_PREFIX="photos";

    @PersistenceContext
    EntityManager em;

    @Inject
    PropertiesService propertiesService;

    private int fileCounter;
    private String currentDir;

    @PostConstruct
    public void init() {
        PhotoContent p;
        // Get all directories with the count of files in them
        List<Object[]> results = em.createQuery("SELECT photo.directory AS dir, COUNT(photo) AS total FROM PhotoContent AS photo GROUP BY photo.directory").getResultList();
        for (Object[] o : results) {
            var filesInDir = ((Number) o[1]).intValue();
            if (filesInDir < MAX_FILES_PER_DIR) {
                fileCounter = filesInDir;
                currentDir = (String) o[0];
                return;
            }
        }
        fileCounter = 0;
        currentDir= prepareNewDir();
    }

    public synchronized void incrementPhotoCounter(){
        fileCounter++;
        if(fileCounter==MAX_FILES_PER_DIR){
            currentDir=prepareNewDir();
            fileCounter=0;
        }
    }

    public String getPhotoDir(){
        return currentDir;
    }

    private String prepareNewDir(){
        var dateFormatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        var dirName=String.format("%s-%s",DIRECTORY_NAME_PREFIX, dateFormatter.format(new Date()));
        var dir=new File(propertiesService.getProperty(PropertiesService.CONTENT_DIR)+dirName);
        var dirCreated=dir.mkdirs();
        if(!dirCreated) throw new RuntimeException("Directory could not be created. "+dir);
        return dirName;
    }
}
