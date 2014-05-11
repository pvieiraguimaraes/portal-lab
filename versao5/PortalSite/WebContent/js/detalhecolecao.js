var numberRegByPage = 10;
var initialPage = 1;

var options = {
	currentPage : initialPage,
	totalPages : getTotalPages(),
	onPageChanged : function(e, oldPage, newPage) {
		$.ajax({
			url : "../?page=containerdetalhecolecao&itemid=" + getItemid()
					+ "&action=ajax&pagina=" + newPage + "&nPagina=" + numberRegByPage,
			success : function(result) {
				$("#colectionsdetails").html(result);
				$("#infoTable").html(
						"Exibindo um total de "
								+ getTotalRegisters() + " registros.");
			}
		});

		$(document).ajaxSend(function() {
			$("#loading").css("display", "inline");
		});
		$(document).ajaxComplete(function(event, request, settings) {
			$("#loading").css("display", "none");
		});
	}
}

function isNumeric(str) {
	var er = /^[0-9]+$/;
	return (er.test(str));
}

function getTotalPages() {
	var x = getTotalRegisters() / numberRegByPage;
	if (isNumeric(x))
		return x;
	return parseInt(x) + parseInt(1);
}

function getTotalRegisters() {
	var total = document.getElementById("colectionCount").value;
	return total;
}

function getItemid() {
	var url = window.location.pathname;
	var index = url.lastIndexOf("/");
	return url.substring(index + 1, url.length);
}

$('#paginador').bootstrapPaginator(options);