/*globals window */
(function(global, $) {
    'use strict';

    var init = function() {
        var pageName = $('.container[data-page-name]:last').attr('id');
        console.log('pageName = ' + pageName);
        if (pageName && global.Module.hasOwnProperty(pageName)) {
            console.log('load module : ' + pageName);
            global.Module[pageName]();
        }
    };

    $(document).ready(init);

})(window, window.jQuery);
