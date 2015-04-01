(function(global, $, undefined){
    "use strict";

    var init = function() {
        $('div#ResetPassword .form-forget-password').validate({
            rules: {
                password: {
                    required: true,
                    rangelength: [6, 30]
                },
                repeatPassword: {
                    equalTo: "#password"
                }
            }
        });
    };

    global.Module.ResetPassword = init;

})(window, window.jQuery);