      function queryFirstPage(){
    	   	var pageNum=document.getElementById('pageNum').value;
        	var pageSizes=$("#pageSize").val();
        	if(pageNum==1){
        		$.ligerDialog.alert('已经是第一页咯');
        	}
        	else{
        		 queryBypage(1,pageSizes);
        	}
       } 
      function queryPrePage(){
          var pageNum=document.getElementById('pageNum').value;
          var preNum=parseInt(pageNum, 10)-1;
          var pageSize=document.getElementById('pageSize').value;
          if(preNum>0){
        	  queryBypage(preNum,pageSize);
          }
          else{
        	  $.ligerDialog.alert('没有上一页咯');
          }
    }      
      function queryByPage(){
    	  if (event.keyCode==13) { //回车键的键值为13,按下回车便进行跳转
	          var pageNum=document.getElementById('pageNum').value;
	          var pageSize=document.getElementById('pageSize').value;
	          var pageNums=$("#sumPageNum").text();
	          var pageNumInt=parseInt(pageNum, 10);
	          var pageNumsInt=parseInt(pageNums, 10);
	          if(pageNumInt<=0||pageNumInt>pageNumsInt){
	        	  $.ligerDialog.alert('请输入正确页数');
	          }
	          else{
	        	  queryBypage(pageNum,pageSize);
	          }
	         
    		}
    } 
     
    function queryNextPage(){
        var pageNum=document.getElementById('pageNum').value;
        var pageNums=$("#sumPageNum").text();
        var sumPageNums=parseInt(pageNums, 10);
        var nextNum=parseInt(pageNum, 10)+1;
        var pageSize=document.getElementById('pageSize').value;
        if(nextNum<=sumPageNums){
      	  queryBypage(nextNum,pageSize);
        }
        else{
      	  $.ligerDialog.alert('没有下一页咯');
        }   
    }
    function queryLastPage(){
    	var pageNum=document.getElementById('pageNum').value;
    	var pageNums=$("#sumPageNum").text();
    	var pageSizes=$("#pageSize").val();
    	if(pageNum==pageNums){
    		$.ligerDialog.alert('已经是最后一页咯');
    	}
    	else{
    		 queryBypage(pageNums,pageSizes);
    	}

  } 
  function pageSizeChange(){
    	var pageSizes=$("#pageSize").val();
    	 queryBypage(1,pageSizes);
  } 