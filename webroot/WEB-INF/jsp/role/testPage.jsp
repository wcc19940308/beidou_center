<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<ctbt:base/>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css">

</head>
<body>

<form id="form1" name="form1" action="">
<input type="hidden" id="roleId" name="roleId" value="${BdRole.roleId}"/>
<table width="500">
	<tr>
		<td width="20%" align="right">角色名称:</td>
		<td width="70%"><input id="roleName" name="roleName" type="text" style="width: 95%;" value="${BdRole.roleName}"/></td>
	</tr>
	<tr>
		<td align="right">角色描述:</td>
		<td><input id="roleDesc" name="roleDesc" type="text" style="width: 95%;" value="${BdRole.roleDesc}"/></td>
	</tr>
	<tr>
		<td align="right">是否有效:</td>
		<td>
			<select name="validity" id="validity" type="select" style="width: 95%;">
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>下拉框测试</td>
		<td>
		<input type="text" id="test1" name="regName" regId="1123" regName="regName名称"/>
       	<input type="hidden" id="test2" />
		</td>
	</tr>
	<tr>
		<td>ligerComboBox加载字典</td>
		<td>
		<input type="text" id="test21" name="countryName"/>
       	<input type="hidden" id="test22" />
		</td>
	</tr>
	<tr>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input value="表单数据" type="button" onclick="getFormData()" class="ctbt-btn">
			<input value="加载行政区划树" type="button" onclick="loadTree()" class="ctbt-btn">
			<input value="按钮1" type="button" onclick="action1()" class="ctbt-btn">
			<input value="第三级窗口" type="button" onclick="openSjtWin()" class="ctbt-btn">
			<input value="关闭" type="button" onclick="closeDialog()" class="ctbt-btn">
		</td>
	</tr>
</table>
<br/>
</form>

<div style="width: 500px; height: 400px; margin: 10px; float: left; border: 1px solid #ccc; overflow: auto;">
	<ul id="SysRegionTree"></ul>
</div>

<div style="display: none"></div>

</body>
</html>

<script type="text/javascript">
var tree;
var box;
$(function(){
	tree = $("#SysRegionTree").ligerTree(
	{
		nodeWidth:500,
		isExpand:false
	});
	
	box = $("#test1").ligerComboBox({
		url:'${WebUrl}/dic/queryProvinceList.ctbt?countryId=100',
		delayLoad:true,
        width : 200,
        valueField:'key',
        textField:'value',
        valueFieldID: 'test2',
        isTextBoxMode:false,
        keySupport: true,
        autocomplete:true,
        onButtonClick: function () {
        	//点击下拉框图标时，根据需要加载列表数据
        	if(this.data && this.data.length > 0){
        		//alert("有数据不用加载");
        	}else{
        		//alert("加载数据");
            	this.reload();
        	}
        }
    }).setText("城市名称显示文本"); 
	
	$("#test2").val("17009");
	
	
	var box2 = $("#test21").ligerComboBox({
		url:'${WebUrl}/dic/getDic.ctbt?dicId=12',
        width : 200,
        valueField:'key',
        textField:'value',
        valueFieldID: 'test22',
        isTextBoxMode:false,
        keySupport: true
    });
});

function action1(){
	//box.setText("输入的中文");
}

function getFormData(){
	var params = $(document.form1).serialize();
	alert(params);
};

function loadTree(){
	$.ajax({
	   type: "POST",
	   url: "${WebUrl}/role/loadSysRegionTree.ctbt",
	   //data: params,
	   dataType:"json",
	   success: function(data){
			//alert(data);
		    //var text = JSON.stringify(data);;
		    //alert(text);
		    
		   tree.setData(data);
	   },
	   error: function(XMLHttpRequest, textStatus){
		    alert("error:"+XMLHttpRequest);
		    alert(textStatus);
	   }
	});
}

function closeDialog(){
	if(parent && parent.testPageWin){
		parent.testPageWin.close();
	}
}

function openSjtWin(){
	parent.openSjtWin();
}
</script>