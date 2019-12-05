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

package net.potm.web.jsf.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.omnifaces.cdi.Param;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named("mapController")
public class MapController implements Serializable {

    boolean newContentMode = false;
    @Param
    Double newContentLat = 0.0;

    @Param
    Double newContentLong = 0.0;

    MapStateModel stateModel=new MapStateModel();

    public void updateNewContentCoordinates() {
        ObjectMapper asd;
        var coordinates = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("coordinates");
        System.out.println("Update coordinates: " + coordinates);
        var coordinatesArray = coordinates.split(",");
        newContentLong = Double.valueOf(coordinatesArray[0]);
        newContentLat = Double.valueOf(coordinatesArray[1]);
        System.out.println("New coordinates: " + newContentLat + "; " + newContentLong);
    }

    public void reportMapViewState() throws JsonProcessingException {
        var mapStateJSON = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mapState");
        ObjectMapper mapper=new ObjectMapper();
        stateModel=mapper.readValue(mapStateJSON,MapStateModel.class);
    }

    public boolean isNewContentMode() {
        return newContentMode;
    }

    public void setNewContentMode(boolean newContentMode) {
        this.newContentMode = newContentMode;
    }

    public Double getNewContentLat() {
        return newContentLat;
    }

    public void setNewContentLat(Double newContentLat) {
        this.newContentLat = newContentLat;
    }

    public Double getNewContentLong() {
        return newContentLong;
    }

    public void setNewContentLong(Double newContentLong) {
        this.newContentLong = newContentLong;
    }

    public MapStateModel getStateModel() {
        return stateModel;
    }
}
