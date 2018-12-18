$(document).ready(main);

function main() {
    let updateSocket = getWebSocketCrossBrowser(getUpdateUrl());
    updateSocket.onmessage = updateMessageHandler;
}

function updateMessageHandler(message) {
    let object = JSON.parse(message.data);
    let name = document.getElementById('name');
    let newName = document.createElement('a');
    newName.id = 'name';
    newName.innerText = object.name;
    name.parentNode.replaceChild(newName, name);
    if (object.description != null) {
        let description = document.getElementById('albumDescription');
        let newDescription = document.createElement('a');
        newDescription.id = 'albumDescription';
        newDescription.innerText = object.description;
        description.parentNode.replaceChild(newDescription, description);
        let score = document.getElementById('albumScore');
        let newScore = document.createElement('a');
        newScore.id = 'albumScore';
        newScore.innerText = object.score;
        score.parentNode.replaceChild(newScore, score);
        let reviewTable = document.getElementById('reviewTable');
        let reviews = object.reviews;
        let newReviewTable = document.createElement('table');
        newReviewTable.id = 'reviewTable';
        let row = newReviewTable.insertRow(0);
        row.insertCell(0).innerHTML = 'Review';
        row.insertCell(1).innerHTML = 'Score';
        for (let i in reviews) {
            let row = newReviewTable.insertRow();
            row.insertCell(0).innerHTML = reviews[i].review;
            row.insertCell(1).innerHTML = reviews[i].score;
        }
        reviewTable.parentNode.replaceChild(newReviewTable, reviewTable);
    }
}

function getUpdateUrl() {
    let loc = window.location, new_uri;
    if (loc.protocol === "https:") {
        new_uri = "wss:";
    } else {
        new_uri = "ws:";
    }
    new_uri += "//" + loc.host + "/dropmusic/liveUpdate";
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