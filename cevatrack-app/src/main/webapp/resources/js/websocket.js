var webSocketUrl = document.getElementById("inh_webSocketUrl").value;
var webSocket = new WebSocket(webSocketUrl + "/todo");
function sendMsg(owner) {
	console.log("Name: " + owner);
	var textAnswer = document.getElementById("frm_addToDo:txt_owner");
	console.log(textAnswer);
	if (textAnswer.value.length != 0) {
		webSocket.send("You have new Todo " + "owner:" + owner);

	}
}
webSocket.onmessage = function(message) {

	var x = document.getElementById("snackbar");
	console.log("message reviewed");
	x.innerHTML = message.data;
	x.className = "show";
	setTimeout(function() {
		x.className = x.className.replace("show", "");
	}, 3000);

}

webSocket.onopen = function() {
	console.log("connection opened");
};

webSocket.onclose = function() {
	console.log("connection closed");
	webSocket.close();
};

webSocket.onerror = function wserror(message) {
	console.log("error: " + message);
}