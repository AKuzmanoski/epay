/**
 * 
 */

$(document).ready(function() { 
	if($(".isPaid").attr("ispaid")=="true") {
		$(".isPaid").css("visibility", "visible");
	}
	
	$(".prom").button();
});