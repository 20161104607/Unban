<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>心理诊断登录界面</title>
<link href="statics/css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="statics/js/jquery2.js"></script>
<script src="statics/js/cloud.js" type="text/javascript"></script>
<script language="javascript" src="statics/js/js.js"></script>
</head>

<body style="background-color:#1c77ac; background-image: url(statics/images/light.png) background-repeat:no-repeat; background-position:center top; overflow:hidden;">
<div id="mainBody">
  <div id="cloud1" class="cloud"></div>
  <div id="cloud2" class="cloud"></div>
</div>
<div class="logintop"> <span>欢迎登录心理诊断后台</span>
  <ul>
    <li><a href="#">返回首页</a></li>
  </ul>
</div>
<div class="loginbody"> <span class="systemlogo"></span>
  <div class="loginbox">
  <form action="login.do" method="post">
    <ul>
      <li>
        <input name="username" type="text" class="loginuser" value="" required placeholder="用户名" onclick="JavaScript:this.value=''"/>
      </li>
      <li>
        <input name="password" type="text" class="loginpwd" value="" required placeholder="密码" onclick="JavaScript:this.value=''"/>
      </li>
      <li>
      	${message}
        <input class="loginbtn" type="submit" value="登录">
      </li>
    </ul>
    </form>
  </div>
</div>
<div class="loginbm">版权所有： lv © Copyright 2014 - 2015.</div>
</body>
</html>