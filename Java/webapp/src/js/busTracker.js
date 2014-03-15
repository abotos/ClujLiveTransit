/*
 * *************************************************************************
 * Copyright (C) FRS Belgium NV ("FRSGlobal"). All rights reserved.
 *
 * This computer program is protected by copyright law and international
 * treaties. Unauthorized reproduction or distribution of this program,
 * or any portion of it, may result in severe civil and criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 * *************************************************************************
 */

globalData = {};

function onPageReady()
{
    var stationId = getUrlVars()['stationId'];

    if (stationId)
    {
        globalData.stationId = stationId;

        $.ajax('stationInfo', {
            type : 'POST',
            dataType : 'json',
            data : {stationId : stationId},
            success : onStationInfoSuccess
        })
    }
    else
    {
        alert('No station specified in request');
    }
}

function onStationInfoSuccess(data, status, request)
{
    globalData.stationInfo = data;

    $('#station').text('Station: ' + data.displayName);

    var stationCoordinate = data.coordinate;

    var stationLatLng = new google.maps.LatLng(stationCoordinate.latitude, stationCoordinate.longitude);
    var mapOptions = {
        center : stationLatLng,
        zoom : 18,
        styles : [
            {
                featureType : 'transit.station.bus',
                stylers : [{visibility : 'off'}]
            }
        ]
    };
    var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
    globalData.map = map;

    var marker = new google.maps.Marker({
        position : stationLatLng,
        title : data.displayName
    });

    globalData.stationMarker = marker;

    // To add the marker to the map, call setMap();
    marker.setMap(map);

    google.maps.event.addListener(map, 'tilesloaded', function(){
        startBusInfoRefresh();
    });
}

function startBusInfoRefresh()
{
    updateBusInfo();
    globalData.refreshTask = setInterval(updateBusInfo, 5000);
}

function updateBusInfo()
{
    var mapBounds = globalData.map.getBounds();
    var bounds = {
        northEast : {
            latitude : mapBounds.getNorthEast().lat(),
            longitude : mapBounds.getNorthEast().lng()
        },
        southWest : {
            latitude : mapBounds.getSouthWest().lat(),
            longitude : mapBounds.getSouthWest().lng()
        }
    };

    $.ajax('busInfo', {
        type : 'POST',
        dataType : 'json',
        data : {stationId : globalData.stationId, mapBounds : JSON.stringify(bounds)},
        success : onBusInfoSuccess
    })
}

function onBusInfoSuccess(data, status, request)
{
    if (globalData.currentMarkers)
    {
        $.each(globalData.currentMarkers, function(index, marker){
            marker.setMap(null);
        });

        delete globalData.currentMarkers;
    }

    globalData.currentMarkers = [];
    $('#buses tr').remove();

    $.each(data, function(index, value){
        // TODO do something with the bus id
        var busId = value.busId;
        var busDisplayImage = 'images/' + value.busDisplayImage + '.png';

        var individualBusInfos = value.individualBusInfos;
        $.each(individualBusInfos, function(individualIndex, individualValue){
            var individualLatLng = new google.maps.LatLng(individualValue.coordinate.latitude, individualValue.coordinate.longitude);

            var individualMarker = new google.maps.Marker({
                position : individualLatLng,
                title : data.stationId,
                icon : busDisplayImage
            });

            globalData.currentMarkers.push(individualMarker);

            individualMarker.setMap(globalData.map);

            if (individualValue.approaching)
            {
                $('#buses').append('<tr>' +
                    '<td class="bus_icon"><img src="' + busDisplayImage + '"></td>' +
                    '<td class="bus">' + value.busName + '</td>' +
                    '<td class="eta">' + ((individualValue.timeToArrival !== -1) ? individualValue.timeToArrival : 'N/A') + ' minutes</td>' +
                    '<td class="info">' + (individualValue.inViewPort ? '' : 'Zoom out for location on map') + '</td>' +
                    '</tr>');
            }
        });
    });
}

// Read a page's GET URL variables and return them as an associative array.
function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}