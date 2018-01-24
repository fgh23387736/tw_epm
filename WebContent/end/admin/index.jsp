<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${user == null }">
  	<script>
  		window.location = "/tw_epm/end/login/login.jsp";
  	</script>
</c:if>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta content="text/html;charset=UTF-8"/>
	<title>工程管理</title>
	<meta name="keywords" content="fsLayuiPlugin,layui,layuiPlugin,layuiæä»¶,layuiå¿«éå¼åæä»¶" />
    <meta name="description" content="fsLayuiPlugin,layui,layuiPlugin,layuiæä»¶,layuiå¿«éå¼åæä»¶" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<meta http-equiv ="Pragma" content = "no-cache"/>
	<meta http-equiv="Cache-Control" content="no cache" />
	<meta http-equiv="Expires" content="0" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no"/>
	<script src="https://cdn.bootcss.com/pace/1.0.2/pace.min.js"></script>
	<link href="https://cdn.bootcss.com/pace/1.0.2/themes/pink/pace-theme-flash.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="../../assets/plugins/layui/css/layui.css" media="all"/>
<!-- 	<link rel="stylesheet" type="text/css" href="https:p//at.alicdn.com/t/font_520106_q8xykrwf86ywrk9.css" media="all"/> -->
	<link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
	<script type="text/javascript" src="../../assets/plugins/jquery/jquery.min.js"></script>
	<link href="../../assets/plugins/contextMenu/jquery.contextMenu.min.css" rel="stylesheet">
	<script src="../../assets/plugins/contextMenu/jquery.contextMenu.min.js"></script>
	<link rel="stylesheet" type="text/css" href="../../assets/css/fs.css?v=1.6.3" media="all"/>
	<script type="text/javascript" src="../../assets/plugins/layui/layui.js"></script>
	<script type="text/javascript" src="../../assets/plugins/frame/js/fsDict.js?v=1.6.3"></script>
	<script type="text/javascript" src="../../assets/plugins/frame/js/fs.js?v=1.6.3"></script>
	<script type="text/javascript" src="../../assets/plugins/frame/js/main.js?v=1.6.3"></script>
