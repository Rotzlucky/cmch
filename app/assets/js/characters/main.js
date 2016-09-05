//Load common code that includes config, then load the app logic for this page.
requirejs(['../common'], function (common) {
    require(["jquery"], function($){
        $(".js-character-block").on("click", function (e) {
            characters.onclickHandler(e);
        });
    });
});

var characters = {};

characters.onclickHandler = function(e) {
    var button = jQuery(e.currentTarget);
};