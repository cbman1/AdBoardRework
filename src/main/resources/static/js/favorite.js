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
    const params = new URLSearchParams(document.location.search);
    const id = params.get('id');
    if (id) {
        var xhr = new XMLHttpRequest();
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
    }
}