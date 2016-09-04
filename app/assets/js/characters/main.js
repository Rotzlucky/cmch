//Load common code that includes config, then load the app logic for this page.
requirejs(['../common'], function (common) {
    require(["jquery", "bootstrap"], function($, bootstrap){
        console.log("Title is : " + $('h1').text());

    });
});