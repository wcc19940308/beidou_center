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
					
				</style>
			</head>

			<body onkeydown="keyLogin();">
				<div id="fullBackground" style="position: absolute; top:0px; left:0px; width:100%; overflow: hidden;z-index: 1;">
					<img src="${WebUrl}/images/bg/login_bg_1.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_2.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_3.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_4.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_5.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_6.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_7.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_8.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_9.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_10.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_11.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_12.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_13.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_14.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_15.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_16.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_17.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_18.png" style="display: block; width: 100%;" />
					<img src="${WebUrl}/images/bg/login_bg_19.png" style="display: block; width: 100%;" />
				</div>
				
				<div id="maxDiv" style="position: absolute; top:0px; left:0px; width:100%; overflow: hidden; z-index: 2;">
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
							<input id="btn_login" type="submit" name="btn_login" value="登录" onclick="f_login()" class="ctbt-btn">
							<input type="reset" name="btn_reset" value="重置" onclick="f_reset()" class="ctbt-btn">
						</div>
					</div>
				</div>

			</body>

			</html>
			<script type="text/javascript">
				var loginForm = $("#form1").ligerForm();
				var regex = {
  mobile: /^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/
}

				function f_login() {
					var params = {}
					params['userName']=loginForm.getData().phone;
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
					$("#fullBackground").height($(window).height());
					$("#fullBackground2").height($(window).height());
				});
				function keyLogin() {
                        if (event.keyCode == 13) //回车键的键值为13
                            $("#btn_login").click(); //调用登录按钮的登录事件
                    }
			</script>