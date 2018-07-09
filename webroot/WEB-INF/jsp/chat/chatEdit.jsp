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
<div class="box">
                <h2>请选择需要发送信息的用户:</h2>
                 <div class="tree"> 
                    <ul id="tree1"></ul> 
                </div> 
</div>

<table>
			<tr>
                <td>
                    <label for="msg">请输入需要发送的信息：</label>
                </td>
                <td>
                    <input type="textarea" id="msg" name="msg" class="ui-textarea" >
                </td>
            </tr>
</table>
<input type="button" value="提交" id="msgButton" class="l-button l-button-submit" onclick="sendMsg()"/>
</form>

</body>
</html>

<script type="text/javascript">
//渲染树形结构
var treeList;
$(function ()
        { 
           treeList = $("#tree1").ligerTree({ 
                nodeWidth: 200,
                url: "${WebUrl}/chat/findAll.ctbt",
                checkbox: true,
                idFieldName: 'id', 
                isExpand: false, 
                slide: false 
            });            
});
 
function sendMsg(){
	var nodes = treeList.getChecked();
	console.log(nodes)
	var msg = document.getElementById("msg").value;
	console.log(msg);
	var list = [];
		for(var i in nodes){
			if(nodes[i].data.children != undefined )
				continue;
			var res = {};
			res.msg_type = 1;
			res.msg_text = msg;
			res.msg_from = "数据中心";
			res.msg_to = nodes[i].data.IC;
			res.to_phone = nodes[i].data.phone;		
			list.push(res);
		}
		console.log(list);
	$.ajax({
		   type: "POST",
		   url: "${WebUrl}/chat/toInsertMsg.ctbt",
		   //data: JSON.stringify(list),
		   data: {"list":list}, 
		    traditional:true, 
		   dataType:"json",
		   success: function(listdata){
			    alert("发送成功");
		   },
		   error: function(error){
			   alert("error");
		   }
		}); 
}

/* function saveChatEdit(){
	var params = $(document.chatEditForm).serialize();
	alert(params);
	console.log(params);
	alert("before");
	$.ajax({
	   type: "POST",
	   url: "${WebUrl}/chat/saveChatEdit.ctbt",
	   //data: $('#chatEditForm').serializeArray(),
	   data:params,
	   dataType:"json",
	   success: function(ResultView){
		    alert(ResultView);
	   		if(ResultView.flag==1){
	   			var msgId = ResultView.data.msgId;
	   			$("#msgId").val(msgId);
	   			alert("保存成功！");
	   		}else{
	   			alert(ResultView.msg);
	   		}
	   },
	   error: function(XMLHttpRequest, textStatus){
		    alert("error:"+XMLHttpRequest);
		    //alert(textStatus);
		    alert("111");
	   }
	});
} */

function closeDialog(){
	parent.closeRoleEditWin();
}
</script>