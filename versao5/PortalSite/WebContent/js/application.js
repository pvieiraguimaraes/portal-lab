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
    
//    $('#colecoes').liteaccordion()

})

}(window.jQuery)

function readmore(component){
    var id = component.id;
	$('#'+id).next('.readmore').slideToggle();
}