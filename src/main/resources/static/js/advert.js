ymaps.ready(function(){
    var coordinates = document.getElementById('coordinates').value;
    var coordinatesArray = coordinates.split(",");
    var myReverseGeocoder = ymaps.geocode([coordinatesArray[0],coordinatesArray[1]]);
    myReverseGeocoder.then(function (res) {
        var address = res.geoObjects.get(0).properties.get('text');
        document.getElementById('address').innerText = address;
    });
});