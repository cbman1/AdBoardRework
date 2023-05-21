function sellAdvert() {
    const params = new URLSearchParams(document.location.search);
    const id = params.get('id');
    if (id) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', '/advert/sold?id=' + id, true);
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

function returnInSell() {
    const params = new URLSearchParams(document.location.search);
    const id = params.get('id');
    if (id) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', '/advert/returnInSell?id=' + id, true);
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