laydate({
    elem: '#theProjectStartDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
    format: 'YYYY-MM-DD hh:mm:ss',
    istime: true //是否开启时间选择
});
laydate({
    elem: '#theProjectEndDateA', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
    format: 'YYYY-MM-DD hh:mm:ss',
    istime: true //是否开启时间选择
});
laydate.skin('molv');  //加载皮肤，参数lib为皮肤名 
$("#pageList_selectAll").click(function(event) {
		var checkall = $(this).prop('checked');
		if(checkall) {
			$("input[name='pageList_eachcheckbox']").each(function() {
				$(this).prop('checked',true);
			});
		}
		else {
			$("input[name='pageList_eachcheckbox']").each(function() {
				$(this).prop('checked',false);
			});
		}
});
var pageListmanage=new MyPage();
pageListmanage.init({
	requesturl:"/tw_epm/actions/project_getByUserAndName.action",//向后台请求数据路径
	dataListId:"#pageList_tbody",//填充数据的元素可根据id(要写#  eg:#content)也可根据class(要写. eg:.content)也可直接写标签名称
	pageUI:"#pageList_pageui",//分页条按钮填充区域命名规则同上
	myname:"pageListmanage",//用户声明的变量名称
    searchData:{
		page:this.pg,
		pageSize:this.pgz,
		keys:"",
		user:0,
		name:$("#projectName").val()
	},//用户需要传到后台的多余数据可以为字符串，数字，或者json数据(此项可用来筛选数据，做查询功能)
	pgz:10,//每页显示数据条数
	but_num:20,//分页条按钮每组最多显示多少页
	beginsearch:true,//是否在初始化时填充数据
	drawDatatoCotent:function (data){
			var allstr="";
			allstr="<tr>"
			+"<td><input type=\"checkbox\" name=\"pageList_eachcheckbox\" value='"+data.projectId+"'>"
			+"</td><td>"+this.checkTypedof(data.name)
			+"</td><td>"+this.checkTypedof(data.startDate)
            +"</td><td>"+this.checkTypedof(data.endDateA)
            +"</td><td>"+this.checkTypedof(data.endDateA)
            +"</td><td>"+this.checkTypedof(data.user.name)+"("+this.checkTypedof(data.user.userId)+")"
			+"</td><td>"+"<a  class='btn btn-warning  btn-sm' href='/tw_epm/end/admin/project/project/project.jsp?id="+data.projectId+"'>详情页</a>\n<button type='button' class='btn btn-danger  btn-sm' onclick='pageListDeleteOne("+data.projectId+")'>删除</button>"
			+"</td></tr>";
			return allstr;
	}
});
function pageListInit(){
    pageListmanage.searchAll();
}
function pageListAdd() {
    $("#theProjectName").val("");
    $("#theProjectStartDate").val("");
    $("#theProjectEndDateA").val("");
    $("#theProjectContent").val("");
    layer.open({
      type: 1,
      shade: false,
      title: "增加角色", //不显示标题
      content: $('#projectBox'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
      btn:["保存"],
      maxWidth:1000,
      resize:true,
      cancel: function(){
        
      },
      btn1:function(index){
        layer.close(index);
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/project_add.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    name:$("#theProjectName").val(),
                    startDate:$("#theProjectStartDate").val(),
                    endDateA:$("#theProjectEndDateA").val(),
                    content:$("#theProjectContent").val()
                },
                success: function(data) {
                    layer.msg('增加成功', {
                        icon: 6
                    });
                    pageListmanage.searchAll();
                    layer.closeAll('loading');
                    
                },
                error: function(data) {
                    pageListmanage.searchAll();
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).error, {
                        icon: 5
                    });
                }
            });
      }
    });
}
/*function pageListEdit(id) {
    layer.open({
        type: 2,
        area: ['1000px', '500px'],
        fix: false,
        //不固定
        maxmin: true,
        content: '/sanhe/webContent/end/admin/goods/goods/addGoods.php?id=' + id,
        title: '修改商品信息'
    });
}*/
function pageListDeleteOne(id) {
    layer.msg('删除后不可恢复，您确定删除吗？', {
        time: 0 //不自动关闭
        ,
        btn: ['删除', '取消'],
        yes: function(index) {
            layer.close(index);
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/project_deleteByIds.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    ids:id
                },
                success: function(data) {
                    layer.msg('删除成功', {
                        icon: 6
                    });
                    pageListmanage.searchAll();
                    layer.closeAll('loading');
                },
                error: function(data) {
                    pageListmanage.searchAll();
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).error, {
                        icon: 5
                    });
                }
            });
        }
    });
}

function pageListDelete() {
    if ($("input[name='pageList_eachcheckbox']:checked").length == 0) {
        layer.msg('请选择至少一条数据', {
            icon: 7
        });
    } else {
        layer.msg('删除后不可恢复，您确定删除吗？', {
            time: 0 //不自动关闭
            ,
            btn: ['删除', '取消'],
            yes: function(index) {
                layer.close(index);
                layer.load(2);
                var str = "";
                $("input[name='pageList_eachcheckbox']:checked").each(function() {
                    str += $(this).val() + "+";
                });
                str = str.substring(0, str.length - 1);
                $.ajax({
                    url: "/tw_epm/actions/project_deleteByIds.action",
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        ids:str
                    },
                    success: function(data) {
                        layer.msg('删除成功', {
                            icon: 6
                        });
                        pageListmanage.searchAll();
                        layer.closeAll('loading');
                    },
                    error: function() {
                        pageListmanage.searchAll();
                        layer.closeAll('loading');
                        layer.msg(JSON.parse(data.responseText).error, {
                            icon: 5
                        });
                    }
                });
            }
        });
    }
}
function pageListSearch(){
	pageListmanage.pg=1;
	pageListmanage.nowgroup=1;
	pageListmanage.searchData.name=$("#projectName").val();
	pageListmanage.searchAll();
}
function pageListSearchAll(){
	$("#projectName").val("");
	pageListSearch();
}