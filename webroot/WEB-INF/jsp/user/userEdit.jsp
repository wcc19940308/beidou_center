<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>

	<!DOCTYPE html>
	<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<link href="${WebUrl}/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<script src="${WebUrl}/js/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
		<script src="${WebUrl}/js/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
		<style>
			#login {
				width: 400px;
				height: 280px;
				position: absolute;
				left: 50%;
				top: 50%;
				margin-left: -190px;
				margin-top: -140px;
				border: 1px;

				align: center;


			}

			#d {
				width: 200px;
				height: 160px;
				position: relative;
				left: 50%;
				top: 50%;
				margin-left: -150px;
				margin-top: -80px;
			}


			.a {
				text-align: right;
			}


			.c {
				text-align: center;
			}

			td {
				height: 30px;
			}
		</style>

	</head>

	<body>
		<div id="login">
			<form>
				<table style="width:380px;">
					<tr>
						<td style="text-align: right; width: 150px;">用户ID：</td>
						<td style="width: 230px;">
							<input id= "userId" name="userId" type="text" width="95%" disabled="true" value="${BdUser.roleId}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">手机号码：</td>
						<td>
								<input id= "phone" name="phone" type="text" width="95%" disabled="true" value="${BdUser.phone}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">用户密码：</td>
						<td>
							<input id= "password" name="password" type="text" width="95%" disabled="true" value="${BdUser.password}"/>
						</td>
					</tr>
					<!-- <tr>
						<td style="text-align: right;">角色名称：</td>
						<td>
								<select name="roleName" id="roleName">
									<option value="">abc</option>
								</select>
						</td>
					</tr> -->
					<tr>
						<td style="text-align: right;">是否有效：</td>
						<td>
							<select type="text" width="95%">
									<option value ="1">有效</option>
									<option value ="0">无效</option>
							</select>
						</td>
					</tr>
					<!-- <tr>
						<td style="text-align: right;">个人名称或企业名称：</td>
						<td>
								<input id= "userId" name="userId" type="text" width="95%" disabled="true" value="${BdUser.roleId}"/>
						</td>
					</tr> -->
					<!-- <tr>
						<td style="text-align: right;">昵称：</td>
						<td>
								<input id= "userId" name="userId" type="text" width="95%" disabled="true" value="${BdUser.roleId}"/>
						</td>
					</tr> -->
					<tr>
						<td style="text-align: right;">用户名称：</td>
						<td>
								<input id= "userName" name="userName" type="text" width="95%" disabled="true" value="${BdUserDetail.userName}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">QQ：</td>
						<td>
								<input id= "qq" name="qq" type="text" width="95%" disabled="true" value="${BdUserDetail.qq}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">微信：</td>
						<td>
								<input id= "weixin" name="weixin" type="text" width="95%" disabled="true" value="${BdUserDetail.weixin}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">联系地址：</td>
						<td>
								<input id= "addr" name="addr" type="text" width="95%" disabled="true" value="${BdUserDetail.addr}"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center; padding-top: 30px; padding-bottom: 30px;">
							<button type="button" onclick="addUser()">确定</button>
							<button type="button" onclick="closeDialog()">取消</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>

	</html>
	<script>
		function EditUser() {
			//通过用户ID更新用户数据
			var params = $(document.roleEditForm).serialize();
			console.log(params)
			$.ajax({
				type: "POST",
				url: "${WebUrl}/user/saveUserAdd.ctbt",
				data: {
					userId: 1284,
					password: 1283,
					phone: 1283,
					roleId: 1,
					validity: 0,
					userName:'lettino',
					qq:'547',
					weixin:'547',
					addr:"Hangzhou",
				},
				success: function (flag) {
					console.log(flag);
					if (flag == 1) {
						alert("添加成功！");
					} else if (flag == 1) {
						alert("添加错误");
					} else {
						alert("请检查网络")
					}
				},
				error: function (XMLHttpRequest, textStatus) {
					alert("error:" + XMLHttpRequest);
					alert(textStatus);
				}
			});
		}

		function closeDialog() {
			parent.closeRoleEditWin();
		}
	</script>