(function (global, $, undefined) {
    'use strict';

    var init = function () {
        var $form = $('form[name="newPackageForm"]');
        $form.validate({
            rules: {
                title: {
                    required: true,
                    maxlength: 80
                },
                price: {
                    required: true,
                    range: [0, 100000000]
                },
                startTime: {
                    required: false,
                    date: true
                },
                endTime: {
                    required: false,
                    date: true
                },
                minPeople: {
                    required: false,
                    range: [1, 1000]
                },
                maxPeople: {
                    required: false,
                    range: [1, 1000]
                }
            }
        }).settings.ignore = ":disabled,:hidden";

        $('#newPackageForm .btn-primary').click(function(e) {
            var form = $('#newPackageForm');
            e.preventDefault();

            var $items = $('.service-item');
            $.each($items, function (index, item) {
                var place = $(item).find('.service-title');
                var desc = $(item).find('.service-desc');
                place.attr('name', place.attr('name').replace("[]", "[" + index + "]"));
                desc.attr('name', desc.attr('name').replace("[]", "[" + index + "]"));
            });

            form.submit();
            
            return false; //is superfluous, but I put it here as a fallback
        });

        var addServiceInput = $('#add-service-input');
        var addService = function() {
            var value = addServiceInput.val();
            if(value) {

                $('<li class="service-item item-wrap has-feedback"> <input type="text" name="trips[].place" class="service-title form-control edit-service-input"  value="'+value+'" /> <textarea class="service-desc form-control" name="trips[].desc" placeholder="Enter descriptions here" /> <span class="glyphicon glyphicon-remove text-danger item-remove"></span> </li>').appendTo(addServiceInput.closest('.items-wrap').find('.items'));

                addServiceInput.val('');
                $('.sortable').sortable();
            }
        };

        addServiceInput.on('keypress', function(e) {
            if(e.keyCode == 13) {
                e.preventDefault();
                addService();
            }
        });

        $('.add-service-btn').click(addService);

        $('.items-wrap').on('keypress', '.edit-service-input', function(e) {
            if(e.keyCode == 13) {
                e.preventDefault();
                $('#add-service-input').focus();
            }
        });

        $('.items-wrap').on('click', '.item-remove', function(e) {
            $(this).closest('li.item-wrap').remove();
        });

        $('#newPackageForm div.trips-wrap').on('click', 'a.trip-remove',function(e) {
            $(this).closest('div.trip').remove();
        });

        $('.sortable').sortable();
;
        $('body').on('click', 'a.delete-package-btn', function(e) {
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
        });

    };

    global.Module.EditPackage = init;
})(window, window.jQuery);
