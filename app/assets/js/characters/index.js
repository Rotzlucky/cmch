//Load common code that includes config, then load the app logic for this page.
requirejs(['../common'], function (common) {
    require(["jquery", "bootstrap", "autocomplete"], function ($, bootstrap, autocomplete) {
        $(".js-character-block").on("click", function (e) {
            characters.show_clickHandler(e);
        });

        jQuery(".js-live-search").on("keyup", function (e) {
            characters.live_searchHandler(e);
        });

        jQuery(".js-new-character-btn").on("click", function (e) {
            characters.create_clickHandler(e);
        });

        jQuery(".js-remove-character").on("click", function (e) {
            characters.confirmDelete_Handler(e);
        });

        if (jQuery(".js-modal-container").children().length > 0) {
            characters.openCreateModal();
        }
    });
});

var characters = {};

characters.show_clickHandler = function (e) {
    var button = jQuery(e.currentTarget);
    var route = jsRoutes.controllers.CharactersController.show(button.data("id"));
    window.location = route.url;
};

characters.live_searchHandler = function (e) {
    var query = jQuery(e.currentTarget).val();
    var container = jQuery(".js-characters-container");
    jQuery.ajax(jsRoutes.controllers.CharactersController.index(query)).done(
        function (data) {
            container.empty();
            container.append(data);
        }
    );
};

characters.autocomplete_Handler = function (element) {
    var e = jQuery(element);
    var route = jsRoutes.controllers.TeamController.teams();
    // Set-up the autocomplete widget.
    e.autocomplete({
        onSelect: function (data) {
            characters.autocomplete_selectHandler(data, jQuery(this));
        },
        width: 200,
        minLength: 1,
        serviceUrl: route.url,
        paramName: "query",
        transformResult: function (response) {
            return {
                suggestions: jQuery.map(jQuery.parseJSON(response), function (item) {
                    return {value: item.teamName, data: item.id};
                })
            };
        }
    });

};

characters.autocomplete_selectHandler = function (data, element) {
    var container = element.closest(".js-team-container");
    var existingElements = container.find(".js-team-element");

    var alreadyIn = false;
    jQuery.each(existingElements, function (index, item) {
        var existingElement = jQuery(item);
        if (existingElement.find("input[type='hidden']").val() == data.data) {
            alreadyIn = true;
        }
    });

    if (!alreadyIn) {
        jQuery.ajax(jsRoutes.controllers.TeamController.show(data.data)).done(
            function (template) {
                template = jQuery(template);
                var count = container.find(".js-team-element").length;
                var idInput = jQuery(template).find("input[type='hidden']");
                idInput.attr("name", "teamIds[" + count + "]");
                template.find(".js-remove").on("click", function (e) {
                    characters.removeTeam_clickHandler(e);
                });
                container.append(template);
            }
        );
    }

    element.val("");
};

characters.removeTeam_clickHandler = function (e) {
    var removeBtn = jQuery(e.currentTarget);
    removeBtn.closest(".js-team-element").remove();
};

characters.create_clickHandler = function (e) {
    var modal = jQuery("#modal-create-character");
    if (modal.length === 0) {
        jQuery.ajax(jsRoutes.controllers.CharactersController.create()).done(
            function (template) {
                jQuery(".js-modal-container").append(template);
                characters.openCreateModal();
            }
        );
    } else {
        characters.openCreateModal();
    }
};

characters.openCreateModal = function () {
    jQuery("#modal-create-character").modal('show');

    jQuery('input.autocomplete').each(function () {
        characters.autocomplete_Handler(this);
    });
};

characters.confirmDelete_Handler = function (e) {
    e.stopImmediatePropagation();

    var removeButton = jQuery(e.currentTarget);
    var id = removeButton.closest(".js-character-block").data("id");

    var modal = jQuery("#modal-confirm-delete");

    if (modal.length === 0) {
        jQuery.ajax(jsRoutes.controllers.CharactersController.deleteConfirm(id)).done(
            function (template) {
                jQuery(".js-modal-container").append(template);
                var modal = jQuery("#modal-confirm-delete");
                modal.modal('show');
                modal.find("a.btn-ok").on("click", function (e) {
                    characters.delete(e);
                });
            }
        );
    } else {
    }
    modal.modal('.show');
};

characters.delete = function (e) {
    var button = jQuery(e.currentTarget);
    var id = button.data("id");
    jQuery.ajax(jsRoutes.controllers.CharactersController.delete(id)).done(
        window.location.reload()
    );
};
