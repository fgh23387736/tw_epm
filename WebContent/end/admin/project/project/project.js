var pageListmanage=new MyPage();
var pageListmanageRole=new MyPage();
var pageListmanageUser=new MyPage();
var pageListmanageWorksiteRecord=new MyPage();
var pageListmanagePoint=new MyPage();
var pageListmanageLearnDoc=new MyPage();
var pageListmanageSpecification=new MyPage();
var pageListmanageChoseUser=new MyPage();
var myChart = echarts.init(document.getElementById('projectChart'));
var option;
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
laydate({
  	elem: '#theProjectEndDateB', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	format: 'YYYY-MM-DD hh:mm:ss',
	istime: true //是否开启时间选择
});
laydate({
    elem: '#pointBoxDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
    format: 'YYYY-MM-DD hh:mm:ss',
    istime: true //是否开启时间选择
});
laydate.skin('molv');  //加载皮肤，参数lib为皮肤名 

var id = pageListmanage.getUrlPra("id");
if(id == null){
	history.back();
}else{
	init();
}
function init(){
    initEchart();
	initProject(id);
	initRole(id);
    initUser(id);
    searchAllRoleForUser();
    initWorksiteRecord(id);
    initPoint(id);
    initLearnDoc(id);
    initSpecification(id);
}

function initProject(id){
	layer.load(2);
    $.ajax({
        url: "/tw_epm/actions/project_getByIds.action",
        type: 'POST',
        dataType: 'json',
        data: {
            keys:"name+startDate+endDateB+endDateA+content+percentage+points",
			ids:id
        },
        success: function(data) {
            if(data.total <= 0){
            	layer.closeAll('loading');
            	layer.msg("项目不存在", {
	                icon: 5
	            });
            }else{

            	$("#projectTitle").html(data.resultList[0].name);
            	$("#theProjectName").val(data.resultList[0].name);
            	$("#theProjectStartDate").val(data.resultList[0].startDate);
            	$("#theProjectEndDateA").val(data.resultList[0].endDateA);
            	$("#theProjectEndDateB").val(data.resultList[0].endDateB);
            	$("#theProjectContent").val(data.resultList[0].content);
            	$("#projectPercentLable").html(data.resultList[0].percentage+"%");
            	$("#projectPercentInside").html(data.resultList[0].percentage+"%");
            	$("#projectPercentBar").attr("style","width:"+data.resultList[0].percentage+"%"+";");
            	var points = data.resultList[0].points;
                $("#pointListForPercentBar").children().remove();
                for (var i = 0; i < points.length; i++) {
                    var thePercent = points[i].state+"%";
                    $("#pointListForPercentBar").append(
                            '<div class="pointForPercentBar" style="left:'+thePercent+';" title="'+points[i].name+'" onclick="window.location=\'/tw_epm/end/admin/project/point/point.jsp?id='+points[i].pointId+'\'">'
                            +thePercent
                            +'</div>'
                        );
                    
                };
                layer.closeAll('loading');
            }
            
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

function saveProject(){
	layer.load(2);
    $.ajax({
        url: "/tw_epm/actions/project_updateByIds.action",
        type: 'POST',
        dataType: 'json',
        data: {
            keys:"name+content+startDate+endDateA+endDateB",
			ids:id,
			name:$("#theProjectName").val(),
			content:$("#theProjectContent").val(),
			startDate:$("#theProjectStartDate").val(),
			endDateA:$("#theProjectEndDateA").val(),
			endDateB:$("#theProjectEndDateB").val()
        },
        success: function(data) {
            layer.closeAll('loading');
        	layer.msg("保存成功", {
                icon: 6
            });  
        },
        error: function(data) {
            layer.closeAll('loading');
            layer.msg(JSON.parse(data.responseText).error, {
                icon: 5
            });
        }
    });
}

function initRole(id){
	pageListmanageRole.init({
		requesturl:"/tw_epm/actions/proRole_getByProjectAndName.action",//向后台请求数据路径
		dataListId:"#pageList_Role_tbody",//填充数据的元素可根据id(要写#  eg:#content)也可根据class(要写. eg:.content)也可直接写标签名称
		pageUI:"#pageList_Role_pageui",//分页条按钮填充区域命名规则同上
		myname:"pageListmanageRole",//用户声明的变量名称
	    searchData:{
			page:this.pg,
			pageSize:this.pgz,
			keys:"name+proRoleId",
			project:id,
			name:$("#roleName").val()
		},//用户需要传到后台的多余数据可以为字符串，数字，或者json数据(此项可用来筛选数据，做查询功能)
		pgz:10,//每页显示数据条数
		but_num:20,//分页条按钮每组最多显示多少页
		beginsearch:true,//是否在初始化时填充数据
		drawDatatoCotent:function (data){
				var allstr="";
				allstr="<tr>"
				+"</td><td>"+this.checkTypedof(data.name)
				+"</td><td>"+"<button type='button' class='btn btn-warning  btn-sm' onclick='editRole(\""+data.name+"\","+data.proRoleId+")'>编辑</button>\n<button type='button' class='btn btn-danger  btn-sm' onclick='deleteRole("+data.proRoleId+")'>删除</button>\n<button type='button' class='btn btn-primary  btn-sm' onclick='configureRoleAuth(\""+data.name+"\","+data.proRoleId+")'>配置权限</button>"
				+"</td></tr>";
				return allstr;
		},
		callBackOk:function(){
			option.series[0].data[0]=this.dataNum;
			myChart.setOption(option);
		}
	});
}

function searchRole(){
    pageListmanageRole.pg=1;
    pageListmanageRole.nowgroup=1;
	pageListmanageRole.searchData.name=$("#roleName").val();
	pageListmanageRole.searchAll();
}

function searchAllRole(){
	$("#roleName").val("");
	searchRole();
}

function deleteRole(id){
	layer.msg('删除后不可恢复，您确定删除吗？', {
        time: 0 //不自动关闭
        ,
        btn: ['删除', '取消'],
        yes: function(index) {
            layer.close(index);
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/proRole_deleteByIds.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    ids:id
                },
                success: function(data) {
                    layer.msg('删除成功', {
                        icon: 6
                    });
                    pageListmanageRole.searchAll();
                    layer.closeAll('loading');
                    searchAllRoleForUser();
                },
                error: function(data) {
                    pageListmanageRole.searchAll();
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).error, {
                        icon: 5
                    });
                }
            });
        }
    });
}

