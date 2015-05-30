/**
 * 
 */

$(document).ready(function() {
	
	$("#dropdown").change(function() {
		  $("#dropdownform").submit();
		});
	
	
	$("#tabs").tabs();
	$( "#listBills" ).selectable({
		  selected: function( event, ui ) {
			  $("#paycheckSelected").val($(".ui-selected").attr("id"));
              $("#list").submit();
		  }
		});
    $("#listBills li").click(function(){
    	alert("sldjf");
    });

});