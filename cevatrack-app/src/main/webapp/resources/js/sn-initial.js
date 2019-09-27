/**
 *  Tidy up the sessionStorage to begin with
 */
if (!window.sessionStorage) {
	alert("This webpage needs session storage to function. Please use a browser with that facility.");
}

if (sessionStorage.getItem("sn-save")) {
	sessionStorage.removeItem("sn-save");	
}
