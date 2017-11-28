<!doctype html>
<html lang="en">

<head>
	<title>三和彩钢</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<?php include $_SERVER['DOCUMENT_ROOT'].'/sanhe/webContent/end/link.php';?>
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
					<h3 class="page-title">商品类型</h3>
					<div class="panel panel-headline demo-icons">
						
						<div class="panel-body">
							<form class="form-inline" role="form">
								<div class="form-group" style="margin:5px 0px;">
									名称：
									<input type="text" class="form-control" id="typeName" 
										   placeholder="请输入名称">
								</div>
								<button type="button" class="btn btn-primary" style="margin:5px 0px;" onclick="pageListSearch()">搜索</button>
								<button type="button" class="btn btn-primary" style="margin:5px 0px;" onclick="pageListSearchAll()">查看全部</button>
							</form>
						</div>
					</div>
					<div class="panel panel-headline demo-icons">
						<div class="panel-heading">
							<div  class="btn btn-success" onclick="pageListAdd()">增加</div>
							<div  class="btn btn-danger" onclick="pageListDelete()">批量删除</div>
						</div>
						<div class="panel-body">
							<table class="table table-bordered">
								<thead>
									<th>
							       	<input type="checkbox" id="pageList_selectAll">
							       	</th>
							        <th>Id</th>
							        <th>名称</th>
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
		<?php include $_SERVER['DOCUMENT_ROOT'].'/sanhe/webContent/end/footer.php'; ?>
	</div>
	<!-- END WRAPPER -->
	<!-- Javascript -->
	<?php include $_SERVER['DOCUMENT_ROOT'].'/sanhe/webContent/end/script.php'; ?>
	<script type="text/javascript" src="goodsType.js"></script>
</body>

</html>
