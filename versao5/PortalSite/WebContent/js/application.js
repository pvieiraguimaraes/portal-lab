// NOTICE!! DO NOT USE ANY OF THIS JAVASCRIPT
// IT'S ALL JUST JUNK FOR OUR DOCS!
// ++++++++++++++++++++++++++++++++++++++++++

!function ($) {

  $(function(){

    var $window = $(window)

    // tooltip 
    $('.tooltip-equipe').tooltip()

  
    $('body').scrollspy()

    // carousel 
    $('#myCarousel').carousel();

    $('.readmore').hide();

})

}(window.jQuery)

function readmore(component){
    var id = component.id;
	$('#'+id).next('.readmore').slideToggle();
}