/**
 * Created by marcelsteffen on 22.09.16.
 */
var CharacterHelper = function() {};

CharacterHelper.prototype.show_clickHandler = function (e) {
    var button = jQuery(e.currentTarget);
    var route = jsRoutes.controllers.CharactersController.show(button.data("id"));
    window.location = route.url;
};

CharacterHelper.prototype.live_searchHandler = function (e) {
    var query = jQuery(e.currentTarget).val();
    var container = jQuery(".js-characters-container");
    jQuery.ajax(jsRoutes.controllers.CharactersController.index(query)).done(
        function (data) {
            container.empty();
            container.append(data);
        }
    );
};

CharacterHelper.prototype.autocomplete_Handler = function (element) {
    var _this = this;
    var e = jQuery(element);
    var route = jsRoutes.controllers.TeamController.teams();
    // Set-up the autocomplete widget.
    e.autocomplete({
        onSelect: function (data) {
            _this.autocomplete_selectHandler(data, jQuery(this));
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

CharacterHelper.prototype.autocomplete_selectHandler = function (data, element) {
    var _this = this;
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
                    _this.removeTeam_clickHandler(e);
                });
                container.append(template);
            }
        );
    }

    element.val("");
};

CharacterHelper.prototype.removeTeam_clickHandler = function (e) {
    var removeBtn = jQuery(e.currentTarget);
    removeBtn.closest(".js-team-element").remove();
};

CharacterHelper.prototype.create_clickHandler = function (e) {
    var _this = this;
    var modal = jQuery("#modal-create-character");
    if (modal.length === 0) {
        jQuery.ajax(jsRoutes.controllers.CharactersController.create()).done(
            function (template) {
                jQuery(".js-modal-container").append(template);
                _this.openCreateModal();
            }
        );
    } else {
        _this.openCreateModal();
    }
};

CharacterHelper.prototype.openCreateModal = function () {
    var _this = this;
    jQuery("#modal-create-character").modal('show');

    jQuery('input.autocomplete').each(function () {
        _this.autocomplete_Handler(this);
    });
};

CharacterHelper.prototype.confirmDelete_Handler = function (e, callback) {
    e.stopImmediatePropagation();

    var _this = this;

    var removeButton = jQuery(e.currentTarget);
    var id = removeButton.data("id");

    var modal = jQuery("#modal-confirm-delete");

    if (modal.length === 0) {
        jQuery.ajax(jsRoutes.controllers.CharactersController.deleteConfirm(id)).done(
            function (template) {
                jQuery(".js-modal-container").append(template);
                var modal = jQuery("#modal-confirm-delete");
                modal.modal('show');
                modal.find("a.btn-ok").on("click", function (e) {
                    _this.delete(e, callback);
                });
            }
        );
    } else {
        modal.modal('.show');
    }
};

CharacterHelper.prototype.delete = function (e, callback) {
    var button = jQuery(e.currentTarget);
    var id = button.data("id");
    jQuery.ajax(jsRoutes.controllers.CharactersController.delete(id)).done(
        callback.call()
    );
};