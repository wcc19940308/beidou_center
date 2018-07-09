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

<script src="${WebUrl}/js/base64/voice-2.0.js" type="text/javascript"></script>

<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css">


</head>
<body>

<form id="queryChatForm" name="queryChatForm" action="">
<div style="height:50px; border:solid 1px #cccccc; margin: 5px; padding-top: 10px;">
	<table style="width: 98%;">
		<tr>
			  <td width="10%" align="right">发送者手机：</td>
			<td width="23%">
			<input id="fromPhone" name="fromPhone" type="text" class="ctbt_qf_input"/>
			</td> 
			<td width="10%" align="right">接受者手机：</td>
			<td width="23%">
			<input id="toPhone" name="toPhone" type="text" class="ctbt_qf_input"/>
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

<div id="maingrid"></div>

<div style="display:none;">
 
</div>
</body> 
<script type="text/javascript">
var gd;
RongIMLib.RongIMVoice.init();
$(function ()
{
    gd = $("#maingrid").ligerGrid({
        height:'99.5%',
        columns: [
        { display: '消息ID', name: 'msgId', align: 'left',  minWidth: 150 },
        { display: '发送时间', name: 'sendTime', align: 'left',  minWidth: 150 },
        { display: '接收时间', name: 'recvTime', minWidth: 150 },
        { display: '消息', name: 'msgTxt', minWidth: 200 },
        { display: '发送者IC卡号', name: 'msgFrom', minWidth: 150 },
        { display: '发送者手机', name: 'fromPhone', minWidth: 150 },
        { display: '接收者IC卡号', name: 'msgTo',  minWidth: 150 },
        { display: '接收者手机', name: 'toPhone', minWidth: 150 },
        { display: '是否确认', name: 'isRecv', minWidth: 80 },
        { display: '接收确认时间', name: 'revcConfirmTime', minWidth: 100 }
        ],
        pageSize:20 ,rownumbers:true,
        toolbar: { items: [
        { text: '查询全部', click: query, icon: 'search' },
        { line: true },
        { text: '发送信息', click: addMsg, icon: 'add' }
        ]
        }
    });
});

//按条件查询
function queryByCondition(){
	var params = $(document.queryChatForm).serialize();
	console.log(params)
	$.ajax({
	   type: "POST",
	   url: "${WebUrl}/chat/queryChatList.ctbt",
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
//查询所有内容
function query(){
	$.ajax({
	   type: "POST",
	   url: "${WebUrl}/chat/showChatList.ctbt",
	   dataType:"json",
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
		    for(var i in listdata){
		    	if(listdata[i].msgType=="3"){
		    		listdata[i].msgTxt = "<input type='image' src='http://127.0.0.1:8080/beidou/images/icons/notice.png' style='width:25px;height:25px' onclick=playVoice('"+listdata[i].msgTxt+"') >"
		    	}
		    }
		    console.log(listdata)
		    var jsonObj = {};
            jsonObj.Rows = listdata;
            console.log(jsonObj);
		    gd.set({ data: jsonObj });
		    console.log(gd);
	   },
	   error: function(error){
		   alert("error");
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

var chatEditWin;
function addMsg(){

	
	var toUrl = '${WebUrl}/chat/toSendMsg.ctbt?'
	chatEditWin = $.ligerDialog.open({title:'发送消息',url:toUrl,height: 500,width: 500});
}

function closeRoleEditWin(){
	if(chatEditWin != null){
		chatEditWin.close();
	}
	
	query();
}

function deleteRow(item){
    gd.deleteSelectedRow();
}

var lastVoice;
//语音部分
function playVoice(res){
	//document.getElementById("playId").onclick = function(){  
	//停止上一次的
	RongIMLib.RongIMVoice.stop(lastVoice);
	RongIMLib.RongIMVoice.play(res);
	lastVoice = res;
	console.log(res);
	/* };
	document.getElementById("stopId").onclick = function(){
	    RongIMLib.RongIMVoice.stop();
	}; */
}


</script>
</html>