<%@page import="com.lly.entity.Archive"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <head>
        <meta charset="utf-8">
        <title>
            用户查看
        </title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="format-detection" content="telephone=no">
        <link rel="stylesheet" href="../css/x-admin.css" media="all">
    </head>
    <%
    	HttpSession session2 = request.getSession();
    	Archive archive =  (Archive)session2.getAttribute("onclick_archive");
    %>
    <body>
        <table class="layui-table">
    <tbody>
    	<from action="updateInfo.to" method="post">
      <tr>
        <td><span class="x-red">*</span>姓名</td>
        <td><input type="text" id="link" name="name" class="layui-input"  required placeholder="姓名" value="<%=archive.getName() %>"></td>
        <td>性别</td>
        <td ><select lay-verify="required" name="sex" class="layui-input">
            	<option value="男"><%=archive.getSex() %></option>
            <%
            	if(archive.getSex().equals("男")){
            %>
            	<option value="女">女</option>
            <%
            	}else{
            %>
            	<option value="男">男</option>
            <%
            	}
            %>
          </select></td>
        <td >籍贯</td>
        <td ><input type="text" id="link" name="from" required placeholder="籍贯" class="layui-input" value="<%=archive.getFrom()%>"></td>
      </tr>
      <tr>
        <td>出生日期</td>
        <td><input type="text" id="link" name="birthday"  required placeholder="出生日期" class="layui-input" value="<%=archive.getBirthday()%>"></td>
        <td>年龄</td>
        <td ><input type="text" id="link" name="age" required placeholder="年龄" class="layui-input" value="<%=archive.getAge()%>"></td>
        <td >宗教信仰</td>
        <td ><input type="text" id="link" name="faith" required placeholder="宗教信仰" class="layui-input" value="<%=archive.getFaith()%>"></td>
      </tr>
      <tr>
        <td>出生地</td>
        <td><input type="text" id="link" name="native1" required placeholder="出生地"  class="layui-input" value="<%=archive.getNative1()%>"></td>
        <td>文化程度</td>
        <td colspan="3" ><select lay-verify="required" name="degree" class="layui-input">
         <option value="<%=archive.getDegree() %>"><%=archive.getDegree() %></option>
        	<%
        		if(archive.getDegree().equals("小学")){
        	%>
            <option value="中学">中学</option>
            <option value="大学">大学</option>
            <%
        		}else if(archive.getDegree().equals("中学")){
            %>
            	<option value="小学">小学</option>
            	<option value="大学">大学</option>
            <%
        		}else{
            %>
            	<option value="小学">小学</option>
            	<option value="中学">中学</option>
            <%
        		}
            %>
          </select></td>
      </tr>
      <tr>
        <td>邮政编码</td>
        <td><input type="text" id="link" name="postal_code" required placeholder="邮政编码" class="layui-input" value="<%=archive.getPostal_code()%>"></td>
        <td>电话号码</td>
        <td ><input type="text" id="link" name="phone" class="layui-input" required placeholder="电话号码" value="<%=archive.getPhone()%>"></td>
        <td >邮箱</td>
        <td ><input type="text" id="link" name="email" class="layui-input" required placeholder="邮箱" value="<%=archive.getEmail()%>"></td>
      </tr>
      <tr>
        <td>现住址</td>
        <td colspan="3"><input type="text" id="link" name="address" required placeholder="现住址" class="layui-input" value="<%=archive.getAddress()%>"></td>
        <td >身高</td>
        <td ><input type="text" id="link" name="high" class="layui-input" required placeholder="身高" value="<%=archive.getHigh()%>"></td>
      </tr>
      <tr>
        <td>证件号码：</td>
        <td colspan="3"><input type="text" id="link" name="id_number" required placeholder="证件号码"  class="layui-input" value="<%=archive.getId_number()%>"></td>
        <td >体重</td>
        <td ><input type="text" id="link" name="weight" required placeholder="体重" class="layui-input" value="<%=archive.getWeight()%>"></td>
      </tr>
      <tr>
        <td>婚姻状况：</td>
        <td><select lay-verify="required" name="marital" class="layui-input">
        	<option value="<%=archive.getMarital()%>"><%=archive.getMarital()%></option>
        	<%
        		if(archive.getMarital().equals("已婚")){
        	%>
            <option value="未婚">未婚</option>
            <%
        		}else{
            %>
            <option value="已婚">已婚</option>
            <%
        		}
            %>
          </select></td>
        <td>慢性病：</td>
        <td ><select lay-verify="required" name="chronic_disease" class="layui-input">
        	<%
        		int a = archive.getChronic_disease();
        		String str = "";
        		switch(a){
        			case 0:
        				str = "无";
        				break;
        			case 1:
        				str = "高血压";
        				break;
        			case 2:
        				str = "糖尿病";
        				break;
        			case 3:
        				str = "精神疾病";
        				break;
        			default:
        				str = "";
        				break;
        			}
        	%>
        	<option value="<%=a%>"><%=str %></option>
        	<%
        		if(a==0){
        	%>
	            <option value="1">高血压</option>
	            <option value="2">糖尿病</option>
	            <option value="3">精神疾病</option>
            <%
        		}else if(a == 1){
            %>
            	<option value="0">无</option>
	            <option value="2">糖尿病</option>
	            <option value="3">精神疾病</option>
            <%
        		}else if(a == 2){
            %>
           		<option value="0">无</option>
           		<option value="1">高血压</option>
	            <option value="3">精神疾病</option>
            <%
        		}else{
            %>
            	<option value="0">无</option>
           		<option value="1">高血压</option>
           		<option value="2">糖尿病</option>
            <%
        		}
            %>
          </select></td>
        <td >是否有儿童：</td>
        <td ><select lay-verify="required" name="children" class="layui-input">
            <option value="0">无</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
          </select></td>
      </tr>
      <tr>
        <td>所属医生：</td>
        <td colspan="5"><input type="text" id="link" name="doctor" class="layui-input"></td>
      </tr>
      
      <tr>
        <td colspan="6" align="right"><a href="findAllArchive.do"><button class="layui-btn">保存</button></a>
          <a href="findAllArchive.do"><button class="layui-btn"> 取消 </button></a></td>
      </tr>
      </from>
    </tbody>
  </table>
    <script src="../lib/layui/layui.js" charset="utf-8">
        </script>
    <script src="../js/x-layui.js" charset="utf-8">
        </script>
    <script>
            layui.use(['form','layer','layedit'], function(){
                $ = layui.jquery;
              var form = layui.form()
              ,layer = layui.layer
              ,layedit = layui.layedit;
            });
        </script>
    </body>

</html>