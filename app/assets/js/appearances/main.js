//Load common code that includes config, then load the app logic for this page.
requirejs(['../common'], function (common) {
    require(["jquery"], function($){
        $(".js-get-more-btn").on("click", function (e) {
            appearances.getMoreHandler(e);
        });
    });
});

var appearances = {};

appearances.getMoreHandler = function(e) {
    var appearances_box = jQuery(".character-appearances");
    $.ajax(jsRoutes.controllers.Application.more(1)).done(
        function (data) {
            jQuery.each(jQuery(data).children(), function(index, item) {
                appearances_box.append(item);
            });
        }
    );
};