<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>人格障碍诊断</title>
<link rel="stylesheet" type="text/css" href="../css/public.css">
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/public.js"></script>
<head></head>

<body id="bg">
	<!-- 左边节点 -->
	<div class="container">

		<div class="leftsidebar_box">
			<a href="main_doctor.jsp" target="main">
            <div class="line">
		    <img class="icon1" src="../images/coin01.png" />
            <img class="icon2" src="../images/coin02.png" />&nbsp;&nbsp;首页
			</div>
            </a>
            <!--症状诊断-->
           <dl class="system_log">
				<dt>
					<img class="icon1" src="../images/coin15.png" />
                    <img class="icon2" src="../images/coin16.png" />
                     儿童保健
                    <img class="icon3" src="../images/coin19.png" />
                    <img class="icon4" src="../images/coin20.png" />
				</dt>
				<dd>
					<img class="coin11" src="../images/coin111.png" />
                    <img class="coin22" src="../images/coin222.png" />
                    <a href="findAllChlid.do" target="main" class="cks">儿童档案</a>
                    <img class="icon5" src="../images/coin21.png" />
				</dd>
                <dd>
					<img class="coin11" src="../images/coin111.png" />
                    <img class="coin22" src="../images/coin222.png" />
                    <a href="findAllYiMiao.do" target="main" class="cks">疫苗接种</a>
                    <img class="icon5" src="../images/coin21.png" />
				</dd>
			</dl>
            <!--档案管理-->
            <dl class="system_log">
				<dt>
					<img class="icon1" src="../images/coin09.png" />
                    <img class="icon2" src="../images/coin10.png" />
                     <a href="findAllArchive.do" target="main" class="cks">档案管理</a>
                    <img class="icon3" src="../images/coin19.png" />
				</dt>
			</dl>
        <!--用户管理-->
			<dl class="system_log">
				<dt>
					<img class="icon1" src="../images/coin07.png" />
                    <img class="icon2" src="../images/coin08.png" />
                     慢性病
                    <img class="icon3" src="../images/coin19.png" />
                    <img class="icon4" src="../images/coin20.png" />
				</dt>
				<dd>
					<img class="coin11" src="../images/coin111.png" />
                    <img class="coin22" src="../images/coin222.png" />
                    <a href="chronic_disease1.do" target="main" class="cks">高血压</a>
                    <img class="icon5" src="../images/coin21.png" />
				</dd>
                <dd>
					<img class="coin11" src="../images/coin111.png" />
                    <img class="coin22" src="../images/coin222.png" />
                    <a href="chronic_disease2.do" target="main" class="cks">糖尿病</a>
                    <img class="icon5" src="../images/coin21.png" />
				</dd>
				<dd>
					<img class="coin11" src="../images/coin111.png" />
                    <img class="coin22" src="../images/coin222.png" />
                    <a href="chronic_disease3.do" target="main" class="cks">精神疾病</a>
                    <img class="icon5" src="../images/coin21.png" />
				</dd>
			</dl>
		</div>
	</div>
</body>
</html>