<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出站数据</title>
<ctbt:base/>

<script src="${WebUrl}/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>

<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script> 

<script src="${WebUrl}/js/base64/voice-2.0.js" type="text/javascript"></script>
<script src="${WebUrl}/js/queryPage.js" type="text/javascript"></script>
<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css">


</head>
<body>
<div id="panel2" class="easyui-panel">
<form id="queryDataPackSendForm" name="queryDataPackSendForm" action="">
<div style="height: 175; margin: 5px; padding-top: 10px;">
	<table style="width: 98%;border-collapse: separate; border-spacing: 0px 10px;">
		<tr>
			<td width="10%" align="right">开始时间：</td>
			<td width="23%">
				<input id="beginTime" name="beginTime" type="text" class="ctbt_qf_input"/>
			</td> 
			<td width="10%" align="right">结束时间：</td>
			<td width="23%">
				<input id="endTime" name="endTime" type="text" class="ctbt_qf_input"/>
			</td>
			<td width="10%" align="right">来源指挥机设备号：</td>
			<td width="23%">
				<input id="masterFrom" name="masterFrom" type="text" class="ctbt_qf_input"/>
			</td>
		</tr>
		<tr>
			<td width="10%" align="right">目标指挥机设备号：</td>
			<td width="23%">
				<input id="masterTo" name="masterTo" type="text" class="ctbt_qf_input"/>
			</td>
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
<ctbt:pagefootTag  pageNum="1" recordNum="0" currentPage="1"/>
<div style="display:none;"></div>
</body> 
<script type="text/javascript">



var gd;
$(function ()
{
	var pw = $(document).width();
	var dw = pw * 0.98 * 0.23 * 0.95 + 3;
	
    gd = $("#maingrid").ligerGrid({
    	
        height:'99.5%',
        columns: [
        { display: '来源服务器编号', name: 'centerNo', align: 'left',  minWidth: 150 },
        { display: '报文类型', name: 'pkgType', minWidth: 80 },
        { display: '中心平台中消息id', name: 'msgId', minWidth: 150 },
        { display: '报文数据', name: 'pkgData', minWidth: 200 },
        { display: '来源指挥机设备号', name: 'masterFrom', minWidth: 150 },
        { display: '来源北斗设备号', name: 'pkgFrom',  minWidth: 150 },
        { display: '来源手机号码', name: 'phoneFrom', minWidth: 150 },
        { display: '目标指挥机设备号', name: 'masterTo', minWidth: 150 },
        { display: '目标北斗设备号', name: 'pkgTo', minWidth: 150 },
        { display: '目标手机号码', name: 'phoneTo', minWidth: 150 },
        { display: '创建时间', name: 'createTime', minWidth: 150,
        	render : function(result) {
				return ctbt.Date2String(result.createTime, 'datetime');	}	
        },
        { display: '发送给指挥机的时间', name: 'sendTime', minWidth: 150,
        	render : function(result) {
				return ctbt.Date2String(result.sendTime, 'datetime');		}
        },
        { display: '发送的数据站编号', name: 'sendSiteNo', minWidth: 150 }
        ],
        usePager:false,rownumbers:true,             
    });
    $("#panel2").ligerPanel({
        title: '出站数据查询',
        width: "100%",
        height : 175,
        
        onToggle : function(isCollapse){
        	if(isCollapse){
        		var wh = $(window).height();
            	gd.setHeight(wh-40);
        	}else{
        		var wh = $(window).height();
            	gd.setHeight(wh-190);
        	}
        	
         },
    });
    
    $("#beginTime").ligerDateEditor({
		format : "yyyy-MM-dd hh:mm:ss",
		showTime : true,
		cancelable : false,
		width : dw
	});

	$("#endTime").ligerDateEditor({
		format : "yyyy-MM-dd hh:mm:ss",
		showTime : true,
		cancelable : false,
		width : dw
	});
});

function queryBypage(pageNum,pageSize){
	var params = $(document.queryDataPackSendForm).serialize();
	var page=document.getElementById('pageNum').value;
	var pageSize=document.getElementById('pageSize').value;
	params = params+"&page="+page;
	params = params+"&pageSize="+pageSize;
	console.log(params)
	$.ajax({
	   type: "POST",
	   url: "${WebUrl}/datapack/queryDataPackSendList.ctbt",
	   data: params,
	   dataType:"json",
	   async : false,
	   success: function(listdata){
		   console.log(listdata)
		   console.log(listdata)
		document.getElementById("pageNum").value=listdata['currentPage'];
				$("#recordNum").text(listdata['recordNum']);
				$("#currentPage").text(listdata['currentPage']);
				$("#sumPageNum").text(listdata['sumPageNum']);
				listdata=listdata['Rows']
				
		    var jsonObj = {};
            jsonObj.Rows = listdata;
            console.log(jsonObj);
		    gd.set({ data: jsonObj });
	   },
	   error: function(error){
		   alert("error");
	   }
	});
} 

//按条件查询
function queryByCondition(){
	var params = $(document.queryDataPackSendForm).serialize();
	var page=document.getElementById('pageNum').value;
	var pageSize=document.getElementById('pageSize').value;
	params = params+"&page="+page;
	params = params+"&pageSize="+pageSize;
	console.log(params)
	$.ajax({
	   type: "POST",
	   url: "${WebUrl}/datapack/queryDataPackSendList.ctbt",
	   data: params,
	   dataType:"json",
	   async : false,
	   success: function(listdata){
		   console.log(listdata)
		   console.log(listdata)
		document.getElementById("pageNum").value=listdata['currentPage'];
				$("#recordNum").text(listdata['recordNum']);
				$("#currentPage").text(listdata['currentPage']);
				$("#sumPageNum").text(listdata['sumPageNum']);
				listdata=listdata['Rows']
				
		    var jsonObj = {};
            jsonObj.Rows = listdata;
            console.log(jsonObj);
		    gd.set({ data: jsonObj });
	   },
	   error: function(error){
		   alert("error");
	   }
	});
} 

//重置
function reset(){
	ctbt.FormUtil.Clean(document.queryRoleFrom);
}

</script>
</html>