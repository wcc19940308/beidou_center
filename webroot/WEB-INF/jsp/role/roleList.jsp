<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<ctbt:base/>

<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css">
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>

<script type="text/javascript">
var gd,toolbar;
var listCountry;
var listPro;
var listCity;
$(function ()
{
    gd = $("#maingrid").ligerGrid({
        height:'99.5%',
        columns: [
        { display: '角色ID', name: 'roleId', align: 'left', width: 100, minWidth: 60 },
        { display: '角色名称', name: 'roleName', minWidth: 120 },
        { display: '是否有效', name: 'validity', minWidth: 60 },
        { display: '角色描述', name: 'roleDesc', minWidth: 140 }
        ],
        pageSize:20 ,rownumbers:true,
        // toolbar: { items: [
        // { text: '增加', click: openEdit, icon: 'add' },
        // { line: true },
        // { text: '修改', click: openEdit, icon: 'modify' },
        // { line: true },
        // { text: '删除', click: deleteRow, icon: 'delete' }
        // ]
        // }
    });
    <ctbt:permButton ids="130,131,132"/>
    $("#panel2").ligerPanel({
        title: '角色消息查询',
        width: "100%",
        height : 110,
        
        onToggle : function(isCollapse){
        	if(isCollapse){
        		var wh = $(window).height();
            	gd.setHeight(wh-35);
        	}else{
        		var wh = $(window).height();
            	gd.setHeight(wh-120);
        	}
        	
         },
    });
    
    
    listCountry = $("#listCountry").ligerComboBox({
        width : 200,
        valueField: 'key',
        textField: 'value',
        valueFieldID: 'country_id',
        autocomplete: true,
        keySupport: true,
        setTextBySource:true,
        onSelected : function(value){
        	loadProvinceList();
        }
    }); 
    
    listPro = $("#listProvince").ligerComboBox({
        width : 200,
        valueField: 'key',
        textField: 'value',
        valueFieldID: 'test1',
        autocomplete: true,
        keySupport: true,
        setTextBySource:true,
        onSelected : function(value){
        	loadCityList();
        }
    }); 

    listCity = $("#listCity").ligerComboBox({
        width : 200,
        valueFieldID: 'test2',
        autocomplete: true,
        keySupport: true,
        setTextBySource:true,
        onSelected : function(value){
        	//alert("onChangeValue:"+value);
        }
    }); 
    
    //加载国家列表
    $.ajax({
 	   type: "POST",
 	   url: "${WebUrl}/dic/queryCountryList.ctbt",
 	   //data: params,
 	   dataType:"json",
 	   success: function(listdata){
 		    //alert(listdata);
 		    //var text = JSON.stringify(listdata);;
 		    //alert(text);

			listCountry.setData(listdata);
 	   }
 	});

});

//加载省份
function loadProvinceList(){
	var countryId = listCountry.getValue();
	alert(countryId);
    $.ajax({
  	   type: "POST",
  	   url: "${WebUrl}/dic/queryProvinceList.ctbt?countryId="+countryId,
  	   //data: params,
  	   dataType:"json",
  	   success: function(listdata){
  		    //alert(listdata);
  		    //var text = JSON.stringify(listdata);;
  		    //alert(text);

 			listPro.setData(listdata);
  	   }
  	});
}

//加载城市
function loadCityList(){
	var provinceId = listPro.getValue();
	alert("loadCityList provinceId:"+provinceId);
    $.ajax({
  	   type: "POST",
  	   url: "${WebUrl}/dic/queryCityList.ctbt?regId="+provinceId,
  	   //data: params,
  	   dataType:"json",
  	   success: function(listdata){
  		    //alert(listdata);
  		    //var text = JSON.stringify(listdata);;
  		    //alert(text);

 			listCity.setData(listdata);
  	   }
  	});
}

function textListPro(){
	listPro.selectValue("fujian");
	//listPro.selectValue("福建");
	
	var selectdata = [
        { text: '舟山', id: 'zhoushan' },
        { text: '宁波', id: 'ningbo' },
        { text: '温州', id: 'wenzhou' }
    ];
	
	listCity.setData(selectdata);
}

function query(){

	$.ajax({
	   type: "POST",
	   url: "${WebUrl}/role/queryRoleList.ctbt",
	   //data: params,
	   dataType:"json",
	   success: function(listdata){
		    //alert(listdata);
		    //var text = JSON.stringify(listdata);;
		    //alert(text);

		    var jsonObj = {};
            jsonObj.Rows = listdata;
		    gd.set({ data: jsonObj });
	   }
	});

}


function reset(){
	ctbt.FormUtil.Clean(document.queryRoleFrom);
}

function clause(aa){
	alert(clause);
	alert(aa);
}

var roleEditWin;
function openEdit(item){

	var roleId = "";
	//alert(item.text);
	if(item.text=="修改"){
		var row = gd.getSelectedRow();
	    if (!row) {
	    	alert('请选择要编辑的记录！'); 
	    	return; 
	    }else{
			roleId = row.roleId;
	    }
	}
	
    //alert(JSON.stringify(row));
    //alert(row.roleId);
    
	var toUrl = '${WebUrl}/role/toRoleEdit.ctbt?roleId='+roleId;
	roleEditWin = $.ligerDialog.open({title:'角色编辑',url:toUrl,height: 400,width: 500});
	
	//openSjtWin();
}

var sjtWin;
function openSjtWin(){

	var toUrl = '${WebUrl}/role/toRoleEdit.ctbt';
	sjtWin = $.ligerDialog.open({
		title:'角色编辑三级窗口',
		url:toUrl,
		height: 500,
		width: 500
	});
}

function closeRoleEditWin(){
	if(roleEditWin != null){
		roleEditWin.close();
	}
	
	query();
}

function deleteRow(item){
    gd.deleteSelectedRow();
}

var testPageWin;
function toTestPage(){
	var toUrl = '${WebUrl}/role/toTestPage.ctbt';
	testPageWin = $.ligerDialog.open({title:'测试',url:toUrl,height: 600,width: 700});
}

</script>
</head>
<body>
<div id="panel2" class="easyui-panel" >
<form name="queryRoleFrom" action="">
<div style="height:110;  margin: 5px; padding-top: 10px;">
	<table style="width: 98%; border-collapse:separate; border-spacing:0px 10px;">
		<tr>
			<td width="10%" align="right">角色名称：</td>
			<td width="23%"><input id="roleName" name="roleName" type="text" class="ctbt_qf_input"/></td>
			<td width="10%" align="right">角色描述：</td>
			<td width="23%"><input id="roleDesc" name="roleDesc" type="text" class="ctbt_qf_input"/></td>
			<td width="10%" align="right">是否有效：</td>
			<td>
				<ctbt:select name="validity" id="validity" data="${ctbtfn:getDic(10)}" defaultValue='${validity}' emptyOption="" styleClass="ctbt_qf_select"/>
			</td>
		</tr>
		<tr>
			<td colspan="6" align="right" height="30">
				<input value="查询" type="button" onclick="query()" class="ctbt-btn"/>
				<input value="重置" type="button" onclick="reset()" class="ctbt-btn"/>
				
			</td>
		</tr>
	</table>
</div>
</form>
</div>
<div id="toptoolbar" style="margin-bottom:2px;padding-bottom:10px"></div>
<div id="maingrid"></div>

<div style="display:none;">
 
</div>
</body> 

</html>