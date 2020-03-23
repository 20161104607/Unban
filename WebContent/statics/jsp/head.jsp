<%@page import="com.lly.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>人格障碍诊断</title>
<link rel="stylesheet" type="text/css" href="../css/public.css" />
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/public.js"></script>
<script type="text/javascript" src="../js/external.js"></script>

</head>
<%
	HttpSession session1 = request.getSession();
	
	User user = (User)session1.getAttribute("login_user");
%>
<body>
	<!-- 头部 -->
	<div class="head">
		<div class="headL">
			<img class="headLogo" src="../images/banner2.png"/>
		</div>
		<div class="headR">
	    <a href="loginout.html" target="_parent">【退出】</a>
		</div>
	</div>
    <div class="user">
    <span><%=user.getUsername() %></span>
    </div> 
    
</body>

</html>