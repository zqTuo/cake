
function layer_full(url,title) {
	var index = layer.open({
		type: 2,
		title: title,
		content: url,
	});
	layer.full(index);
}

//全局请求美团session是否过期
$.ajax({
	type: "GET",
	url: "/cake-admin/sys/meituancoupon/checkSession",
	contentType: "application/json",
	data: {},
	success: function(r){
		if(r.code !== 0){
			window.location.href = "/cake-admin/sys/meituancoupon/auth"
		}
	}
});