function editRole(name,id,auth){
	$("#roleBoxName").val(name);
	layer.open({
	  type: 1,
	  shade: false,
	  title: "编辑角色", //不显示标题
	  content: $('#roleBox'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
	  btn:["保存"],
	  cancel: function(){
	    
	  },
	  btn1:function(index){
	  	layer.close(index);
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/proRole_updateByIds.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    ids:id,
                    keys:"name+auth",
                    name:$("#roleBoxName").val()
                },
                success: function(data) {
                    layer.msg('修改成功', {
                        icon: 6
                    });
                    pageListmanageRole.searchAll();
                    layer.closeAll('loading');
                    searchAllRoleForUser();
                },
                error: function(data) {
                    pageListmanageRole.searchAll();
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).error, {
                        icon: 5
                    });
                }
            });
	  }
	});
}

function addRole(){
    $("#roleBoxName").val("");
    layer.open({
      type: 1,
      shade: false,
      title: "增加角色", //不显示标题
      content: $('#roleBox'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
      btn:["保存"],
      cancel: function(){
        
      },
      btn1:function(index){
        layer.close(index);
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/proRole_add.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    name:$("#roleBoxName").val(),
                    project:id
                },
                success: function(data) {
                    layer.msg('增加成功', {
                        icon: 6
                    });
                    pageListmanageRole.searchAll();
                    layer.closeAll('loading');
                    searchAllRoleForUser();
                },
                error: function(data) {
                    pageListmanageRole.searchAll();
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).error, {
                        icon: 5
                    });
                }
            });
      }
    });
}



