//Load common code that includes config, then load the app logic for this page.
requirejs(['../common'], function (common) {
    require(["jquery", "bootstrap"], function($, bootstrap){
        requirejs(['characters/CharacterHelper'], function () {
            var helper = new CharacterHelper();

            jQuery(".js-delete-btn").on("click", function (e) {
                helper.confirmDelete_Handler(e, function () {
                    var url = jsRoutes.controllers.CharactersController.index("").url;
                    window.location = url;
                });
            });
        });
    });
});
