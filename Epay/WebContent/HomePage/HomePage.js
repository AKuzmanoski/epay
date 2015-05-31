/**
 * 
 */

$(document).ready(function() {
	$(".date").datepicker({
		altField : "#actualDate"
	});

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
	
	$("#accountnumber").blur(function() {
		validateAccountNumber($(this));
	});
	
	$("#datefrom").blur(function() {
		isValidDate($(this));
	});
	
	$("#dateto").blur(function() {
		isValidDate($(this));
	});
	
	$("#registration").submit(function(event) {
		if (!validate()) {
			if(event.preventDefault) event.preventDefault(); else event.returnValue = false;
		}
	});

});

function validate() {
	return isValidAccountInfo();
}

function isValidAccountInfo() {
	var isValid = true;
	isValid = validateAccountNumber($("#accountnumber")) && isValid;
	isValid = isEmptyValidator($("#datefrom")) && isValid;
	isValid = isValidDate($("#datefrom")) && isValid;
	isValid = isEmptyValidator($("#dateto")) && isValid;
	isValid = isValidDate($("#dateto")) && isValid;
	
	return isValid;
}


function isEqualValidator(field1, field2) {
	if(field1.val() == field2.val()) {
		field1.next().css("display", "none");
		return true;
	} else {
		field1.next().css("display", "inline");
		return false;
	}
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

function isEmptyValidator(field) {
	return regexValidator(/.+/, field);
}

function isValidDate(field) {
	d = new Date(field.val());
	if (isNaN(d.getTime())) { // d.valueOf() could also work
		// date is not valid
		field.next().css("display", "inline");
		return false;
	} else {
		// date is valid
		field.next().css("display", "none");
		return true;
	}
}

function validateEmail(field) {
    var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    result = re.test(field.val());
    if (result) {
    	field.next().css("display", "none");
		return true;
    } else {
    	field.next().css("display", "inline");
		return false;
    }
}

function validateAccountNumber(field) {
	if (isEmptyValidator(field)) {
		field.next().css("display", "none");
		return true;
	} else {
		field.next().css("display", "inline");
		return false;
	}
}

function isIbanValid(value) {

    var lengthMap = getLengthMap();

    //cleanup
    value = value.toString().toUpperCase().replace(/\s/g, '').replace(/[-]/g, '');
    //check if alphanumeric
    if (!/^[a-zA-Z0-9]+$/.test(value)) return false;
    //extract countrycode
    var countryCode = value.substring(0, 2);
    //check if letter
    if (!/([a-z]+[\s\-]?)*/i.test(countryCode)) return false;
    //check string length
    if (value.length != lengthMap[countryCode]) return false;

    value = value.concat(value.substring(0, 4)).substring(4);
    value = value.replace(countryCode, countryCodeToStringValue(countryCode));

    return modulo(value, 97) == 1;

    function countryCodeToStringValue(countryCode) {
        return "".concat(((countryCode.charCodeAt(0)) - 55).toString() + (countryCode.charCodeAt(1) - 55).toString());
    }

    function modulo(divident, divisor) {
        var quantization = 12;
        while (quantization < divident.length) {
            var part = divident.substring(0, quantization);
            divident = (part % divisor) + divident.substring(quantization);
        }
        return divident % divisor;
    }

    function getLengthMap() {
        var lengthMap = {};
        lengthMap["AD"] = 24;
        lengthMap["AT"] = 20;
        lengthMap["AZ"] = 28;
        lengthMap["BH"] = 22;
        lengthMap["BE"] = 16;
        lengthMap["BA"] = 20;
        lengthMap["BR"] = 29;
        lengthMap["BG"] = 22;
        lengthMap["CR"] = 21;
        lengthMap["HR"] = 21;
        lengthMap["CY"] = 28;
        lengthMap["CZ"] = 24;
        lengthMap["DK"] = 18;
        lengthMap["DO"] = 28;
        lengthMap["EE"] = 20;
        lengthMap["FO"] = 18;
        lengthMap["FI"] = 18;
        lengthMap["FR"] = 27;
        lengthMap["DE"] = 22;
        lengthMap["GR"] = 27;
        lengthMap["GI"] = 23;
        lengthMap["GL"] = 18;
        lengthMap["GT"] = 28;
        lengthMap["HU"] = 28;
        lengthMap["IS"] = 26;
        lengthMap["IE"] = 22;
        lengthMap["IL"] = 23;
        lengthMap["IT"] = 27;
        lengthMap["JO"] = 30;
        lengthMap["KZ"] = 20;
        lengthMap["KW"] = 30;
        lengthMap["LV"] = 21;
        lengthMap["LB"] = 28;
        lengthMap["LI"] = 21;
        lengthMap["LT"] = 20;
        lengthMap["LU"] = 20;
        lengthMap["MK"] = 19;
        lengthMap["MT"] = 31;
        lengthMap["MR"] = 27;
        lengthMap["MU"] = 30;
        lengthMap["MC"] = 27;
        lengthMap["MD"] = 24;
        lengthMap["ME"] = 22;
        lengthMap["NL"] = 18;
        lengthMap["NO"] = 15;
        lengthMap["PK"] = 24;
        lengthMap["PS"] = 29;
        lengthMap["PL"] = 28;
        lengthMap["PT"] = 25;
        lengthMap["QA"] = 29;
        lengthMap["RO"] = 24;
        lengthMap["SM"] = 27;
        lengthMap["SA"] = 24;
        lengthMap["RS"] = 22;
        lengthMap["SK"] = 24;
        lengthMap["SI"] = 19;
        lengthMap["ES"] = 24;
        lengthMap["SE"] = 24;
        lengthMap["CH"] = 21;
        lengthMap["TN"] = 24;
        lengthMap["TR"] = 26;
        lengthMap["AE"] = 23;
        lengthMap["GB"] = 22;

        return lengthMap;
    }
}