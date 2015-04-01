(function(global, $, undefined){
    "use strict";

    var init = function() {
        $('div#ForgetPassword .form-forget-password').validate({
            rules: {
                email: {
                    required: true,
                    email: true
                }
            }
        });
    };

    global.Module.ForgetPassword = init;

})(window, window.jQuery);