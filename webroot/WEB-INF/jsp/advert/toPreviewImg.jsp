<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt"%>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>广告管理</title>
<ctbt:base />
<link href="${WebUrl}/js/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="${WebUrl}/css/validate.css" rel="stylesheet" type="text/css" />

<script src="${WebUrl}/js/jquery/jquery-1.9.0.min.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/ligerui.all.js"
	type="text/javascript"></script>
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
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerForm.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerComboBox.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/jquery-validation/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/jquery-validation/jquery.metadata.js"
	type="text/javascript"></script>
<script src="${WebUrl}/js/jquery-validation/messages_cn.js"
	type="text/javascript"></script>

<script src="${WebUrl}/js/jquery-validation/tooltip.js"
	type="text/javascript"></script>
<script type="text/javascript">
$(function (){
	var editPage = window.top.edit_page ;
		var doc = $(editPage).find('input[id="file"]');
		var valueStr =  localStorage.getItem("base64");
		var input = document.getElementById("file");
		if(valueStr!=1){
			
		input.innerHTML ="<img id='advBase64'  src='"+valueStr+"' style='width:725px;height:350px;margin-top:10px' alt=''/>"
		}
		else{
			input.innerHTML ="<li id='advBase64'><font color='red'>暂无图片，请选择图片</font></li>"	
		}
		$("#colseSelf").click(function(){
			parent.closeWin2();
		});
});

</script>
</head>
<body>
<div id="file"  style="position:absolute;width:725px;height:400px;">
</div>
<div style="position:absolute;  
top:89%; left:50%;"><input  type="button" id="colseSelf"  value="关闭" style="width:30px;height:20px"/> </div>

</body>

</html>