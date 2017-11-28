<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<%
			String[] url_pages = request.getRequestURI().toString().split("/");
			String[] url_pages2 = url_pages[url_pages.length-1].split("\\.");
			String url_page = url_pages2[0];
		%>
		<div id="sidebar-nav" class="sidebar">
			<div class="sidebar-scroll">
				<nav>
					<ul class="nav">
						<li><a href="/tw_epm/end/admin/index/index.jsp" class="<% if(url_page.equals("index")) out.print("active");%>"><i class="lnr lnr-home"></i> <span>首页</span></a></li>
						<li>
							<a href="#menuProject" data-toggle="collapse" class="<% if(url_page.equals("projects")||url_page.equals("project")) out.print("active");else out.print("collapsed");%>"><i class="lnr lnr-store"></i> <span>项目管理</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>
							<div id="menuProject" class="collapse <% if(url_page.equals("projects")||url_page.equals("project")) out.print("in");else out.print("");%>">
								<ul class="nav">
									<li><a href="/tw_epm/end/admin/project/projects/projects.jsp" class="<% if(url_page.equals("projects")||url_page.equals("project")) out.print("active");%>">项目管理</a></li>
								</ul>
							</div>
						</li>
						<li><a href="/tw_epm/end/admin/personalInformation/personalInformation.jsp" class="<% if(url_page.equals("personalInformation")) out.print("active");%>"><i class="lnr lnr-user"></i> <span>个人中心</span></a></li>
					</ul>
				</nav>
			</div>
		</div>