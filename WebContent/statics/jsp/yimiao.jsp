<%@page import="com.lly.entity.YiMiao"%>
<%@page import="com.lly.entity.Child"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>用户管理</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="../css/x-admin.css" media="all">
<link rel="stylesheet" href="../css/pag.css" media="all">

</head>
<%
	HttpSession session2 = request.getSession();
	List<Child> arc = (List<Child>) session2.getAttribute("find_yimiao");
	
	List<YiMiao> yimiao =  (List<YiMiao>)session.getAttribute("YiMiao");
	Integer num = (Integer)session.getAttribute("num");
	Integer yijiezhong = (Integer)session.getAttribute("yijiezhong");
	String str = (String)session.getAttribute("str");
	String str1 = (String)session.getAttribute("zhonglei");
%>
<body>
	<div class="x-nav">
		<span class="layui-breadcrumb"> <a><cite>首页</cite></a> <a><cite>档案管理</cite></a>
		</span> <a class="layui-btn layui-btn-small"
			style="line-height: 1.6em; margin-top: 3px; float: right"
			href="javascript:location.replace(location.href);" title="刷新"><i
			class="layui-icon" style="line-height: 30px">ဂ</i></a>
	</div>
	<div class="x-body">
		<div class="tools">
			<span class="x-right" style="line-height: 25px">未接种儿童：<%=arc.size()%>
				  &nbsp;&nbsp;&nbsp;
				  已接种儿童：<%=yijiezhong %>
				  &nbsp;&nbsp;&nbsp;
				 儿童总数：<%=num %>
			</span>
			</xblock>
		</div>
		<table class="tablelist">
			<thead>
				<form action="findYiMiao.do" method="post">
				<tr>
					<td><select lay-verify="required" name="zhonglei"
						class="layui-input">
							<option value="<%=str1 %>"><%=str1 %></option>
						<%
							switch (str1) {
							case "牛痘":
						%>
							<option value="卡介苗">卡介苗</option>
							<option value="乙肝">乙肝</option>
							<option value="小儿麻痹">小儿麻痹</option>
						<%
								break;
							case "卡介苗":
								str = "kajiemiao";
						%>
							<option value="牛痘">牛痘</option>
							<option value="乙肝">乙肝</option>
							<option value="小儿麻痹">小儿麻痹</option>
						<%
								break;
							case "乙肝":
								str = "yigan";
						%>
							<option value="牛痘">牛痘</option>
							<option value="卡介苗">卡介苗</option>
							<option value="小儿麻痹">小儿麻痹</option>
						<%
								break;
							case "小儿麻痹":
								str = "xiaoermabi";
						%>
							<option value="牛痘">牛痘</option>
							<option value="卡介苗">卡介苗</option>
							<option value="乙肝">乙肝</option>
						<%
								break;
							default:
								break;
							}
						%>	
					</select></td>
					<td colspan="6" align="right"><input type="submit"
							class="layui-btn" value="查找未接种儿童" />
					</td>
				</tr>
					</form>
				<tr>
					</th>
					<th>姓名</th>
					<th>性别</th>
					<th>年龄</th>
					<th>父亲姓名</th>
					<th>父亲电话</th>
					<th>母亲姓名</th>
					<th>母亲电话</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (int i = 0; i < arc.size(); i++) {
				%>
				<tr>
					<td><%=arc.get(i).getName()%></td>
					<td><%=arc.get(i).getSex()%></td>
					<td><%=arc.get(i).getAge()%></td>
					<td><%=arc.get(i).getFather_name()%></td>
					<td><%=arc.get(i).getFather_phone()%></td>
					<td><%=arc.get(i).getMother_name()%></td>
					<td><%=arc.get(i).getMother_phone()%></td>
					<td><a href="updateyijiezhong.do?id=<%=arc.get(i).getId()%>"><button
								class="sp">已接种</button></a> <a
						href="faduanxin.do?phone=<%=arc.get(i).getFather_phone()%>"><button
								class="sp">发短信通知</button></a></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
	<br />
	<br />
	<br />
	<script src="../lib/layui/layui.js" charset="utf-8"></script>
	<script src="../js/x-layui.js" charset="utf-8"></script>
	<script src="../js/jquery2.js" charset="utf-8"></script>
	<script src="../js/js.js" charset="utf-8"></script>
	<script>
		layui.use([ 'laydate', 'element', 'laypage', 'layer' ], function() {
			$ = layui.jquery;
			lement = layui.element();//面包导航
			laypage = layui.laypage;//分页
			layer = layui.layer;//弹出层

		});
		/*用户-添加*/
		function user_management_add(title, url, w, h) {
			x_admin_show(title, url, w, h);
		}
		/*用户-查看*/
		function user_management_show(title, url, w, h) {
			x_admin_show(title, url, w, h);
		}
		// 用户-编辑
		function user_management_edit(title, url, w, h) {
			x_admin_show(title, url, w, h);
		}
	</script>
	<script type="text/javascript">
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>