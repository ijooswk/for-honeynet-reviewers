function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) 
		return unescape(r[2]); 
	return null;
}

function ajaxGet(url, data, successCallback, errorCallback) {
	var basicUrl = "http://sml.pku.edu.cn:8012/greeting/";
	$.ajax({
		url: basicUrl + url,
		data: data,
		crossDomain: true,
		type: "GET",
		success: function(data) {
			if (successCallback)
				successCallback(data);
		},
		error: function(data){
			if (errorCallback)
				errorCallback(data);
		}
	})
}

function ajaxPost(url, data, successCallback, errorCallback) {
	var basicUrl = "http://sml.pku.edu.cn:8012/greeting/";
	$.ajax({
		url: basicUrl + url,
		data: data,
		crossDomain: true,
		type: "POST",
		success: function(data) {
			if (successCallback)
				successCallback(data);
		},
		error: function(data){
			if (errorCallback)
				errorCallback(data);
		}
	})
}