</head>
<body>
<div class="layui-layout layui-layout-admin">

  <!-- 顶部 -->
  <div class="layui-header">
    <a href="./index.jsp" class="layui-hide-xs"><div class="layui-logo">工程管理</div></a>
    <a href="javascript:;" class="layui-hide-xs"><div class="fsSwitchMenu"><i class="fa fa-outdent"></i></div></a>
    
    <!-- 顶部菜单 -->
    <ul class="layui-nav layui-layout-left fsTopMenu" id="fsTopMenu" lay-filter="fsTopMenu">
    	
      <!-- <li class="layui-nav-item layui-this" dataPid="fs1"><a href="javascript:;"><i class="layui-icon">&#xe68e;</i> <cite>控制台</cite></a></li>
      <li class="layui-nav-item" dataPid="fs2"><a href="javascript:;">测试</a></li> -->
    </ul>
    
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
        	<img src="/tw_epm/assets/images/100.jpg" class="layui-nav-img">
         	${user.name}
        </a>
        <dl class="layui-nav-child">
          <dd><a href="javascript:;">基本资料</a></dd>
          <dd class="layui-hide-sm"><a href="/tw_epm/actions/user_signOut.action"><i class="fa fa-sign-out"></i> 退出</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item layui-hide-xs"><a href="/tw_epm/actions/user_signOut.action"><i class="fa fa-sign-out"></i> 退出</a></li>
    </ul>
  </div>


  <!-- 左边菜单 -->
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree fsLeftMenu"  lay-filter="fsLeftMenu" id="fsLeftMenu">
      	<!-- <li class="layui-nav-item" dataPid="fs1" style="">
      		<a href="javascript:;" menuId="home" dataUrl="views/home/index.html"><i class="layui-icon">&#xe68e;</i> <cite>首页</cite></a>
      	</li>
        <li class="layui-nav-item layui-nav-itemed" dataPid="fs1" style="display: none;">
          <a href="javascript:;">案例</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:;" menuId="datagrid" dataUrl="views/datagrid/index.html"><i class="layui-icon">&#xe705;</i> <cite>数据表格666</cite></a></dd>
            <dd><a href="javascript:;" menuId="datagrid2" dataUrl="views/datagrid2/index.html"><i class="layui-icon">&#xe60a;</i> <cite>数据表格2</cite></a></dd>
            <dd><a href="javascript:;" menuId="treeDatagrid" dataUrl="views/treeDatagrid/index.html"><i class="layui-icon">&#xe62c;</i> <cite>树+表格</cite></a></dd>
            <dd><a href="javascript:;" menuId="multiDatagrid" dataUrl="views/multiDatagrid/index.html"><i class="layui-icon">&#xe60a;</i> <cite>多数据表格</cite></a></dd>
            <dd><a href="javascript:;" menuId="tabDatagrid" dataUrl="views/tabDatagrid/index.html"><i class="layui-icon">&#xe609;</i> <cite>tab数据表格</cite></a></dd>
            <dd><a href="javascript:;" menuId="complexDatagrid" dataUrl="views/complexDatagrid/index.html"><i class="layui-icon">&#xe615;</i> <cite>复杂数据表格</cite></a></dd>
            <dd><a href="javascript:;" menuId="linkageDatagrid" dataUrl="views/linkageDatagrid/index.html"><i class="layui-icon">&#xe614;</i> <cite>联动数据表格</cite></a></dd>
            <dd><a href="javascript:;" menuId="linkageDatagrid2" dataUrl="views/linkageDatagrid2/index.html"><i class="layui-icon">&#xe658;</i> <cite>联动数据表格(复杂)</cite></a></dd>
            <dd><a href="javascript:;" menuId="staticDatagrid" dataUrl="views/staticDatagrid/index.html"><i class="layui-icon">&#xe6b2;</i> <cite>表格数据提交</cite></a></dd>
          </dl>
        </li>
        <li class="layui-nav-item " dataPid="fs1" style="display: none;">
          <a href="javascript:;">其他页面</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:;" menuId="demo_404" dataUrl="404.html">404</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item " dataPid="fs2" style="display: none;">
          <a href="javascript:;">基本元素</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:;" menuId="demo_button" dataUrl="http://www.layui.com/demo/button.html">按钮</a></dd>
            <dd><a href="javascript:;" menuId="demo_form"  dataUrl="http://www.layui.com/demo/form.html">表单</a></dd>
            <dd><a href="javascript:;" menuId="demo_tab" dataUrl="http://www.layui.com/demo/tab.html">选项卡</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item" dataPid="fs2" style="display: none;"><a href="javascript:;" menuId="demo_badge" dataUrl="http://www.layui.com/demo/badge.html">徽章</a></li>
        <li class="layui-nav-item" dataPid="fs2" style="display: none;"><a href="javascript:;" menuId="demo_table" dataUrl="http://www.layui.com/demo/table.html">数据表格</a></li>
        -->
      </ul>
    </div>
  </div>

  <!-- 右边内容区域 -->
  <div class="layui-body layui-form">
  	<div class="layui-tab layui-tab-card fsTab" lay-filter="fsTab" lay-allowClose="true">
  		
  		<!-- 菜单导航 -->
		<ul class="layui-tab-title" id="fsTabMenu">
			<li class="layui-this" lay-id="home"><i class="layui-icon">&#xe68e;</i><cite>首页</cite><p class="layui-tab-close" style="display: none;"></p></li>
		</ul>
		<!-- 内容 -->
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show" lay-id="1">
				<iframe src="views/home/index.html?v=1.6.2.2"></iframe>
			</div>
		</div>
	</div>
  </div>

  <div class="layui-footer">
    <!-- 底部固定区域 -->
    &copy;HongLi
  </div>
</div>

<!-- 移动导航 -->
<div class="site-tree-mobile layui-hide">
  <i class="layui-icon">&#xe602;</i>
</div>
<div class="site-mobile-shade"></div>
</body>
</html>
<script type="text/javascript">
  function showProject(){
    console.log("ok");
    $("[menuid=2]").click();
    $("[menuid=projectHome]").click();
  }
  
</script>
