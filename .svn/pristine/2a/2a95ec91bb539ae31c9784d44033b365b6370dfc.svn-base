<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/ctbt-tag.tld" prefix="ctbt" %>
<%@ taglib uri="/WEB-INF/ctbt-fn.tld" prefix="ctbtfn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<ctbt:base/>
<link href="${WebUrl}/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />

<script src="${WebUrl}/js/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
<script src="${WebUrl}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>

    <script type="text/javascript">
 
    $(function(){

    $("#save").click(function(){
    	var ship=${Bdship.shipId} 
   		 var urls;
	 	 if(ship==0){
			  urls="${WebUrl}/ship/insert.ctbt" 
			  console.log(urls)
		} 
		 else{
			 urls="${WebUrl}/ship/updateShip.ctbt"
		 }  
   		if(validate_add()){
   			var para = $('form').serializeObject() ; 
   			para['province']=$("#province").ligerComboBox().getValue()
   			para['city']=$("#city").ligerComboBox().getValue()
   			para['cityArea']=$("#cityArea").ligerComboBox().getValue()
   			para['country']=$("#country").ligerComboBox().getValue()
   			para['town']=$("#town").ligerComboBox().getValue()
   			para['village']=$("#village").ligerComboBox().getValue()  
   			para['validity']=$("#validity").ligerComboBox().getValue()
   			para['townname']=$("#town").val()
   			para['villagename']=$("#village").val()   			
	    $.ajax({
	    	async : false,
	        type: 'post',
	        url: urls,
	        data: para,
	        dataType:'json',
	        success: function(result) {
	        	parent.fresh();
	        	if(result==1){
	        		alert("保存成功！");}
	        	else{
	        		alert("保存失败！");
	        	}
	        },
			error:function(XMLHttpRequest, textStatus, errorThrown){   
				alert("error"+errorThrown);     
		} 	
	    });
    	}
	    	});
      
    $("#reset").click(function(){
    		 parent.closeRoleEditWin();
    	//ctbt.FormUtil.Clean(document.queryRoleFrom);
    });

	$("#country").ligerComboBox(	
            {   url: "${WebUrl}/dic/queryCountryList.ctbt" ,
            	valueField:'key',
                textField:'value',
                value:${Bdship.country},
          onSelected: function (id) {  
            $.ajax({  
                url: '${WebUrl}/dic/queryProvinceList.ctbt?countryId='+id, 
    	        type: 'post',
    	        dataType:'json',
                success: function(data){
                    liger.get('province').setData(data);   
                }  
            });  
         
    },  
    onButtonClick: function () {  
        classes = null;  
    }  
});  


$("#province").ligerComboBox({       
	url: '${WebUrl}/dic/queryAllProvince.ctbt?regId='+${Bdship.province},                    
    valueField:'key',
    textField:'value',
    value:${Bdship.province},
    onSelected: function (id) {  
        $.ajax({  
            url: '${WebUrl}/dic/queryCityList.ctbt?regId='+id, 
	        type: 'post',
	        dataType:'json',
            success: function(data){      
                liger.get('city').setData(data);   
            }  
        });  
     
},  
    });
  

$("#city").ligerComboBox({  
	url: "${WebUrl}/dic/queryAllCity.ctbt?regId="+${Bdship.city},                     
    valueField:'key',
    textField:'value',
    value:${Bdship.city},
    onSelected: function (id) {  
        $.ajax({  
            url: '${WebUrl}/dic/queryCityAreaList.ctbt?regId='+id, 
	        type: 'post',
	        dataType:'json',
            success: function(data){      
                liger.get('cityArea').setData(data);   
            }  
        });  
     
},  
    });


$("#cityArea").ligerComboBox({       
	url: "${WebUrl}/dic/queryAllCityArea.ctbt?regId="+${Bdship.cityArea},                  
    valueField:'key',
    textField:'value',
    value:${Bdship.cityArea},
    onSelected: function (id) {  
        $.ajax({  
            url: '${WebUrl}/dic/queryTownList.ctbt?regId='+id, 
	        type: 'post',
	        dataType:'json',
            success: function(data){  
                liger.get('town').setData(data);   
            }  
        });  
     
},  
    });
    

$("#town").ligerComboBox({       
	url: "${WebUrl}/dic/queryAllTown.ctbt?regId="+${Bdship.town},                    
    valueField:'key',
    textField:'value',
    value:${Bdship.town},
    autocomplete:false,
    keySupport:true,
    onSelected: function (id) {  
        $.ajax({  
            url: '${WebUrl}/dic/queryVillageList.ctbt?regId='+id, 
	        type: 'post',
	        dataType:'json',
            success: function(data){
                liger.get('village').setData(data);   
            }  
        });  
     
},  
    });    
console.log(${Bdship.village}+'village')
$("#village").ligerComboBox({       
	url: "${WebUrl}/dic/queryAllVillage.ctbt?regId="+${Bdship.village},                  
   // valueFieldID: 'chooceClass',   
    valueField:'key',
    textField:'value',
    value:${Bdship.village},
    autocomplete: false,
    keySupport:true
    }) 
    

  
$("#validity").ligerComboBox(	
            {   url: '${WebUrl}/dic/loadDic.ctbt?dicId='+10 ,
            	valueField:'key',
                textField:'value' ,
                value:${Bdship.validity},
}                       
            );   
    
    
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
    

    //数据有效性检验
	function validate_add() {
		 var para = $('form').serializeObject() ; 
		 if(para['shipNo']==""){
			 alert("船舶编号不能为空");
			 return false; 
		 }
		 if(para['shipName']==""){
			 alert("船舶名不能为空");
			 return false; 
		 }
		 if(para['shipOwner']==""){
			 alert("船东不能为空");
			 return false;
		 }
		 if(para['shipOwner']==""){
			 alert("船东不能为空");
			 return false;
		 }
		 return true;   
	
	}

    </script>
<style type="text/css">
	td.tx
		{
		text-align: right; width: 100px; padding-right: 10px;
		}
</style>
</head>
<body>
	<form action="" onsubmit='return false' name="queryRoleFrom" id="formID">
	<table style="margin-top:20px;">
		<tr style="height:30px;">
			<td class="tx">船舶编号:</td>
			<input  type="hidden" id="shipId" name="shipId" value="${Bdship.shipId}"  />
			<td style="text-align: left; width: 100px;"><input type="text" id="shipNo" name="shipNo" value="${Bdship.shipNo}" /></td>
			<td class="tx"><span style="color:red;font-size:20px">*</span>船舶名称:</td>
			<td style="text-align: left; width: 100px;"><input type="text" id="shipName" name="shipName" value="${Bdship.shipName}"/></td>
			<td class="tx"><span style="color:red;font-size:20px">*</span>船东:</td>
			<td style="text-align: left; width: 100px;"><input type="text" id="shipOwner" name="shipOwner" value="${Bdship.shipOwner}"/></td>
		</tr>
		<tr style="height:30px;">
			<td class="tx">船东身份证:</td>
			<td><input type="text" id="shipOwnerSfz" name="shipOwnerSfz" value="${Bdship.shipOwnerSfz}"/></td>
			<td class="tx">船舶描述:</td>
			<td><input type="text" id="shipDesc" name="shipDesc" value="${Bdship.shipDesc}"/></td>
			<td class="tx">船舶类型:</td>
			<td><input type="text" id="shipType" name="shipType" value="${Bdship.shipType}"/></td>
		</tr>

			<tr style="height:30px;">
			<td class="tx">船长:</td>
			<td><input type="text" id="shipLength" name="shipLength" value="${Bdship.shipLength}"/></td>
			<td class="tx">船宽:</td>
			<td><input type="text" id="shipWidth" name="shipWidth" value="${Bdship.shipWidth}"/></td>
			<td class="tx">吃水:</td>
			<td><input type="text" id="shipWater" name="shipWater" value="${Bdship.shipWater}"/></td>
		</tr>
			<tr style="height:30px;">
			<td class="tx">联系方式:</td>
			<td><input type="text" id="shipOwnerTel" name="shipOwnerTel" value="${Bdship.shipOwnerTel}"/></td>
			<td class="tx">船舶吨位:</td>
			<td><input type="text" id="shipTons" name="shipTons" value="${Bdship.shipTons}"/></td>
			<td class="tx">无线电呼号:</td>
			<td><input type="text" id="radioCallNo" name="radioCallNo" value="${Bdship.radioCallNo}"/></td>
		</tr>
		<tr style="height:30px;">
			<td class="tx">北斗IC卡号1:</td>
			<td><input type="text" id="cardNo1" name="cardNo1" value="${Bdship.cardNo1}"/></td>
			<td class="tx">设备序列号1:</td>
				<td><input type="text" id="serialNo1" name="serialNo1" value="${Bdship.serialNo1}"/></td>
		<td class="tx">类别:</td>
			<td><input type="text" id="typeOther" name="typeOther" value="${Bdship.typeOther}"/></td>
			
		</tr>
		<tr style="height:30px;">
			<td class="tx">北斗IC卡号2:</td>
			<td><input type="text" id="cardNo2" name="cardNo2" value="${Bdship.cardNo2}"/></td>
			<td class="tx">设备序列号2:</td>
			<td><input type="text" id="serialNo2" name="serialNo2" value="${Bdship.serialNo2}"/></td>
			<td class="tx">MMSI:</td>
			<td><input type="text" id="mmsi" name="mmsi" value="${Bdship.mmsi}"/></td>
		</tr>
		
		<tr style="height:30px;">
	
			<td class="tx">国家:</td>
			<td>
        <input id="country" name="country" />
        
 		   </td>
			<td class="tx">省份:</td>
			<td>
			  <input id="province" name="province"/>
			</td>
				<td class="tx">城市:</td>
			<td>
			<input id="city" name="city"/>
			</td>
		</tr>
		<tr style="height:30px;">
	
			<td class="tx">区县:</td>
			<td>
			<input id="cityArea" name="cityArea"/>

			</td>
			<td class="tx">镇/街道:</td>
			<td>
	<input id="town" name="town"/>

</td>
			
			<td class="tx">村/服务站:</td>
			<td>

	 
	 <input id="village" name="village"/>

	
	</td>
			
		</tr>
		<tr style="height:30px;">
					<td class="tx">是否有效:</td>
			<td>
			
			 <input id="validity" name="validity" />

			</td>

	<!-- 	<td class="tx">船舶类型(新):</td>
			<td>
				<select  id="shipTypeNew" name="shipTypeNew">
					<option value="2">Volvo</option>
					<option value="1">Saab</option>
					<option value="1">Fiat</option>
					<option value="1">Audi</option>
				</select>
			</td> 

 		<td class="tx">所属机构id:</td>
			<td>
					<select  id="orgId" name="orgId">
					<option value="2">Volvo</option>
					<option value="1">Saab</option>
					<option value="1">Fiat</option>
					<option value="1">Audi</option>
				</select>
			</td> -->
		</tr>

		<tr>
            <td colspan="6" style="text-align: center; padding-top: 30px; padding-bottom: 30px;">
					   <button type="button" id="save">保存</button>&nbsp;&nbsp;&nbsp;
					   <button type="button"  id="reset" >关闭</button>
					
					   (<span style="color:red;font-size:20px">*</span>为必填) 
            </td>
    
            
         </tr>
		
	</table>
	</form>
</body>
</html>