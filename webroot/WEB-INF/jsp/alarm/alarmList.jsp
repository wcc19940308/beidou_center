<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt"%>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报警管理</title>
<ctbt:base />

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
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerComboBox.js"
	type="text/javascript"></script>
<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css"
	rel="stylesheet" type="text/css">
</head>

<body>
	<div id="panel2" class="easyui-panel">
		<form name="queryAlarmFrom" name="queryAlarmFrom" action="">
			<div style="height: 175; margin: 5px; padding-top: 10px;">
				<table
					style="width: 98%; border-collapse: separate; border-spacing: 0px 10px;">
					<tr>
						<td width="10%" align="right">发送时间起：</td>
						<td width="23%">
						<input class="ctbt_qf_input" type="text"
							id="beginTime1" name="beginTime1" /></td>
						<td width="10%" align="right">发送时间止：</td>
						<td width="23%">
						<input class="ctbt_qf_input" type="text"
							 id="endTime1" name="endTime1" /></td>
						<td width="10%" align="right">发送者：</td>
						<td width="23%">
						<input class="ctbt_qf_input" id="msgFrom"
							name="msgFrom" type="text" /></td>
					</tr>
					<tr>
						<td width="10%" align="right">接收时间起：</td>
						<td width="23%">
						<input class="ctbt_qf_input" type="text"
							id="beginTime2" name="beginTime2" /></td>
						<td width="10%" align="right">接收时间止：</td>
						<td width="23%">
						<input class="ctbt_qf_input" type="text"
							 id="endTime2" name="endTime2" /></td>
						<td width="10%" align="right">接收者：</td>
						<td width="23%">
						<input class="ctbt_qf_input" id="msgTo"
							name="msgTo" type="text" /></td>
					</tr>
					<tr>
						<td width="10%" align="right">报警类型：</td>
						<td><input class="ctbt_qf_input" type="text" id="test1">
							<input class="ctbt_qf_input" type="hidden" id="test2"></td>
						<td width="10%"></td>
						<td width="23%"></td>
						<td width="10%"></td>
						<td width="23%"></td>
					</tr>
					<tr>
						<td colspan="6" align="right" height="30"><input value="查询"
							type="button" onclick="queryByCondition()" class="ctbt-btn" /> <input
							value="重置" type="button" onclick="reset()" class="ctbt-btn" /></td>
					</tr>
				</table>
			</div>
		</form>

	</div>

	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div class="l-clear"></div>
	<div id="maingrid"></div>
	<div style="display: none;"></div>


