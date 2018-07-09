$(document).ready(function(){
	//ctbt.ButtonInit();
	//ctbt.FormInit();
	//ctbt.CheckBoxInit();
});

//
String.prototype.trim = function(){   
  return this.replace(/(^\s*)|(\s*$)/g,"");
}

String.prototype.startWith = function(str){ 
	var reg=new RegExp("^"+str);
	return reg.test(this);
}

String.prototype.endWith = function(str){
	var reg=new RegExp(str+"$");
	return reg.test(this);
}

String.prototype.replaceAll = function(s1, s2) {      
    return this.replace(new RegExp(s1, "gm"), s2); //g全局     
}

var ctbt = {};

//实现继承的方法，通过将父对象的属性复制给子对象
ctbt.extend = function(childObj, parentObj){ 
	var p = parentObj.prototype;
	var c = childObj.prototype;
	for (var i in p) {
		c[i] = p[i];
	}
	//c.uber = p;
};

//处理ID中袋“.”的情况,以便jQuery能识别
ctbt.toJqId = function(id){
	var strAry = id.split(".");
	var reg=new RegExp("\\\\$");
	var newId = "";
	for(var k = 0; k < strAry.length; k++){
		if(k < strAry.length - 1){
			if(reg.test(strAry[k])){
				newId += strAry[k]+".";
			}else{
				newId += strAry[k]+"\\.";
			}	
		}else{
			newId += strAry[k];
		}
	}
	
	return newId;
};

ctbt.ButtonInit = function(){
	$(":button,input[type='submit'],input[type='reset']").each(function(){
		var btn = $(this);
		var className = btn.attr("class");
		if(className != null && className.indexOf("_") > -1){
			var names = className.split("_");//btn50_out
			var n = names[0];//btn50
			var current = names[1];//btn50
			
			btn.mouseover(function(){
				if(current != "disabled"){
					btn.attr("class",n+"_over");
				}
			});
			
			btn.mouseout(function(){
				if(current != "disabled"){
					var HpxSelected = btn.attr("HpxSelected");
					if(HpxSelected == "true"){
						//btn.attr("class",n+"_out");
					}else{
						btn.attr("class",n+"_out");	
					}
				}
			});
		}
	});
}

//对所有的CheckBox对象添加默认事件，被点击是，根据勾选情况，设置value 1|0
ctbt.CheckBoxInit = function(){
	$(":input[type='checkbox']").each(function(){
		var jqChk = $(this);
		jqChk.click(function(){
			if(jqChk.attr("checked") == "checked" || jqChk.attr("checked") == "true"){
				jqChk.val("1");
			}else{
				jqChk.val("0");
			}
		});
	});
}

//判断 对象 是否选中
ctbt.IsChecked = function(obj){
	var jqObj = $(obj);
	var checked = false;
	var checkedStr = jqObj.attr("checked");
	if(checkedStr == "true" || checkedStr == "checked"){
		checked = true;
	}
	
	return checked;
}

ctbt.FormInit = function(){
	$(".ValidatorForm").each(function(){
		var form = $(this);
		var els = form.find(":input[label]");
		if(els && els.length > 0) els.bind("change",function(){ctbt.validator.Validate(this)});
	});
}

ctbt.SetSelected = function(btnObj){
	var btn = $(btnObj);
	btn.attr("HpxSelected","true");
	ctbt.SetOver(btnObj);
}

ctbt.SetUnSelected = function(btnObj){
	var btn = $(btnObj);
	btn.attr("HpxSelected","false");
	ctbt.SetOut(btnObj);
}

ctbt.SetOut = function(btnObj){
	var btn = $(btnObj);
	var className = btn.attr("class");
	if(className != null && className.length > 0){
		var nameAry = className.split(" ");
		for(var k = 0; k < nameAry.length; k++){
			if(nameAry[k].endWith("_over")){
				nameAry[k] = nameAry[k].substring(0,nameAry[k].length - 5)+"_out";
			}
		}
		
		var newClass = nameAry.join(" ");
		btn.removeClass().addClass(newClass);
	}
}

ctbt.SetOver = function(btnObj){
	var btn = $(btnObj);
	var className = btn.attr("class");
	if(className != null && className.length > 0){
		var nameAry = className.split(" ");
		for(var k = 0; k < nameAry.length; k++){
			if(nameAry[k].endWith("_out")){
				nameAry[k] = nameAry[k].substring(0,nameAry[k].length - 4)+"_over";
			}
		}
		
		var newClass = nameAry.join(" ");
		btn.removeClass().addClass(newClass);
	}
}

