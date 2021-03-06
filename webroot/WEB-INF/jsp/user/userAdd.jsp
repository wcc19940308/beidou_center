<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
		<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>
			<!DOCTYPE html>
			<html>

			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
				<title></title>
				<ctbt:base/>
				<link href="${WebUrl}/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
				<script src="${WebUrl}/js/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
			

			</head>

			<body>
				<div id="login">
					<form id="formAdd">
						<table style="border-collapse:separate; border-spacing:30px 10px;width:98%;" align="center">
							<tr>
								<td width="20%" align="right">用户ID：</td>
								<td width="50%">
									<input id="userId" name="userId" type="text" style="width:325px;" disabled="true" value="${BdUser.roleId}" />
								</td>
							</tr>
							<tr>
								<td width="20%" align="right">手机号码：</td>
								<td width="50%">
									<input id="phone" name="phone" type="text" style="width:325px;" value="${BdUser.phone}" />
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
									<input type="text" name="validity" id="validity"  >
									
								</td>
							</tr>
							<!-- <tr>
						<td style="text-align: right;">个人名称或企业名称：</td>
						<td>
							<input type="text" width="95%" />
						</td>
					</tr> -->
							<!-- <tr>
						<td style="text-align: right;">昵称：</td>
						<td>
							<input type="text" width="95%" />
						</td>
					</tr> -->
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
				var box1,box2,box3;
				$(function () {
						box1 = $("#validity").ligerComboBox({
							url: '${WebUrl}/dic/getDic.ctbt?dicId=10',
							width:327,
							valueField:'key',
							textField:'value',
							valueFieldID:'test1',
							isTextBoxMode:false,
							keySupport:true
						});
						box2 = $("#roleName").ligerComboBox({
							url: '${WebUrl}/dic/getDic.ctbt?dicId=14',
							width:327,
							valueField:'key',
							textField:'value',
							valueFieldID:'test2',
							isTextBoxMode:false,
							keySupport:true
						});
						
				})
				function addUser() {
					var params = $(document.roleEditForm).serialize();
					$.ajax({
						type: "POST",
						url: "${WebUrl}/user/saveUserAdd.ctbt",
						data: {
							//注意修改 这里是假数据 UserId不需要添加
							password: $("#password").val(),
							phone: $("#phone").val(),
							roleId: box2.getValue(),
							validity: box1.getValue(),
							userName: $("#userName").val(),
							qq: $("#qq").val(),
							weixin: $("#weixin").val(),
							addr: $("#addr").val(),
						},
						success: function (flag) {
							console.log(flag);
							if (flag == 'success') {
								alert("添加成功！");
							}else {
								alert("添加错误")
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