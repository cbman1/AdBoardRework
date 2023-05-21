var imgElement = document.getElementById("avatarImage");
var newImage = new Image();
newImage.onload = function() {
    imgElement.src = newImage.src;
}

var newPath = '/images/uploads/avatar/' + userDto.avatarPath;
newImage.src = newPath;