$(document).ready(function() {
	console.log("map responsive starts!")
	if (window.innerWidth > 768) {						
		$('#map').css("height", "600px");		
	} else{						
		$('#map').css("height", "300px");
	}
});
$(window).resize(function() {
	if (window.innerWidth > 768) {
		//alert('hello map resize more than 768px size: ' + window.innerWidth);
		$('#map').css("height", "600px");		
	} else{
		//alert('hello map resize less than 768px: size: ' + window.innerWidth);
		$('#map').css("height", "300px");
	}
});			
