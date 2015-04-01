(function (global, $, undefined) {
    'use strict';

    var init = function () {
        $('#fileupload').fileupload({
            dataType: 'json',
            start: function() {
                $('.fileinput-button').addClass('disabled');
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('.fileinput-button .progress').css(
                    'width',
                    progress + '%'
                );
            },
            done: function (e, data) {
                var result = data.result;
                if(result.state == 'success') {
                    var url = result.url;
                    var filename = result.filename;
                    $('#avatarUrl').val(filename);
                    $('.fileinput-button').removeClass('disabled');
                    $('.avatar-preview').attr('src', url);
                }
                $('.fileinput-button .progress').css('width', '0px');
            }
        });

        citySelect('#nation', '#city');  // country and city selection

        $('.datepicker').datepicker({
            format: 'mm-dd-yyyy'
        });
        var $select = $('#favorite-team');
        $select.find('option:first').removeAttr("value");
        $select.select2({
            placeholder: "Select a Club",
            width: "100%"
        });
    };

    global.Module.UserProfile = init;
})(window, window.jQuery);