function initUser(id){
    pageListmanageUser.init({
        requesturl:"/tw_epm/actions/userPro_getByProjectAndUserName.action",//向后台请求数据路径
        dataListId:"#pageList_User_tbody",//填充数据的元素可根据id(要写#  eg:#content)也可根据class(要写. eg:.content)也可直接写标签名称
        pageUI:"#pageList_User_pageui",//分页条按钮填充区域命名规则同上
        myname:"pageListmanageUser",//用户声明的变量名称
        searchData:{
            page:this.pg,
            pageSize:this.pgz,
            keys:"",
            project:id,
            userName:$("#userName").val()
        },//用户需要传到后台的多余数据可以为字符串，数字，或者json数据(此项可用来筛选数据，做查询功能)
        pgz:10,//每页显示数据条数
        but_num:20,//分页条按钮每组最多显示多少页
        beginsearch:true,//是否在初始化时填充数据
        drawDatatoCotent:function (data){
                switch(data.proRole.auth){
                    case 0:
                        data.proRole.theauth = "项目临时人员";
                        break;
                    case 1:
                        data.proRole.theauth = "项目工作人员";
                        break;
                    case 2:
                        data.proRole.theauth = "现场负责人";
                        break;
                    case 3:
                        data.proRole.theauth = "项目经理";
                        break;
                    default:
                        data.proRole.theauth = "无效权限";
                }
                var allstr="";
                allstr="<tr>"
                +"</td><td>"+this.checkTypedof(data.user.userId)
                +"</td><td>"+this.checkTypedof(data.user.name)
                +"</td><td>"+this.checkTypedof(data.user.tel)
                +"</td><td>"+this.checkTypedof(data.proRole.name)
                +"</td><td>"+"<button type='button' class='btn btn-warning  btn-sm' onclick='editUser("+data.user.userId+","+data.userProId+","+data.proRole.proRoleId+")'>编辑</button>\n<button type='button' class='btn btn-danger  btn-sm' onclick='deleteUser("+data.proRoleId+")'>删除</button>"
                +"</td></tr>";
                return allstr;
        },
        callBackOk:function(){
			option.series[0].data[1]=this.dataNum;
			myChart.setOption(option);
		}
    });
}

function searchUser(){
    pageListmanageUser.pg = 1;
    pageListmanageUser.nowgroup = 1;
    pageListmanageUser.searchData.userName=$("#userName").val();
    pageListmanageUser.searchAll();
}

function searchAllUser(){
    $("#userName").val("");
    searchUser();
}

function deleteUser(id){
    layer.msg('删除后不可恢复，您确定删除吗？', {
        time: 0 //不自动关闭
        ,
        btn: ['删除', '取消'],
        yes: function(index) {
            layer.close(index);
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/userPro_deleteByIds.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    ids:id
                },
                success: function(data) {
                    layer.msg('删除成功', {
                        icon: 6
                    });
                    pageListmanageUser.searchAll();
                    layer.closeAll('loading');
                },
                error: function(data) {
                    pageListmanageUser.searchAll();
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).error, {
                        icon: 5
                    });
                }
            });
        }
    });
}

function editUser(userId,id,proRole){
    $("#userBoxUser").val(userId);
    $("#userBoxProRole").val(proRole);
    layer.open({
      type: 1,
      shade: false,
      title: "编辑人员", //不显示标题
      content: $('#userBox'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
      btn:["保存"],
      cancel: function(){
        
      },
      btn1:function(index){
        layer.close(index);
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/userPro_updateByIds.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    ids:id,
                    keys:"proRole",
                    proRole:$("#userBoxProRole").val()
                },
                success: function(data) {
                    layer.msg('修改成功', {
                        icon: 6
                    });
                    pageListmanageUser.searchAll();
                    layer.closeAll('loading');
                },
                error: function(data) {
                    pageListmanageUser.searchAll();
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).error, {
                        icon: 5
                    });
                }
            });
      }
    });
}

