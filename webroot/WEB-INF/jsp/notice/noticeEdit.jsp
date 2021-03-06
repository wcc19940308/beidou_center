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

<form name="sendMsgForm">

<table width="98%">
<tr>
<td width="300">
	<div>
        <input type="text" id="findTxt" name="findTxt" style="width:100px;"/>
        <input type="button" id="findButton" name="findButton" onclick="search()" value="搜索"/> 
        <input type="button" id="choose1" name="choose1" onclick="chooseAll()" value="全选"/>
		<input type="button" id="choose2" name="choose2" onclick="chooseInvert()" value="全不选"/>     
		<input type="button" id="choose3" name="choose3" onclick="expandAll()" value="展开"/>    
	</div>
	
	<div id="shipTree" style="width:300px; height:350px; overflow:scroll; border: 1px solid #ffcccc;">
	</div>
</td>
<td>
	<table style="border-collapse:separate; border-spacing:0px 10px;width:98%;">
		<tr>
	              <td width="10%" align="right">
	                  <label for="from">from：</label>
	              </td>
	              <td width="83%">
	                  <input type="text" id="from" name="from" class="ctbt_qf_input" value="<%=com.ctbt.beidou.base.CommValue.CENTER_CARD%>">
	              </td>
	          </tr>
	          <tr>
	          	<td width="10%"  align="right">
	                  <label for="to">to：</label>
	              </td>
	              <td>
	                  <input type="hidden" id="to" name="to">
	                  <div id="showUserDiv" class="showUserDiv"></div>
	              </td>
	          </tr>
	          <tr>    
	          	<td width="10%"  align="right">
	                  <label for="msg">信息：</label>
	              </td>
	              <td>
	                  <textarea rows="10" cols="50" id="msgTxt" name="msgTxt" class="ctbt_qf_input"></textarea>
	              </td>
	          </tr>
		<tr>
			<td colspan="3" align="center" height="50">
				<input type="button" value="发送"  class="ctbt-btn" onclick="sendMsg()"/>
			</td>
		</tr>
	</table>
</td>
</tr>
</table>


</form>

</body>
</html>

<script type="text/javascript">
var shipTree;

$(function (){
	window.top.edit_page = this;
	
	shipTree = $("#shipTree").ligerTree({ 
	     nodeWidth: 300,
	     url: "${WebUrl}/dic/queryShipTree.ctbt",
	     checkbox: true,
	     idFieldName: 'id',
	     slide: false,
	     isExpand: false, 
	     enabledCompleteCheckbox:true,
	     onSuccess: function(){		    		    
			shipTree.selectNode(function(data){
				//if()
			});
			
			shipTree.refreshTree();
	     }		
	});
	
});

function getChecked(){
    var notes = shipTree.getChecked();
    var text = "";
    for (var i = 0; i < notes.length; i++)
    {
        
        text += notes[i].data.text + ",";
    }
    alert('选择的节点数：' + text);
}

function getSelected(){
    var note = shipTree.getSelected(); 
    alert('选择的是:' + note.data.text);
}

//全选
function chooseAll(){
	 shipTree.selectNode(function(data){	
		return true;
	});
}
//全部取消
function chooseInvert(){
	shipTree.selectNode(function(data){
		shipTree.cancelSelect(data);
	});
	//shipTree.refreshTree();
}

//全部展开
function expandAll(){
	shipTree.expandAll();			
}

function search(){
	text = document.getElementById("findTxt").value;
	text = text;
	console.log(text);
	
}

//最后确定要插入发送的信息
function sendMsg(){
	
	var msg = $("#msgTxt")[0].value;
	if(msg == null || msg == ""){
		alert("发送内容不能为空！");
		return;
	}
	
    var nodes = shipTree.getChecked();
    var nodeAry = new Array();
    var sysRegsionIds = "";
    for (var i = 0; i < nodes.length; i++) {
        var id = nodes[i].data.id;
        var type = nodes[i].data.type;
        if(type != "BdShip"){
        	sysRegsionIds += id+",";
        }
    }
    
    for (var i = 0; i < nodes.length; i++) {
    	var pid = nodes[i].data.pid;
    	if(sysRegsionIds.indexOf(pid+",") >= 0){
    		//如果父节点也处于本次选中的节点，该节点可以丢弃
    		
    	}else{
    		var nd = {};
            nd.id = nodes[i].data.id;
            nd.text = nodes[i].data.text;
            nd.level = nodes[i].data.level;
            nd.pid = nodes[i].data.pid;
            nd.countryId = nodes[i].data.countryId;
            nd.type = nodes[i].data.type;
            nodeAry.push(nd);
    	}
    }
    
//     alert(JSON.stringify(nodeAry));
//     return;
    
    //var dataObj = {"msg":msg, "nodes":nodes};
    var sendData = "msg="+msg+"&nodes="+JSON.stringify(nodeAry);

	$.ajax({
	   type: "POST",
	   url: "${WebUrl}/notice/sendNotice.ctbt",
	   data: sendData,
	   dataType:"json",
	   success: function(listdata){
		    alert("通知信息发送成功");
	   },
	   error: function(error){
		   alert("通知信息发送失败");
	   }
	}); 
}

var dialog = frameElement.dialog;
function closeWin(){
	parent.queryByCondition();
	dialog.close();
	$("#to")[0].val("");
}



function reset(){
	ctbt.FormUtil.Clean(document.to);
}

function showUserDivClick(div){
	var isOn = $(div).attr("isOn");
	var msgTo = $(div).attr("msgTo");
	
	$("#showUserDiv").find("div[name='userDivBlock']").removeClass("userDivBlock2").addClass("userDivBlock").attr("isOn","0");
	
	if(isOn == "1"){
		//当前是选中状态，执行 取消选中操作
		$(div).removeClass("userDivBlock2").addClass("userDivBlock");
		$(div).attr("isOn","0");
	}else{
		//当前是未选中状态，执行 选中操作
		$(div).removeClass("userDivBlock").addClass("userDivBlock2");
		$(div).attr("isOn","1");
	}
}

function deleteUserDiv(msgTo){
	$("#showUserDiv").find("div[name='userDivBlock'][msgTo='"+msgTo+"']").remove();
	var jsonStr = $("#to").val();
	var ary = JSON.parse(jsonStr);

	for(var i=0;i<ary.length;i++){
		var o = ary[i];
		var p = o.msgTo;
		if(p == msgTo){
			ary.splice(i,1);
		}
	}
	
	$("#to").val(JSON.stringify(ary));
	$("#ycxg").html('共'+ary.length+'个');
}


</script>