/**
 * Dependency: jquery.js
 *
 * City content related js
 */

/**
 * update cities by selecting a country
 */
(function ($) {
    var URL = "/location/cities/";
    var city = function (countryElem, cityElem) {
        $(countryElem).change(function () {
            $.ajax({
                url: URL,
                type: 'POST',
                data: JSON.stringify({ name: $(countryElem).val() }),
                contentType: 'application/json;charset=UTF-8',
                success: function (data) {
                    $(cityElem).html(data);
                }
            });
        });
    };
    window.citySelect = city;  // make citySelect public
})(jQuery);