function addUser(){
    $("#userBoxUser").val("");
    $("#userBoxProRole").val(0);
    layer.open({
      type: 1,
      shade: false,
      title: "增加人员", //不显示标题
      content: $('#userBox'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
      btn:["保存"],
      cancel: function(){
        
      },
      btn1:function(index){
        layer.close(index);
        layer.load(2);
        $.ajax({
            url: "/tw_epm/actions/userPro_add.action",
            type: 'POST',
            dataType: 'json',
            data: {
                user:$("#userBoxUser").val(),
                proRole:$("#userBoxProRole").val(),
                project:id
            },
            success: function(data) {
                layer.msg('修改成功', {
                    icon: 6
                });
                pageListmanageUser.searchAll();
                layer.closeAll('loading');
            },
            error: function(data) {
                pageListmanageUser.searchAll();
                layer.closeAll('loading');
                layer.msg(JSON.parse(data.responseText).error, {
                    icon: 5
                });
            }
        });
      }
    });
}

function searchAllRoleForUser(){
   //var proRoles = $("#userBoxProRole");
   var proRoles = document.getElementById("userBoxProRole");
   console.log(proRoles);
    $.ajax({
        url: "/tw_epm/actions/proRole_getByProjectAndName.action",
        type: 'POST',
        dataType: 'json',
        data: {
            keys:"proRoleId+name",
            project:id,
            name:""
        },
        success: function(data) {
            //proRoles.children().remove();
            proRoles.options.length=0;
            for (var i = 0; i < data.resultList.length; i++) {
                console.log(data.resultList[i]);
                //proRoles.append('<option value="'+data.resultList[i].proRoleId+'">'+data.resultList[i].name+'</option>');
                proRoles.options.add(new Option(data.resultList[i].name,data.resultList[i].proRoleId));
            };
        },
        error: function(data) {
            layer.msg(JSON.parse(data.responseText).error, {
                icon: 5
            });
        }
    });
}

function initWorksiteRecord(id){
    pageListmanageWorksiteRecord.init({
        requesturl:"/tw_epm/actions/worksiteRecord_getByProject.action",//向后台请求数据路径
        dataListId:"#pageList_WorksiteRecord_tbody",//填充数据的元素可根据id(要写#  eg:#content)也可根据class(要写. eg:.content)也可直接写标签名称
        pageUI:"#pageList_WorksiteRecord_pageui",//分页条按钮填充区域命名规则同上
        myname:"pageListmanageWorksiteRecord",//用户声明的变量名称
        searchData:{
            page:this.pg,
            pageSize:this.pgz,
            keys:"",
            project:id
        },//用户需要传到后台的多余数据可以为字符串，数字，或者json数据(此项可用来筛选数据，做查询功能)
        pgz:10,//每页显示数据条数
        but_num:20,//分页条按钮每组最多显示多少页
        beginsearch:true,//是否在初始化时填充数据
        drawDatatoCotent:function (data){
                var allstr="";
                allstr="<tr>"
                +"</td><td>"+"<img src='"+this.checkTypedof(data.thumbnail)+"'>"
                +"</td><td>"+this.checkTypedof(data.position)
                +"</td><td>"+this.checkTypedof(data.date)
                +"</td><td>"+this.checkTypedof(data.user.name)+"("+this.checkTypedof(data.user.userId)+")"
                +"</td><td>"+this.checkTypedof(data.remarks)
                //+"</td><td>"+"<button type='button' class='btn btn-warning  btn-sm' onclick='showWorksiteRecordDetial('"+data.thumbnail+"','"+data.remarks+"')'>详情</button>"
                +"</td></tr>";
                return allstr;
        },
        callBackOk:function(){
			option.series[0].data[2]=this.dataNum;
			myChart.setOption(option);
		}
    });
}

function initPoint(id){
    pageListmanagePoint.init({
        requesturl:"/tw_epm/actions/point_getByProjectAndName.action",//向后台请求数据路径
        dataListId:"#pageList_Point_tbody",//填充数据的元素可根据id(要写#  eg:#content)也可根据class(要写. eg:.content)也可直接写标签名称
        pageUI:"#pageList_Point_pageui",//分页条按钮填充区域命名规则同上
        myname:"pageListmanagePoint",//用户声明的变量名称
        searchData:{
            page:this.pg,
            pageSize:this.pgz,
            keys:"name+state+date+user+describe+pointId",
            project:id,
            name:$("#pointName").val()
        },//用户需要传到后台的多余数据可以为字符串，数字，或者json数据(此项可用来筛选数据，做查询功能)
        pgz:10,//每页显示数据条数
        but_num:20,//分页条按钮每组最多显示多少页
        beginsearch:true,//是否在初始化时填充数据
        drawDatatoCotent:function (data){
                var allstr="";
                allstr="<tr>"
                +"</td><td>"+this.checkTypedof(data.name)
                +"</td><td>"+this.checkTypedof(data.state)+"%"
                +"</td><td>"+this.checkTypedof(data.date)
                +"</td><td>"+this.checkTypedof(data.user.name)+"("+this.checkTypedof(data.user.userId)+")"
                +"</td><td>"+this.checkTypedof(data.describe)
                +"</td><td>"+"<a  class='btn btn-warning  btn-sm' href='/tw_epm/end/admin/project/point/point.jsp?id="+data.pointId+"'>详情页</a>\n<button type='button' class='btn btn-danger  btn-sm' onclick='deletePoint("+data.pointId+")'>删除</button>"
                +"</td></tr>";
                return allstr;
        },
        callBackOk:function(){
			option.series[0].data[3]=this.dataNum;
			myChart.setOption(option);
		}
    });
}

function choseUser(){
	var index = layer.open({
      type: 1,
      shade: false,
      title: "选择用户", //不显示标题
      maxWidth:1500,
      content: $('#userChoseBox'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
      cancel: function(){
        
      }
    });
    initChoseUser(index);
}


function initChoseUser(boxIndex){
    pageListmanageChoseUser.init({
        requesturl:"/tw_epm/actions/user_getByNameAndLoginName.action",//向后台请求数据路径
        dataListId:"#pageList_ChoseUser_tbody",//填充数据的元素可根据id(要写#  eg:#content)也可根据class(要写. eg:.content)也可直接写标签名称
        pageUI:"#pageList_ChoseUser_pageui",//分页条按钮填充区域命名规则同上
        myname:"pageListmanageChoseUser",//用户声明的变量名称
        searchData:{
            page:this.pg,
            pageSize:this.pgz,
            keys:"userId+name+type+tel+email+wechat+point",
            name:$("#choseUserName").val(),
            loginName:""
        },//用户需要传到后台的多余数据可以为字符串，数字，或者json数据(此项可用来筛选数据，做查询功能)
        pgz:10,//每页显示数据条数
        pg:1,
        nowgroup:1,
        but_num:20,//分页条按钮每组最多显示多少页
        beginsearch:true,//是否在初始化时填充数据
        drawDatatoCotent:function (data){
                var allstr="";
                allstr="<tr>"
                +"</td><td>"+this.checkTypedof(data.userId)
                +"</td><td>"+this.checkTypedof(data.name)
                +"</td><td>"+this.checkTypedof(data.type)
                +"</td><td>"+this.checkTypedof(data.tel)
                +"</td><td>"+this.checkTypedof(data.email)
                +"</td><td>"+this.checkTypedof(data.wechat)
                +"</td><td>"+this.checkTypedof(data.point)
                +"</td><td>"+"<button type='button' class='btn btn-warning  btn-sm' onclick='choseTheUserToAdd("+data.userId+","+boxIndex+")'>选择</button>"
                +"</td></tr>";
                return allstr;
        }
    });
}

function searchChosenUser(){
    pageListmanageChoseUser.pg = 1;
    pageListmanageChoseUser.nowgroup = 1;
    pageListmanageChoseUser.searchData.name=$("#choseUserName").val();
    pageListmanageChoseUser.searchAll();
}

function searchAllChosenUser(){
    $("#choseUserName").val("");
    searchChosenUser();
}

function choseTheUserToAdd(userId,boxIndex){
    $("#userBoxUser").val(userId);
    layer.close(boxIndex);
}


function initEchart(){
    option = {
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['角色数', '人员数', '现场记录数', '项目节点数'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'数量',
                type:'bar',
                barWidth: '60%',
                data:[0, 0, 0, 0]
            }
        ]
    };

    
    myChart.setOption(option);
}

function searchPoint(){
    pageListmanagePoint.pg = 1;
    pageListmanagePoint.nowgroup = 1;
    pageListmanagePoint.searchData.name=$("#pointName").val();
    pageListmanagePoint.searchAll();
}

function searchAllPoint(){
    $("#pointName").val("");
    searchPoint();
}

function deletePoint(pointId){
    layer.msg('删除后不可恢复，您确定删除吗？', {
        time: 0 //不自动关闭
        ,
        btn: ['删除', '取消'],
        yes: function(index) {
            layer.close(index);
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/point_deleteByIds.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    ids:pointId
                },
                success: function(data) {
                    layer.msg('删除成功', {
                        icon: 6
                    });
                    pageListmanagePoint.searchAll();
                    layer.closeAll('loading');
                    initProject(id);
                },
                error: function(data) {
                    pageListmanagePoint.searchAll();
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).error, {
                        icon: 5
                    });
                }
            });
        }
    });
}


