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
				<script src="${WebUrl}/js/queryPage.js" type="text/javascript"></script>

				<script type="text/javascript">
					var gd, box3, roleBox, toptoolbar;
					var storage = window.localStorage;

					function itemclick(item) {
						console.log(item.text);
					}
					var userEditWin;

					$(function () {
						var pw = $(document).width();
						var dw = pw * 0.98 * 0.23 * 0.95 + 3;
						
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
							usePager:false,
							rownumbers: true,
						});

						$("#panel2").ligerPanel({
							title: '用户消息查询',
							width: "100%",
							height: 110,

							onToggle: function (isCollapse) {
								if (isCollapse) {
									var wh = $(window).height();
									gd.setHeight(wh - 40);
								} else {
									var wh = $(window).height();
									gd.setHeight(wh - 120);
								}

							},
						});

						box3 = $("#validity").ligerComboBox({
							url: '${WebUrl}/dic/getDic.ctbt?dicId=10',
							width: dw,
							valueField: 'key',
							textField: 'value',
							value: '1',
							isTextBoxMode: false,
							keySupport: true
						});
						roleBox = $("#roleName").ligerComboBox({
							url: '${WebUrl}/dic/getDic.ctbt?dicId=14',
							width: dw,
							valueField: 'key',
							textField: 'value',
							isTextBoxMode: false,
							keySupport: true
						});
						$("#pageloading").hide();
						//把role信息保存到本地缓存
						<ctbt:permButton ids="133,134,135"/>
							// toptoolbar = $("#toptoolbar").ligerToolBar({
							// 	items: [{
							// 			text: '增加',
							// 			click: add_win_open,
							// 			icon: 'add'
							// 		},
							// 		{
							// 			line: true
							// 		},
							// 		{
							// 			text: '修改',
							// 			click: add_win_open,
							// 			icon: 'modify'
							// 		},
							// 		{
							// 			line: true
							// 		},
							// 		{
							// 			text: '删除',
							// 			click: deleteItem,
							// 			icon : 'delete'
							// 		}
							// 	]
							// });
					});

					function query() {
						// console.log( $("#phone").val())
						// console.log( roleBox.getValue())
						// console.log( box3.getValue())
						if(box3.getValue()==""){
							alert("请选择是否有效,默认为是")
						}
						
						$.ajax({
							type: "POST",
							url: "${WebUrl}/user/queryUserList.ctbt",
							data: {
								"phone": $("#phone").val(),
								"roleId": roleBox.getValue(),
								"validity": box3.getValue(),
								"page":document.getElementById('pageNum').value,
								"pageSize":document.getElementById('pageSize').value
							},
							dataType: "json",
							success: function (listdata) { //成功后返回数据			
								var text = JSON.stringify(listdata);;
								// console.log(text)
								var jsonObj = {};
								jsonObj.Rows = listdata['Row'];
								console.log(listdata['Row'])
								gd.set({
									data: jsonObj
								});
						document.getElementById("pageNum").value=listdata["currentPage"];
						$("#recordNum").text(listdata["recordNum"]);
						$("#currentPage").text(listdata["currentPage"]);
						$("#sumPageNum").text(listdata["sumPageNum"]);	
							}
						});
					}

			function queryBypage(pageNum,pageSize) {
				if(box3.getValue()==""){
							alert("请选择是否有效,默认为是")
						}
				else{
				$.ajax({
					url : '${WebUrl}/user/queryUserList.ctbt',
					type : "POST",
					data: {
								"phone": $("#phone").val(),
								"roleId": roleBox.getValue(),
								"validity": box3.getValue(),
								// "page":document.getElementById('pageNum').value,
								// "pageSize":document.getElementById('pageSize').value
								"page":pageNum,
								"pageSize":pageSize
							},
					dataType:'json',
					success : function(listdata) {
								// console.log(text)
								var jsonObj = {};
								jsonObj.Rows = listdata['Row'];
								console.log(listdata['Row'])
								gd.set({
									data: jsonObj
								});
						document.getElementById("pageNum").value=listdata["currentPage"];
						$("#recordNum").text(listdata["recordNum"]);
						$("#currentPage").text(listdata["currentPage"]);
						$("#sumPageNum").text(listdata["sumPageNum"]);	
					
					},
					error:function() { 
						$.ligerDialog.alert("服务器异常，请重试！");
				}});
				}};


					function add_win_open(item) {
						var userId = "";
						console.log(item);
						if (item.text == "添加") {
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
								data: {
									selectData: gd.getSelectedRow()
								}
							});
						}
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
				<div id="panel2" class="easyui-panel">
					<form name="queryUserFrom" action="">
						<div style="height: 110;  margin: 5px; padding-top: 10px;">
							<table style="width: 98%; border-collapse:separate; border-spacing:0px 10px;">
								<tr>
									<td width="10%" align="right">手机：</td>
									<td width="23%">
										<input id="phone" name="phone" type="text" class="ctbt_qf_input" />
									</td>
									<td width="10%" align="right">角色名称：</td>
									<td width="23%">
										<input id="roleName" name="roleName" type="text" />
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
				<!-- <input value="增加" type="button" onclick="add_win_open(this.value)">
						<input value="修改" type="button" onclick="add_win_open(this.value)">
						<input value="删除" type="button" onclick="deleteItem()"> -->
				<div id="toptoolbar" style="margin-bottom:2px;padding-bottom:10px"></div>
				<div id="maingrid"></div>
				<ctbt:pagefootTag  pageNum="1" recordNum="0" currentPage="1"/>
				<div style="display: none;"></div>
			</body>

			</html>