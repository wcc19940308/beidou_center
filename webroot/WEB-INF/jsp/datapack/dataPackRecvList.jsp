<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>入站数据</title>
<ctbt:base/>

<script src="${WebUrl}/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>

<script src="${WebUrl}/js/base64/voice-2.0.js" type="text/javascript"></script>

<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css">


</head>
<body>
<div id="panel2" class="easyui-panel" style="height: 150px;">
<form id="queryDataPackRecvForm" name="queryDataPackRecvForm" action="">
<div style="height: 150px; margin: 5px; padding-top: 10px;">
	<table style="width: 98%;">
		<tr>
			<td width="10%" align="right">开始时间：</td>
			<td width="23%">
				<input id="beginTime" name="beginTime" type="text" class="ctbt_qf_input"/>
			</td> 
			<td width="10%" align="right">结束时间：</td>
			<td width="23%">
				<input id="endTime" name="endTime" type="text" class="ctbt_qf_input"/>
			</td>
		</tr>
		
		<tr>
			<td width="10%" align="right">来源指挥机设备号：</td>
			<td width="23%">
				<input id="masterFrom" name="masterFrom" type="text" class="ctbt_qf_input"/>
			</td> 
			<td width="10%" align="right">目标指挥机设备号：</td>
			<td width="23%">
				<input id="masterTo" name="masterTo" type="text" class="ctbt_qf_input"/>
			</td>
		</tr>
		
		<tr>
			<td width="10%" align="right">来源北斗设备号：</td>
			<td width="23%">
				<input id="pkgFrom" name="pkgFrom" type="text" class="ctbt_qf_input"/>
			</td> 
			<td width="10%" align="right">目标北斗设备号：</td>
			<td width="23%">
				<input id="pkgTo" name="pkgTo" type="text" class="ctbt_qf_input"/>
			</td>
		</tr>
		
		<tr>
			<td width="10%" align="right">来源手机号码：</td>
			<td width="23%">
				<input id="phoneFrom" name="phoneFrom" type="text" class="ctbt_qf_input"/>
			</td> 
			<td width="10%" align="right">目标手机号码：</td>
			<td width="23%">
				<input id="phoneTo" name="phoneTo" type="text" class="ctbt_qf_input"/>
			</td>
		</tr>
		
		<tr>
			<td colspan="6" align="right" height="30">
				<input value="查询" type="button" onclick="queryByCondition()" class="ctbt-btn"/>
				<input value="重置" type="button" onclick="reset()" class="ctbt-btn"/>
			</td>
		</tr>
	</table>
</div>
</form>
</div>

<div id="maingrid"></div>

<div style="display:none;"></div>
</body> 
<script type="text/javascript">
var gd;
$(function ()
{
	gd = $("#maingrid").ligerGrid({
        height:'99.5%',
        columns: [
        { display: '中心平台中消息id', name: 'msgId', minWidth: 150 },
        { display: '报文类型', name: 'pkgType', minWidth: 80 },
        { display: '报文数据', name: 'pkgData', minWidth: 200 },       
        { display: '来源指挥机设备号', name: 'masterFrom', minWidth: 150 },
        { display: '来源北斗设备号', name: 'pkgFrom',  minWidth: 150 },
        { display: '来源手机号码', name: 'phoneFrom', minWidth: 150 },
        { display: '目标指挥机设备号', name: 'masterTo', minWidth: 150 },
        { display: '目标北斗设备号', name: 'pkgTo', minWidth: 150 },
        { display: '目标手机号码', name: 'phoneTo', minWidth: 150 },
        { display: '接收时间', name: 'recvTime', minWidth: 150 },
        { display: '数据来源的数据站编号', name: 'fromSiteTo', minWidth: 150 }
        ],
        pageSize:20 ,rownumbers:true,              
    });
    $("#panel2").ligerPanel({
        title: '聊天消息查询',
        width: "100%",
        height : 90,
        
        onToggle : function(isCollapse){
        	if(isCollapse){
        		var wh = $(window).height();
            	gd.setHeight(wh-35);
        	}else{
        		var wh = $(window).height();
            	gd.setHeight(wh-100);
        	}
        	
         },
    });
});

//按条件查询
function queryByCondition(){
	var params = $(document.queryDataPackRecvForm).serialize();
	console.log(params)
	$.ajax({
	   type: "POST",
	   url: "${WebUrl}/datapack/queryDataPackRecvList.ctbt",
	   data: params,
	   dataType:"json",
	   async : false,
	   success: function(listdata){
		   console.log(listdata)
		   for(var i in listdata){
			   	  var now = new Date(listdata[i].sendTime) 
			  	  var   year=now.getFullYear();     
	              var   month=now.getMonth()+1;     
	              var   date=now.getDate();     
	              var   hour=now.getHours();     
	              var   minute=now.getMinutes();     
	              var   second=now.getSeconds();
	              listdata[i].sendTime = year+"-"+month+"-"+date+"  "+hour+":"+minute+":"+second
		   }
		   console.log(listdata)
		    var jsonObj = {};
            jsonObj.Rows = listdata;
            console.log(jsonObj);
		    gd.set({ data: jsonObj });
	   },
	   error: function(error){
		   console.log(listdata)
		   alert("error");
	   }
	});
} 

function reset(){
	ctbt.FormUtil.Clean(document.queryRoleFrom);
}


</script>
</html>