function addPoint(){
    $("#pointBoxName").val("");
    $("#pointBoxState").val(0);
    $("#pointBoxDate").val("");
    $("#pointBoxDescribe").val("");
    layer.open({
      type: 1,
      shade: false,
      title: "增加节点", //不显示标题
      content: $('#pointBox'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
      btn:["保存"],
      maxWidth:2000,
      cancel: function(){
        
      },
      btn1:function(index){
        layer.close(index);
        layer.load(2);
        $.ajax({
            url: "/tw_epm/actions/point_add.action",
            type: 'POST',
            dataType: 'json',
            data: {
                state:$("#pointBoxState").val(),
                name:$("#pointBoxName").val(),
                date:$("#pointBoxDate").val(),
                describe:$("#pointBoxDescribe").val(),
                project:id
            },
            success: function(data) {
                layer.msg('修改成功', {
                    icon: 6
                });
                pageListmanagePoint.searchAll();
                layer.closeAll('loading');
                initProject(id);
            },
            error: function(data) {
                pageListmanagePoint.searchAll();
                layer.closeAll('loading');
                layer.msg(JSON.parse(data.responseText).error, {
                    icon: 5
                });
            }
        });
      }
    });
}

var nowProRoleAuthBox = 0;
function configureRoleAuth(name,proRoleId){
	if(nowProRoleAuthBox !=0){
		layer.close(nowProRoleAuthBox);
	}
	
	$("#theProRoleId").val(0);
    var authArr = [
        'DOCUMENT',
        'SPECIFICATION',
        'NOTICE',
        'WORKSITE_RECORD',
        'POINT',
        'LOG',
        'MATERIAL',
        'LEARN_DOC',
        'POINT_PROBLEM',
        'POINT_ANSWER'
    ];
    for (var i = 0; i < authArr.length; i++) {
        $("#checkbox_"+authArr[i]).bootstrapSwitch('setState',false);
        $("#checkbox_"+authArr[i]).attr('title',0);
    };
    layer.load(2);
    $.ajax({
        url: "/tw_epm/actions/proRole_getByIds.action",
        type: 'POST',
        dataType: 'json',
        data: {
            ids:proRoleId,
            keys:'auths'
        },
        success: function(data) {
            if(data.total > 0){
                for (var i = 0; i < data.resultList[0].auths.length; i++) {
                    var auth = data.resultList[0].auths[i];
                    $("#checkbox_"+auth.name).bootstrapSwitch('setState',true);
                    $("#checkbox_"+auth.name).attr('title',auth.proRoleAuthId);
                };

            }
            $("#theProRoleId").val(proRoleId);
            nowProRoleAuthBox = layer.open({
              type: 1,
              shade: false,
              title: name+"-配置权限", //不显示标题
              content: $('#proRoleAuthBox'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
              maxWidth:2000,
              cancel: function(){
                $("#theProRoleId").val(0);
                nowProRoleAuthBox = 0;
              }
            });
            layer.closeAll('loading');
        },
        error: function(data) {
            layer.closeAll('loading');
            layer.msg(JSON.parse(data.responseText).error, {
                icon: 5
            });
        }
    });
}

function configureAuth(checkBox,auth){
    console.log(checkBox[0].checked);
    console.log("auth:"+auth);
    if($("#theProRoleId").val() != 0){
        if(checkBox[0].checked){
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/proRoleAuth_add.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    auth:auth,
                    proRole:$("#theProRoleId").val()
                },
                success: function(data) {
                    layer.msg('修改成功', {
                        icon: 6
                    });
                    layer.closeAll('loading');
                },
                error: function(data) {
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).error, {
                        icon: 5
                    });
                }
            });
        }else{
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/proRoleAuth_deleteByIds.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    ids:$("#checkbox_"+auth).attr('title')
                },
                success: function(data) {
                    layer.msg('修改成功', {
                        icon: 6
                    });
                    layer.closeAll('loading');
                },
                error: function(data) {
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).error, {
                        icon: 5
                    });
                }
            });
        }
    }
    
}


