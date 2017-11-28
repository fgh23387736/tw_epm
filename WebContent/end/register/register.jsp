<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en" class="fullscreen-bg">

<head>
	<title>工程项目管理系统</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<jsp:include   page="/end/link.jsp" flush="true"/>
</head>

<body>
	<!-- WRAPPER -->
	<div id="wrapper">
		<div class="vertical-align-wrap">
			<div class="vertical-align-middle">
				<div class="auth-box ">
					<div class="left">
						<div class="content">
							<div class="header">
								<div class="logo text-center"><img src="../assets/img/logo-dark.png" alt="Klorofil Logo" style="width:50%;"></div>
								<p class="lead">注册新账户</p>
							</div>
							<form class="form-auth-small" action="#">
								<div class="form-group">
									<label for="signin-email" class="control-label sr-only">用户名</label>
									<input type="text" class="form-control" id="Username" value="" placeholder="用户名" onblur="oBlur_1()" onfocus="oFocus_1()">
								</div>
								<div class="form-group">
									<label for="signin-password" class="control-label sr-only">密码</label>
									<input type="password" class="form-control" id="Password" value="" placeholder="密码" onblur="oBlur_2()" onfocus="oFocus_2()">
								</div>
								<div class="form-group">
									<label for="signin-password" class="control-label sr-only">重复密码</label>
									<input type="password" class="form-control" id="RePassword" value="" placeholder="重复密码" onblur="oBlur_3()" onfocus="oFocus_3()">
								</div>
								<!-- <div class="form-group clearfix">
									<label class="fancy-radio" style="float:left;margin-right:10px;">
										<input name="Type" value="1" type="radio" checked>
										<span><i></i>普通用户</span>
									</label>
									<label class="fancy-radio" style="float:left;margin-right:10px;">
										<input name="Type" value="2" type="radio">
										<span><i></i>管理员</span>
									</label>
								</div> -->
								<div class="btn btn-primary btn-lg btn-block" onclick="submitTest()">注册</div>
								
							</form>
						</div>
					</div>
					<div class="right">
						<div class="overlay"></div>
						<div class="content text">
							<h1 class="heading">让工作变成享受</h1>
							<p>--工程项目管理系统</p>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- END WRAPPER -->
	<jsp:include   page="/end/script.jsp" flush="true"/>
	<script type="text/javascript" src="${pageContext.request.contextPath }/end/register/register.js"></script>
</body>

</html>
