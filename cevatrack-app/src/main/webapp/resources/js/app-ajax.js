/**
 * ***************************************
 * Busy Status for p:ajax
 */
$(document).on("ajaxStart pfAjaxSend", function() {
    $("html").addClass("progress");
}).on("ajaxStop pfAjaxComplete", function() {
    $("html").removeClass("progress");
});

/* and for f:ajax in case I want to use that */
jsf.ajax.addOnEvent(function(data) {
    $("html").toggleClass("progress", data.status == "begin");
});