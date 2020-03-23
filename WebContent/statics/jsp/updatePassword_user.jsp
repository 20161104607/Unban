<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户修改密码</title>
</head>
<body>
	<div style="align: center;">
		<form action="updatePsword.do" method="post">
			原密码：<input type="password" id="olep" name="oldpassword"/><br/><br/>
			新密码：<input type="password" id="newp" name="password1"/><br/><br/>
			重复输入：<input type="password" id="password" name="password"/><br/><br/>
			<input type="submit" value="提交"/>
			<input type="reset" value="重置"/> 
		</form>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#olep").			
		})
	</script>
</body>
</html>