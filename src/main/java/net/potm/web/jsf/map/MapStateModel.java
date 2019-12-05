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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapStateModel implements Serializable {
    Double newContentLat = 0.0;
    Double newContentLong = 0.0;
    Boolean newContentMode = false;
    Double centerLat = 0.0;
    Double centerLon = 0.0;
    Double zoomLevel = 0.0;
    List<ContentModel> contentList = new ArrayList<>();

    public MapStateModel() {
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

    public Boolean getNewContentMode() {
        return newContentMode;
    }

    public void setNewContentMode(Boolean newContentMode) {
        this.newContentMode = newContentMode;
    }

    public Double getCenterLat() {
        return centerLat;
    }

    public void setCenterLat(Double centerLat) {
        this.centerLat = centerLat;
    }

    public Double getCenterLon() {
        return centerLon;
    }

    public void setCenterLon(Double centerLon) {
        this.centerLon = centerLon;
    }

    public Double getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(Double zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public List<ContentModel> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentModel> contentList) {
        this.contentList = contentList;
    }

    public String toJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Serialization error.", e);
        }
    }
}
