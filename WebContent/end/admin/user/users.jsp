<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${user == null || user.type != 2}">
  	<script>
  		window.location = "/tw_epm/end/login/login.jsp";
  	</script>
</c:if>
<!doctype html>
<html lang="en">
<head>
	<title>项目管理</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<link rel="stylesheet" type="text/css" href="users.css">
	<jsp:include   page="/end/link.jsp" flush="true"/>
</head>

<body>
	<!-- WRAPPER -->
	<div id="wrapper">
		<!-- NAVBAR -->
		<jsp:include   page="../navbar.jsp" flush="true"/>
		<!-- END NAVBAR -->
		<!-- LEFT SIDEBAR -->
		<jsp:include   page="../leftSidebar.jsp" flush="true"/>
		<!-- END LEFT SIDEBAR -->
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content">
				<div class="container-fluid">
					<h3 class="page-title">人员管理</h3>
					<div class="panel panel-headline demo-icons">
						
						<div class="panel-body">
							<form class="form-inline" role="form">
								<div class="form-group" style="margin:5px 0px;">
									人员名称:
									<input type="text" class="form-control" id="userName" 
										   placeholder="人员名称">
								</div>
								<button type="button" class="btn btn-primary" style="margin:5px 0px;" onclick="pageListSearch()">查找</button>
								<button type="button" class="btn btn-primary" style="margin:5px 0px;" onclick="pageListSearchAll()">查找全部</button>
							</form>
						</div>
					</div>
					<div class="panel panel-headline demo-icons">
						<div class="panel-heading">
							<!-- <div  class="btn btn-success" onclick="pageListAdd()">添加</div>
							<div  class="btn btn-danger" onclick="pageListDelete()">删除</div> -->
						</div>
						<div class="panel-body">
							<table class="table table-bordered">
								<thead>
									<!-- <th>
							       	<input type="checkbox" id="pageList_selectAll">
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
								<tbody id="pageList_tbody">
								</tbody>
							</table>
							<div class="pageUI">
							    <div class="page_footer" id="pageList_pageui" onselectstart="return false">
							    </div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- END MAIN CONTENT -->
		</div>
		<!-- END MAIN -->
		<div class="clearfix"></div>
		<jsp:include   page="/end/footer.jsp" flush="true"/>
	</div>
	<!-- END WRAPPER -->
	<div id="userTypeBox">
		<table class="table table-bordered">
			<div class="form-group" >
				<select class="form-control" id="userTypeBoxType" >
					<option value="0" selected>普通用户</option>
					<option value="1" selected>项目负责人</option>
				</select>
			</div>
		</table>
	</div>
	<!-- Javascript -->
	<script src="${pageContext.request.contextPath }/statics/js/laydate/laydate.js"></script>
	<jsp:include   page="/end/script.jsp" flush="true"/>
	<script type="text/javascript" src="users.js"></script>
</body>

</html>
