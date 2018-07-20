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
			<tr style="height:30px;">
                <td width="20%"  align="right">
                    <label for="from">from：</label>
                </td>
                <td width="60%">
                    <input type="text" id="from" name="from" class="ctbt_qf_input" value="数据中心">
                </td>
                <td></td>
            </tr>

            <tr style="height:30px;">
            	<td width="20%"  align="right">
                    <label for="to">to：</label>
                </td>
                <td width="60%">
                    <input type="text" id="to" name="to" class="ctbt_qf_input">
                </td>
                <td>
                    <input type="button" onclick="parent.peopleInfo()" value="" class="ctbt-btn-popselect">
                </td>
            </tr>

            <tr style="height:180px;">    
            	<td width="20%"  align="right">
                    <label for="msg">信息：</label>
                </td>
                <td width="60%">
                    <textarea rows="10" cols="50" id="msg" name="msg" class="ctbt_qf_input"  ligeruiid="textbox1"></textarea>
                </td>
                <td></td>
            </tr>
            <tr style="height:30px;">
            		<input type="text" id="hideMsg" hidden="true"/>
            </tr>
			<tr style="height:30px;">
				<td colspan="6" align="center">
					<input type="button" value="确定"  class="ctbt-btn" onclick="sendMsg()"/>
					<input type="button" value="关闭"  class="ctbt-btn" onclick="closeWin()"/>
				</td>
			</tr>
</table>
</form>

</body>
</html>

<script type="text/javascript">
$(function (){
	window.top.edit_page = this;
});
		
//最后确定要插入发送的信息
function sendMsg(){
	var list = document.getElementById("hideMsg").value;
	var msg = $("#msg")[0].value;
	var from = $("#from")[0].value;
	
    var myList = JSON.parse(list);
    for(var i in myList){
    	myList[i].msgTxt = msg;
    	myList[i].msgFrom = from;
    }
	
	$.ajax({
		   type: "POST",
		   url: "${WebUrl}/chat/toInsertMsg.ctbt",
		   data: {"list":JSON.stringify(myList)},
		   dataType:"json",
		   success: function(listdata){
			   console.log(listdata);
			    alert("toInsertMsg发送成功");
			    closeWin();
		   },
		   error: function(error){
			   alert("toInsertMsg发送失败");
		   }
		});   
	
}

var dialog = frameElement.dialog;
function closeWin(){
	dialog.close();
	$("#to")[0].val("");
}


function reset(){
	ctbt.FormUtil.Clean(document.to);
}


</script>