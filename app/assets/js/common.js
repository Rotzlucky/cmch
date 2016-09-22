(function(requirejs) {
    "use strict";

    requirejs.config({
        baseUrl : "js/lib",
        shim : {
            "bootstrap" : { "deps" :['jquery'] }
        },
        paths : {
            characters: '/assets/js/characters',
            "jquery" : "/webjars/jquery/1.11.1/jquery",
            "bootstrap" : "/webjars/bootstrap/3.3.6/js/bootstrap.min",
            "autocomplete" : "/webjars/jQuery-Autocomplete/1.2.7/jquery.autocomplete"
        }
    });

    requirejs.onError = function(err) {
        console.log(err);
    };
})(requirejs);

