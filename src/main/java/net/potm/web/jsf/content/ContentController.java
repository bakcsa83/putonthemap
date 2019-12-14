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

package net.potm.web.jsf.content;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.GpsDirectory;
import net.potm.persistence.model.ContentBase;
import net.potm.persistence.service.ContentService;
import net.potm.web.jsf.user_session.UserSessionController;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class ContentController implements Serializable {

    @Inject
    UserSessionController usc;

    @Inject
    ContentService contentService;


    ContentBase selectedContent;
    Boolean deleteButtonRendered;
    Boolean addButtonRendered;
    String coordinates;

    UploadedFile uploadedFile;
    StreamedContent preview;

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destruct() {

    }

    public void updateUI() {
        deleteButtonRendered = false;
        addButtonRendered = false;

        if (usc.getAuthenticated()) {
            addButtonRendered = true;
            if (selectedContent != null) {
                selectedContent = contentService.fetchOwner(selectedContent);
                if (selectedContent.getOwner().getId() == usc.getUser().getId()) {
                    deleteButtonRendered = true;
                }
            }
        }
    }

    public void selectionChanged() {
        updateUI();
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException, ImageProcessingException {
//        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("upload ok. " + event.getFile().getFileName());

        preview = new DefaultStreamedContent(event.getFile().getInputstream(), "image/jpeg");
        Metadata metadata = ImageMetadataReader.readMetadata(event.getFile().getInputstream());


        for (Directory directory : metadata.getDirectories()) {
            System.out.println("Dir: " + directory);
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }

        var gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
        Double lat = 0.0;
        Double lng = 0.0;
        if (gpsDirectory != null) {
            GeoLocation location = gpsDirectory.getGeoLocation();
            lat = location.getLatitude();
            lng = location.getLongitude();
        }


        coordinates = "Lat: " + lat + "; Long: " + lng;
        System.out.println("Coordinated: " + coordinates);

        PrimeFaces.current().executeScript(String.format("showMarkerLayer(%s,%s);", lat.toString(), lng.toString()));
    }

    public ContentBase getSelectedContent() {
        return selectedContent;
    }

    public void setSelectedContent(ContentBase selectedContent) {
        this.selectedContent = selectedContent;
    }

    public Boolean getDeleteButtonRendered() {
        return deleteButtonRendered;
    }

    public void setDeleteButtonRendered(Boolean deleteButtonRendered) {
        this.deleteButtonRendered = deleteButtonRendered;
    }

    public Boolean getAddButtonRendered() {
        return addButtonRendered;
    }

    public void setAddButtonRendered(Boolean addButtonRendered) {
        this.addButtonRendered = addButtonRendered;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public StreamedContent getPreview() throws IOException {
        if(uploadedFile==null) return null;
        return new DefaultStreamedContent(uploadedFile.getInputstream(), "image/jpeg");
    }

    public void setPreview(StreamedContent preview) {
        this.preview = preview;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
