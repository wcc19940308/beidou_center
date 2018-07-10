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
<link href="${WebUrl}/js/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css">



    
<script type="text/javascript">
  
        function itemclick(item)
        {
            alert(item.text);
          
        }

        var gd;
        $(function ()
        {
            gd = $("#maingrid").ligerGrid({
                height:'99.5%',
                columns: [
                { display: '发送时间', name: 'sendTime', align: 'left', width: 80, minWidth: 60 },
                { display: '接收时间', name: 'recvTime', minWidth: 60 },
                { display: '报警类型', name: 'msgType', minWidth: 60 },
                { display: '报警信息', name: 'msgTxt', minWidth: 60 },
                { display: '发送者', name: 'msgFrom', minWidth: 60 },
                { display: '接收者', name: 'msgTo', minWidth: 60 },
                { display: '是否接收', name: 'isRecv', minWidth: 30 },
                { display: '接收确认时间', name: 'recvConfirmTime', minWidth: 60 },
                { display: '是否排除', name: 'isExclude', minWidth: 30 },
                { display: '排除确认时间', name: 'excludeConfirmTime', minWidth: 60 },
                
                ], 
                pageSize:20 ,rownumbers:true,
                toolbar: { items: [
                { text: '发送', click: openEdit, icon: 'add' },            
                ]
                }
            });
              
            $("#pageloading").hide();
        });
        

        
        function query(){
        	//gd.url='${WebUrl}/role/queryRoleList.ctbt?role.id=22';
        	//gd.loadServerData('${WebUrl}/role/queryRoleList.ctbt?role.id=22', clause);
        	
        	$.ajax({
        	   type: "POST",
        	   url: "${WebUrl}/alarm/queryAlarmList.ctbt",
        	   //data: params,
        	   dataType:"json",
        	   success: function(listdata){
        		    //alert(listdata);
        		    //var text = JSON.stringify(listdata);;
        		    //alert(text);

        		    var jsonObj = {};
                    jsonObj.Rows = listdata;
        		    gd.set({ data: jsonObj });
        	   }
        	});
        }
        
        
        function clause(aa){
        	alert(clause);
        	alert(aa);
        }
        
        var alarmEditWin;
        function openEdit(item){


        	
            //alert(JSON.stringify(row));
            //alert(row.roleId);
            
        	var toUrl = '${WebUrl}/alarm/toAlarmEdit.ctbt';
        	alarmEditWin = $.ligerDialog.open({title:'发送报警',url:toUrl,height: 500,width: 500});
        	//setTimeout(closeRoleEditWin, 2000);
        }

        
        function closeAlarmEditWin(){
        	if(alarmEditWin != null){
        		alarmEditWin.close();
        	}
        	
        	query();
        }
        
        function reset(){
        	ctbt.FormUtil.Clean(document.queryAlarmFrom);
        }
 
</script>

</head>
<body>

<form name="queryAlarmFrom" action="">
<div style="height:100px; border:solid 1px #cccccc; margin: 5px; padding-top: 10px;">
	<table style="width: 98%;">
		<tr>
			<td width="10%" align="right">发送者：</td>
			<td width="23%"><input id="msgFrom" name="msgFrom" type="text" class="ctbt_qf_input"/></td>
			<td width="10%" align="right">接收者：</td>
			<td width="23%"><input id="msgTo" name="msgTo" type="text" class="ctbt_qf_input"/></td>
			<td width="10%" align="right">报警类型：</td>
			<td>
				<select>
					<option value="chenChuan">沉船</option>
				</select>
			</td>
		</tr>
		<tr style="height:30px;">
			<td width="10%"  align="right">发送时间：</td>
			<td><input type="datetime-local" value="2018-07-04T00:00:00" id="sendTime" name="sendTime"/></td>
			<td width="10%"  align="right">接收时间：</td>
			<td><input type="datetime-local" value="2018-07-04T00:00:00" id="recvTime" name="recvTime"/></td>
		</tr>
		
		<tr>
			<td colspan="6" align="right" height="30">
				<input value="查询" type="button" onclick="query()" class="ctbt-btn"/>
				<input value="重置" type="button" onclick="reset()" class="ctbt-btn"/>
			</td>
		</tr>
	</table>	
</div>
</form>

<div class="l-loading" style="display:block" id="pageloading"></div>
<div class="l-clear"></div>
<div id="maingrid"></div>
<div style="display:none;"></div>
 

</body>
</html>