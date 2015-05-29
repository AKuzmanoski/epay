/**
 * 
 */
$(document).ready(function() { 
	$(".selectable li").button();
	
	$(".selectable li").click(function () {
		$("#paycheckSelected").val($(this).attr("id"));
		 $("#patcheckListForm").submit();
	});
	
	$("input[type=button].prominent, input[type=submit].prominent").button();
	$("#documentForm").submit(function( event ) {
		if (!validateDocumentForm()) {
			if(event.preventDefault) event.preventDefault(); else event.returnValue = false;
		}
	});
	
	$("#fileName").blur(function() {
		validateNonEmpty($(this));
	});
	
	$("#file").blur(function() {
		validateNonEmpty($(this));
	});
});

function validateDocumentForm() {
	var isValid = true;
	isValid = validateNonEmpty($("#fileName"));
	isValid = validateNonEmpty($("#file"));
}

function validateNonEmpty(field) {
	return regexValidator(/.+/, field);
}

function regexValidator(reg, field) {
	if (!reg.test(field.val())) {
		field.next().css("display", "inline");
		return false;
	} else {
		field.next().css("display", "none");
		return true;
	}
}