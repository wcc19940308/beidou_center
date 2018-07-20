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
<table style="border-collapse:separate; border-spacing:0px 10px;">
			<tr>
                <td width="20%"  align="right">
                    <label for="from">from：</label>
                </td>
                <td width="60%">
                    <input type="text" id="from" name="from" class="ctbt_qf_input" value="数据中心">
                </td>
                <td></td>
            </tr>
            <tr>
            	<td width="20%"  align="right">
                    <label for="to">to：</label>
                </td>
                <td width="60%">
                    <input type="hidden" id="to" name="to">
                    <div id="showUserDiv" class="showUserDiv"></div>
                </td>
                <td>
                    <input type="button" onclick="parent.peopleInfo()" value="" class="ctbt-btn-popselect">
                </td>
            </tr>
            <tr>    
            	<td width="20%"  align="right">
                    <label for="msg">信息：</label>
                </td>
                <td width="60%">
                    <textarea rows="10" cols="50" id="msgTxt" name="msgTxt" class="ctbt_qf_input"></textarea>
                </td>
                <td></td>
            </tr>
            <tr>
            		<input type="text" id="hideMsg" hidden="true"/>
            </tr>
			<tr>
				<td colspan="3" align="center">
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
		   url: "${WebUrl}/alarm/toInsertMsg.ctbt",
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


function showUserDivClick(div){
	var isOn = $(div).attr("isOn");
	var phone = $(div).attr("phone");
	
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

function deleteUserDiv(phone){
	$("#showUserDiv").find("div[name='userDivBlock'][phone='"+phone+"']").remove();
	var jsonStr = $("#hideMsg").val();
	alert(jsonStr);
	var ary = JSON.parse(jsonStr);
	alert(ary);
	alert(ary.length);
	for(var i=0,flag=true,len=attrList.length;i<len;flag ? i++ : i){
	    if( attrList[i]&&(attrList[i].attrId=='530'||attrList[i].attrId=='534'||attrList[i].attrId=='9')){
	        attrList.splice(i,1);
	        flag = false;
	    } else {
	        flag = true;
	    }
	}

}

</script>