ctbt.SetDisabled = function(btnObj){
	var btn = $(btnObj);
	var className = btn.attr("class");
	if(className != null && className.indexOf("_") > -1){
		var names = className.split("_");//btn50_out
		var n = names[0];//btn50
		btn.attr("class",n+"_disabled");
	}
}

//单个对象的选中 开关效果
ctbt.SingleClick = function(clickObj){
	var jqObj = $(clickObj);
	var state = $.trim(jqObj.attr("HpxSelected"));//当前的状态
	if(state == "true"){
		//由选中状态 改为未选中状态
		ctbt.SetUnSelected(clickObj);
		return false;
	}else{
		//由未选中状态 改为选中状态
		ctbt.SetSelected(clickObj);
		return true;
	}
};

/*
一组对象的单个选中效果
*/
ctbt.GroupClick = function(eventObj){
	var clickObj = $(eventObj);
	//先去掉之前选中的tabBtn的选中效果
	var id = clickObj.attr("id");
	var name = clickObj.attr("name");
	var tagName = clickObj[0].tagName;
	$(document.body).find(tagName+"[name='"+name+"']").each(function(){
		var obj = $(this);
		var objId = obj.attr("id");
		if(objId == id){
			//选中的对象
			ctbt.SetSelected(obj);
		}else{
			//其他设为未选中
			ctbt.SetUnSelected(obj);	
		}
	});
};

/*
清楚一组对象的选中状态
*/
ctbt.GroupClickClear = function(tagName, name){
	$(document.body).find(tagName+"[name='"+name+"']").each(function(){
		var obj = $(this);
		ctbt.SetUnSelected(obj);
	});
};

//从css的背景图片路径，分离出路径、文件名、扩展名 三部分
ctbt.getUrlArgs = function(url){
	var fileName = "";
	var fileType = "";
	
	var loc = url.lastIndexOf("/");
	if(loc > -1){
		fileName = url.substring(loc+1);
		loc = fileName.lastIndexOf(".");
		fileType = fileName.substring(loc+1);
	}
	
	return {name:fileName,type:fileType,size:-1,url:url};
};

