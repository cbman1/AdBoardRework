function addFavorite() {
    const params = new URLSearchParams(document.location.search);
    const id = params.get('id');
    if (id) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', '/advert/add/favorite?id=' + id, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 202) {
                    console.log('Request sent successfully');
                } else {
                    console.log('Request failed');
                }
            }
        };
        xhr.send();
    }
}

function removeFavorite() {
    var xhr = new XMLHttpRequest();
    var idAdvert = document.getElementById('idAdvert').value;
    console.log(idAdvert);
    const params = new URLSearchParams(document.location.search);
    const id = params.get('id');
    console.log(id)
    if (id) {
        xhr.open('GET', '/advert/remove/favorite?id=' + id, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 202) {
                    console.log('Request sent successfully');
                } else {
                    console.log('Request failed');
                }
            }
        };
        xhr.send();
    } else {
        console.log(idAdvert);
        xhr.open('GET', '/advert/remove/favorite?id=' + idAdvert, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 202) {
                    var contentElement = document.getElementById('card');
                    contentElement.innerHTML = xhr.responseText;
                    console.log('Request sent successfully');
                } else {
                    console.log('Request failed');
                }
            }
        };
        xhr.send();
    }
}