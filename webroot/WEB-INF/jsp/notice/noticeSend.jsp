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
<input value="关闭" type="button" onclick="closeWin()" class="ctbt-btn">
</form>

</body>
</html>

<script type="text/javascript">
//渲染树形结构
var treeList;
//查询的信息
var text = "";
$(function () {
	window.top.send_page = this;
	var editPage = window.top.edit_page ;
	//根据to中是否有内容进行判断	
	//正则表达式，取出电话号码,变成电话1，电话2的形式
		var doc = $(editPage).find('input[id="to"]');
		var value = doc[0].value;
		value = value.split(";");
	//如果to框中已有值，则展示to中的树形结构，且打上勾
	if(value != ""){
		treeList = $("#tree1").ligerTree({ 
		     nodeWidth: 200,
		     url: "${WebUrl}/notice/findAll.ctbt?",
		     checkbox: true,
		     idFieldName: 'id', 
		     isExpand: true, 
		     slide: false,
		     enabledCompleteCheckbox:true,
		     onSuccess: function(){
		    	 treeList.selectNode(function(data){
		    		 console.log(data);
						for(var i in value){
							if(value[i] == data.text){
								return true;
							}
						}  
				});
				treeList.refreshTree();
		     }
	 	});
	}
	//如果第一次加载，to中没有内容，展示整棵树，并且不打勾
	else{
		treeList = $("#tree1").ligerTree({ 
		     nodeWidth: 200,
		     url: "${WebUrl}/notice/findAll.ctbt?text="+text,
		     checkbox: true,
		     idFieldName: 'id', 
		     isExpand: true, 
		     slide: false
		 });
	}
});

function prove(){
	treeList.selectNode(function(data){
		return true;
	})
}

function sendMsg(){
	var nodes = treeList.getChecked();
	var toList = "";
	var insertList = [];
		for(var i in nodes){
			if(nodes[i].data.children != undefined )
				continue;
			var insertRes = {};
			
			toList += nodes[i].data.text+";";
			
			insertRes.msgType = 1;
			insertRes.msgTo = nodes[i].data.IC;
			insertRes.toPhone = nodes[i].data.phone;
			
			insertList.push(insertRes);
		}
		console.log(insertList);		
		var editPage = window.top.edit_page ;
		//to框中显示数据
		$(editPage).find('input[id="to"]').val(toList);
		
		//向隐藏域中放需要插入的数据
		$(editPage).find('input[id="hideMsg"]').val(JSON.stringify(insertList));
		
		closeWin();
		//dialog.close();		
}
var dialog = frameElement.dialog;
function closeWin(){
	parent.closeChatSendWin();
	//dialog.close();
}


function search(){
	text = document.getElementById("findTxt").value;
	text = text;
	console.log(text);
	treeList = $("#tree1").ligerTree({ 
         nodeWidth: 200,
         url: "${WebUrl}/notice/findAll.ctbt?text="+text,
         checkbox: true,
         idFieldName: 'id', 
         isExpand: true, 
         slide: false
     });   
}
</script>