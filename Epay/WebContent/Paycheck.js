/**
 * 
 */

$(document).ready(function() {


	$("input[type=text]").each(function(){
		if($(this).val()!=""){
			$(this).attr("readonly",true);
		}
	
	});

});