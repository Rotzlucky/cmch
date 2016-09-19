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

        if (jQuery(".js-modal-container").children().length > 0) {
            characters.openModal();
        }
    });
});

var characters = {};

characters.show_clickHandler = function (e) {
    var button = jQuery(e.currentTarget);
    var route = jsRoutes.controllers.CharactersController.show(button.data("id"));
    window.location = route.url;
};

characters.create_clickHandler = function (e) {
    jQuery.ajax(jsRoutes.controllers.CharactersController.create()).done(
        function (template) {
            jQuery(".js-modal-container").append(template);
            characters.openModal();
        }
    );
};

characters.openModal = function() {
    jQuery("#modal-create-character").modal('show');

    jQuery('input.autocomplete').each(function () {
        characters.autocomplete_Handler(this);
    });
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
    var teamIdsElement = container.find(".js-hidden-team-ids");
    var teamNamesElement = container.find(".js-team-names");

    var teamIds = teamIdsElement.val();
    var teamNames = teamNamesElement.val();

    if (teamNames.indexOf(data.value + ",") == -1) {
        if (teamIds.length === 0) {
            teamIds += data.data;
        } else {
            teamIds += "," + data.data;
        }

        if (teamNames.length === 0) {
            teamNames += data.value;
        } else {
            teamNames += "," + data.value;
        }

        teamIdsElement.val(teamIds);
        teamNamesElement.val(teamNames);
    }

    element.val("");
};
