(function (global, $, undefined) {
    'use strict';

    var deletePackage = function(e) {
        var id = $(this).data('id');
        if (id && confirm("Do you want to delete this package?")) {
            $.ajax({
                url: "/packages/" + id + "/",
                type: "DELETE",
                success: function() {
                    window.location.href = "/home/";
                }
            });
        }
    };

    var init = function() {
        $('body').on('click', 'a.delete-package-btn', deletePackage);
    };

    global.Module.Package = init;
})(window, window.jQuery);
