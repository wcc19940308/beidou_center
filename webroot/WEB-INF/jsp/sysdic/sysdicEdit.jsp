<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt"%>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典管理</title>
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
				$.ligerDialog.alert('服务器异常，请重试！');}
		}); 
		 manager = $("#maingrida").ligerGrid({
    		 data:jsonObj,
             columns: [
             { display: '字典键', name: 'itemKey',
                 editor: { type: 'text' ,maxValue:'10',}
             },
             { display: '字典值', name: 'itemValue',
                 editor: { type: 'text' }
             },
             { display: '排序号', name: 'itemOrder',  type: 'int', editor: { type: 'int'},
         	},
             { display: '有效性', width: 70, name: 'validity',type:'text',
                 editor: { type: 'select', data:ValidData,valueField: 'key',textField:'value'},
                 render: function (item)
                 {
            		if (parseInt(item.validity) == 1) return '是';
                     return '否';
                 }                 
             },
             ],
             rownumbers:true,
             enabledEdit: true, clickToEdit: true, isScroll: false,   
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
			url : '${WebUrl}/sysdic/tosysDicItemR.ctbt',
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
    function getData(rowid,e,editing)
	 { 		
    	var urls='${WebUrl}/sysdic/updatesysDicItem.ctbt'
        if(Ifrepeate()){
            $.ajax({
    	        type: 'post',
    	        url: urls,
    	        data:{ds:JSON.stringify(dataBack())},
    	        dataType:'json',
    	        success: function(result) {
    	        	if(result==1){
    	        		$.ligerDialog.alert('保存成功！');
    	        		fresh();
    	        	}
    	        	else{
    	        		$.ligerDialog.alert('保存失败，请修改后重新提交！');
    	        	}	
    	        },
    	        error:function(XMLHttpRequest, textStatus, errorThrown){ 
    	        	$.ligerDialog.alert('服务器错误，请刷新网页后重新填写');
 	  			} 	
    	    }); 
        }       
    }
    
    //前端判断数据字典键是否有重复与数据有效性检验
    function Ifrepeate()
    { 
    	manager.endEdit();
        var data = manager.getData();
   		var length=data.length;
   		var a = {}
   		for(var k in data){
   			if(data[k].itemKey.length>10){
   				$.ligerDialog.warn("输入项[字典键]长度不能超过10，请修改后重新提交");
    			return false;
   			}
   			if(data[k].itemValue.length>20){
   				$.ligerDialog.warn("输入项[字典值]长度不能超过20，请修改后重新提交");
    			return false;
   			}
   			if(data[k].itemOrder>99999999999){
   				$.ligerDialog.warn("输入项[排序值]长度不能超过11，请修改后重新提交");
    			return false;
   			}
   			a[data[k].itemKey]=0;
   		}
   		for(var k in data){
   			a[data[k].itemKey]=a[data[k].itemKey]+1;
   			if(a[data[k].itemKey]==2){
   			 $.ligerDialog.alert('数据中的字典键有重复值，请修改后保存！');
   				return false;
   			}
   		}
   		return true; 		
    } 
    //返回数据并且格式化数据
    function dataBack()
    { 
        var data = manager.getData();
   		var length=data.length;
   		var json=[];
   		var i=0;
   		for(var k in data){
   			data[k]['dicId']=${sysDicItems};
   			if(data[k]['itemKey']!=""){
   				json[i++]=data[k]
   			}
   		}
   		return json; 		
    }  
    function deleteRow()
    {
    	var row = manager.getSelectedRow();
    	 if(row==null){
    		 $.ligerDialog.alert('请选择行进行取消保存');
    		 manager.endEdit();
    	 }
    	 else{
        manager.deleteSelectedRow();
        }
    } 
	 function addNewRow(){
	  manager.addEditRow();
      } 
 	 function cancelAllEdit() {
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
	 <a class="l-button" style="width:100px;float:left; margin-left:63px;" onclick="addNewRow()">添加</a>
	 <a class="l-button" style="width:100px;float:left; margin-left:6px;" onclick="deleteRow()">取消某行保存</a>
	 <a class="l-button" style="width:100px;float:left; margin-left:6px;" onclick="getData()">保存</a>
	 <a class="l-button" style="width:100px;float:left; margin-left:6px;" onclick="cancelAllEdit()">关闭</a>
</body>
</html>