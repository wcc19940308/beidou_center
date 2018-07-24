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

<script type="text/javascript">
	function itemclick(item) {
		alert(item.text);

	}

	var gd;
	$(function() {
		gd = $("#maingrid").ligerGrid({
			height : '99.5%',
			columns : [ {
				display : '发送时间',
				name : 'sendTime',
				align : 'left',
				width : 180,
				minWidth : 160,
				render : function(result) {
					return ctbt.Date2String(result.sendTime, 'datetime');
				}
			}, {
				display : '接收时间',
				name : 'recvTime',
				minWidth : 160
			}, {
				display : '报警类型',
				name : 'msgType',
				minWidth : 160
			}, {
				display : '报警信息',
				name : 'msgTxt',
				minWidth : 160
			}, {
				display : '发送者',
				name : 'msgFrom',
				minWidth : 160
			}, {
				display : '接收者',
				name : 'msgTo',
				minWidth : 160
			}, {
				display : '是否接收',
				name : 'isRecv',
				minWidth : 30
			}, {
				display : '接收确认时间',
				name : 'recvConfirmTime',
				minWidth : 160
			}, {
				display : '是否排除',
				name : 'isExclude',
				minWidth : 30
			}, {
				display : '排除确认时间',
				name : 'excludeConfirmTime',
				minWidth : 160
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

		$("#test1").ligerComboBox({
			url : '${WebUrl}/dic/getDic.ctbt?dicId=15',
			valueField : 'key',
			textField : 'value',
			width : 220
		});

		$("input[name='sendTime']").ligerDateEditor({
			format : "yyyy-MM-dd hh:mm:ss",
			showTime : true,
			cancelable : false,
			width : "250"
		});

		$("input[name='recvTime']").ligerDateEditor({
			format : "yyyy-MM-dd hh:mm:ss",
			showTime : true,
			cancelable : false,
			width : "250"
		});
	});

	function query() {
		//gd.url='${WebUrl}/role/queryRoleList.ctbt?role.id=22';
		//gd.loadServerData('${WebUrl}/role/queryRoleList.ctbt?role.id=22', clause);

		$.ajax({
			type : "POST",
			url : "${WebUrl}/alarm/queryAlarmList.ctbt",
			//data: params,
			dataType : "json",
			success : function(listdata) {
				//alert(listdata);
				//var text = JSON.stringify(listdata);;
				//alert(text);

				var jsonObj = {};
				jsonObj.Rows = listdata;
				gd.set({
					data : jsonObj
				});
			}
		});
	}

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
		var toUrl = '${WebUrl}/chat/toSendMsg.ctbt';

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

		query();
	}

	function reset() {
		ctbt.FormUtil.Clean(document.queryAlarmFrom);
	}
</script>

</head>
<body>


	<div id="panel2" class="easyui-panel">

		<form name="queryAlarmFrom" action="">
			<div style="height: 175; margin: 5px; padding-top: 10px;">
				<table
					style="width: 98%; border-collapse: separate; border-spacing: 0px 10px;">
					<tr>
						<td width="10%" align="right">发送者：</td>
						<td width="23%"><input class="ctbt_qf_input" id="msgFrom"
							name="msgFrom" type="text" /></td>
						<td width="10%" align="right">接收者：</td>
						<td width="23%"><input class="ctbt_qf_input" id="msgTo"
							name="msgTo" type="text" /></td>
						<td width="10%" align="right">报警类型：</td>
						<td><input class="ctbt_qf_input" type="text" id="test1">
							<input class="ctbt_qf_input" type="hidden" id="test2"></td>
					</tr>
					<tr>
						<td width="10%" align="right">发送时间起：</td>
						<td width="23%"><input class="ctbt_qf_input" type="text"
							value="" id="sendTime" name="sendTime" /></td>
						<td width="10%" align="right">发送时间止：</td>
						<td width="23%"><input class="ctbt_qf_input" type="text"
							value="" id="sendTime" name="sendTime" /></td>
						<td width="10%"></td>
						<td width="23%"></td>
					</tr>
					<tr>
						<td width="10%" align="right">接收时间起：</td>
						<td width="23%"><input class="ctbt_qf_input" type="text"
							value="" id="recvTime" name="recvTime" /></td>
						<td width="10%" align="right">接收时间止：</td>
						<td width="23%"><input class="ctbt_qf_input" type="text"
							value="" id="recvTime" name="recvTime" /></td>
						<td width="10%"></td>
						<td></td>
					</tr>

					<tr>
						<td colspan="6" align="right" height="30"><input value="查询"
							type="button" onclick="query()" class="ctbt-btn" /> <input
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
</html>