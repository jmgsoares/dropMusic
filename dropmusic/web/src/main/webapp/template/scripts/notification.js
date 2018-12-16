$(document).ready(main);

function main() {
    let socket = getWebSocketCrossBrowser(getNotificationUrl());
    socket.onmessage = messageHandler;
}

function messageHandler(message) {
    alert(message.data);
}

function getNotificationUrl() {
    let loc = window.location, new_uri;
    if (loc.protocol === "https:") {
        new_uri = "wss:";
    } else {
        new_uri = "ws:";
    }
    new_uri += "//" + loc.host + "/dropmusic/notification";
    return new_uri;
}

function getWebSocketCrossBrowser(host) {
    if ('WebSocket' in window)
        websocket = new WebSocket(host);
    else if ('MozWebSocket' in window)
        websocket = new MozWebSocket(host);
    else {
        alert('Get a real browser which supports WebSocket.');
        websocket = null;
    }
    return websocket;
}