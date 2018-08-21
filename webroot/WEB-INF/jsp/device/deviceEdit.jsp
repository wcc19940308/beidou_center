<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt"%>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备管理</title>
<ctbt:base />
<link href="${WebUrl}/css/validate.css" rel="stylesheet" type="text/css" />
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
    $(function(){	  
    	 
    	$("#save").click(function(){
    	var ship=${BdDevice.msid} 
   		 var urls;
	 	 if(ship==0){
			  urls="${WebUrl}/device/insertDevice.ctbt" 	  
		} 
		 else{
			 urls="${WebUrl}/device/updateDevice.ctbt"
		 }  
   		var para = $('form').serialize() ;
   		if(vaildaty.form())	{
	    $.ajax({
	    	async : false,
	        type: 'post',
	        url: urls,
	        data: para,
	        dataType:'json',
	        success: function(result) {
	        	if(result==1){
	        		alert('保存成功')
	        		parent.closeRoleEditWin();
	        	}
	        	else{
	        		$.ligerDialog.alert('请检查并重填表项！');
	        	}
	        	
	        },
			error:function(XMLHttpRequest, textStatus, errorThrown){  
				$.ligerDialog.alert('请检查并重填表项！');  
		} 	
	    });}
   		else{
   			$.ligerDialog.alert('请检查并重填表项！');
   		}

	    	});
      
    $("#reset").click(function(){
    	parent.closeRoleEditWin();
    });
    $.metadata.setType("attr", "validate");
    var vaildaty=$("#queryRoleFrom").validate({
    	     onfocusout: function(element){
    	         $(element).valid();
    	         }
    	 });
   
    var siteNo=$("#siteNo").ligerComboBox({       
    	url: '${WebUrl}/dic/getDic.ctbt?dicId=102', 
    	valueField:'key',
        textField:'value',
        value:'${BdDevice.siteNo}',
        width:327,
        }); 
});
 </script>

</head>
<body>
	<form onsubmit='return false' name="queryRoleFrom" id="queryRoleFrom">
		<table style="margin-top: 35px;border-collapse:separate; border-spacing:30px 10px;width:98%;" align="center">
				<tr>
				<td width="30%" align="right">站点编号:</td>
				<td width="50%"><input id="siteNo" name="siteNo" /></td>
				</tr>
			
			<tr>
				<td width="30%" align="right">指挥机名称:</td>
				<input type="hidden" id="msid" name="msid" value="${BdDevice.msid}" />		
				<td width="50%">
				<input type="text" id="masterName" name="masterName" style="width:325px"
					value="${BdDevice.masterName}" validate="{maxlength:10}" /></td>
			</tr>
			<tr>
				<td width="30%" align="right">指挥机序列号:</td>
				<td width="50%">
			<input type="text" id="masterSerialNo" name="masterSerialNo" style="width:325px" value="${BdDevice.masterSerialNo}" validate="{maxlength:20,number:true}"/>		
				</td>
		   </tr>
		   
		   		<tr>
				<td width="30%" align="right"><span
					style="color: red; font-size: 20px">*</span>指挥机北斗IC卡号:</td>
				<td width="50%">
			<input type="text" id="masterCardNo" name="masterCardNo" style="width:325px" value="${BdDevice.masterCardNo}" validate="{required:true,maxlength:6,number:true}"/>		
				
				</td>
		   </tr>
		   
		   		<tr>
				<td width="30%" align="right">一体机序列号:</td>
				<td width="50%">
			<input type="text" id="slaveSerialNo" name="slaveSerialNo" style="width:325px" value="${BdDevice.slaveSerialNo}" validate="{maxlength:20,number:true}"/>		
				
				</td>
		   </tr>
		   		<tr>
				<td width="30%" align="right"><span
					style="color: red; font-size: 20px">*</span>一体机北斗IC卡号:</td>
				<td width="50%">
			<input type="text" id="slaveCardNo" name="slaveCardNo" style="width:325px" value="${BdDevice.slaveCardNo}" validate="{required:true,maxlength:6,number:true}"/>		
				
				</td>
		   </tr>
		   
		   <tr style="height: 30px;" align="right">
				<td class="tx">通播号:</td>
				<td width="50%"><input type="text" id="publicId" name="publicId"
					value="${BdDevice.publicId}" style="width:325px"
					validate="{maxlength:11,number:true}" /></td>
			</tr>
		   		
			<tr>
				<td colspan="6"
					style="text-align: center; padding-top: 10px; padding-left:70px;">
					<button type="button" id="save">保存</button>&nbsp;&nbsp;&nbsp;
					<button type="button" id="reset">关闭</button> (<span
					style="color: red; font-size: 20px">*</span>为必填)
				</td>
			</tr>

		</table>
	</form>
</body>
</html>