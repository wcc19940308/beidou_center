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
                <h3>请选择需要发送信息的用户:</h3>
                <input type="text" id="findTxt" name="findTxt" />
                <input type="button" id="findButton" name="findButton" onclick="search()" value="搜索"/>
                 <div class="tree"> 
                    <ul id="tree1"></ul> 
                </div> 
</div>

<input type="button" value="确定" class="ctbt-btn" onclick="sendMsg()"/>
<input value="关闭" type="button" onclick="close()" class="ctbt-btn">
</form>

</body>
</html>

<script type="text/javascript">
//渲染树形结构
var treeList;

//查询的信息
var text = null;
$(function ()
        { 
           treeList = $("#tree1").ligerTree({ 
                nodeWidth: 200,
                url: "${WebUrl}/chat/findAll.ctbt?text="+text,
                checkbox: true,
                idFieldName: 'id', 
                isExpand: false, 
                slide: false
            });   
           
});

var list;
function sendMsg(){
	var nodes = treeList.getChecked();
	console.log(nodes)
	list = [];
		for(var i in nodes){
			if(nodes[i].data.children != undefined )
				continue;
			var res = {};
			res.msgType = 1;
			res.msgFrom = "数据中心";
			res.msgTo = nodes[i].data.IC;
			res.toPhone = nodes[i].data.phone;		
			list.push(res);
		}
		console.log(list);
	/* $.ajax({
		   type: "POST",
		   url: "${WebUrl}/chat/showFromTo.ctbt",
		   data: {"list":JSON.stringify(list)},
		    data: {"list":list}, 
		   traditional:true,  
		   dataType:"json",
		   success: function(listdata){
			   console.log(listdata);
			    alert("发送成功");
		   },
		   error: function(error){
			   alert("error");
		   }
		});  */
}

function close(){
	parent.closeChatSendWin();
}


function search(){
	text = document.getElementById("findTxt").value;
	console.log(text);
	treeList = $("#tree1").ligerTree({ 
         nodeWidth: 200,
         url: "${WebUrl}/chat/findAll.ctbt?text="+text,
         checkbox: true,
         idFieldName: 'id', 
         isExpand: false, 
         slide: false
     });   
}
	
	

</script>