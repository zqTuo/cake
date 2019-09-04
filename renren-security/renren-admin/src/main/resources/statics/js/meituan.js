
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
			layer_full("/cake-admin/sys/meituancoupon/auth?o_url=" + window.location.href,"<span style='color: red;font-size: 2.14rem;'>美团/点评授权已失效！请尽快授权恢复验券与销券功能！</span>")
		}
	}
});