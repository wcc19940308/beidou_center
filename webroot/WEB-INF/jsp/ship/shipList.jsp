<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>
<!DOCTYPE html>
<html>
<head>
<title>ctbt同博</title> 
<ctbt:base />    
    <meta name="keywords" content="免费控件,免费UI控件,免费开源UI,免费开源UI控件,免费开源UI框架,可编辑表格,datagrid,editgrid"> 
    <link href="${WebUrl}/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css">
    <link href="${WebUrl}/js/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css">
    <link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css">
    <script src="${WebUrl}/js/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 
    <script src="${WebUrl}/js/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="${WebUrl}/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${WebUrl}/js/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>

    <script src="${WebUrl}/js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="${WebUrl}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
        <script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
        <script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>

        <script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
        <script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>

    <script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>

    
   
</head>
<body>  
   
    <form action=""  name="queryRoleFrom">
    <div style="height:50px; border:solid 1px #cccccc; margin: 5px; padding-top: 10px;">
	<table style="width: 98%;">
		<tr>
			<td width="10%" align="right">船舶编号：</td>
			<td width="23%"><input id="shipno" name="shipno" type="text" style="width: 95%;"/></td>
			<td width="10%" align="right">船东：</td>
			<td width="23%"><input id="shipowner" name="shipowner" type="text" style="width: 95%;"/></td>
			<td width="10%" align="right">北斗IC卡号：</td>
			<td width="23%"><input id="cardno1" name="cardno1" type="text" style="width: 95%;"/></td>
			
		</tr>
		<tr>
			<td colspan="6" align="right" height="30">
				<input value="查询" type="button" id="query"/>
				<input value="重置" type="button" id="reset" />
			</td>
		</tr>
	</table>
	
</div>
 
 </form>
 
<div class="l-loading" style="display:block" id="pageloading"></div>
 <a class="l-button" style="width:120px;float:left; margin-left:10px; display:none;" onclick="deleteRow()">删除选择的行</a>

 <div class="l-clear"></div>
 
    <div id="maingrid"></div>
 
<div style="display:none;">

