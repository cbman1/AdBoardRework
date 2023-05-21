let stompClient;
const currentUserId = document.getElementById("current-user-id").value;
const currentUserUsername = document.getElementById("current-user-username").value;

window.onload = async function connect() {
    var socket = new SockJS("http://localhost/ws");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, function () {
        alert("error during connection");
    });

    const contactRows = document.getElementsByClassName("person");

    for (let i = 0; i < contactRows.length; i++) {
        contactRows[i].addEventListener("click", async function () {
            const url = "/chats/get/chat/" + contactRows.item(i).id.replace("contact-", "") + "/" + currentUserId;
            let response = await fetch(url);
            document.getElementById("chat-partial").innerHTML = await response.text();
        });
    }
}

function onConnected() {
    const destination = "/user/" + currentUserId + "/queue/messages";
    stompClient.subscribe(destination, onMessageReceived);
}

function send() {
    var recipientId = document.getElementById("otherUserId").value;
    var recipientUsername = document.getElementById("otherUserEmail").value;
    let message = document.getElementById("send-message").value;
    sendMessage(message, recipientId, recipientUsername);
    document.getElementById("send-message").value = "";
}

function onMessageReceived(message) {
    const msg = JSON.parse(message.body);
    if (msg.senderId.toString() === document.getElementById("other-user-id").innerText) {
        drawMessage(msg.content, new Date(msg.sendDate), msg.senderUsername, false);
        scrollMessages();
    }
}

function sendMessage(msg, recipientId, recipientUsername) {
    if (msg.trim() !== "") {
        const message = {
            senderId: currentUserId,
            recipientId: recipientId,
            senderUsername: currentUserUsername,
            recipientUsername: recipientUsername,
            content: msg.trim(),
            sendDate: new Date()
        };
        stompClient.send("/app/chat", {}, JSON.stringify(message));
        drawMessage(message.content, message.sendDate, "You", true);
        scrollMessages();
    }
}

function drawMessage(msg, date, sender, isMyMessage) {
    let list = document.getElementById("chat-messages");
    const newMessageBlock = document.createElement("div");
    newMessageBlock.classList.add("bubble", "pb-3")
    newMessageBlock.innerText = msg;

    if (isMyMessage) {
        newMessageBlock.classList.add("outgoing");
    } else {
        newMessageBlock.classList.add("incoming");
    }

    list.appendChild(newMessageBlock);
}

function scrollMessages() {
    const messagesBlock = document.getElementById("chat-messages");
    messagesBlock.scrollTop = messagesBlock.scrollHeight;
}

$(".person").on('click', function(){
    $(this).toggleClass('focus').siblings().removeClass('focus');
})