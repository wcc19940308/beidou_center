<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>     
<ctbt:base/>

<script src="${WebUrl}/js/jquery-validation/jquery.validate.min.js"></script>
<script src="${WebUrl}/js/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="${WebUrl}/js/jquery-validation/messages_cn.js" type="text/javascript"></script>


</head>
<body>

<form id="alarmEditForm" name="alarmEditForm" action="" class="liger-form" data-validate="{validate: true}">

<table width="500">
	<tr>
		<td width="20%" align="right"><span style="color:red">*</span>接收者1:</td>
		<td width="70%">
		<input id="msgTo" name="msgTo" type="text" class="ui-textbox" validate="{required:true}"  />
		</td>
	</tr>
	<tr>
		<td width="20%" align="right"><span style="color:red">*</span>报警信息:</td>
		<td><textarea rows="5" cols="50" id="msgTxt" name="msgTxt" validate="{required:true}"></textarea></td>
	<tr>
		<td align="center" colspan="2">
			<input value="发送" type="button" onclick="saveAlarmEdit()" class="ctbt-btn">
			<input value="关闭" type="button" onclick="closeDialog()" class="ctbt-btn">
		</td>
	</tr>
</table>
</form>

</body>
</html>



<script type="text/javascript">
$(function ()
{
    $("#alarmEditForm").ligerForm({
        validate: true
    });
});


function saveAlarmEdit(){
	
	var form = liger.get('alarmEditForm');
    if (form.valid()) {
        alert('验证通过');
    }
    else {
        form.showInvalid();
        return;
    }

    alert("112");
    return;
    
	var params = $(document.alarmEditForm).serialize();
	$.ajax({
	   type: "POST",
	   url: "${WebUrl}/alarm/saveAlarmEdit.ctbt",
	   data: params,
	   dataType:"json",
	   success: function(ResultView){
		    //alert('1');
	   		if(ResultView.flag==1){
	   			var msgId = ResultView.data.msgId;
	   			$("#msgId").val(msgId);
	   			alert("发送成功！");
	   		}else{
	   			alert(ResultView.msg);
	   		}
	   },
	   error: function(XMLHttpRequest, textStatus){
		    alert("error:"+XMLHttpRequest);
		    alert(textStatus);
	   }
	});
}

function closeDialog(){
	parent.closeAlarmEditWin();
}




</script>