</body>
<script type="text/javascript">
	function itemclick(item) {
		alert(item.text);

	}

	var gd;
	var selectOption;
	$(function() {
		gd = $("#maingrid").ligerGrid({
			height : '99.5%',
			columns : [ {
				display : '发送时间',
				name : 'send_time',
				align : 'left',
				width : 180,
				minWidth : 160,
				render : function(result) {
					return ctbt.Date2String(result.send_time, 'datetime');
				}
			}, {
				display : '接收时间',
				name : 'recv_time',
				minWidth : 160,
				render : function(result) {
					return ctbt.Date2String(result.recv_time, 'datetime');
				}
			}, {
				display : '报警类型',
				name : 'item_value',
				minWidth : 160
			}, {
				display : '报警信息',
				name : 'msg_txt',
				minWidth : 160
			}, {
				display : '发送者',
				name : 'msg_from',
				minWidth : 160
			}, {
				display : '接收者',
				name : 'msg_to',
				minWidth : 160
			}, {
				display : '是否接收',
				name : 'is_recv',
				minWidth : 30
			}, {
				display : '接收确认时间',
				name : 'recv_confirm_time',
				minWidth : 160,
				render : function(result) {
					return ctbt.Date2String(result.recv_confirm_time, 'datetime');
				}
			}, {
				display : '是否排除',
				name : 'is_exclude',
				minWidth : 30
			}, {
				display : '排除确认时间',
				name : 'exclude_confirm_time',
				minWidth : 160,
				render : function(result) {
					return ctbt.Date2String(result.exclude_confirm_time, 'datetime');
				}
			},

			],
			pageSize : 20,
			rownumbers : true,
			toolbar : {
				items : [ {
					text : '发送信息',
					click : openEdit,
					icon : 'add'
				}, ]
			}

		});

		$("#pageloading").hide();

		$("#panel2").ligerPanel({
			title : '报警消息查询',
			width : "100%",
			height : 175,

			onToggle : function(isCollapse) {
				if (isCollapse) {
					var wh = $(window).height();
					gd.setHeight(wh - 40);
				} else {
					var wh = $(window).height();
					gd.setHeight(wh - 190);
				}

			},
		});

		selectOption = $("#test1").ligerComboBox({
			url : '${WebUrl}/dic/getDic.ctbt?dicId=15',
			valueField : 'key',
			textField : 'value',
			width : 250
		});

		$("input[name='beginTime1']").ligerDateEditor({
			format : "yyyy-MM-dd hh:mm:ss",
			showTime : true,
			cancelable : false,
			width : "250"
		});

		$("input[name='endTime1']").ligerDateEditor({
			format : "yyyy-MM-dd hh:mm:ss",
			showTime : true,
			cancelable : false,
			width : "250"
		});
		
		$("input[name='beginTime2']").ligerDateEditor({
			format : "yyyy-MM-dd hh:mm:ss",
			showTime : true,
			cancelable : false,
			width : "250"
		});

		$("input[name='endTime2']").ligerDateEditor({
			format : "yyyy-MM-dd hh:mm:ss",
			showTime : true,
			cancelable : false,
			width : "250"
		});
		
	});

	//按条件查询
	function queryByCondition() {
		var params = $(document.queryAlarmFrom).serialize();
		console.log(params);
		params = params+"&msgType="+selectOption.getValue();
		console.log(params);
		$.ajax({
			type : "POST",
			url : "${WebUrl}/alarm/queryAlarmList.ctbt",
			data : params,
			dataType : "json",
			async : false,
			success : function(listdata) {
				console.log(listdata)
				/* for ( var i in listdata) {
					var now = new Date(listdata[i].sendTime)
					var year = now.getFullYear();
					var month = now.getMonth() + 1;
					var date = now.getDate();
					var hour = now.getHours();
					var minute = now.getMinutes();
					var second = now.getSeconds();
					listdata[i].sendTime = year + "-" + month + "-" + date
							+ "  " + hour + ":" + minute + ":" + second
				} */
				for(var i in listdata){
					if(listdata[i].is_recv == "1")
						listdata[i].is_recv = "已接收";
					else if(listdata[i].is_recv == "0")
						listdata[i].is_recv = "未接受";
					
					if(listdata[i].is_exclude == "1")
						listdata[i].is_exclude = "已排除";
					else if(listdata[i].is_exclude == "0")
						listdata[i].is_exclude = "未排除";
				}
				var jsonObj = {};
				jsonObj.Rows = listdata;
				console.log(jsonObj);
				gd.set({
					data : jsonObj
				});
			},
			error : function(error) {
				console.log(listdata)
				alert("error");
			}
		});
	}

	/* 	function query() {
	 $.ajax({
	 type : "POST",
	 url : "${WebUrl}/alarm/queryAlarmList.ctbt",
	 //data: params,
	 dataType : "json",
	 success : function(listdata) {
	 var jsonObj = {};
	 jsonObj.Rows = listdata;
	 gd.set({
	 data : jsonObj
	 });
	 }
	 });
	 } */

	function clause(aa) {
		alert(clause);
		alert(aa);
	}

	var alarmEditWin;
	function openEdit(item) {

		var toUrl = '${WebUrl}/alarm/toAlarmEdit.ctbt';
		alarmEditWin = $.ligerDialog.open({
			title : '发送报警',
			url : toUrl,
			height : 450,
			width : 700
		});
		//setTimeout(closeRoleEditWin, 2000);
	}

	//人员信息对话框
	var chatSendWin = null;
	var count = 1;
	//第一次加载的时候读取整棵树，下次加载的时候根据to中的内容构造树形结构
	function peopleInfo() {
		var toUrl = '${WebUrl}/alarm/toSendMsg.ctbt';
		if (chatSendWin == null)
			chatSendWin = $.ligerDialog.open({
				id : "sendWin",
				title : '请选择',
				url : toUrl,
				height : 500,
				width : 300,
				left : 700,
				isResize : true
			});
		else {
			chatSendWin.show();
		}
	}

	function closeChatSendWin() {

		chatSendWin.close();
		chatSendWin = null;
		console.log("22222" + chatSendWin);

	}

	function closeAlarmEditWin() {
		if (alarmEditWin != null) {
			alarmEditWin.close();
		}

		queryByCondition();
	}

	function reset() {
		ctbt.FormUtil.Clean(document.queryAlarmFrom);
	}
</script>


</html>