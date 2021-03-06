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
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="../css/x-admin.css" media="all">
<link rel="stylesheet" href="../css/pag.css" media="all">

</head>
<%
	HttpSession session2 = request.getSession();
	List<Child> arc = (List<Child>)session2.getAttribute("findList");
	
%>
<body>
<div class="x-nav"> <span class="layui-breadcrumb"> <a><cite>首页</cite></a> <a><cite>档案管理</cite></a> </span> <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"  href="javascript:location.replace(location.href);" title="刷新"><i class="layui-icon" style="line-height:30px">ဂ</i></a> </div>
<div class="x-body">
  <div class="tools">
    <ul class="toolbar">
      <li onclick="user_management_add('添加用户','child_add.jsp','1000','600')"><span><img src="../images/t01.png" /></span>添加</li>
      <li onclick="user_management_add('数据导出','ertongdownload.do','1000','600')"><span><img src="../images/t02.png" /></span>导出</li>
    </ul>
    <span class="x-right" style="line-height:25px">共有数据：<%=arc.size() %> 条</span></xblock>
  </div>
  <table class="tablelist">
    <thead>
      <tr>
        </th>
        <th>姓名</th>
        <th> 性别 </th>
        <th> 年龄</th>
        <th> 父亲姓名</th>
        <th> 父亲电话 </th>
        <th> 母亲姓名 </th>
        <th> 母亲电话 </th>
        <th> 操作 </th>
      </tr>
    </thead>
    <tbody>
      <%
      	for(int i = 0;i<arc.size();i++){
      %>
      <tr>
        <td><%=arc.get(i).getName() %></td>
        <td><%=arc.get(i).getSex() %></td>
        <td ><%=arc.get(i).getAge() %></td>
        <td ><%=arc.get(i).getFather_name() %></td>
        <td><%=arc.get(i).getFather_phone() %></td>
        <td><%=arc.get(i).getMother_name() %></td>
        <td><%=arc.get(i).getMother_phone() %></td>
        <td>
        <a href="deleteInfo.do?id=<%=arc.get(i).getId()%>"><button class="sp">删除</button></a>
        </td>
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
            layui.use(['laydate','element','laypage','layer'], function(){
                $ = layui.jquery;
              lement = layui.element();//面包导航
              laypage = layui.laypage;//分页
              layer = layui.layer;//弹出层

            });
             /*用户-添加*/
            function user_management_add(title,url,w,h){
                x_admin_show(title,url,w,h);
            }
            /*用户-查看*/
            function user_management_show(title,url,w,h){
                x_admin_show(title,url,w,h);
            }
            // 用户-编辑
            function user_management_edit (title,url,w,h) {
                x_admin_show(title,url,w,h); 
            }
            </script> 
<script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>