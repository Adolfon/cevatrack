$(document).ready(function() {
	console.log("map responsive starts!")
	if (window.innerWidth > 768) {						
		$('#map').css("height", "640px");
		$('#map_image_view').css("height", "640px");	
		$('#special_map_container').css("padding-top","0px");
		if (window.innerWidth < 1020) {
			$('#special_map_container').css("padding-top","20px");
		}
	} else{						
		if (window.innerWidth > 460) {
			$('#map').css("height", "440px");	
			$('#map_image_view').css("height", "440px");
			$('#special_map_container').css("padding-top","20px");
		}else{	
			$('#map').css("height", "300px");
			$('#map_image_view').css("height", "300px");
			$('#special_map_container').css("padding-top","20px");
		}
	}
});
$(window).resize(function() {
	if (window.innerWidth > 768) {
		//alert('hello map resize more than 768px size: ' + window.innerWidth);
		$('#map').css("height", "640px");	
		$('#map_image_view').css("height", "640px");
		$('#special_map_container').css("padding-top","0px");
		if (window.innerWidth < 1020) {
			$('#special_map_container').css("padding-top","20px");
		}
	} else{
		if (window.innerWidth > 460) {
			$('#map').css("height", "440px");	
			$('#map_image_view').css("height", "440px");
			$('#special_map_container').css("padding-top","20px");
		}else{		
			//alert('hello map resize less than 768px: size: ' + window.innerWidth);
			$('#map').css("height", "300px");
			$('#map_image_view').css("height", "300px");
			$('#special_map_container').css("padding-top","20px");
		}
	}
});			