function initLearnDoc(id){
    pageListmanageLearnDoc.init({
        requesturl:"/tw_epm/actions/learnDoc_getByProjectAndName.action",//向后台请求数据路径
        dataListId:"#pageList_LearnDoc_tbody",//填充数据的元素可根据id(要写#  eg:#content)也可根据class(要写. eg:.content)也可直接写标签名称
        pageUI:"#pageList_LearnDoc_pageui",//分页条按钮填充区域命名规则同上
        myname:"pageListmanageLearnDoc",//用户声明的变量名称
        searchData:{
            page:this.pg,
            pageSize:this.pgz,
            keys:"name+type+user+content+learnDocId",
            project:id,
            name:$("#learnDocName").val()
        },//用户需要传到后台的多余数据可以为字符串，数字，或者json数据(此项可用来筛选数据，做查询功能)
        pgz:10,//每页显示数据条数
        but_num:20,//分页条按钮每组最多显示多少页
        beginsearch:true,//是否在初始化时填充数据
        drawDatatoCotent:function (data){
                var allstr="";
                allstr="<tr>"
                +"</td><td>"+this.checkTypedof(data.name)
                +"</td><td>"+this.checkTypedof(data.type)
                +"</td><td>"+this.checkTypedof(data.user.name)+"("+this.checkTypedof(data.user.userId)+")"
                +"</td><td>"+"<a  class='btn btn-warning  btn-sm' href='"+data.content+"'>下载</a>\n<button type='button' class='btn btn-danger  btn-sm' onclick='deleteLearnDoc("+data.learnDocId+")'>删除</button>"
                +"</td></tr>";
                return allstr;
        }
    });
}

