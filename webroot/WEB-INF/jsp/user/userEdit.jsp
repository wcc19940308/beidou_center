<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
		<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>
			<!DOCTYPE html>
			<html>

			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
				<title></title>
				<link href="${WebUrl}/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
				<script src="${WebUrl}/js/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
				

			</head>

			<body>
				<div id="login">
					<form>
						<table style="border-collapse:separate; border-spacing:30px 10px;width:98%;" align="center">
							<tr>
								<td width="20%" align="right">用户ID：</td>
								<td width="50%">
									<input id="userId" name="userId" type="text" style="width:325px;" disabled="true" value="${BdUser.userId}" />
								</td>
							</tr>
							<tr>
								<td width="20%" align="right">手机号码：</td>
								<td width="50%">
									<input id="phone" name="phone" type="text" style="width:325px;" disabled="true" value="${BdUser.phone}" />
								</td>
							</tr>
							<tr>
								<td width="20%" align="right">用户密码：</td>
								<td width="50%">
									<input id="password" name="password" type="text" style="width:325px;" value="${BdUser.password}" />
								</td>
							</tr>
							<tr>
								<td width="20%" align="right">角色名称：</td>
								<td width="50%">
									<input type="text" name="roleName" id="roleName" >
								</td>
							</tr>
							<tr>
								<td width="20%" align="right">是否有效：</td>
								<td width="50%">
									<input type="text"  id="validity" name="validity" ></input>
								</td>
							</tr>
							<tr>
								<td width="20%" align="right">用户名称：</td>
								<td width="50%">
									<input id="userName" name="userName" type="text" style="width:325px;" value="${BdUserDetail.userName}" />
								</td>
							</tr>
							<tr>
								<td width="20%" align="right">QQ：</td>
								<td width="50%">
									<input id="qq" name="qq" type="text" style="width:325px;" value="${BdUserDetail.qq}" />
								</td>
							</tr>
							<tr>
								<td width="20%" align="right">微信：</td>
								<td width="50%">
									<input id="weixin" name="weixin" type="text" style="width:325px;" value="${BdUserDetail.weixin}" />
								</td>
							</tr>
							<tr>
								<td width="20%" align="right">联系地址：</td>
								<td width="50%">
									<input id="addr" name="addr" type="text" style="width:325px;" value="${BdUserDetail.addr}" />
								</td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: center; padding-top: 30px; padding-bottom: 30px;">
									<button type="button" onclick="EditUser()">确定</button>
									<button type="button" onclick="closeDialog()">取消</button>
								</td>
							</tr>
							
						</table>
					</form>
				</div>
			</body>

			</html>
			<script>
				var validityBox, roleNameBox;
				var dialog = frameElement.dialog;
				$(function () {
					validityBox = $("#validity").ligerComboBox({
						url: '${WebUrl}/dic/getDic.ctbt?dicId=10',
						width: 327,
						valueField: 'key',
						textField: 'value',
						isTextBoxMode: false,
						keySupport: true,					
					});
					roleNameBox = $("#roleName").ligerComboBox({
						url: '${WebUrl}/dic/getDic.ctbt?dicId=14',
						width: 327,
						valueField: 'key',
						textField: 'value',
						isTextBoxMode: false,
						keySupport: true,
					});
					validityBox.set({
						value: "${BdUser.validity}"
					});
					roleNameBox.set({
						value: "${BdUser.roleId}"
					});
				})

				function EditUser() {
					//通过用户ID更新用户数据
					var parms = {};
					parms["userId"] = $("#userId").val()
					parms['password'] = $("#password").val()
					parms['phone'] = $("#phone").val()
					parms['roleId'] = roleNameBox.getValue()
					parms['validity'] = validityBox.getValue()
					parms['userName'] = $("#userName").val()
					parms['qq'] = $("#qq").val()
					parms['weixin'] = $("#weixin").val()
					parms['addr'] = $("#addr").val()
					console.log(parms)
					if (parms["userId"] == null) {
						alert("没有Id 请重新选择！")
						return
					}
					$.ajax({
						type: "POST",
						url: "${WebUrl}/user/saveUserEdit.ctbt",
						data: {
							userId: parms['userId'],
							password: parms['password'],
							phone: parms['phone'],
							roleId: parms['roleId'],
							validity: parms['validity'],
							userName: parms['userName'],
							qq: parms['qq'],
							weixin: parms['weixin'],
							addr: parms['addr']
						},
						success: function (flag) {
							console.log(flag);
							if (flag == 1) {
								alert("修改成功！");
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
					parent.closeUserEditWin();
				}
			</script>