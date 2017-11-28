<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
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
					<!-- OVERVIEW -->
					<div class="panel panel-headline">
						<div class="panel-heading">
							<h3 class="panel-title">站点数据</h3>
							<p class="panel-subtitle" id="nowDate"></p>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-md-3">
									<div class="metric">
										<span class="icon"><i class="fa fa-users"></i></span>
										<p>
											<span class="number" id='UserNumber'>0</span>
											<span class="title">管理员数</span>
										</p>
									</div>
								</div>
								<div class="col-md-3">
									<div class="metric">
										<span class="icon"><i class="fa fa-archive"></i></span>
										<p>
											<span class="number" id='GoodsNumber'>0</span>
											<span class="title">项目数量</span>
										</p>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="metric">
										<span class="icon"><i class="fa fa-shopping-bag"></i></span>
										<p>
											<span class="number" id='OrderNubmer'>0</span>
											<span class="title">访客数量</span>
										</p>
									</div>
								</div>
								<div class="col-md-3">
									<div class="metric">
										<span class="icon"><i class="fa fa-location-arrow"></i></span>
										<p>
											<span class="number" id='LoginNubmer'>0</span>
											<span class="title">访问数量</span>
										</p>
									</div>
								</div>
							</div>
							<div class="row" id="Charts" style="height:300px;">
								
							</div>
						</div>
					</div>
					<!-- END OVERVIEW -->
                    <div class="copyrights">Collect from <a href="http://www.cssmoban.com/" >企业网站模板</a></div>
					<div class="row">
						<div class="col-md-6">
							<!-- RECENT PURCHASES -->
							
							<!-- END RECENT PURCHASES -->
						</div>
						<div class="col-md-6">
							<!-- MULTI CHARTS -->
							
							<!-- END MULTI CHARTS -->
						</div>
					</div>
					<div class="row">
						<div class="col-md-7">
							<!-- TODO LIST -->
							
							<!-- END TODO LIST -->
						</div>
						<div class="col-md-5">
							<!-- TIMELINE -->
							
							<!-- END TIMELINE -->
						</div>
					</div>
					<div class="row">
						<div class="col-md-4">
							<!-- TASKS -->
							
							<!-- END TASKS -->
						</div>
						<div class="col-md-4">
							<!-- VISIT CHART -->
							
							<!-- END VISIT CHART -->
						</div>
						<div class="col-md-4">
							<!-- REALTIME CHART -->
							
							<!-- END REALTIME CHART -->
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
	<!-- Javascript -->
	<jsp:include   page="/end/script.jsp" flush="true"/>
	<script type="text/javascript" src="index.js"></script>
	
</body>

</html>
