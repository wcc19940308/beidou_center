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
                <input type="button" id="choose1" name="choose1" onclick="chooseAll()" value="全选"/>
    			<input type="button" id="choose2" name="choose2" onclick="chooseInvert()" value="全不选"/>
		</div>  	
    	<div style="height:300px; overflow:auto;">               
            <div class="tree"> 
                <ul id="tree1"></ul> 
            </div>
        </div>
        <div  style="margin-top:20px" align="center">
			<input type="button" value="确定" class="ctbt-btn" onclick="sendMsg()"/>
			<input value="关闭" type="button" onclick="closeWin()" class="ctbt-btn">
        </div> 	
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
		var valueStr = doc[0].value;
	//如果to框中已有值，则展示to中的树形结构，且打上勾
	if(valueStr != ""){
		var value = JSON.parse(valueStr);
		treeList = $("#tree1").ligerTree({ 
		     nodeWidth: 200,
		     url: "${WebUrl}/weather/findAll.ctbt?",
		     checkbox: true,
		     idFieldName: 'id', 
		     isExpand: true, 
		     slide: false,
		     enabledCompleteCheckbox:true,
		     onSuccess: function(){
		    	 treeList.selectNode(function(data){
		    		 console.log(data);
						for(var i in value){
							if(value[i].msgTo == data.card_no1){
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
		     url: "${WebUrl}/weather/findAll.ctbt?text="+text,
		     checkbox: true,
		     idFieldName: 'id', 
		     isExpand: true, 
		     slide: false
		 });
	}
});

//全选
function chooseAll(){
	 treeList.selectNode(function(data){	
		return true;
	});
}
//全部取消
function chooseInvert(){
	treeList.selectNode(function(data){
		treeList.cancelSelect(data);
	});
	//treeList.refreshTree();
}

function prove(){
	treeList.selectNode(function(data){
		return true;
	})
}

function sendMsg(){
	var editPage = window.top.edit_page ;
	var nodes = treeList.getChecked();
	var toList = "";
	var insertList = [];
	var jDiv = $(editPage).find("div[id='showUserDiv']");
	jDiv.empty();
	for(var i in nodes){
		console.log(JSON.stringify(nodes[i]));
	}
		for(var i in nodes){
			if(nodes[i].data.children != undefined )
				continue;
			var insertRes = {};

			insertRes.msgType = 1;
			insertRes.msgTo = nodes[i].data.card_no1;
			
			insertList.push(insertRes);
			
			var html = "<div name='userDivBlock' class='userDivBlock' onclick='showUserDivClick(this)' msgTo='"+insertRes.msgTo+"'>";
			html += "<div style='width:70px; overflow: hidden;float: left;'>"+insertRes.msgTo+"</div>";
			html += "<div class='userDivBlockClose' onclick='deleteUserDiv("+insertRes.msgTo+")'></div>";
			html += "</div>";

			jDiv.append(html);
		}
		/* for(var i in insertList){
			console.log(insertList[i]);
		} */			
		//var editPage = window.top.edit_page ;		
		//向隐藏域中放需要插入的数据
		$(editPage).find('input[id="to"]').val(JSON.stringify(insertList));
		$(editPage).find("div[id='ycxg']").html('共'+insertList.length+'个');
		closeWin();		
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
         url: "${WebUrl}/weather/findAll.ctbt?text="+text,
         checkbox: true,
         idFieldName: 'id', 
         isExpand: true, 
         slide: false
     });   
}
</script>