<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt"%>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<ctbt:base />
<title>短信管理</title>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>

<script src="${WebUrl}/js/base64/voice-2.0.js" type="text/javascript"></script>

<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css">

<script src="${WebUrl}/js/queryPage.js" type="text/javascript"></script>

<script type="text/javascript">
	var gd, sendTimeStart, sendTimeEnd, msgTypeBox, phoneBox;
	var storage = window.localStorage;

	function itemclick(item) {
		console.log(item.text);
	}
	var userEditWin;

	function add_win_open(item) {
		var userId = "";
		console.log(item.text);
			userEditWin = $.ligerDialog.open({
				height : 400,
				url : '${WebUrl}/phonemsg/toPhoneToSend.ctbt',
				width : 700,
				showMax : true,
				showToggle : true,
				isResize : true,
				slide : false,
				isDrag : true
			});
		

	}
	//初始化ligerUI
	$(function() {
		var pw = $(document).width();
		var dw = pw * 0.98 * 0.23 * 0.95 + 3;
		//ajax 获取键值对
		<ctbt:permButton ids="127"/>
		gd = $("#maingrid").ligerGrid({
			height : '99.5%',
			columns : [ {
				display : '手机号码',
				name : 'phone',
				align : 'left',
				width : 100,
				minWidth : 160
			}, {
				display : '短信类型',
				name : 'msg_type',
				minWidth : 240,
				render : function(item) {
					if (item.msg_type == "1") {
						return "验证短信"
					} else if (item.msg_type == "2") {
						return "验证短信"
					}else if (item.msg_type == "3") {
						return "验证短信"
					}
				}
			}, {
				display : '短信内容',
				name : 'msg_txt',
				minWidth : 240
			}, {
				display : '验证码',
				name : 'msg_code',
				minWidth : 240
			}, {
				display : '发送时间：',
				name : 'send_time',
				minWidth : 240
			}, {
				display : '发送标记：',
				name : 'send_flag',
				minWidth : 240,

			}

			],
			
			usePager:false,
			rownumbers : true,
		});

		$("#panel2").ligerPanel({
	        title: '短信消息查询',
	        width: "100%",
	        height : 145,
	        
	        onToggle : function(isCollapse){
	        	if(isCollapse){
	        		var wh = $(window).height();
	            	gd.setHeight(wh-40);
	        	}else{
	        		var wh = $(window).height();
	            	gd.setHeight(wh-160);
	        	}
	        	
	         },
	    });
		
		phoneBox = $("#phone").ligerTextBox({
			width : dw,
		});
		msgTypeBox = $("#msgType").ligerComboBox({
			width : dw,
			initIsTriggerEvent : false,
			url : '${WebUrl}/dic/getDic.ctbt?dicId=18',
			// onSelected: function (value) {
			// 	alert('选中事件:' + value);
			valueField : 'key',
			textField : 'value',
			isTextBoxMode : false,
			keySupport : true
		});
		sendTimeStart = $("#sendTimeStart").ligerDateEditor({
			width : dw,
			showTime : true,
			labelWidth : 80,
			labelAlign : 'left',
			format: "yyyy-MM-dd hh:mm:ss"
		});
		sendTimeEnd = $("#sendTimeEnd").ligerDateEditor({
			width : dw,
			showTime : true,
			labelWidth : 80,
			labelAlign : 'left',
			format: "yyyy-MM-dd hh:mm:ss"
		});
		$("#pageloading").hide();
		//把role信息保存到本地缓存

	});

	function deleteRow() {
		//提示是否需要删除这个用户的数据
		gd.deleteSelectedRow();
	}

		function query() {
				
						var phone = phoneBox.getValue();
						var msgType = msgTypeBox.getValue();
						sendTimeStartinput = sendTimeStart.getValue()!=null?utc2format(sendTimeStart.getValue()):null;
						sendTimeEndinput = sendTimeEnd.getValue()!=null?utc2format(sendTimeEnd.getValue()):null
						$.ajax({
							type: "POST",
							url: "${WebUrl}/phonemsg/queryPhonemsgList.ctbt",
							data: {
								"phone": phone,
								"msgType": msgType,
								"sendTimeStart": sendTimeStartinput,
								"sendTimeEnd": sendTimeEndinput,
								"page":document.getElementById('pageNum').value,
								"pageSize":document.getElementById('pageSize').value
							},
							dataType: "json",
							success: function (listdata) { //成功后返回数据			
								var text = JSON.stringify(listdata);;
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
				var phone = phoneBox.getValue();
						var msgType = msgTypeBox.getValue();
						sendTimeStartinput = sendTimeStart.getValue()!=null?utc2format(sendTimeStart.getValue()):null;
						sendTimeEndinput = sendTimeEnd.getValue()!=null?utc2format(sendTimeEnd.getValue()):null
				$.ajax({
					url : '${WebUrl}/phonemsg/queryPhonemsgList.ctbt',
					type : "POST",
					data: {
						"phone": phone,
								"msgType": msgType,
								"sendTimeStart": sendTimeStartinput,
								"sendTimeEnd": sendTimeEndinput,
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
				};

	function reset() {
		ctbt.FormUtil.Clean(document.queryRoleFrom);
	}

	function closeUserEditWin() {
		if (userEditWin != null) {
			userEditWin.close();
		}
		// query();
	}

	//wcc begin

	//人员信息对话框
	var phoneToSend2 = null;
	//第一次加载的时候读取整棵树，下次加载的时候根据to中的内容构造树形结构

	function peopleInfo() {
		var toUrl = '${WebUrl}/chat/toSendPhoneMsg.ctbt';
		//phoneToSend.mask();
		if (phoneToSend2 == null)
			phoneToSend2 = $.ligerDialog.open({
				id : "sendWin",
				title : '请选择',
				url : toUrl,
				height : 500,
				width : 300,
				left : 700,
				isResize : true
			});
		else {
			phoneToSend2.show();
		}
	}
	
	function closePhoneToSend(){
		if(phoneToSend != null){
			phoneToSend.close();
		}
		//query();
	}

	function closePhoneToSend2(){		
			phoneToSend2.close();
			phoneToSend2 = null;			
	}
	function utc2format(sendTimeStart){
		var date = new Date(sendTimeStart),
        y = date.getFullYear(),
        month = date.getMonth()+1 > 9 ? date.getMonth()+1 : '0' + parseInt(date.getMonth()+1),
        day = date.getDate() > 9 ? date.getDate() : '0' + date.getDate(),
        h =  date.getHours() > 9 ? date.getHours() : '0' + date.getHours(),
        m = date.getMinutes() > 9 ? date.getMinutes() : '0' + date.getMinutes(),
        s = date.getSeconds() > 9 ? date.getSeconds() : '0' + date.getSeconds();
   	 	var res = y + '-' + month + '-' + day + ' ' + h + ':' + m+':'+s;
		return res;
	}
</script>
</head>

<body>
	<div id="panel2" class="easyui-panel" >
	<form name="queryRoleFrom" action="">
		<div
			style="height: 145; margin: 5px; padding-top: 10px;">
			<table style="width: 98%; border-collapse:separate; border-spacing:0px 10px;">
				
				<tr>
					<td width="10%" align="right">发送开始时间：</td>
					<td width="23%"><input name="sendTimeStart" id="sendTimeStart" type="text" /></td>
					<td width="10%" align="right">发送结束时间：</td>
					<td width="23%"><input name="sendTimeEnd" id="sendTimeEnd" type="text" /></td>
					<td width="10%" align="right">短信类型：</td>
					<td width="23%"><input id="msgType" name="msgType" /></td>
					
				</tr>
				<tr>
					<td width="10%" align="right">手机号码：</td>
					<td width="23%"><input id="phone" name="phone" /></td>
					<td width="10%" ></td>
					<td></td>
					<td width="10%" ></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="6" align="right" height="30"><input value="查询"
						type="button" onclick="query()" class="ctbt-btn"> <input
						value="重置" type="button" onclick="reset()" class="ctbt-btn">
					</td>
				</tr>
			</table>
		</div>
		</form>
		</div>

			<div class="l-loading" style="display: block" id="pageloading"></div>
			<a class="l-button"
				style="width: 120px; float: left; margin-left: 10px; display: none;"
				onclick="deleteRow()">删除选择的行</a>
			<div class="l-clear"></div>
			<div id="toptoolbar" style="margin-bottom:2px;padding-bottom:10px"></div>
			<div id="maingrid"></div>
			<ctbt:pagefootTag  pageNum="1" recordNum="0" currentPage="1"/>
			<div style="display: none;"></div>
</body>

</html>