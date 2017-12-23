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
	requesturl:"/tw_epm/actions/user_getByNameAndLoginName.action",//向后台请求数据路径
	dataListId:"#pageList_tbody",//填充数据的元素可根据id(要写#  eg:#content)也可根据class(要写. eg:.content)也可直接写标签名称
	pageUI:"#pageList_pageui",//分页条按钮填充区域命名规则同上
	myname:"pageListmanage",//用户声明的变量名称
    searchData:{
		page:this.pg,
        pageSize:this.pgz,
        keys:"userId+name+type+tel+email+wechat+point",
        name:$("#userName").val(),
        loginName:""
	},//用户需要传到后台的多余数据可以为字符串，数字，或者json数据(此项可用来筛选数据，做查询功能)
	pgz:10,//每页显示数据条数
	but_num:20,//分页条按钮每组最多显示多少页
	beginsearch:true,//是否在初始化时填充数据
	drawDatatoCotent:function (data){
            var userType = data.type;
            switch(data.type){
                case 1:
                    data.type = "项目负责人";
                break;
                case 2:
                    data.type = "总经理";
                break;
                default:
                    data.type = "普通用户";
                    userType = 0;
                break;
            }
            var buttons = "<button type='button' class='btn btn-warning  btn-sm' onclick='changeUserType("+data.userId+","+userType+")'>修改权限</button>";
            if(userType == 2){
                buttons = "<button type='button' class='btn btn-warning  btn-sm' onclick='changeUserType("+data.userId+","+userType+")' disabled>修改权限</button>";
            }
			var allstr="";
            allstr="<tr>"
            +"</td><td>"+this.checkTypedof(data.userId)
            +"</td><td>"+this.checkTypedof(data.name)
            +"</td><td>"+this.checkTypedof(data.type)
            +"</td><td>"+this.checkTypedof(data.tel)
            +"</td><td>"+this.checkTypedof(data.email)
            +"</td><td>"+this.checkTypedof(data.wechat)
            +"</td><td>"+this.checkTypedof(data.point)
            +"</td><td>"+buttons
            +"</td></tr>";
            return allstr;
	}
});
function pageListInit(){
    pageListmanage.searchAll();
}

function pageListSearch(){
	pageListmanage.pg=1;
	pageListmanage.nowgroup=1;
	pageListmanage.searchData.name=$("#userName").val();
	pageListmanage.searchAll();
}
function pageListSearchAll(){
	$("#userName").val("");
	pageListSearch();
}

function changeUserType(userId,userType){
    $("#userTypeBoxType").val(userType);
    layer.open({
      type: 1,
      shade: false,
      title: "修改权限", //不显示标题
      content: $('#userTypeBox'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
      btn:["保存"],
      maxWidth:1000,
      resize:true,
      cancel: function(){
        
      },
      btn1:function(index){
        layer.close(index);
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/user_updateByIds.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    ids:userId,
                    keys:"type",
                    type:$("#userTypeBoxType").val()
                },
                success: function(data) {
                    layer.msg('修改成功', {
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