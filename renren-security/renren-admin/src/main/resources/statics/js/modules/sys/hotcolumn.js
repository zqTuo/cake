$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/hotcolumn/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			// { label: '商品列表',class:'details-control',width: 30},
			{ label: '栏目标题', name: 'hotTitle', index: 'hot_title', width: 80 },
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '修改时间', name: 'updateTime', index: 'update_time', width: 80 }, 			
			{ label: '修改管理员', name: 'updateBy', index: 'update_by', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		hotColumn: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.hotColumn = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.hotColumn.id == null ? "sys/hotcolumn/save" : "sys/hotcolumn/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.hotColumn),
                    success: function(r){
                        if(r.code === 0){
                             layer.msg("操作成功", {icon: 1});
                             vm.reload();
                             $('#btnSaveOrUpdate').button('reset');
                             $('#btnSaveOrUpdate').dequeue();
                        }else{
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
               if(!lock) {
                    lock = true;
		            $.ajax({
                        type: "POST",
                        url: baseURL + "sys/hotcolumn/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function(r){
                            if(r.code == 0){
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            }else{
                                layer.alert(r.msg);
                            }
                        }
				    });
			    }
             }, function(){
             });
		},
		getInfo: function(id){
			$.get(baseURL + "sys/hotcolumn/info/"+id, function(r){
                vm.hotColumn = r.hotColumn;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});

//行折叠与展开
$('#jqGrid tbody').on('click', 'td.details-control', function () {
    var rowid = $('#jqGrid').jqGrid('getGridParam','selrow');
    var row = $("#gridTable").jqGrid('getRowData',rowid);
    var tr = $(this).closest('tr');
    if (row.child.isShown()) {
        // This row is already open - close it
        row.child.hide();
        tr.removeClass('shown');
    } else {
        // Open this row
        //row.child( format(row.data()) ).show();

        var id = getSelectedRow();
        if(id == null){
            return ;
        }
        $.get(baseURL + "sys/hotcolumn/getInfo/"+id, function(r){
            row.child(format(r.list)).show();
            tr.addClass('shown');
        });
    }
});

function format(dataArr) {
    var html = '<table id="tempTable" class="table table-border table-bordered table-bg table-hover" ' +
        'cellpadding="6" cellspacing="0" border="0">' +
        '<tr class="text-c">' +
        '<th>商品图片</th>' +
        '<th>商品名称</th>' +
        '<th>商品价格</th>' +
        '</tr>' +
        '<tbody class="tempbody">';
    for (var i = 0; i < dataArr.length; i++) {
        var item = dataArr[i];
        html += '<tr>' +
            '<td><input type="checkbox" class="checkItem" ' +
            'data-pid="'+ item.id +'" name="checkItems"></td>' +
            '<td><img src="' + item.productImg + '" width="80px" height="80px"></td>' +
            '<td>'+item.productName+'</td>' +
            '<td>'+item.productPrice+'</td>' +
            ' </tr>';
    }
    html += '</tbody></table>';
    return html;
}