//The build will inline common dependencies into this file.

//For any third party dependencies, like jQuery, place them in the lib folder.

//Configure loading modules from the lib directory,
//except for 'app' ones, which are in a sibling
//directory.
(function(requirejs) {
    "use strict";

    requirejs.config({
        baseUrl : "js/libs",
        shim : {
            "bootstrap" : { "deps" :['jquery'] }
        },
        paths : {
            characters: '../characters',
            "jquery" : "/webjars/jquery/1.11.1/jquery.min",
            "bootstrap" : "/webjars/bootstrap/3.3.6/js/bootstrap.min",
        }
    });

    requirejs.onError = function(err) {
        console.log(err);
    };
})(requirejs);

