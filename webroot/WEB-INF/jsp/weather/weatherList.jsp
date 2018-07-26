<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>气象管理</title>
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
<div id="panel2" class="easyui-panel" >
<form id="queryWeatherForm" name="queryWeatherForm" action="">
<div style="height:110; margin: 5px; padding-top: 10px;">
	<table style="width: 98%; border-collapse:separate; border-spacing:0px 10px;">
		<tr>
			<td width="10%" align="right">开始时间：</td>
			<td width="23%">
				<input id="beginTime" name="beginTime" type="text" class="ctbt_qf_input"/>
			</td> 
			<td width="10%" align="right">结束时间：</td>
			<td width="23%">
				<input id="endTime" name="endTime" type="text" class="ctbt_qf_input"/>
			</td>
			<td width="10%" align="right">目标IC卡号：</td>
			<td width="23%">
				<input id="cardNo" name="cardNo" type="text" class="ctbt_qf_input"/>
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
RongIMLib.RongIMVoice.init();
$(function ()
{
    gd = $("#maingrid").ligerGrid({
        height:'99.5%',
        columns: [
        { display: '发送时间', name: 'sendTime', align: 'left',  minWidth: 150
        	/* render : function(result) {
				return ctbt.Date2String(result.sendTime, 'datetime');	}	 */
        },
        { display: '天气', name: 'msgTxt', minWidth: 200 },
        { display: '发送者', name: 'msgFrom', minWidth: 150 },
        
        { display: '接收者IC卡号', name: 'msgTo',  minWidth: 150 },
        ],
        pageSize:20 ,rownumbers:true,
        toolbar: { items: [
        { text: '发送信息', click: addMsg, icon: 'add' },
        { line: true }
        ]
        }
    });
    $("#panel2").ligerPanel({
        title: '气象消息查询',
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
    
    $("#beginTime").ligerDateEditor({
		format : "yyyy-MM-dd hh:mm:ss",
		showTime : true,
		cancelable : false,
		width : "250"
	});

	$("#endTime").ligerDateEditor({
		format : "yyyy-MM-dd hh:mm:ss",
		showTime : true,
		cancelable : false,
		width : "250"
	});
    
});

//按条件查询
function queryByCondition(){
	var params = $(document.queryWeatherForm).serialize();
	console.log(params)
	$.ajax({
	   type: "POST",
	   url: "${WebUrl}/weather/queryWeatherList.ctbt",
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

//发送信息对话框
var chatEditWin;
function addMsg(){	
	var toUrl = '${WebUrl}/weather/showFromTo.ctbt?'
	chatEditWin = $.ligerDialog.show({id:"editWin",title:'发送公告',url:toUrl,height: 450,width: 700,isResize:true});
}

//人员信息对话框
var chatSendWin = null;
var count = 1;
//第一次加载的时候读取整棵树，下次加载的时候根据to中的内容构造树形结构
function peopleInfo(){		
	var toUrl = '${WebUrl}/weather/toSendMsg.ctbt';
	
	if(chatSendWin == null)
		chatSendWin = $.ligerDialog.open({id:"sendWin",title:'请选择',url:toUrl,height: 500,width: 300,left: 700,isResize:true});
	else{
		chatSendWin.show();
	}		
}

function closeChatEditWin(){
	if(chatEditWin != null){
		chatEditWin.close();
	}

	queryByCondition();
}

function closeChatSendWin(){
	
		chatSendWin.close();
		chatSendWin = null;		
}

function deleteRow(item){
    gd.deleteSelectedRow();
}

var lastVoice;
//语音部分
function playVoice(res){
	//停止上一次的
	RongIMLib.RongIMVoice.stop(lastVoice);
	RongIMLib.RongIMVoice.play(res);
	lastVoice = res;
	console.log(res);	
}


</script>
</html>