ymaps.ready(init);
function init(){
    var myMap = new ymaps.Map("map", {
        center: [55.76, 37.64],
        zoom: 7
    });

    var myCollection = new ymaps.GeoObjectCollection();

    myMap.events.add('click', function(e) {
        myCollection.removeAll();
        let coords = e.get('coords');
        document.getElementById('coordinates').value = coords;
        let placemark = new ymaps.Placemark(coords, {
            'preset': 'islands#blueDotIcon'
        });
        myCollection.add(placemark)
        myMap.geoObjects.add(myCollection)
    })

    var geolocation = ymaps.geolocation;

    geolocation.get({
        // Выставляем опцию для определения положения по ip
        provider: 'yandex',
        // Карта автоматически отцентрируется по положению пользователя.
        mapStateAutoApply: true,
        // Включим автоматическое геокодирование результата.
        autoReverseGeocode: true
    }).then(function (result) {

        // Выведем результат геокодирования.
        myMap.geoObjects.add(result.geoObjects);

        var address = result.geoObjects.get(0).properties.get('text');
        document.getElementById('address').innerText = address;

        // Выведем в консоль результат геокодирования.
        console.log(result.geoObjects.get(0).properties.get('metaDataProperty'));

    });
}