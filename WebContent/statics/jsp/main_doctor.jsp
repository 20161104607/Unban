<%@page import="com.lly.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页</title>
<link rel="stylesheet" href="../css/style.css" media="all"> 
</head>
<%
	HttpSession session2 = request.getSession();
	User user = (User)session2.getAttribute("login_user");
%>
<body background="../images/mainbg.jpg">
		<div class="clearfix" id="contentBox">
  <div id="first" class="buttonBox">
    <button style="color:#666; font-size:20px;">姓名：<%=user.getUsername() %></button>
    <div class="border"></div>
    <div class="border"></div>
  </div>

  <div id="first" class="buttonBox">
    <button style="color:#666; font-size:20px;"><%=user.getAddress() %></button>
    <div class="border"></div>
    <div class="border"></div>
  </div>

<div id="first" class="buttonBox">
    <button style="color:#666; font-size:20px;">最后登录日期：<%=user.getLasttime() %></button>
    <div class="border"></div>
    <div class="border"></div>
  </div>

</div>
</body>
</html>