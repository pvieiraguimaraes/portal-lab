// NOTICE!! DO NOT USE ANY OF THIS JAVASCRIPT
// IT'S ALL JUST JUNK FOR OUR DOCS!
// ++++++++++++++++++++++++++++++++++++++++++

!function ($) {

  $(function(){

    var $window = $(window)

    // tooltip 
    $('.tooltip-equipe').tooltip()

    // tooltip glossario
    $('.tooltip-glossario').tooltip()
  
    $('body').scrollspy()

    // carousel 
    $('#myCarousel').carousel();

    $('.readmore').hide();
    
//    $('#myModal').modal('show');
    
//    $('#colecoes').liteaccordion();

})

}(window.jQuery)

function getUrlParameter(sParam) {
	var sPageURL = window.location.search.substring(1);
	var sURLVariables = sPageURL.split('&');
	for (var i = 0; i < sURLVariables.length; i++) {
		var sParameterName = sURLVariables[i].split('=');
		if (sParameterName[0] == sParam) {
			return sParameterName[1];
		}
	}
}

function readmore(component){
    var id = component.id;
	$('#'+id).next('.readmore').slideToggle();
}