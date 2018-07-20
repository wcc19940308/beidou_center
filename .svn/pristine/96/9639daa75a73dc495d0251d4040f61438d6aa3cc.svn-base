<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt"%>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
    $(function(){	
   		var jsonObj = {};
    	var ValidData ;
		jsonObj.Rows = ${sysDic};
	 	$.ajax({
			url : '${WebUrl}/dic/getDic.ctbt?dicId=10',
			type : "POST",
			async:false,
			success : function(result) {
					ValidData=result
			},
			error : function() {
				$.ligerDialog.alert('服务器异常，请重试！');
			}
		}); 
		 manager = $("#maingrida").ligerGrid({
    		 data:jsonObj,
             columns: [
           /*   { display: '字典编号', name: 'dicId', width: 50, type: 'text',frozen:true }, */
             { display: '字典键', name: 'itemKey',
                 editor: { type: 'text' ,maxValue:'10',}
             },
             { display: '字典值', name: 'itemValue',
                 editor: { type: 'text' }
             },
             { display: '排序号', name: 'itemOrder', width: 50, type: 'int', editor: { type: 'int'},
         	},
             { display: '有效性', width: 50, name: 'validity',type:'text',
                 editor: { type: 'select', data:ValidData,valueField: 'key',textField:'value'},
                 render: function (item)
                 {
            		if (parseInt(item.validity) == 1) return '是';
                     return '否';
                 }                 
             },
             { display: '操作', isSort: false, width: 120, render: function (rowdata, rowindex, value)
                 {
                     var h = "";
                     if (!rowdata._editing)
                     {
                         h += "<a href='javascript:beginEdit(" + rowindex + ")'>修改</a> "; 
                     }
                     else
                     {
                         h += "<a href='javascript:endEdit(" + rowindex + ")'>提交</a> ";
                         h += "<a href='javascript:cancelEdit(" + rowindex + ")'>取消</a> "; 
                     }
                     return h;
                 }
                 }
             ],
             rownumbers:true,
             enabledEdit: true, clickToEdit: false, isScroll: false,   
             width: '100%',
            
         });
    
    	 $.metadata.setType("attr", "validate");
    	 var vaildaty=$("#queryRoleFrom").validate({
    	 	     onfocusout: function(element){
    	 	         $(element).valid();
    	 	     }
    	 	 });  
});
function fresh(){
 	$.ajax({
		url : '${WebUrl}/sysDicItem/tosysDicItemR.ctbt',
		type : "POST",
		data : {
			dicId : ${sysDicItems}
		},
		dataType : 'json',
		success : function(result) {
			var jsonObj = {};
			jsonObj.Rows = result;
			manager.set({
				data : jsonObj
			});
		},
		error : function() {
			$.ligerDialog.alert('服务器异常，请重试！');
		}
	}); 
}

    var states=0;
    function beginEdit(id) { 
        manager.beginEdit(id);
        if(states!=3){
       	 states=1;
       	 }
    }

    function cancelEdit(rowid) { 
        manager.cancelEdit(rowid);
        fresh();
    }
    function validateGrid() {
    	var rows = manager.getSelectedRow();
    		if (rows['itemKey'].length == 0) {
    			$.ligerDialog.warn("输入项[字典键]不能为空，请修改后重新提交");
    			return false;
    		}
    		if (rows['itemKey'].length >10) {
    			$.ligerDialog.warn("输入项[字典键]长度不能超过10，请修改后重新提交");
    			return false;
    		}
    		if (rows['itemValue'].length >20) {
    			$.ligerDialog.warn("输入项[字典值]长度不能超过20，请修改后重新提交");
    			return false;
    		}
    		if (rows['itemOrder'].length >11) {
    			$.ligerDialog.warn("输入项[排序号]长度不能超过11，请修改后重新提交");
    			return false;
    		}    
    	return true;
    }
    function endEdit(rowid,e,editing)
    {   manager.endEdit(rowid);
   	    var row = manager.getSelectedRow();
   		 row["dicId"]=${sysDicItems};
   		 var urls;
        if(states==1){
        	urls='${WebUrl}/sysDicItem/updatesysDicItem.ctbt'
        }
        else if(states==3 || states==2){
        	urls='${WebUrl}/sysDicItem/insert.ctbt' 
        	}
        if(validateGrid()){
            $.ajax({
    	        type: 'post',
    	        url: urls,
    	        data: row,
    	        dataType:'json',
    	        success: function(result) {
    	        	if(result==1){
    	        		states=0;
    	        		$.ligerDialog.alert('保存成功！');
    	        	}
    	        	else{
    	        		$.ligerDialog.alert('与原有键值冲突，请修改后重新提交填写！');
    	        		states=3;
    	        	}	
    	        },
    	        error:function(XMLHttpRequest, textStatus, errorThrown){ 
    	        	$.ligerDialog.alert('请根据提示修改后重新提交填写！');
 	  			} 	
    	    }); 
        }
         else{
			states=3;
       }          
    }
	   function addNewRow()
       {  manager.addEditRow();
           states=2;
       } 

 	   function cancelAllEdit()
       {
		   parent.closeRoleEditWin();
       }
 </script>
<style type="text/css">
td.tx {
	text-align: right;
	width: 120px;
	padding-right: 37px;
}
</style>
</head>
<body>
<div id="maingrida"></div>
<br>
<a class="l-button" style="width:100px;float:left; margin-left:156px;" onclick="addNewRow()">添加</a>
 <a class="l-button" style="width:100px;float:left; margin-left:6px;" onclick="cancelAllEdit()">关闭</a>
</body>
</html>