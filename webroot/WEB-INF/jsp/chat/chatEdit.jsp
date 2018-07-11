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

<script src="${WebUrl}/js/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>

<link href="${WebUrl}/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css">
<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css">

</head>
<body>

<div id="maingrid"></div> 
<form name="sendMsgForm">
<table>
			<tr>
                <td>
                    <label for="from">from：</label>
                </td>
                <td>
                    <input type="text" id="from" name="from" class="ui-textarea" value="数据中心">
                </td>
            </tr>
            <br/>
            <tr>
            	<td>
                    <label for="to">to：</label>
                </td>
                <td>
                    <input type="textarea" id="to" name="to" class="ui-textarea" value="${toList}">
                </td>
                <td>
                    <input type="button" id="find" name="find" onclick="parent.peopleInfo()" value="人员信息">
                </td>
            </tr>
            <br/>
            <tr>    
            	<td>
                    <label for="msg">请输入需要发送的信息：</label>
                </td>
                <td>
                    <input type="textarea" id="msg" name="msg" class="ui-textarea" >
                </td>
            </tr>
</table>
<input type="button" value="确定"  class="ctbt-btn " onclick="sendMsg()"/>
<input type="button" value="关闭"  class="ctbt-btn " onclick="closeWin()"/>
</form>

</body>
</html>

<script type="text/javascript">
var toList = "";
var insertList = "";
//最后确定要插入发送的信息
function sendMsg(){
	var list = 
	console.log(list);
	/* $.ajax({
		   type: "POST",
		   url: "${WebUrl}/chat/toInsertMsg.ctbt",
		   data: {"list":JSON.stringify(list)},
		   dataType:"json",
		   success: function(listdata){
			   console.log(listdata);
			    alert("toInsertMsg发送成功");
		   },
		   error: function(error){
			   alert("toInsertMsg发送失败");
		   }
		});  */
}
function closeWin(){	
	parent.closeChatEditWin();
}

function showSendMsg(){
	console.log("wccccccccccccccccccc");
	console.log(toList);
	console.log(insertList);
	document.getElementById("msg").value = toList;
}

</script>