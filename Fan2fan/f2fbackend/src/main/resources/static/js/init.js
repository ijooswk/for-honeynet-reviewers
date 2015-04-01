(function(global, dropzone){
    "use strict";

    global.Module = {};

    if (dropzone) {
        dropzone.autoDiscover = false;
    }

    $(document).ready(function() {
        $("img.lazy").unveil(200, function() {
            $(this).load(function() {
                this.style.opacity = 1;
            });
        });
    });

})(window, window.Dropzone);
