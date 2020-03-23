<%@page import="com.lly.entity.Doctor"%>
<%@page import="com.lly.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>用户管理-编辑</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="../css/x-admin.css" media="all">
</head>
<%
HttpSession session1 = request.getSession();

Doctor user = (Doctor)session1.getAttribute("login_doc");
%>
<body>
<div class="x-body">
		<form action="addchildArchives.do" method="post">
			 <table class="layui-table">
				<tbody>
					<tr>
						<td><span class="x-red">*</span>姓名</td>
						<td><input type="text" id="link" name="name" required placeholder="姓名"
							class="layui-input" value=""></td>
						<td><span class="x-red">*</span>性别</td>
						<td><select lay-verify="required" name="sex"
							class="layui-input">
								<option value="女">女</option>
								<option value="男">男</option>
						</select></td>
						<td><span class="x-red">*</span>年龄</td>
						<td><input type="text" id="link" name="age" required placeholder="年龄"
							class="layui-input" value=""></td>
					</tr>
					<tr>
						<td><span class="x-red">*</span>父亲id</td>
						<td><input type="text" id="link" name="father_id" required placeholder="父亲id"
							class="layui-input" value=""></td>
						<td><span class="x-red">*</span>母亲id</td>
						<td><input type="text" id="link" name="mother_id" required placeholder="母亲id"
							class="layui-input" value=""></td>
					</tr>
					<tr>
						<td>所属医生：</td>
						<td colspan="5"><input type="text" disabled="disabled"
							id="link" name="doctor" class="layui-input"
							value="<%=user.getId()%>"></td>
					</tr>

					<tr>
						<td colspan="6" align="right"><input type="submit"
							class="layui-btn" value="保存" />
							<button class="layui-btn">取消</button></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
<script src="../lib/layui/layui.js" charset="utf-8">
        </script> 
<script src="../js/x-layui.js" charset="utf-8">
        </script> 
<script>
            layui.use(['form','layer'], function(){
                $ = layui.jquery;
              var form = layui.form()
              ,layer = layui.layer;
            });
        </script>
</body>
</html>