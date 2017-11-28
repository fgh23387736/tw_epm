<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<title>项目管理</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<jsp:include   page="/end/link.jsp" flush="true"/>
	<link rel="stylesheet" type="text/css" href="project.css">
</head>

<body>
	<!-- WRAPPER -->
	<div id="wrapper">
		<!-- NAVBAR -->
		<jsp:include   page="../../navbar.jsp" flush="true"/>
		<!-- END NAVBAR -->
		<!-- LEFT SIDEBAR -->
		<jsp:include   page="../../leftSidebar.jsp" flush="true"/>
		<!-- END LEFT SIDEBAR -->
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content">
				<div class="container-fluid">
					<h3 class="page-title" id='projectTitle' style="float:left"></h3>
					<div style="clear:both;"></div>
					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">相关数据</h3>
							<div class="right">
							</div>
						</div>
						<div class="panel-body" style="display: block;">
							<div>
								<p>项目进度 <span class="label-percent" id="projectPercentLable">0%</span></p>
								<div class="progress" style="margin-bottom:0px;">
									<div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%" id="projectPercentBar">
										<div id="projectPercentInside"></div>
									</div>
								</div>
								<div id="pointListForPercentBar">
									<!-- <div class="pointForPercentBar">
										100%
									</div> -->
								</div>
							</div>
							<div id="projectChart">
								
							</div>
						</div>
					</div>
					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">编辑项目基本信息</h3>
							<div class="right">
								<div type="button" class="btn btn-primary"  onclick="saveProject()">保存</div>
								<button type="button" class="btn-toggle-collapse"><i class="lnr lnr-chevron-up"></i></button>
								<button type="button" class="btn-remove"><i class="lnr lnr-cross"></i></button>
							</div>
						</div>
						<div class="panel-body" style="display: block;">
							<table class="table table-bordered">
								<tbody >
									<tr>
										<td>名称</td>
										<td>
											<input type="text" class="form-control" id="theProjectName"
									   placeholder="名称" >
										</td>
									</tr>
									<tr>
										<td>开始时间</td>
										<td id="">
											<input type="text" class="form-control" id="theProjectStartDate"
									   placeholder="开始时间" >
										</td>
									</tr>
									<tr>
										<td>预计结束时间</td>
										<td id="">
											<input type="text" class="form-control" id="theProjectEndDateA"
									   placeholder="预计结束时间" >
										</td>
									</tr>
									<tr>
										<td>实际结束时间</td>
										<td id="">
											<input type="text" class="form-control" id="theProjectEndDateB"
									   placeholder="实际结束时间" >
										</td>
									</tr>
									<tr>
										<td>内容</td>
										<td id="">
											<textarea class="form-control" placeholder="" rows="4" id="theProjectContent"></textarea>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="panel" >
						<div class="panel-heading">
							<h3 class="panel-title">项目角色</h3>
							<div class="right">
								<form class="form-inline" role="form" style="float:left;margin-right:3px;" onsubmit="return false;">
									<div class="form-group">
										<input type="text" class="form-control" id="roleName"
											   placeholder="角色名称" >
									</div>
									<div type="button" class="btn btn-primary"  onclick="searchRole()">搜索</div>
									<div type="button" class="btn btn-primary"  onclick="searchAllRole()">查看全部</div>
									<div type="button" class="btn btn-success"  onclick="addRole()">增加</div>
								</form>
								<button type="button" class="btn-toggle-collapse"><i class="lnr lnr-chevron-up"></i></button>
								<button type="button" class="btn-remove"><i class="lnr lnr-cross"></i></button>
							</div>
						</div>
						<div class="panel-body" style="display: block;">
							<table class="table table-bordered">
								<thead>
									<!-- <th>
											<input type="checkbox" id="pageList_Word_selectAll">
											</th> -->
											<th>名称</th>
											<th>权限</th>
											<th>操作</th> 
								</thead>
								<tbody id="pageList_Role_tbody">
								</tbody>
							</table>
							<div class="pageUI">
									<div class="page_footer" id="pageList_Role_pageui" onselectstart="return false">
									</div>
							</div>
						</div>
					</div>
					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">项目人员</h3>
							<div class="right">
								<form class="form-inline" role="form" style="float:left;margin-right:3px;" onsubmit="return false;">
									<div class="form-group">
										<input type="text" class="form-control" id="userName"
											   placeholder="用户姓名" >
									</div>
									<div type="button" class="btn btn-primary"  onclick="searchUser()">搜索</div>
									<div type="button" class="btn btn-primary"  onclick="searchAllUser()">查看全部</div>
									<div type="button" class="btn btn-success"  onclick="addUser()">增加</div>
								</form>
								<button type="button" class="btn-toggle-collapse"><i class="lnr lnr-chevron-up"></i></button>
								<button type="button" class="btn-remove"><i class="lnr lnr-cross"></i></button>
							</div>
						</div>
						<div class="panel-body" style="display: block;">
							<table class="table table-bordered">
								<thead>
									<!-- <th>
											<input type="checkbox" id="pageList_selectAll">
											</th> -->
											<th>人员Id</th>
											<th>名称</th>
											<th>手机</th>
											<th>角色</th>
											<th>操作</th>
								</thead>
								<tbody id="pageList_User_tbody">
								</tbody>
							</table>
							<div class="pageUI">
									<div class="page_footer" id="pageList_User_pageui" onselectstart="return false">
									</div>
							</div>
						</div>
					</div>

					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">现场记录</h3>
							<div class="right">
								<!-- 搜索条，后期可能会用，现在不用 -->
								<!-- <form class="form-inline" role="form" style="float:left;margin-right:3px;" onsubmit="return false;">
									<div class="form-group">
										<input type="text" class="form-control" id="userNameWorksiteRecord"
											   placeholder="上传用户姓名" >
									</div>
									<div type="button" class="btn btn-primary"  onclick="searchWorksiteRecord()">搜索</div>
									<div type="button" class="btn btn-primary"  onclick="searchAllWorksiteRecord()">查看全部</div>
									<div type="button" class="btn btn-success"  onclick="addWorksiteRecord()">增加</div>
								</form>-->
								<button type="button" class="btn-toggle-collapse"><i class="lnr lnr-chevron-up"></i></button>
								<button type="button" class="btn-remove"><i class="lnr lnr-cross"></i></button>
							</div>
						</div>
						<div class="panel-body" style="display: block;">
							<table class="table table-bordered">
								<thead>
									<!-- <th>
											<input type="checkbox" id="pageList_selectAll">
											</th> -->
											<th>图片</th>
											<th>位置</th>
											<th>日期</th>
											<th>上传者名称(编号)</th>
											<th>备注</th>
											<!-- <th>操作</th> -->
								</thead>
								<tbody id="pageList_WorksiteRecord_tbody">
								</tbody>
							</table>
							<div class="pageUI">
									<div class="page_footer" id="pageList_WorksiteRecord_pageui" onselectstart="return false">
									</div>
							</div>
						</div>
					</div>

					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">项目节点</h3>
							<div class="right">
								<!-- 搜索条，后期可能会用，现在不用 -->
								<form class="form-inline" role="form" style="float:left;margin-right:3px;" onsubmit="return false;">
									<div class="form-group">
										<input type="text" class="form-control" id="pointName"
											   placeholder="节点名称" >
									</div>
									<div type="button" class="btn btn-primary"  onclick="searchPoint()">搜索</div>
									<div type="button" class="btn btn-primary"  onclick="searchAllPoint()">查看全部</div>
									<div type="button" class="btn btn-success"  onclick="addPoint()">增加</div>
								</form>
								<button type="button" class="btn-toggle-collapse"><i class="lnr lnr-chevron-up"></i></button>
								<button type="button" class="btn-remove"><i class="lnr lnr-cross"></i></button>
							</div>
						</div>
						<div class="panel-body" style="display: block;">
							<table class="table table-bordered">
								<thead>
									<!-- <th>
											<input type="checkbox" id="pageList_selectAll">
											</th> -->
											<th>名称</th>
											<th>项目进度</th>
											<th>日期</th>
											<th>上传者名称(编号)</th>
											<th>问题</th>
											<th>操作</th>
								</thead>
								<tbody id="pageList_Point_tbody">
								</tbody>
							</table>
							<div class="pageUI">
									<div class="page_footer" id="pageList_Point_pageui" onselectstart="return false">
									</div>
							</div>
						</div>
					</div>
				</div>
				<!-- END MAIN CONTENT -->
			</div>
			<!-- END MAIN -->
		</div>
		<div class="clearfix"></div>
		<jsp:include   page="/end/footer.jsp" flush="true"/>
	</div>
	<!-- END WRAPPER -->
	
	<div id="roleBox">
		<div class="form-group">
			<input type="text" class="form-control" id="roleBoxName"
				   placeholder="名称" >
		</div>
		<div class="form-group">
			<select class="form-control" id="roleBoxAuth">
				<option value="0">项目临时人员</option>
				<option value="1">项目工作人员</option>
				<option value="2">现场负责人</option>
			</select>
		</div>
	</div>

	<div id="userBox">
		<div class="input-group">
			<input type="number" class="form-control" id="userBoxUser"
					   placeholder="用户ID" min="1">
			<span class="input-group-btn"><button class="btn btn-primary" type="button" onclick="choseUser()">选择</button></span>
		</div>
		<br>
		<div class="form-group" >
			<select class="form-control" id="userBoxProRole" >
			</select>
		</div>
	</div>
	
	<div id="userChoseBox">
		<div class="panel" >
			<div class="panel-heading">
				<h3 class="panel-title">选择人员</h3>
				<div class="right">
					<form class="form-inline" role="form" style="float:left;margin-right:3px;" onsubmit="return false;">
						<div class="form-group">
							<input type="text" class="form-control" id="choseUserName"
								   placeholder="人员名称" >
						</div>
						<div type="button" class="btn btn-primary"  onclick="searchChosenUser()">搜索</div>
						<div type="button" class="btn btn-primary"  onclick="searchAllChosenUser()">查看全部</div>
					</form>
				</div>
			</div>
			<div class="panel-body" style="display: block;">
				<table class="table table-bordered">
					<thead>
						<!-- <th>
								<input type="checkbox" id="pageList_Word_selectAll">
								</th> -->
								<th>编号</th>
								<th>名称</th>
								<th>级别</th>
								<th>手机</th>
								<th>email</th>
								<th>微信</th>
								<th>积分</th>
								<th>操作</th>
					</thead>
					<tbody id="pageList_ChoseUser_tbody">
					</tbody>
				</table>
				<div class="pageUI">
						<div class="page_footer" id="pageList_ChoseUser_pageui" onselectstart="return false">
						</div>
				</div>
			</div>
		</div>
	</div>

	<div id="pointBox">
		<table class="table table-bordered">
			<tbody >
				<tr>
					<td>名称</td>
					<td>
						<input type="text" class="form-control" id="pointBoxName"
				   placeholder="名称" >
					</td>
				</tr>
				<tr>
					<td>进度(%)</td>
					<td id="">
						<input type="number" class="form-control" id="pointBoxState"
				   placeholder="进度" min="0" max="100">
					</td>
				</tr>
				<tr>
					<td>日期</td>
					<td id="">
						<input type="text" class="form-control" id="pointBoxDate"
				   placeholder="日期" >
					</td>
				</tr>
				<tr>
					<td>描述</td>
					<td id="">
						<textarea class="form-control" placeholder="" rows="4" id="pointBoxDescribe"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<!-- Javascript -->
	<script src="${pageContext.request.contextPath }/statics/js/laydate/laydate.js"></script>
	<jsp:include   page="/end/script.jsp" flush="true"/>
	
	<script type="text/javascript" src="project.js"></script>
	
</body>
</html>
