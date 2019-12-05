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

const defaultLat=46.585884;
const defaultLong=16.846554;
var newContentMode=false;

const map = new ol.Map({
    target: 'map',
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        })
    ],
    view: new ol.View({
        center: ol.proj.fromLonLat([16.846554,46.585884]),
        zoom: 10
    })
});

const newContentMarkerStyle = new ol.style.Style({
    image: new ol.style.Icon({
        scale: .1,
        src: circlePng
    })
});

var marker = new ol.Feature({
    geometry: new ol.geom.Point(
        ol.proj.fromLonLat([16.846554,46.585884])
    ),
});

var vectorSource = new ol.source.Vector({
    features: [marker]
});

var markerVectorLayer = new ol.layer.Vector({
    source: vectorSource,
    style: [newContentMarkerStyle]
});
map.addLayer(markerVectorLayer);


var modify = new ol.interaction.Modify({
    features: new ol.Collection([marker])
});

marker.on('change',function(){
    console.log('Feature Moved To:' + this.getGeometry().getCoordinates());
},marker);

map.addInteraction(modify);

map.on("moveend", function(e){
    reportMapViewState();
});



// Restore map if centerLat/Lon is not 0.0
if(mapState.centerLat!=0.0 && mapState.centerLon!='0.0'){
    map.getView().setCenter(ol.proj.fromLonLat([mapState.centerLon,mapState.centerLat]));
    map.getView().setZoom(mapState.zoomLevel);
    console.log('Set previous center position')
}
else{
    console.log('Set current location')
    getLocation();
}

function showMarkerLayer(lat,long){
    map.removeLayer(markerVectorLayer);
    map.addLayer(markerVectorLayer);
    if(lat==0 && long==0){
        marker.getGeometry().setCoordinates(map.getView().getCenter())
    }

    else{
        marker.getGeometry().setCoordinates(ol.proj.fromLonLat([long,lat]));
        map.getView().setCenter(ol.proj.fromLonLat([long,lat]));
    }

    map.getView().setZoom(13);
    newContentMode=true;
    setTimeout(syncBean,1000);
}

function syncBean(){
    _updateNewContentCoordinates([{name:'coordinates', value:ol.proj.transform(marker.getGeometry().getCoordinates(),'EPSG:3857', 'EPSG:4326')}]);
    if(newContentMode==true){
        setTimeout(syncBean,1000);
    }
}

function removeMarkerLayer(){
    map.removeLayer(markerVectorLayer);
}

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(reportUserPosition);
    } else {
        console.log('Geolocation is not supported');
    }
}

function reportUserPosition(position) {
    console.log(position.coords.latitude);
    console.log(position.coords.longitude);
    map.getView().setCenter(ol.proj.fromLonLat([position.coords.longitude,position.coords.latitude]));
}

function reportMapViewState(){
    console.log('reportMapViewState');
    let center=ol.proj.transform(map.getView().getCenter(),'EPSG:3857', 'EPSG:4326');
    mapState.centerLon=center[0];
    mapState.centerLat=center[1];
    mapState.zoomLevel=map.getView().getZoom();
    _reportViewState([{name:'mapState', value:JSON.stringify(mapState)}]);
}

//ol.proj.transform(marker.getGeometry().getCoordinates(),'EPSG:3857', 'EPSG:4326');