ctbt.FormUtil = {
	Fill:function(form,jsondata){
		var jqFormObj = $(form);
		
		var tagName = jqFormObj[0].tagName;
		if(tagName.toLowerCase() == "form" && jsondata){			
			jqFormObj.find('input,textarea,select').each(function(){
				var tagName = this.tagName.toLowerCase();
				var el = jQuery(this);
				var name = el.attr("name")||el.attr("id");
				if(name.indexOf(".") > -1) name = name.substring(name.indexOf(".")+1); 
				if(tagName == "input"){
					var type = el.attr("type").toLowerCase();
					if(type == "button" || type == "submit" || type == "reset"){
						//这几个不处理
					}else if(type == "checkbox"){
						this.checked = (jsondata[name] && jsondata[name] == "1" ? true:false);
					}else if(type == "radio"){
						if(jsondata[name] && jsondata[name] == el.val()){
							this.checked = true;
						}else{
							this.checked = false;
						}
					}else{
						if(jsondata[name]) el.val(jsondata[name]);
						else el.val("");
					}
				}else{
					if(jsondata[name]) el.val(jsondata[name]);
					else el.val("");
				}
			});
		}
	},
	
	//为表单添加一个隐藏属性
	AddHidden:function(form,name,value){
		var jqFormObj = $(form);
		
		var tagName = jqFormObj[0].tagName;
		if(tagName.toLowerCase() == "form" && name && value){
			var hidden = jqFormObj.find("input[type='hidden'][name='"+name+"']");
			if(hidden.length == 0){
				hidden = $("<input type='hidden' name='"+name+"'/>");
				jqFormObj.append(hidden);
			}
			hidden.val(value);
		}
	},
	
	Clean:function(form){
		var jqFormObj = $(form);
		
		var tagName = jqFormObj[0].tagName;
		if(tagName.toLowerCase() == "form"){
			//ctbt.validator.CleanStatus,清除验证过后的一些错误或正确的图标
			//if(ctbt.validator.CleanStatus) ctbt.validator.CleanStatus(form);
			
			var names = "";
			jqFormObj.find('input,textarea,select').each(function(){
				var tagName = this.tagName.toLowerCase();
				var el = jQuery(this);
				if(tagName == "input"){
					var type = el.attr("type").toLowerCase();
					if(type == "button" || type == "submit" || type == "reset"){
						//这几个不处理
					}else if(type == "checkbox"){
						this.value = 0;
						this.checked = false;
					}else if(type == "radio"){
						//默认选中每一组的第一个radio button
						var name = el.attr("name");
						if(names.indexOf(name) > -1){
							//已经存在，说明不是同组的第一个，不选中
							this.checked=false;
						}else{
							//同组第一个，选中	
							this.checked=true;
							names += name+";";
						}
					}else{
						el.val("");
					}
				}else{
					el.val("");
				}
			});
		}
	},
	
	getJsonData:function(form){
		var jqFormObj = $(form);
		var data = "";
		
		var tagName = jqFormObj[0].tagName;
		if(tagName.toLowerCase() == "form"){
			jqFormObj.find('input,textarea,select,radio').each(function(){
				var el = jQuery(this); 
				var fieldName = el.attr('name');
				tagName = el[0].tagName;
				if(tagName.toLowerCase() == "input"){
					if(el.attr("type").toLowerCase() == "checkbox"){
						//checkbox 类型，根据checked==true|false,设置value==1|0;
						data += "'"+fieldName+"':'"+((el.attr("checked") == true) ? "1":"0")+"',";
					}else if(el.attr("type").toLowerCase() == "radio"){
						if(el.attr("checked") == true){
							data += "'"+fieldName+"':'"+ctbt.FormUtil.filter(el.val())+"',";
						}
					}else if(el.attr("type").toLowerCase() == "button"){
						//按钮不作为参数
					}else{
						data += "'"+fieldName+"':'"+ctbt.FormUtil.filter(el.val())+"',";
					}
				}else{
					data += "'"+fieldName+"':'"+ctbt.FormUtil.filter(el.val())+"',";
				}
			});
		}
		
		if(data.endWith(",")){
			data = data.substring(0,data.length - 1);
		}
		
		return eval("({"+data+"})");//返回json数据格式
	},
	
	getParams:function(form){
		var jqFormObj = $(form);
		var params = "";
		
		var tagName = jqFormObj[0].tagName;
		if(tagName.toLowerCase() == "form"){
			jqFormObj.find('input,textarea,select,radio').each(function(){
				var el = jQuery(this); 
				var fieldName = el.attr('name');
				tagName = el[0].tagName;
				if(tagName.toLowerCase() == "input"){
					if(el.attr("type").toLowerCase() == "checkbox"){
						//checkbox 类型，根据checked==true|false,设置value==1|0;
						params += "&"+fieldName+"="+((el.attr("checked") == true) ? "1":"0");
					}else if(el.attr("type").toLowerCase() == "radio"){
						if(el.attr("checked") == true){
							params += "&"+fieldName+"="+ctbt.FormUtil.filter(el.val());
						}
					}else if(el.attr("type").toLowerCase() == "button"){
						//按钮不作为参数
					}else{
						params += "&"+fieldName+"="+ctbt.FormUtil.filter(el.val());
					}
				}else{
					params += "&"+fieldName+"="+ctbt.FormUtil.filter(el.val());
				}
			});
		}
		
		if(params.startWith("&")){
			params = params.substring(1, params.length);
		}
		
		return params;
	},
	
	//过滤特殊字符
	filter:function(str){
		return str;
		/*
		if(str){
			for(var k = 0; k < ctbt.FormUtil.FSCA.length; k++){
				var charAry = ctbt.FormUtil.FSCA[k];
				str = str.replace(new RegExp(charAry[0],"g"), charAry[1]);
			}
			
			return str;
		}else{
			return "";	
		}
		*/
	},
	
	//还原特殊字符
	unfilter:function(str){
		return str;
		/*
		if(str){
			for(var k = 0; k < ctbt.FormUtil.FSCA.length; k++){
				var charAry = ctbt.FormUtil.FSCA[k];
				str = str.replace(new RegExp(charAry[1],"g"), charAry[0]);
			}
			
			return str;
		}else{
			return "";	
		}
		*/
	},
	
	//特殊字符字典表,正则表达式
	FSCA:[
		["\"","<YCSC01>"],
		["\'","<YCSC02>"],
		["\\{","<YCSC03>"],
		["\\}","<YCSC04>"],
		["\\&","<YCSC05>"],
		["%","<YCSC06>"],
		["\\$","<YCSC07>"],
		["@","<YCSC08>"],
		[",","<YCSC09>"],
		["\\\\","<YCSC10>"],
		[";","<YCSC11>"],
		["=","<YCSC12>"],
		["\\+","<YCSC13>"]
	]
};

