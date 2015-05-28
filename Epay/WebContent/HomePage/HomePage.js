/**
 * 
 */

$(document).ready(function() {
	
	$("#dropdown").change(function() {
		  $("#dropdownform").submit();
		});
	$("#tabs").tabs();
	$("#listBills").selectable();

});