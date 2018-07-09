<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
		<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>
			<!DOCTYPE html>
			<html>

			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
				<ctbt:base/>
				<title>表格控件 - toolbar</title>
				<meta name="keywords" content="免费控件,免费UI控件,免费开源UI,免费开源UI控件,免费开源UI框架,可编辑表格,datagrid,editgrid">
				<link href="${WebUrl}/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css">
				<link href="${WebUrl}/js/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css">
				<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css">
				<script src="${WebUrl}/js/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/core/base.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>

				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>

				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>

				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>


				<script type="text/javascript">
					var gd;
					var storage=window.localStorage;
					function itemclick(item) {
						console.log(item.text);
					}
					var userEditWin;

					function add_win_open(item) {
						var userId = "";
						console.log(item.text);
						if (item.text == "增加") {
							userEditWin = $.ligerDialog.open({
								height: 400,
								url: '${WebUrl}/user/toUserAdd.ctbt',
								width: 600,
								showMax: true,
								showToggle: true,
								showMin: true,
								isResize: true,
								slide: false,
								isDrag: true
							});
						}
						if (item.text == "修改") {
							var row = gd.getSelectedRow();
							if (!row) {
								alert('请选择要编辑的记录！');
								return;
							} else {
								userId = row.userId;
							}
							//获取记录userId 从数据库获取到相应数据,显示到窗口上 
							userEditWin = $.ligerDialog.open({
								height: 400,
								url: '${WebUrl}/user/toUserEdit.ctbt',
								urlParms: {
									userId: userId
								},
								width: 600,
								showMax: true,
								showToggle: true,
								showMin: true,
								isResize: true,
								slide: false,
								isDrag: true
							});
						}
					}

					$(function () {
						gd = $("#maingrid").ligerGrid({
							height: '100%',
							columns: [{
									display: '用户ID',
									name: 'userId',
									align: 'left',
									width: 100,
									minWidth: 160
								}, {
									display: '手机',
									name: 'phone',
									minWidth: 240
								}, {
									display: '角色名称',
									name: 'roleName',
									minWidth: 240
								}, {
									display: '是否有效：',
									name: 'validity',
									minWidth: 240
								},

							],
							pageSize: 30,
							rownumbers: true,
							toolbar: {
								items: [{
									text: '增加',
									click: add_win_open,
									icon: 'add'
								}, {
									line: true
								}, {
									text: '修改',
									click: add_win_open,
									icon: 'modify'
								}, {
									line: true
								}, {
									text: '删除',
									click: itemclick,
									icon: 'delete'
								}]
							}
						});

						$("#pageloading").hide();
						//把role信息保存到本地缓存

					});

					function deleteRow() {
						g.deleteSelectedRow();
					}

					function query() {
						//gd.url='${WebUrl}/role/queryRoleList.ctbt?role.id=22';
						//gd.loadServerData('${WebUrl}/role/queryRoleList.ctbt?role.id=22', clause);
						var phone = $("#phone").val();
						var roleName = $("#roleName").val();
						var validity = $("#validity").val();
						console.log(phone)
						console.log(roleName)
						console.log(validity)
						$.ajax({
							type: "POST",
							url: "${WebUrl}/user/queryUserList.ctbt",
							data: {
								"phone": phone,
								"roleName": roleName,
								"validity": validity
							},
							dataType: "json",
							success: function (listdata) { //成功后返回数据			
								var text = JSON.stringify(listdata);;
								console.log(text)
								var jsonObj = {};
								jsonObj.Rows = listdata;
								gd.set({
									data: jsonObj
								});
							}
						});
					}

					function add() {
						//gd.url='${WebUrl}/role/queryRoleList.ctbt?role.id=22';
						//gd.loadServerData('${WebUrl}/role/queryRoleList.ctbt?role.id=22', clause);
						$.ajax({
							type: "POST",
							url: "${WebUrl}/user/queryUserList.ctbt",
							//data: params,
							dataType: "json",
							success: function (listdata) { //成功后返回数据
								alert(listdata);
								var text = JSON.stringify(listdata);;
								alert(text);

								var jsonObj = {};
								jsonObj.Rows = listdata;
								gd['g'].set({
									data: jsonObj
								});
							}
						});
					}

					function modify() {
						$.ajax({
							type: "POST",
							url: "${WebUrl}/user/queryUserList.ctbt",
							//data: params,
							dataType: "json",
							success: function (listdata) {
								// alert(listdata);
								var text = JSON.stringify(listdata);;
								// alert(text);

								var jsonObj = {};
								jsonObj.Rows = listdata;
								gd.set({
									data: jsonObj
								});
							}
						});
					}

					function reset() {
						ctbt.FormUtil.Clean(document.queryRoleFrom);
					}

					function closeUserEditWin() {
						if (userEditWin != null) {
							userEditWin.close();
						}
						// query();
					}

					function getRoleList() {
						$.ajax({
							type: "POST",
							url: "${WebUrl}/dic/queryProvinceList.ctbt?countryId=100",
							//data: params,
							dataType: "json",
							success: function (listdata) {
								console.log(listdata);
								storage['a']=1;
							}
						});
					}
				</script>


			</head>

			<body>

				<form name="queryRoleFrom" action="">
					<div style="height: 50px; border: solid 1px #cccccc; margin: 5px; padding-top: 10px;">
						<table style="width: 98%;">
							<tr>
								<td width="10%" align="right">手机：</td>
								<td width="23%">
									<input id="phone" name="phone" type="text" style="width: 95%;" />
								</td>
								<td width="10%" align="right">角色名称：</td>
								<td width="23%">
									<input id="roleName" name="roleName" type="text" style="width: 95%;" />
								</td>
								<td width="10%" align="right">是否有效：</td>
								<td>
									<!-- <select name="validity" id="validity" type="select" style="width: 95%;">
										<option value="1">是</option>
										<option value="0">否</option>
									</select> -->
									<ctbt:select name="validity" id="validity" 
									data="${ctbtfn:getDic(10)}" defaultValue='${validity}' emptyOption="" styleClass="ctbt_qf_select"/>
								</td>
							</tr>
							<tr>
								<td colspan="6" align="right" height="30">
									<input value="查询" type="button" onclick="query()">
									<input value="重置" type="button" onclick="reset()">
								</td>
							</tr>
						</table>

					</div>
					<form name="queryRoleFrom" action="">
						<div class="l-loading" style="display: block" id="pageloading"></div>
						<a class="l-button" style="width: 120px; float: left; margin-left: 10px; display: none;" onclick="deleteRow()">删除选择的行</a>


						<div class="l-clear"></div>

						<div id="maingrid"></div>

						<div style="display: none;"></div>
			</body>

			</html>