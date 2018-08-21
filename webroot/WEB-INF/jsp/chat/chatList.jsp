<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聊天管理</title>
<ctbt:base/>

<script src="${WebUrl}/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>

<script src="${WebUrl}/js/base64/voice-2.0.js" type="text/javascript"></script>
<script src="${WebUrl}/js/queryPage.js" type="text/javascript"></script>	
<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css">


</head>
<body>
<div id="panel2" class="easyui-panel" >
<form id="queryChatForm" name="queryChatForm" action="">
<div style="height:150; margin: 5px; padding-top: 10px;">
	<table style="width:98%; border-collapse:separate; border-spacing:0px 10px;">
		<tr>
			<td width="10%" align="right">开始时间：</td>
			<td width="23%">
				<input id="beginTime" name="beginTime" type="text" class="ctbt_qf_input"/>
			</td> 
			<td width="10%" align="right">结束时间：</td>
			<td width="23%">
				<input id="endTime" name="endTime" type="text" class="ctbt_qf_input"/>
			</td>
			<td width="10%" align="right">发送者手机：</td>
			<td width="23%">
				<input id="fromPhone" name="fromPhone" type="text" class="ctbt_qf_input"/>
			</td>
		</tr>
		<tr> 
			<td width="10%" align="right">接收者手机：</td>
			<td width="23%">
				<input id="toPhone" name="toPhone" type="text" class="ctbt_qf_input"/>
			</td>
			<td width="10%"></td>
			<td width="23%"></td>
			<td width="10%"></td>
			<td width="23%"></td>
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
<div id="toptoolbar" style="margin-bottom:2px;padding-bottom:10px"></div>
<div id="maingrid"></div>
<ctbt:pagefootTag  pageNum="1" recordNum="0" currentPage="1"/>
<div style="display:none;"></div>
</body> 
<script type="text/javascript">
var gd;
RongIMLib.RongIMVoice.init();
$(function ()
{
	var pw = $(document).width();
	var dw = pw * 0.98 * 0.23 * 0.95 + 3;
	var toolbar
	<ctbt:permButton ids="109"/>
    gd = $("#maingrid").ligerGrid({
        height:'99.5%',
        columns: [
        { display: '发送时间', name: 'sendTime', align: 'left',  minWidth: 150,
        	render : function(result) {
				return ctbt.Date2String(result.sendTime, 'datetime');}			
        },
        { display: '接收时间', name: 'recvTime', minWidth: 150,
        	render : function(result) {
				return ctbt.Date2String(result.recvTime, 'datetime');	}
        },
        { display: '消息', name: 'msgTxt', width: 400 },
        { display: '发送者IC卡号', name: 'msgFrom', minWidth: 150 },
        { display: '发送者手机', name: 'fromPhone', minWidth: 150 },
        { display: '接收者IC卡号', name: 'msgTo',  minWidth: 150 },
        { display: '接收者手机', name: 'toPhone', minWidth: 150 },
        { display: '是否接收', name: 'isRecv', minWidth: 80 },
        { display: '接收确认时间', name: 'revcConfirmTime', minWidth: 100,
        	render : function(result) {
				return ctbt.Date2String(result.sendTime, 'datetime');}
        }
        ],
        usePager:false,rownumbers:true,
    });
    $("#panel2").ligerPanel({
        title: '聊天消息查询',
        width: "100%",
        height : 150,
        
        onToggle : function(isCollapse){
        	if(isCollapse){
        		var wh = $(window).height();
            	gd.setHeight(wh-35);
        	}else{
        		var wh = $(window).height();
            	gd.setHeight(wh-160);
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
		 var params = $(document.queryChatForm).serialize();
			var page=document.getElementById('pageNum').value;
			var pageSize=document.getElementById('pageSize').value;
			params = params+"&page="+page;
			params = params+"&pageSize="+pageSize;
			console.log(params)
			$.ajax({
			   type: "POST",
			   url: "${WebUrl}/chat/queryChatList.ctbt",
			   data: params,
			   dataType:"json",
			   async : false,
			   success: function(listdata){
				   cnosole.log(listdata)
					document.getElementById("pageNum").value=listdata['currentPage'];
					$("#recordNum").text(listdata['recordNum']);
					$("#currentPage").text(listdata['currentPage']);
					$("#sumPageNum").text(listdata['sumPageNum']);
					listdata=listdata['Rows']
					
				   for(var i in listdata){
						if(listdata[i].isRecv == "1")
							listdata[i].isRecv = "已接收";
						else if(listdata[i].isRecv == "0")
							listdata[i].isRecv = "未接受";
				   }
				   console.log(listdata)
				   for(var i in listdata){
				    	if(listdata[i].msgType=="3"){
				    		listdata[i].msgTxt = "<input type='image' src='http://127.0.0.1:8080/beidou/images/icons/notice.png' style='width:25px;height:25px' onclick=playVoice('"+listdata[i].msgTxt+"') >"
				    	}
				    }
				    var jsonObj = {};
		            jsonObj.Rows = listdata;
		            console.log(jsonObj);
				    gd.set({ data: jsonObj });
			   },
			   error: function(error){
				   //console.log(listdata)
				   alert("error");
			   }
			});
	 }
//按条件查询
function queryByCondition(){
	var params = $(document.queryChatForm).serialize();
	var page=document.getElementById('pageNum').value;
	var pageSize=document.getElementById('pageSize').value;
	params = params+"&page="+page;
	params = params+"&pageSize="+pageSize;
	console.log("1111111");
	$.ajax({
	   type: "POST",
	   url: "${WebUrl}/chat/queryChatList.ctbt",
		data : params,
		dataType : "json",
		async : false,
	   success: function(listdata){
		   console.log(listdata)
		 document.getElementById("pageNum").value=listdata['currentPage'];
				$("#recordNum").text(listdata['recordNum']);
				$("#currentPage").text(listdata['currentPage']);
				$("#sumPageNum").text(listdata['sumPageNum']);
				listdata=listdata['Rows'] 
		   for(var i in listdata){
				if(listdata[i].isRecv == "1")
					listdata[i].isRecv = "已接收";
				else if(listdata[i].isRecv == "0")
					listdata[i].isRecv = "未接受";
		   }
		   console.log(listdata)
		   for(var i in listdata){
		    	if(listdata[i].msgType=="3"){
		    		listdata[i].msgTxt = "<input type='image' src='http://127.0.0.1:8080/beidou/images/icons/notice.png' style='width:25px;height:25px' onclick=playVoice('"+listdata[i].msgTxt+"') >"
		    	}
		    }
		    var jsonObj = {};
		    console.log(listdata);
            jsonObj.Rows = listdata;
            console.log(jsonObj);
		    gd.set({ data: jsonObj }); 

	   },
	   error: function(error){
		   //console.log(listdata)
		   console.log("123");
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

//发送信息对话框
var chatEditWin;
function addMsg(){	
	var toUrl = '${WebUrl}/chat/showFromTo.ctbt?'
	chatEditWin = $.ligerDialog.open({id:"editWin",title:'发送消息',url:toUrl,height: 450,width: 700,isResize:true});
	
}

//人员信息对话框
var chatSendWin = null;
var count = 1;
//第一次加载的时候读取整棵树，下次加载的时候根据to中的内容构造树形结构

function peopleInfo(){		
	var toUrl = '${WebUrl}/chat/toSendMsg.ctbt';
	//chatEditWin.mask();
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
		console.log("22222"+chatSendWin);
		
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