/*
获得一个内容由sian组成的，长度为length的字符串
*/
ctbt.lengthSign = function(length, sign){
	var len = length*1;
	var str = "";
	for(var k = 0; k < len; k++){
		str += sign;
	}
	
	return str;
};

/*
在一个table中，选中一行后，选中行高亮显示，未选中去除高亮
*/
ctbt.TRSelected = function(rowObj){
	var name = $(rowObj).attr("name");
	//alert(name);
	var rowAry = $("tr[name='"+name+"']");
	//alert(rowAry+""+rowAry.length);
	if(rowAry){
		for(var k = 0; k < rowAry.length; k++){
			var cs = (k+1)%2;
			$(rowAry[k]).attr("HPXSelected","false").children().removeClass().addClass("select"+cs);
		}
	}
	
	$(rowObj).attr("HPXSelected","true").children().removeClass().addClass("select2");
};

//替换 下拉框 的数据
ctbt.ChangeSelectData = function(listObj,dataAry,defValue){
	var jqList = $(listObj);
	jqList.html("");//置空
	if(dataAry && dataAry.length){
		var optionsHtml = "";
		var data = null;
		for(var k = 0; k < dataAry.length; k++){
			data = dataAry[k];
			if(defValue && defValue.length > 0 && defValue == data[0]){
				optionsHtml += "<option value='"+data[0]+"' selected='selected'>"+data[1]+"</option>";
			}else{
				optionsHtml += "<option value='"+data[0]+"'>"+data[1]+"</option>";
			}
		}
		
		jqList.html(optionsHtml);
	}
};

ctbt.Random = function(){
	var date = new Date();
	var no = date.getTime()+Math.floor(Math.random()*100000).toString();
	return no;
};

ctbt.GetStr = function(str, def){
	return str ? str : (def ? def : "");
};

ctbt.GetInt = function(i, d){
	return i ? i*1 : (d ? d*1 : 0);
};

//获得当前时间的
ctbt.CurrentTime = function(){
	var date = new Date();
	var h = date.getHours();
	var m = date.getMinutes();
	var s = date.getSeconds();
	
	if(m < 10) m = "0"+m;
	if(s < 10) s = "0"+s;
	
	var str = h+":"+m+":"+s;
	
	return str;
};

//格式化日期
ctbt.Date2String = function(dateObj,format){
	if(dateObj == null || dateObj == "") return "";
	
	var year = 0;
	var month = 0;
	var day = 0;
	var hour = 0;
	var minute = 0;
	var second = 0;
	var numberReg = new RegExp("^\\d+$");
	if(typeof dateObj == 'object' && dateObj.time){
		//java中的date类型，经json转换后的对象中，有time属性
		var date = new Date();
		date.setTime(dateObj.time);
		year = date.getFullYear();
		month = date.getMonth()+1;
		day = date.getDate();
		hour = date.getHours();
		minute = date.getMinutes();
		second = date.getSeconds();
	}else if((typeof dateObj == 'string' && numberReg.test(dateObj)) || typeof dateObj == 'number'){
		//数字型字符串 或者 数字
		var date = new Date();
		date.setTime(dateObj*1);
		year = date.getFullYear();
		month = date.getMonth()+1;
		day = date.getDate();
		hour = date.getHours();
		minute = date.getMinutes();
		second = date.getSeconds();
	}else if(typeof dateObj == 'string'){
		//yyyy-MM-dd hh:mm:ss 字符串,至少要有yyyy-MM-dd，可以没有hh:mm:ss部分
		var m = dateObj.match(new RegExp("^(\\d{4})[-./](\\d{1,2})[-./](\\d{1,2})[ ]{0,1}(\\d{0,2})[:]{0,1}(\\d{0,2})[:]{0,1}(\\d{0,2})$"));
		if(m != null){
			if(m.length > 1) year = m[1]*1;
			if(m.length > 2) month = m[2]*1;
			if(m.length > 3) day = m[3]*1;
			if(m.length > 4) hour = m[4]*1;
			if(m.length > 5) minute = m[5]*1;
			if(m.length > 6) second = m[6]*1;
		}
	}
	
	year = year < 10 ? "0"+year : year;
	month = month < 10 ? "0"+month : month;
	day = day < 10 ? "0"+day : day;
	hour = hour < 10 ? "0"+hour : hour;
	minute = minute < 10 ? "0"+minute : minute;
	second = second < 10 ? "0"+second : second;
	
	if(format != null && format.toLowerCase() == "datetime"){
		return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
	}else{
		return year+"-"+month+"-"+day
	}
};