<!doctype html>
<html lang="en">

<head>
	<title>三和彩钢</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<?php include $_SERVER['DOCUMENT_ROOT'].'/sanhe/webContent/end/link.php';?>
	<style type="text/css">
		.ImgUrlInput{
			width: 100px;
			height: 100px;
			opacity: 0;
			float: left;
			cursor: pointer;
			
		}
		.HeadImgUrl{
			margin-left:-100px;
			
		}
		.ImgUrlInput:hover{
			border: 2px solid #78A3ED;
		}
	</style>
</head>

<body>
	<!-- WRAPPER -->
	<div id="wrapper">
		<!-- NAVBAR -->
		<?php include $_SERVER['DOCUMENT_ROOT'].'/sanhe/webContent/end/admin/navbar.php';?>
		<!-- END NAVBAR -->
		<!-- LEFT SIDEBAR -->
		<?php include $_SERVER['DOCUMENT_ROOT'].'/sanhe/webContent/end/admin/leftSidebar.php';?>
		<!-- END LEFT SIDEBAR -->
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content">
				<div class="container-fluid">
					<h3 class="page-title">公司数据</h3>

					<div class="panel">
						<div class="panel-body">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td>公司联系人姓名:</td>
										<td>
											<input type="text" class="form-control" placeholder="公司联系人姓名" id="Name" >
										</td>
									</tr>
									<tr>
										<td>公司联系人电话:</td>
										<td>
											<input type="text" class="form-control" placeholder="公司联系人电话" id="Phone" >
										</td>
									</tr>
									<tr>
										<td>公司固定电话:</td>
										<td>
											<input type="text" class="form-control" placeholder="公司固定电话" id="LocalPhone" >
										</td>
									</tr>
									<tr>
										<td>公司联系人邮箱:</td>
										<td>
											<input type="text" class="form-control" placeholder="公司联系人邮箱" id="Email" >
										</td>
									</tr>
									<tr>
										<td>公司地址:</td>
										<td>
											<input type="text" class="form-control" placeholder="公司地址" id="Address" >
										</td>
									</tr>
									<tr>
										<td>公司所在经度:</td>
										<td>
											<input type="text" class="form-control" placeholder="公司所在经度" id="Longitude" >
										</td>
									</tr>
									<tr>
										<td>公司所在纬度:</td>
										<td>
											<input type="text" class="form-control" placeholder="公司所在纬度" id="Latitude" >
										</td>
									</tr>
									<tr>
										<td>公司简介:</td>
										<td>
											<textarea   id="Introduce" name="Introduce" style="max-width:750px;"></textarea>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<div  class="btn btn-primary" onclick="submit()">提交修改</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<!-- TABBED CONTENT -->
				</div>
			</div>
			<!-- END MAIN CONTENT -->
		</div>
		<!-- END MAIN -->
		<div class="clearfix"></div>
		<?php include $_SERVER['DOCUMENT_ROOT'].'/sanhe/webContent/end/footer.php'; ?>
	</div>
	<!-- END WRAPPER -->
	<!-- Javascript -->
	<script type="text/javascript" src="/sanhe/static/js/ueditor/utf8-php/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="/sanhe/static/js/ueditor/utf8-php/ueditor.all.js"></script>
    <!-- 实例化编辑器 -->
    <script type="text/javascript">
        
    </script>
    <?php include $_SERVER['DOCUMENT_ROOT'].'/sanhe/webContent/end/script.php'; ?>
	<script type="text/javascript" src="companyInformation.js"></script>
</body>

</html>
