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
	requesturl:"/tw_epm/actions/project_getByJoinUserAndName.action",//向后台请求数据路径
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
			+"</td><td>"+"<a  class='btn btn-warning  btn-sm' href='/tw_epm/end/admin/project/joinProject/joinProject.jsp?id="+data.projectId+"'>详情页</a>"
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
	pageListmanage.searchData.name=$("#projectName").val();
	pageListmanage.searchAll();
}
function pageListSearchAll(){
	$("#projectName").val("");
	pageListSearch();
}