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
			  $("#typeOfItem").val($(".ui-selected").attr("type"));
              $("#list").submit();
		  }
		});
    $("#listBills li").click(function(){
    	alert("sldjf");
    });

});