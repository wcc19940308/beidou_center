<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
		<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>

			<!DOCTYPE html>
			<html>

			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
				<title>${ProjectName}</title>
				<link href="${WebUrl}/ctbt.ico" rel="icon" type="image/x-icon" />
				<link href="${WebUrl}/ctbt.ico" rel="shortcut icon" type="image/x-icon" />

				<ctbt:base/>

				<style type="text/css">
					body {
						width: 100%;
						height: 100%;
						background:url('${WebUrl}/images/bg/login_bg.png') no-repeat;
						background-size: 100%;
					}
				</style>
			</head>

			<body>

				<div id="maxDiv">
					<div style="position: absolute; top: 50%; left:50%; width: 400px; height: 220px;  margin:0px auto; margin-top:-100px; margin-left:-200px; 
					border:solid 1px #cccccc;background:url('${WebUrl}/images/bg/p20.png');"  >
						<div style="text-align: center; font-size: 20px; margin-top: 30px;margin-bottom: 30px;">${ProjectName}</div>
						<div id="form1" class="liger-form" align="center" labelAlign="right" inputWidth="200" 
						labelWidth="120" space="40" >
							<div class="fields">
								<input id="phone" name="phone" data-type="text" data-label="手机号" validate="{required:true}" />
								<input id="pwd" name="pwd" data-type="password" data-label="密&nbsp;&nbsp;码" validate="{required:true}" />
							</div>
						</div>

						<div style="text-align: center;margin-top: 30px;margin-bottom: 30px;">
							<input type="submit" name="btn_login" value="登录" onclick="f_login()" class="ctbt-btn">
							<input type="reset" name="btn_reset" value="重置" onclick="f_reset()" class="ctbt-btn">
						</div>
					</div>
				</div>

			</body>

			</html>
			<script type="text/javascript">
				var loginForm = $("#form1").ligerForm();
				function f_login() {
					var params = {}
					// params['userId']=loginForm.getData().phone;
					params['phone'] = loginForm.getData().phone;
					params['password'] = loginForm.getData().pwd;
					params['loginDevice']='1'
					params['netType']='1'
					$.ajax({
						type: "POST",
						url: "${WebUrl}/user/login.ctbt",
						data: params,
						// datatype: JSON,
						success: function (flag) { //成功后返回数据
							if(flag=="success"){
								alert("登陆成功")
								window.location.href = "${WebUrl}/user/toMain.ctbt"
							}else{
								alert("请验证账户密码")
							}
							
						},
						error: function (errorFlag) {
							alert("请检查网络连接")
						}
					});

				}

				function f_reset() {
					
				}

				$(function () {
					$("#maxDiv").height($(window).height());
				});
			</script>