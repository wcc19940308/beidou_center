<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>

<!DOCTYPE html>
<html>
<head>
<title>船舶管理</title> 
<ctbt:base/>    
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="${WebUrl}/js/queryPage.js" type="text/javascript"></script>

<script src="${WebUrl}/js/base64/voice-2.0.js" type="text/javascript"></script>

<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css">
 <script type="text/javascript">
	//得到所有的数据
	$(function(){
		var a;
        a=window['g'] =
        $("#maingrid").ligerGrid({
            height:'99.5%',
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
            usePager:false,
            rownumbers:true,
        }); 
        <ctbt:permButton ids="100,101,102"/>
        	
        $("#pageloading").hide();	
		 //  fresh();		
		   $("#panel2").ligerPanel({
	            title: '船舶消息查询',
	            width: "100%",
	            height : 145,
	            
	            onToggle : function(isCollapse){
	            	if(isCollapse){
	            		var wh = $(window).height();
	                	g.setHeight(wh-40);
	            	}else{
	            		var wh = $(window).height();
	                	g.setHeight(wh-160);
	            	}      	
	             },
	        });
		 $("#query").click(function(){
			var item=$("#queryRoleFrom").serializeObject();
			item['page']=document.getElementById('pageNum').value;
			item['pageSize']=document.getElementById('pageSize').value;
			if(/^\s+$/gi.test(document.getElementById('cardNo1').value)){
				$.ligerDialog.alert('请正确输入北斗IC卡号1编号');
				return;
			}
			else if(/^\s+$/gi.test(document.getElementById('cardNo2').value))
			{
				$.ligerDialog.alert('请正确输入北斗IC卡号2编号');
				return;
			}
			else{
			$.ajax({
				url : '${WebUrl}/ship/selectByitem.ctbt',
				type : "POST",
				data: item,
				dataType:'json',
				success : function(result) {
					window['g'].set({
						data : result
					});
					document.getElementById("pageNum").value=result['currentPage'];
					$("#recordNum").text(result['recordNum']);
					$("#currentPage").text(result['currentPage']);
					$("#sumPageNum").text(result['sumPageNum']);	
				},
				error:function() { 
					$.ligerDialog.alert("服务器异常，请重试！");
  
			}});
			}
		});		
		 $("#reset").click(function(){
			 ctbt.FormUtil.Clean(document.queryRoleFrom);
			});
		//json化序列化数据
		 $.fn.serializeObject = function()   
		 {   
		    var o = {};   
		    var a = this.serializeArray();   
		    $.each(a, function() {   
		        if (o[this.name]) {   
		            if (!o[this.name].push) {   
		                o[this.name] = [o[this.name]];   
		            }   
		            o[this.name].push(this.value || '');   
		        } else {   
		            o[this.name] = this.value || '';   
		        }   
		    });   
		    return o;   
		 }; 
		 	 
	 });
		    function queryBypage(pageNum,pageSize) {
		    	var item=$("#queryRoleFrom").serializeObject();
				item['page']=document.getElementById('pageNum').value;
				item['pageSize']=document.getElementById('pageSize').value;
				if(/^\s+$/gi.test(document.getElementById('cardNo1').value)){
					$.ligerDialog.alert('请正确输入北斗IC卡号1编号');
					return;
				}
				else if(/^\s+$/gi.test(document.getElementById('cardNo2').value))
				{
					$.ligerDialog.alert('请正确输入北斗IC卡号2编号');
					return;
				}
				else{
				$.ajax({
					url : '${WebUrl}/ship/selectByitem.ctbt',
					type : "POST",
					data: item,
					dataType:'json',
					success : function(result) {
						window['g'].set({
							data : result
						});
						document.getElementById("pageNum").value=result['currentPage'];
						$("#recordNum").text(result['recordNum']);
						$("#currentPage").text(result['currentPage']);
						$("#sumPageNum").text(result['sumPageNum']);	
					},
					error:function() { 
						$.ligerDialog.alert("服务器异常，请重试！");
				}});
				}};

	function fresh(){
		$.ajax({
		url : '${WebUrl}/ship/selectAll.ctbt',
		type : "GET",
		dataType:'json',
		success : function(result) {
			window['g'].set({
				data : result
			});
			document.getElementById("pageNum").value=result['currentPage'];
			$("#recordNum").text(result['recordNum']);
			$("#currentPage").text(result['currentPage']);
			$("#sumPageNum").text(result['sumPageNum']);	
		},
		error:function() { 
			$.ligerDialog.alert("服务器异常，请重试！");
	}});  
	}
    function f_open1() {
    	var row = window['g'].getSelectedRow();
    	   if (!row) {
				$.ligerDialog.alert("请选择要删除的记录！");
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
					$.ligerDialog.alert("删除失败，请重试！");   
			}});  
        });}
    }
     var win1;
        function f_open(item)
        {   var toUrl;
        	if(item.text=="修改"){
        		var row = window['g'].getSelectedRow();
        	    if (!row) {
        	    	$.ligerDialog.alert("请选择要修改的记录！"); 
        	    	return; 
        	    }else{
        	    	var shipId = row.shipId;
        	    	toUrl = '${WebUrl}/ship/toShipEdit.ctbt?shipId='+shipId;
        	    }
        	}
        	else{
        		toUrl = '${WebUrl}/ship/toShipEdit.ctbt';
        	}
         win1 = $.ligerDialog.open({ height: 495, url:toUrl, width: 900, showMax: true, showToggle: true, isResize: true, slide: false }); 
        }
               
        function closeRoleEditWin(){
        	if(win1 != null){
        		win1.close();
        		document.getElementById("query").click();
        	}
        }
       

 </script>   
