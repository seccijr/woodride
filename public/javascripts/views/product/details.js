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
        var quantity = $("#confirm-product-dialog-quantity").val();
        $("#add-to-cart-quantity").val(quantity);
        $("#product-add-to-cart").click();
    });

    $("#confirm-product-dialog-more").click(function () {
        var quantity = $("#confirm-product-dialog-quantity").val();
        $("#confirm-product-dialog-quantity").val(parseInt(quantity) + 1);
    });

    $("#confirm-product-dialog-less").click(function () {
        var quantity = $("#confirm-product-dialog-quantity").val();
        var newQuantity = parseInt(quantity) - 1;
        $("#confirm-product-dialog-quantity").val(newQuantity > 0 ? newQuantity : 0);
    });
});