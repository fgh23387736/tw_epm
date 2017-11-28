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
	<link rel="stylesheet" type="text/css" href="point.css">
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
					<h3 class="page-title" id='pointTitle' style="float:left"></h3>
					<div style="clear:both;"></div>
					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">编辑节点基本信息</h3>
							<div class="right">
								<div type="button" class="btn btn-primary"  onclick="savePoint()">保存</div>
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
											<input type="text" class="form-control" id="thePointName"
									   placeholder="名称" >
										</td>
									</tr>
									<tr>
										<td>进度百分比%</td>
										<td>
											<input type="number" class="form-control" id="thePointState"
									   placeholder="进度百分比" min="0" max="100">
										</td>
									</tr>
									<tr>
										<td>时间</td>
										<td id="">
											<input type="text" class="form-control" id="thePointDate"
									   placeholder="时间" >
										</td>
									</tr>
									<tr>
										<td>描述</td>
										<td id="">
											<textarea class="form-control" placeholder="" rows="4" id="thePointDescribe"></textarea>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">问题列表</h3>
							<div class="right">
								<button type="button" class="btn-toggle-collapse"><i class="lnr lnr-chevron-up"></i></button>
								<button type="button" class="btn-remove"><i class="lnr lnr-cross"></i></button>
							</div>
						</div>
						<div class="panel-body" style="display: block;" id="pointProblems">
							<div class="pointProblemDiv">
								<div class="name">
									这是提问者姓名
								</div>
								<div class="content">
									这是问题
								</div>
								<div class="time">
									2017-01-11 下午 1:02<span>[回复]</span>
								</div>
								<div class="answerList">
									<div class="answer">
										<div class="name">
											这是回复者姓名
										</div>
										<div class="content">
											这是回答
										</div>
										<div class="time">
											2017-01-11 下午 1:02
										</div>
									</div>
									
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
	
	<div id="answerBox">
		<div class="form-group">
			<textarea class="form-control" placeholder="" rows="4" id="answerBoxAnswer"></textarea>
		</div>
	</div>
	<!-- Javascript -->
	<script src="${pageContext.request.contextPath }/statics/js/laydate/laydate.js"></script>
	<jsp:include   page="/end/script.jsp" flush="true"/>
	
	<script type="text/javascript" src="point.js"></script>
	
</body>
</html>