function searchLearnDoc(){
    pageListmanageLearnDoc.pg=1;
    pageListmanageLearnDoc.nowgroup=1;
    pageListmanageLearnDoc.searchData.name=$("#learnDocName").val();
    pageListmanageLearnDoc.searchAll();
}

function searchAllLearnDoc(){
    $("#learnDocName").val("");
    searchLearnDoc();
}

function addLearnDoc(){
    $("#learnDocBoxName").val("");
    $("#learnDocBoxType").val(0);
    $("#learnDocBoxProject").val(id);
    layer.open({
      type: 1,
      shade: false,
      title: "增加学习资料", //不显示标题
      content: $('#learnDocBox'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
      btn:["提交"],
      maxWidth:2000,
      cancel: function(){
        
      },
      btn1:function(index){
        layer.close(index);
        layer.load(2);
        $.ajax({
            url: "/tw_epm/actions/learnDoc_add.action",
            type: 'POST',
            dataType:"json",
            contentType: false,
            processData: false,
            data:new FormData($("#learnDocBoxForm")[0]),
            success: function(data) {
                layer.msg('修改成功', {
                    icon: 6
                });
                layer.closeAll('loading');
                pageListmanageLearnDoc.searchAll();
            },
            error: function(data) {
                pageListmanageLearnDoc.searchAll();
                layer.closeAll('loading');
                layer.msg(JSON.parse(data.responseText).error, {
                    icon: 5
                });
            }
        });
      }
    });
}

function deleteLearnDoc(learnDocId){
    layer.msg('删除后不可恢复，您确定删除吗？', {
        time: 0 //不自动关闭
        ,
        btn: ['删除', '取消'],
        yes: function(index) {
            layer.close(index);
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/learnDoc_deleteByIds.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    ids:learnDocId
                },
                success: function(data) {
                    layer.msg('删除成功', {
                        icon: 6
                    });
                    pageListmanageLearnDoc.searchAll();
                    layer.closeAll('loading');
                },
                error: function(data) {
                    pageListmanageLearnDoc.searchAll();
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).error, {
                        icon: 5
                    });
                }
            });
        }
    });
}


