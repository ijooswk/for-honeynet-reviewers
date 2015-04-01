$(function(){
    var accessToken = window.location.hash.substring(1);
    $.ajax({
        type: "GET",
        url: "http://sml.pku.edu.cn:8012/greeting?account=11",
        dataType: "jsonp",
        success: function(data){
             alert("get");
                         /*$('#resText').empty();   //清空resText里面的所有内容
            var html = '';
            $.each(data, function(commentIndex, comment){
                html += '<div class="comment"><h6>' + comment['username']
                    + ':</h6><p class="para"' + comment['content']
                    + '</p></div>';
            });
            $('#resText').html(html);*/
        }
    });
});