</div>
</body> 
 <script type="text/javascript">
	//得到所有的数据
	$(function(){
		var a;
        a=window['g'] =
        $("#maingrid").ligerGrid({
            height:'100%',
            columns: [
            { display: '船舶编号', name: 'shipNo', minWidth: 100,align: 'left', width: 100, minWidth: 160 },
            { display: '船名', name: 'shipName', minWidth: 100 },
            { display: '船东', name: 'shipOwner', minWidth: 100 },
            { display: '北斗IC卡号1', name: 'cardNo1', minWidth: 150 },
            { display: '设备序列号1', name: 'serialNo1', minWidth: 150 },
            { display: '北斗IC卡号2', name: 'cardNo2', minWidth: 150 },
            { display: '设备序列号2', name: 'serialNo2', minWidth: 150 },
            { display: 'MMSI', name: 'mmsi', minWidth: 150 },
            { display: '联系方式', name: 'shipOwnerTel', minWidth: 150 },
            
            ], 
            pageSize:30 ,rownumbers:true,
            toolbar: { items: [
            { text: '增加', click: f_open, icon: 'add' },
            { line: true },
            { text: '修改', click: f_open, icon: 'modify' },
            { line: true },
            { text: '删除', click: f_open1, icon:'delete' }
            ]
            }
        });
          
        $("#pageloading").hide();	

		   fresh();		
		
		 $("#query").click(function(){
			var shipno =$("#shipno").val();
			var shipowner =$("#shipowner").val();
			var cardno1 =$("#cardno1").val();
			//console.log(shipno,shipowner,cardno1)
			$.ajax({
				url : '${WebUrl}/ship/selectByitem.ctbt',
				type : "POST",
				data: { shipNo: shipno, shipOwner: shipowner,cardNo1: cardno1},
				dataType:'json',
				success : function(result) {
					var jsonObj = {};
					jsonObj.Rows = result;
					window['g'].set({
						data : jsonObj
					});
				},
				error:function() { 
					alert("服务器异常，请重试！");    
			} 	
			});
			
		});
		
		 $("#reset").click(function(){
			 $("#shipno").val("");
			 $("#shipowner").val("");
			 $("#cardno1").val("");
			 ctbt.FormUtil.Clean(document.queryRoleFrom);
			});
	 });

	
	function fresh(){
		$.ajax({
		url : '${WebUrl}/ship/selectAll.ctbt',
		type : "GET",
		dataType:'json',
		success : function(result) {
			var jsonObj = {};
			jsonObj.Rows = result;
			window['g'].set({
				data : jsonObj
			});
		},
		error:function() { 
			alert("服务器异常，请重试！");    
	} 	
	});  
		}
	

	

    function f_open1() {
    	var row = window['g'].getSelectedRow();
    	   if (!row) {
   	    	alert('请选择要删除的记录！'); 
   	    	return; 
   	    }else{
    	 $.ligerDialog.confirm('确定删除吗?', function (confirm) {
            if (confirm)
            	var shipId=row.shipId;
			$.ajax({
				url : '${WebUrl}/ship/deleteShip.ctbt',
				type : "POST",
				data: { shipId: shipId},
				dataType:'json',
				success : function(result) {
				if(result==1){
					 $.ligerDialog.alert('删除成功');
					 document.getElementById("query").click();
				}
				},
				error:function() { 
					alert("删除失败，请重试！");    
			} 	
			});  
        });}
    }
    
   
        
        
        var win1;
        function f_open(item)
        {   var toUrl;
        	if(item.text=="修改"){
        		var row = window['g'].getSelectedRow();
        	    if (!row) {
        	    	alert('请选择要修改的记录！'); 
        	    	return; 
        	    }else{
        	    	var shipId = row.shipId;
        	    	toUrl = '${WebUrl}/ship/toShipEdit.ctbt?shipId='+shipId;
        	    }
        	}
        	else{
        		toUrl = '${WebUrl}/ship/toShipEdit.ctbt';
        	}

         win1 = $.ligerDialog.open({ height: 450, url:toUrl, width: 840, showMax: true, showToggle: true, showMin: true, isResize: true, slide: false }); 
        }
        
        
        function closeRoleEditWin(){
        	if(win1 != null){
        		win1.close();
        	}
        	fresh();
        }   
 

    </script> <script type="text/javascript">
	//得到所有的数据
	$(function(){
		var a;
        a=window['g'] =
        $("#maingrid").ligerGrid({
            height:'100%',
            columns: [
            { display: '船舶编号', name: 'shipNo', minWidth: 100,align: 'left', width: 100, minWidth: 160 },
            { display: '船名', name: 'shipName', minWidth: 100 },
            { display: '船东', name: 'shipOwner', minWidth: 100 },
            { display: '北斗IC卡号1', name: 'cardNo1', minWidth: 150 },
            { display: '设备序列号1', name: 'serialNo1', minWidth: 150 },
            { display: '北斗IC卡号2', name: 'cardNo2', minWidth: 150 },
            { display: '设备序列号2', name: 'serialNo2', minWidth: 150 },
            { display: 'MMSI', name: 'mmsi', minWidth: 150 },
            { display: '联系方式', name: 'shipOwnerTel', minWidth: 150 },
            
            ], 
            pageSize:30 ,rownumbers:true,
            toolbar: { items: [
            { text: '增加', click: f_open, icon: 'add' },
            { line: true },
            { text: '修改', click: f_open, icon: 'modify' },
            { line: true },
            { text: '删除', click: f_open1, icon:'delete' }
            ]
            }
        });
          
        $("#pageloading").hide();	

		   fresh();		
		
		 $("#query").click(function(){
			var shipno =$("#shipno").val();
			var shipowner =$("#shipowner").val();
			var cardno1 =$("#cardno1").val();
			//console.log(shipno,shipowner,cardno1)
			$.ajax({
				url : '${WebUrl}/ship/selectByitem.ctbt',
				type : "POST",
				data: { shipNo: shipno, shipOwner: shipowner,cardNo1: cardno1},
				dataType:'json',
				success : function(result) {
					var jsonObj = {};
					jsonObj.Rows = result;
					window['g'].set({
						data : jsonObj
					});
				},
				error:function() { 
					alert("服务器异常，请重试！");    
			} 	
			});
			
		});

		 $("#reset").click(function(){
			 ctbt.FormUtil.Clean(document.queryRoleFrom);
			});
	 });

	
	function fresh(){
		$.ajax({
		url : '${WebUrl}/ship/selectAll.ctbt',
		type : "GET",
		dataType:'json',
		success : function(result) {
			var jsonObj = {};
			jsonObj.Rows = result;
			window['g'].set({
				data : jsonObj
			});
		},
		error:function() { 
			alert("服务器异常，请重试！");    
	} 	
	});  
		}

    function f_open1() {
    	var row = window['g'].getSelectedRow();
    	   if (!row) {
   	    	alert('请选择要删除的记录！'); 
   	    	return; 
   	    }else{
    	 $.ligerDialog.confirm('确定删除吗?', function (confirm) {
            if (confirm)
            	var shipId=row.shipId;
			$.ajax({
				url : '${WebUrl}/ship/deleteShip.ctbt',
				type : "POST",
				data: { shipId: shipId},
				dataType:'json',
				success : function(result) {
				if(result==1){
					 $.ligerDialog.alert('删除成功');
					 document.getElementById("query").click();
				}
				},
				error:function() { 
					alert("删除失败，请重试！");    
			} 	
			});  
        });}
    }
    
	    var win1;
        function f_open(item)
        {   var toUrl;
        	if(item.text=="修改"){
        		var row = window['g'].getSelectedRow();
        	    if (!row) {
        	    	alert('请选择要修改的记录！'); 
        	    	return; 
        	    }else{
        	    	var shipId = row.shipId;
        	    	toUrl = '${WebUrl}/ship/toShipEdit.ctbt?shipId='+shipId;
        	    }
        	}
        	else{
        		toUrl = '${WebUrl}/ship/toShipEdit.ctbt';
        	}
         win1 = $.ligerDialog.open({ height: 450, url:toUrl, width: 840, showMax: true, showToggle: true, showMin: true, isResize: true, slide: false }); 
        }

        function closeRoleEditWin(){
        	if(win1 != null){
        		win1.close();
        	}
        }   
     </script>
</html>