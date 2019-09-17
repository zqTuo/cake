$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/coursemenu/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '上课时间', name: 'courseTime', index: 'courseTime', width: 80 },
			{ label: '课程名称', name: 'title', index: 'title', width: 80 },
			{ label: '最大人数', name: 'num', index: 'num', width: 80 }
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

layui.use('laydate', function(){
    var laydate = layui.laydate;

    laydate.render({
        elem: '#datetimeStart'
        ,type: 'date'
        ,format: 'yyyy-MM-dd'
        ,value: new Date()
        ,done: function(value, date, endDate){
            console.log(value); //得到日期生成的值，如：2017-08-18
            var postJson = {date:value};
            //传入查询条件参数
            $("#jqGrid").jqGrid("setGridParam",{postData:postJson});
            //每次提出新的查询都转到第一页
            $("#jqGrid").jqGrid("setGridParam",{page:1});
            //提交post并刷新表格
            $("#jqGrid").jqGrid("setGridParam",{url:baseURL + 'sys/coursemenu/list'}).trigger("reloadGrid");
        }
    });
    laydate.render({
        elem: '#courseDate'
        ,type: 'date'
        ,format: 'yyyy-MM-dd'
        ,value: new Date()
    });
});

$("#datetimeStart").on('change',function () {
    console.log($(this).val())
})

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		courseMenu: {},
        timeList:[],
        courseList:[]
	},
    mounted: function(){
        var that = this;
        that.getTimeList();
        that.getCourseList();

    },
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.courseMenu = {};
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
                var url = vm.courseMenu.id == null ? "sys/coursemenu/save" : "sys/coursemenu/update";

                vm.courseMenu.menuDate = $("#courseDate").val();

                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.courseMenu),
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
                        url: baseURL + "sys/coursemenu/delete",
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
			$.get(baseURL + "sys/coursemenu/info/"+id, function(r){
                vm.courseMenu = r.courseMenu;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        getTimeList:function () {
            $.get(baseURL + "sys/sendtime/dataList/1", function(r){
                vm.timeList = r.timeList;
            });
        },
        getCourseList:function () {
            $.get(baseURL + "sys/course/all", function(r){
                vm.courseList = r.courseList;
            });
        }
	}
});