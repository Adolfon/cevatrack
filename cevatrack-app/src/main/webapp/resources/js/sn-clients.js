/**
 * javascript for the clients smartnet view
 
if (sessionStorage.getItem("sn-save")) {
	$("#sn-save").html(sessionStorage.getItem("sn-save"));	
}


// Save the search result table when leaving the page.
$(window).unload(function () {
	//console.log('saving to local storage');
	sessionStorage.setItem("sn-save", $("#sn-save").html());
});

*/