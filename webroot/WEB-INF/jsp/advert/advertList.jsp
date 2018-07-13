<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt"%>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn"%>
<!DOCTYPE html>
<html>
<head>
<title>ctbt同博</title>
<ctbt:base />
<meta name="keywords"
	content="免费控件,免费UI控件,免费开源UI,免费开源UI控件,免费开源UI框架,可编辑表格,datagrid,editgrid">
<link href="${WebUrl}/js/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css">
<link href="${WebUrl}/js/ligerUI/skins/ligerui-icons.css"
	rel="stylesheet" type="text/css">
<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css"
	rel="stylesheet" type="text/css">
<link href="${WebUrl}/css/validate.css" rel="stylesheet" type="text/css" />

<script src="${WebUrl}/js/jquery/jquery-1.9.0.min.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/ligerui.all.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerGrid.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerToolBar.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerForm.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerComboBox.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/jquery-validation/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/jquery-validation/jquery.metadata.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/jquery-validation/messages_cn.js"
	type="text/javascript"></script>

<script src="${WebUrl}/js/jquery-validation/tooltip.js"
	type="text/javascript"></script>
</head>
<body>
	<form action="" name="queryRoleFrom" id="queryRoleFrom">
		<div
			style="height: 50px; border: solid 1px #cccccc; margin: 5px; padding-top: 10px;">
			<table style="width: 98%;">
				<tr>
					<td width="10%" align="right">广告标题：</td>
					<td width="23%"><input id="advTitleQ" name="advTitleQ" type="text"
						style="width: 95%;" /></td>
					<td width="10%" align="right">广告内容：</td>
					<td width="23%"><input id="advTextQ" name="advTextQ"
						type="text" style="width: 95%;" /></td>
					<td width="10%" align="right">是否有效：</td>
					<td width="23%"><input id="validityQ" name="validityQ" /></td>
				</tr>
				<tr>
				<td colspan="6" align="right" height="30"><input value="查询" type="button" id="query" /> 
						<input id="reset" value="重置" type="button" /></td>
				</tr>
			</table>

		</div>

	</form>

	<div class="l-loading" style="display: block" id="pageloading"></div>
	<a class="l-button"
		style="width: 120px; float: left; margin-left: 10px; display: none;"
		onclick="deleteRow()">删除选择的行</a>

	<div class="l-clear"></div>

	<div id="maingrid"></div>

	<div style="display: none;"></div>
</body>
<script type="text/javascript">
	//得到所有的数据
	$(function() {
		var a;
		a = window['g'] = $("#maingrid").ligerGrid({
			height : '100%',
			columns : [ {
				display : '标题',	name : 'advTitle',minWidth : 100,align : 'left',width : 100,minWidth : 160
			}, {
				display : '内容类型',	name : 'advType',minWidth : 100,
				render : function(result) {
					if(result.advType==1){
						return "文字";
					}else if(result.advType==2){ 
						return "图片";
					}else{ 
						return "flash";
					}}
			}, {display : '内容',	name : 'advText',minWidth : 100	}, 
			{display : '开始时间',name : 'advStart',minWidth : 150,		
				render : function(result) {	return ctbt.Date2String(result.advStart,'datetime');
				}},
			{display : '结束时间',	name : 'advEnd',minWidth : 150,
				render : function(result) {
					return ctbt.Date2String(result.advEnd,'datetime');
				}}, {display : '轮播持续时间',name : 'advTime',minWidth : 150,
				render : function(result) {	return result.advTime+"s";
				}
			}, {display : '优先级',name : 'orderNo',minWidth : 150
			}, {display : '是否有效',name : 'validity',minWidth : 150,
				render : function(result) {
					if(result.validity==1){
						return "是";	
						}
					else{
						return "否";
					}}
			}, ],
			pageSize : 30,rownumbers : true,
			toolbar : { items : [ 
				{text : '增加',click : f_open,icon : 'add'}, {line : true	}, 
				{text : '修改',click : f_open,icon : 'modify' }, {line : true }, 
				{text : '停用',click : f_open1,icon : 'delete'	} ]	}});
		$("#pageloading").hide();
		fresh();
		var validityV = $("#validityQ").ligerComboBox({
			url : '${WebUrl}/dic/getDic.ctbt?dicId=10',
			valueField : 'key',
			textField : 'value',
		});
		var queryByitem=$("#query").click(function() {
			var advtitle = $("#advTitleQ").val();
			var advtext = $("#advTextQ").val();
			var valid = validityV.getValue();
			$.ajax({
				url : '${WebUrl}/advert/queryAdvertList.ctbt',
				type : "POST",
				data : {
					advTitle : advtitle,
					advText : advtext,
					validity : valid
				},
				dataType : 'json',
				success : function(result) {
					var jsonObj = {};
					jsonObj.Rows = result;
					window['g'].set({
						data : jsonObj
					});
				},
				error : function() {
					alert("服务器异常，请重试！");
				}
			});

		});

		$("#reset").click(function() {
			validityV.setText("");
			ctbt.FormUtil.Clean(document.queryRoleFrom);
		});
	});

	function fresh() {
		$.ajax({
			url : '${WebUrl}/advert/selectAllAdvert.ctbt',
			type : "GET",
			dataType : 'json',
			success : function(result) {
				var jsonObj = {};
				jsonObj.Rows = result;
				window['g'].set({
					data : jsonObj
				});
				},error : function() {
				alert("服务器异常，请重试！");
			}
		});
	}
	function f_open1() {
		var row = window['g'].getSelectedRow();
		if (!row) {
			alert('请选择要停用的广告！');
			return;
		} else {
			$.ligerDialog.confirm('确定停用吗?', function(confirm) {
				if (confirm){
					var advId = row.advId;
				$.ajax({
					url : '${WebUrl}/advert/updateByPrimaryKeyValidity.ctbt',
					type : "POST",
					data : {
						advId : advId
					},
					dataType : 'json',
					success : function(result) {
						if (result == 1) {
							$.ligerDialog.alert('停用成功');
							document.getElementById("query").click();
						}
					},error : function() {
						alert("停用失败，请重试！");
					}
				});
				}				
			});
		}
	}
	var win1;
	function f_open(item) {
		var toUrl;
		if (item.text == "修改") {
			var row = window['g'].getSelectedRow();
			if (!row) {
				alert('请选择要修改的记录！');
				return;
			} else {
				var advId = row.advId;
				toUrl = '${WebUrl}/advert/toAdvertEdit.ctbt?advId='+ advId;
			}
		} else {
			toUrl = '${WebUrl}/advert/toAdvertEdit.ctbt';
		}
		win1 = $.ligerDialog.open({
			height : 540,
			url : toUrl,
			width : 669,
			showMax : true,
			showToggle : true,
			showMin : true,
			isResize : true,
			slide : false
		});
	}

	function closeRoleEditWin() {
		if (win1 != null) {
			win1.close();
		}	}
</script>
</html>