function initSpecification(id){
    pageListmanageSpecification.init({
        requesturl:"/tw_epm/actions/specification_getByProjectAndName.action",//向后台请求数据路径
        dataListId:"#pageList_Specification_tbody",//填充数据的元素可根据id(要写#  eg:#content)也可根据class(要写. eg:.content)也可直接写标签名称
        pageUI:"#pageList_Specification_pageui",//分页条按钮填充区域命名规则同上
        myname:"pageListmanageSpecification",//用户声明的变量名称
        searchData:{
            page:this.pg,
            pageSize:this.pgz,
            keys:"name+date+user+content+specificationId",
            project:id,
            name:$("#specificationName").val()
        },//用户需要传到后台的多余数据可以为字符串，数字，或者json数据(此项可用来筛选数据，做查询功能)
        pgz:10,//每页显示数据条数
        but_num:20,//分页条按钮每组最多显示多少页
        beginsearch:true,//是否在初始化时填充数据
        drawDatatoCotent:function (data){
                var allstr="";
                allstr="<tr>"
                +"</td><td>"+this.checkTypedof(data.name)
                +"</td><td>"+this.checkTypedof(data.date)
                +"</td><td>"+this.checkTypedof(data.user.name)+"("+this.checkTypedof(data.user.userId)+")"
                +"</td><td>"+"<a  class='btn btn-warning  btn-sm' href='"+data.content+"'>下载</a>\n<button type='button' class='btn btn-danger  btn-sm' onclick='deleteSpecification("+data.specificationId+")'>删除</button>"
                +"</td></tr>";
                return allstr;
        }
    });
}

function searchSpecification(){
    pageListmanageSpecification.pg=1;
    pageListmanageSpecification.nowgroup=1;
    pageListmanageSpecification.searchData.name=$("#specificationName").val();
    pageListmanageSpecification.searchAll();
}

function searchAllSpecification(){
    $("#specificationName").val("");
    searchSpecification();
}

function addSpecification(){
    $("#specificationBoxName").val("");
    $("#specificationBoxProject").val(id);
    layer.open({
      type: 1,
      shade: false,
      title: "增加项目规范", //不显示标题
      content: $('#specificationBox'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
      btn:["提交"],
      maxWidth:2000,
      cancel: function(){
        
      },
      btn1:function(index){
        layer.close(index);
        layer.load(2);
        $.ajax({
            url: "/tw_epm/actions/specification_add.action",
            type: 'POST',
            dataType:"json",
            contentType: false,
            processData: false,
            data:new FormData($("#specificationBoxForm")[0]),
            success: function(data) {
                layer.msg('修改成功', {
                    icon: 6
                });
                layer.closeAll('loading');
                pageListmanageSpecification.searchAll();
            },
            error: function(data) {
                pageListmanageSpecification.searchAll();
                layer.closeAll('loading');
                layer.msg(JSON.parse(data.responseText).error, {
                    icon: 5
                });
            }
        });
      }
    });
}

function deleteSpecification(specificationId){
    layer.msg('删除后不可恢复，您确定删除吗？', {
        time: 0 //不自动关闭
        ,
        btn: ['删除', '取消'],
        yes: function(index) {
            layer.close(index);
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/specification_deleteByIds.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    ids:specificationId
                },
                success: function(data) {
                    layer.msg('删除成功', {
                        icon: 6
                    });
                    pageListmanageSpecification.searchAll();
                    layer.closeAll('loading');
                },
                error: function(data) {
                    pageListmanageSpecification.searchAll();
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).error, {
                        icon: 5
                    });
                }
            });
        }
    });
}