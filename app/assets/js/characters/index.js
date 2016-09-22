//Load common code that includes config, then load the app logic for this page.
requirejs(['../common'], function (common) {
    require(["jquery", "bootstrap", "autocomplete"], function ($, bootstrap, autocomplete) {
        requirejs(['characters/CharacterHelper'], function () {
            var helper = new CharacterHelper();

            jQuery(".js-character-block").on("click", function (e) {
                helper.show_clickHandler(e);
            });

            jQuery(".js-live-search").on("keyup", function (e) {
                helper.live_searchHandler(e);
            });

            jQuery(".js-new-character-btn").on("click", function (e) {
                helper.create_clickHandler(e);
            });

            jQuery(".js-remove-character").on("click", function (e) {
                helper.confirmDelete_Handler(e, function() {
                    window.location.reload();
                });
            });

            if (jQuery(".js-modal-container").children().length > 0) {
                helper.openCreateModal();
            }

        });
    });
});


