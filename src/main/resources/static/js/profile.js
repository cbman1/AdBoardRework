let reviewsButton = document.getElementById('reviewsButton');
const params = new URLSearchParams(document.location.search);
const id = params.get('id');
console.log(id);
reviewsButton.addEventListener('click', function() {
    if (id) {
        window.location.href = '/profile/reviews?id=' + id;
    } else {
        alert('Параметр "id" не найден');
    }
});

let chatButton = document.getElementById('chatButton');
chatButton.addEventListener('click', function() {
    if (id) {
        window.location.href = '/chats/new/' + id;
    } else {
        alert('Параметр "id" не найден');
    }
});

function toggleButton(isActive) {
    var csrfToken = getCsrfToken()
    var csrfHeader = "XSRF-TOKEN"
    var email = document.getElementById('idForRedirect').value;

    $.ajax({
        type: "POST",
        url: "/profile",
        data: {
            isActive: isActive,
            email: email
        },
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function(response) {
            var replacement = $(response).find('#productCards').html()
            $('#productCards').html(replacement);
            console.log(response);
        },
        error: function(xhr, status, error) {
            console.log(xhr.responseText);
        }
    });

    // $.ajax({
    //     url: "/profile",
    //     type: "GET",
    //     success: function(response) {
    //         $("#productCards").html(response);
    //     },
    //     error: function(xhr, status, error) {
    //         console.error("Ошибка:", status, error);
    //     }
    // });


    if (isActive) {
        document.getElementById("activeButton").classList.add("active");
        document.getElementById("soldButton").classList.remove("active");

    } else {
        document.getElementById("activeButton").classList.remove("active");
        document.getElementById("soldButton").classList.add("active");
    }
}

function getCsrfToken() {
    var csrfToken = null;
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.startsWith('XSRF-TOKEN=')) {
            csrfToken = cookie.substring('XSRF-TOKEN='.length, cookie.length);
            break;
        }
    }
    return csrfToken;
}