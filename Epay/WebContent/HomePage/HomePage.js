/**
 * 
 */

$(document).ready(function() {

	$(".selectable li").button();

	$("#receivedListPaychecks li").click(function() {
		$("#receivedIdPaycheck").val($(this).attr("id"));
		$("#paycheckReceivedForm").submit();
	});
	
	$("#sentListPaychecks li").click(function() {
		$("#sentIdPaycheck").val($(this).attr("id"));
		$("#paycheckSentForm").submit();
	});
	
	$("#receivedListInvoices li").click(function() {
		$("#receivedIdInvoice").val($(this).attr("id"));
		$("#invoiceReceivedForm").submit();
	});
	
	$("#sentListInvoices li").click(function() {
		$("#sentIdInvoice").val($(this).attr("id"));
		$("#invoiceSentForm").submit();
	});

	$("input[type=button].prominent, input[type=submit].prominent").button();

	$("#dropdown").change(function() {
		$("#dropdownform").submit();
	});

	$("#tabs").tabs();
	$("#listBills").selectable({
		selected : function(event, ui) {
			$("#paycheckSelected").val($(".ui-selected").attr("id"));
			$("#list").submit();
		}
	});
	$("#listBills li").click(function() {
		alert("sldjf");
	});

});