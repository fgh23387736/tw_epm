<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	text
	time=<%=new java.util.Date() %>
	${pageContext.request.contextPath }/imgage
</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/vendor/jquery/jquery.min.js"></script>
<script type="text/javascript">
	/* $.ajax({
        url: '/tw_epm/user_Login.action',
        type: 'POST',
        dataType: 'json',
        data: {
            page:10,
            pageSize:10,
            loginName:"233",
            searchData:JSON.stringify({
            	test:666
            })
        },
        success: function(data) {
            
        },
        error: function(data) {
            
        }
    }); */

    $.ajax({
        url: '/tw_epm/user_Login.action',
        type: 'POST',
        dataType: 'json',
        data: {
            page:"1",
            pageSize:"10",
            searchData:{
            	test:666
            }
        },
        success: function(data) {
            
        },
        error: function(data) {
            
        }
    });
</script>
