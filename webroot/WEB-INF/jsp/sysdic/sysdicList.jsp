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
	<script type="text/javascript">
	//得到所有的数据
	$(function() {
	 window['g'] = $("#maingrid").ligerGrid({
			height : '100%',columns : [ 
			{display : '字典编号',	name : 'dicId',minWidth : 209,align : 'left'}, 
			{display : '字典名称',	name : 'dicName',minWidth : 250,},
			{display : '字典取值表名',	name : 'tableName',minWidth : 250,},
			{display : '字典key值',	name : 'keyColumn',minWidth : 200	}, 
			{display : '字典value值',name : 'valueColumn',minWidth : 250,},
			{display : '字典取值sql',	name : 'dicSql',minWidth : 200,},
			{display : '有效性',	name : 'validity',minWidth : 200,render: function (item)
              {
         		if (parseInt(item.validity) == 1) return '是';
                  return '否';
              } },
			  ],
			pageSize : 30,rownumbers : true,
			toolbar : { items : [
				{text : '维护',click : f_open,icon : 'add'}, {line : true	}, ]	}});
		$("#pageloading").hide();
		fresh();	
		var queryByitem=$("#query").click(function(){
			var item=$("#queryRoleFrom").serialize();
			$.ajax({
				url : '${WebUrl}/sysdic/selectAllByItem.ctbt',
				type : "POST",
				data : item,
				dataType : 'json',
				success : function(result) {
					var jsonObj = {};
					jsonObj.Rows = result;
					window['g'].set({
						data : jsonObj
					});
				},
				error : function() {
					$.ligerDialog.alert('服务器异常，请重试！');
				}
			});
		});
		$("#reset").click(function() {
			ctbt.FormUtil.Clean(document.queryRoleFrom);
		});
	});

	function fresh() {
		$.ajax({
			url : '${WebUrl}/sysdic/selectAll.ctbt',
			type : "POST",
			success : function(result) {
				var jsonObj = {};
				jsonObj.Rows = result;
				window['g'].set({
					data : jsonObj
				});
				},error : function() {
					$.ligerDialog.alert('服务器异常，请重试！');
			}
		});
	}

	var win1;
	function f_open(item) {
		var toUrl;
		var states;
			var row = window['g'].getSelectedRow();
			if (!row) {
				$.ligerDialog.alert('请选择要修改的记录！');
				return;
			} else {
				var dicId = row.dicId;
				toUrl = '${WebUrl}/sysDicItem/tosysDicItemE.ctbt?dicId='+ dicId;
			}
		if(row.tableName=='sys_dic_item'){
		win1 = $.ligerDialog.open({
			height : 640,
			url : toUrl,
			width : 569,
			showMax : true,
			showToggle : true,
			showMin : true,
			isResize : true,
			slide : false
		});
		}
		else{
			$.ligerDialog.alert('本条数据暂不提供维护服务');
		}
		}
	
	function closeRoleEditWin() {
		if (win1 != null) {
			win1.close();
		}	
}
</script>
</head>
<body>
	<form action="" name="queryRoleFrom" id="queryRoleFrom">
		<div
			style="height: 50px; border: solid 1px #cccccc; margin: 5px; padding-top: 10px;">
			<table style="width: 98%;">
				<tr>
					<td width="10%" align="right">字典编号：</td>
					<td width="23%"><input id="dicId" name="dicId" type="text"
						style="width: 95%;" /></td>
					<td width="10%" align="right">字典名称：</td>
					<td width="23%"><input id="dicName" name="dicName"
						type="text" style="width: 95%;" /></td>
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

</html>