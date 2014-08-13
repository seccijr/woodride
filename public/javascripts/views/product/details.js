$(document).ready(function(){
    $("#etalage").etalage({
        show_icon: false,
        thumb_image_width: 315,
        thumb_image_height: 350,
        source_image_width: 900,
        source_image_height: 1000,
        zoom_area_width: 725
    });

    $("#confirm-product-dialog-ok").click(function () {
        $("#product-add-to-cart").click();
    });
});