</head>
<body>
	<div id="panel2" class="easyui-panel" >  
    <form action=""  name="queryRoleFrom" id="queryRoleFrom">
    <div style="height:145;  margin: 5px; padding-top: 10px;">
	<table style="width: 98%;  border-collapse:separate; border-spacing:0px 10px;">
		<tr>
			<td width="10%" align="right">船舶编号：</td>
			<td width="23%"><input id="shipNo" name="shipNo" type="text" class="ctbt_qf_input"/></td>
			<td width="10%" align="right">船东：</td>
			<td width="23%"><input id="shipOwner" name="shipOwner" type="text" class="ctbt_qf_input"/></td>
			<td width="10%" align="right">北斗IC卡号1：</td>
			<td width="23%"><input id="cardNo1" name="cardNo1" type="text" class="ctbt_qf_input"/></td>	
		</tr>
		<tr>
			<td width="10%" align="right">设备序列号1：</td>
			<td width="23%"><input id="serialNo1" name="serialNo1" type="text" class="ctbt_qf_input"/></td>
			<td width="10%" align="right">北斗IC卡号2：</td>
			<td width="23%"><input id="cardNo2" name="cardNo2" type="text" class="ctbt_qf_input"/></td>
			<td width="10%" align="right">设备序列号2：</td>
			<td width="23%"><input id="serialNo2" name="serialNo2" type="text" class="ctbt_qf_input"/></td>	
		</tr>
		<tr>
			<td colspan="6" align="right" height="30">
				<input value="查询" type="button" id="query" class="ctbt-btn" />
				<input value="重置" type="button" id="reset"  class="ctbt-btn" />
			</td>
		</tr>
	</table>	
</div>
 </form>
 </div>
<div class="l-loading" style="display:block" id="pageloading"></div>
 <a class="l-button" style="width:120px;float:left; margin-left:10px; display:none;" onclick="deleteRow()">删除选择的行</a>
 <div class="l-clear"></div>
 <div id="toptoolbar" style="margin-bottom:2px;padding-bottom:10px"></div>
 <div id="maingrid"></div>
<ctbt:pagefootTag  pageNum="1" recordNum="0" currentPage="1"/>
<div style="display:none;">
</div>
</body> 
</html>