var encodedFilePath = document.getElementById("inh_filePath").value;
var fileWebSocketUrl = document.getElementById("inh_fileWebSocketUrl").value;
console.log("Name: " + encodedFilePath);
var decodeFilePath = decodeURIComponent(encodedFilePath);
console.log("DECODE Name: " + decodeFilePath);
var filePath = atob(decodeFilePath);
var fileWebSocket = new WebSocket(fileWebSocketUrl + "/logFiles/" + filePath);
var divMsg = document.getElementById("msg-box");
var spinner = document.getElementById("spin")
keepAlive();

fileWebSocket.onmessage = function(message) {
	divMsg.innerHTML += message.data + "<br/>";
	window.scrollTo(0, document.body.scrollHeight);
	spinner.style.display = '';
}

function setupWebSocket() {
	console.log("Re opening websocket");
	fileWebSocket = new WebSocket(fileWebSocketUrl + "/logFiles/" + filePath);
}

function keepAlive() {
	if (fileWebSocket.readyState == fileWebSocket.OPEN) {
		fileWebSocket.send('ping');
		console.log("ping sent");
	}
	timerId = setTimeout(keepAlive, 5000);
}

function clearLog() {
	divMsg.innerHTML = '';
}

fileWebSocket.onopen = function() {
	console.log("file ws connection opened" + encodedFilePath);
};

fileWebSocket.onclose = function() {
	console.log("file ws connection closed" + encodedFilePath);
	setTimeout(setupWebSocket, 1000);
};

fileWebSocket.onerror = function wserror(message) {
	console.log("error: " + message);
}