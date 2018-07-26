<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
		<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>
			<!DOCTYPE html>
			<html>

			<head>
				<ctbt:base/>
				<title>用户管理</title>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
				<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css">
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
				<script src="${WebUrl}/js/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>


				<script type="text/javascript">
					var gd, box3,roleBox;
					var storage = window.localStorage;

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
								isDrag: true,
								
							});
						}
						if (item.text == "修改") {
							var row = gd.getSelectedRow();
							console.log(row)
							if (!row) {
								alert('请选择要编辑的记录！');
								return;
							} else {
								userId = row.userId;
							}
							//获取记录userId 从数据库获取到相应数据显示到窗口上 
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
								isDrag: true,
								data:{
									selectData:gd.getSelectedRow()
								}
							});
						}
					}

					$(function () {
						gd = $("#maingrid").ligerGrid({
							height: '99.5%',
							columns: [{
									display: '用户ID',
									name: 'userId',
									align: 'left',
									width: 100,
									minWidth: 160,
									// hide:ture
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
								}

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
									click: deleteItem,
									icon: 'delete'
								}]
							}
						});
						
						$("#panel2").ligerPanel({
					        title: '用户消息查询',
					        width: "100%",
					        height : 110,
					        
					        onToggle : function(isCollapse){
					        	if(isCollapse){
					        		var wh = $(window).height();
					            	gd.setHeight(wh-40);
					        	}else{
					        		var wh = $(window).height();
					            	gd.setHeight(wh-120);
					        	}
					        	
					         },
					    });
						
						box3 = $("#validity").ligerComboBox({
							url: '${WebUrl}/dic/getDic.ctbt?dicId=10',
							width: 250,
							valueField: 'key',
							textField: 'value',
							value: '1',
							isTextBoxMode: false,
							keySupport: true
						});
						roleBox = $("#roleName").ligerComboBox({
							url: '${WebUrl}/dic/getDic.ctbt?dicId=14',
							width: 250,
							valueField: 'key',
							textField: 'value',
							value: '1',
							isTextBoxMode: false,
							keySupport: true
						});
						$("#pageloading").hide();
						//把role信息保存到本地缓存
						
					});

					function query() {
						$.ajax({
							type: "POST",
							url: "${WebUrl}/user/queryUserList.ctbt",
							data: {
								"phone": $("#phone").val(),
								"roleId": roleBox.getValue(),
								"validity": box3.getValue()
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

				

					function deleteItem() {
						var userId = ""
						var row = gd.getSelectedRow();
						if (!row) {
							alert('请选择要删除的记录！');
							return;
						} else {
							userId = row.userId;
							console.log("this is " + userId)
						}
						$.ajax({
							type: "POST",
							url: "${WebUrl}/user/saveUserDelete.ctbt",
							data: {
								userId: userId
							},
							dataType: "json",
							success: function (flag) {
								console.log(flag)
								query();
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

					}
				</script>


			</head>

			<body>
				<div id="panel2" class="easyui-panel" >
				<form name="queryRoleFrom" action="">
					<div style="height: 110;  margin: 5px; padding-top: 10px;">
						<table style="width: 98%; border-collapse:separate; border-spacing:0px 10px;">
							<tr>
								<td width="10%" align="right">手机：</td>
								<td width="23%">
									<input id="phone" name="phone" type="text" class="ctbt_qf_input" />
								</td>
								<td width="10%" align="right">角色名称：</td>
								<td width="23%">
									<input id="roleName" name="roleName" type="text"  />
								</td>
								<td width="10%" align="right">是否有效：</td>
								<td>
									<input name="validity" id="validity" />

								</td>
							</tr>
							<tr>
								<td colspan="6" align="right" height="30">
									<input value="查询" type="button" onclick="query()" class="ctbt-btn">
									<input value="重置" type="button" onclick="reset()" class="ctbt-btn">
								</td>
							</tr>
						</table>

					</div>
					</form>
					</div>
					
						<div class="l-loading" style="display: block" id="pageloading"></div>
						<a class="l-button" style="width: 120px; float: left; margin-left: 10px; display: none;" onclick="deleteRow()">删除选择的行</a>

						<div class="l-clear"></div>

						<div id="maingrid"></div>

						<div style="display: none;"></div>
			</body>

			</html>