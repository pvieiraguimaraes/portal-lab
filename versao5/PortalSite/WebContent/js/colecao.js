// NOTICE!! DO NOT USE ANY OF THIS JAVASCRIPT
// IT'S ALL JUST JUNK FOR OUR DOCS!
// ++++++++++++++++++++++++++++++++++++++++++

//Slider Colections
function sliderColections() {
    $('#one').liteAccordion({
            onTriggerSlide : function() {
                this.find('figcaption').fadeOut();
            },
            onSlideAnimComplete : function() {
                this.find('figcaption').fadeIn();
            },
            autoPlay : true,
            pauseOnHover : true,
            theme : 'stitch',
            rounded : true,
            enumerateSlides : true
    }).find('figcaption:first').show();
    $('#two').liteAccordion();
    $('#three').liteAccordion({ theme : 'dark', rounded : true, enumerateSlides : true, firstSlide : 2, linkable : true, easing: 'easeInOutQuart' });
    $('#four').liteAccordion({ theme : 'light', firstSlide : 3, easing: 'easeOutBounce', activateOn: 'mouseover' });
    
};

$(document).ready(sliderColections);
