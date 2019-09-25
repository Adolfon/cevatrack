$(document).ready(function() {
	$("div.user_right_menu_icon").click(function() {
		$("div.cjf_user_menu_item_right").toggle();
	});
});
$(window).resize(function() {
	if (window.innerWidth > 768) {
		$("div.cjf_user_menu_item_right").removeAttr("style");
		$("div.cjf_user_menu_item_right").show();
	} else{
		$("div.cjf_user_menu_item_right").hide();
	}
});





