var options = {
	currentPage : 1,
	totalPages : 10,
	onPageChanged : function(e, oldPage, newPage) {
		$.ajax({
					url:"../?page=containerdetalhecolecao&itemid="+getItemid()+"&action=ajax&pagina="+newPage+"&nPagina=3",
					success:function(result){
						$("#colectionsdetails").html(result);
					}
				});
	}
}

function getItemid(){
	var url = window.location.pathname;
	var index = url.lastIndexOf("/");
	return url.substring(index + 1, url.length);
}

$('#paginador').bootstrapPaginator(options);