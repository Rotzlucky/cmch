//Load common code that includes config, then load the app logic for this page.
requirejs(['../common'], function (common) {
    require(["jquery"], function($){
        $(".js-character-block").on("click", function (e) {
            characters.show_clickHandler(e);
        });

        jQuery(".js-live-search").on("keyup", function (e) {
            characters.liveSearchHandler(e);
        });

        jQuery(".js-new-character-btn").on("click", function (e) {
            characters.create_clickHandler(e);
        });
    });
});

var characters = {};

characters.show_clickHandler = function(e) {
    var button = jQuery(e.currentTarget);
    var route = jsRoutes.controllers.CharactersController.show(button.data("id"));
    window.location = route.url;
};

characters.create_clickHandler = function(e) {
    var route = jsRoutes.controllers.CharactersController.create();
    window.location = route.url;
};

characters.liveSearchHandler = function(e) {
    var query = jQuery(e.currentTarget).val();
    var container = jQuery(".js-characters-container");
    jQuery.ajax(jsRoutes.controllers.CharactersController.index(query)).done(
        function(data) {
            container.empty();
            container.append(data);
        }
    );
};