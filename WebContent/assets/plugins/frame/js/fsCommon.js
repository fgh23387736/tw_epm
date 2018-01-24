/**
 * @Description: 通用组件
 * @Copyright: 2017 www.fallsea.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.6.3
 * @License：MIT
 */
layui.define(['layer','form','fsConfig'], function (exports) {
	
  var form = layui.form,
    layer = layui.layer,
    fsConfig = layui.fsConfig,
    statusName = $.result(fsConfig,"global.result.statusName","errorNo"),
    msgName = $.result(fsConfig,"global.result.msgName","errorInfo"),
    dataName = $.result(fsConfig,"global.result.dataName","results.data"),
    loadDataType = $.result(fsConfig,"global.loadDataType","0");
  	servletUrl = $.result(fsConfig,"global.servletUrl");
	function isJSON(str) {
	        if (typeof str == 'string') {
	            try {
	                var obj=JSON.parse(str);
	                if(str.indexOf('{')>-1){
	                    return true;
	                }else{
	                    return false;
	                }

	            } catch(e) {
	                console.log(e);
	                return false;
	            }
	        }
	        return false;
	}
  var fsCommon = {

    /**错误msg提示 */
  	errorMsg:function (text) {
      top.layer.msg(text, {icon: 2,time:5000});
    },
    /**成功 msg提示 */
    successMsg:function (text) {
    	top.layer.msg(text, {icon: 1,time:5000});
    },
    /**警告弹出提示*/
    warnMsg:function (text) {
    	top.layer.msg(text, {icon: 0});
    },
    confirm : function (title, text,callBackFunc) {
      layer.confirm(text, {
        title: title,
        resize: false,
        btn: ['确定', '取消'],
        btnAlign: 'c',
        anim:1,
        icon: 3
      },callBackFunc, function () {

      })

    },
    getParameterListFromUrl : function () {
       var url = location.search; //获取url中"?"符后的字串
	   var theRequest = new Object();
	   if (url.indexOf("?") != -1) {
	      var str = url.substr(1);
	      strs = str.split("&");
	      for(var i = 0; i < strs.length; i ++) {
	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
	      }
	   }
	   return theRequest;

    },
    invokeServer : function (funcNo,param,callBackFunc,async)
    {
      var url = "/fsbus/" + funcNo;
      fsCommon.invoke(url, param, callBackFunc,async);
    },
    invoke : function (url,param,callBackFunc,async)
    {
      if(!$.isEmpty(servletUrl)){
        url = servletUrl + url;
      }
      if($.isEmpty(async)){
      	async = true;
      }
    	//打开加载层
    	var index = layer.load();
      $.ajax({
        url: url,
        type: 'post',
        async: async,
        data: param,
        dataType : "json",
        success: function(result,te,status){
        	console.log(status);
        	if(status.status == 204){
        		result = {}
        	}
        	result[statusName] = 0;
	        if(result[statusName] != "0"){
	            var filters = fsConfig["filters"];
	            if(!$.isEmpty(filters)){
	              	var otherFunction = filters[result[statusName]];
	              	if($.isFunction(otherFunction)){
	                	otherFunction(result);
	                	return;
	              	}
	            }
	        }
	        callBackFunc(result);
        },
        error : function (XMLHttpRequest, textStatus, errorThrown)
        {
        	if(isJSON(XMLHttpRequest.responseText)){
        		fsCommon.errorMsg(JSON.parse(XMLHttpRequest.responseText).error);
        	}else{
        		if(textStatus=="404"){
	      		 	fsCommon.errorMsg("请求地址出错!");
	           }else if(textStatus=="302"){  
	          	 fsCommon.errorMsg('连接网页出错!');  
	           }else if(textStatus=="timeout"){  
	          	 fsCommon.errorMsg("请求超时!");  
	           }else{
	          	 fsCommon.errorMsg('请求异常!');  
	           }
        	}
        	
        	 console.error(errorThrown);
        },
        complete : function(XMLHttpRequest, textStatus)
        {
        	//关闭加载层
        	layer.close(index);
        }
      });
            
    },
    //是否需要刷新table,true 需要
    isRefreshTable : function () 
    {
    	var refreshTable=top.$('meta[name="refreshTable"]').attr("content");
    	if(refreshTable == "1")
    	{
    		//刷新后，清空
    		top.$('meta[name="refreshTable"]').attr("content","0");
    		return true;
    	}
    	return false;
    },
    getUrlParam : function ()
    {
    	var param = window.location.search;
	    var pattern = /([^?&=]+)=([^&#]*)/g;
	    var dict = {};
	    var search = null;
	    if (typeof param === "object" && param instanceof Location) {
        search = param.search;
	    }
	    else if (typeof param === "string") {
        search = param;
	    }
	    else {
        throw new Error("参数类型非法！请传入window.loaction对象或者url字符串。");
	    }
	    search.replace(pattern, function (rs, $1, $2) {
        var key = decodeURIComponent($1);
        var value = decodeURIComponent($2);
        dict[key] = value;
        return rs;
	    });
	    return dict;
    },
    /**
     * 设置刷新table状态，1 需要刷新
     */
    setRefreshTable : function (state)
    {
    	var refreshTable = top.$('meta[name="refreshTable"]');
    	if(refreshTable.length==0)
    	{
    		top.$('head').append("<meta name=\"refreshTable\" content=\""+state+"\"/>");
    	}else{
    		refreshTable.attr("content",state);
    	}
    },
    /**
     * 更新form表单数据
     */
    autofill : function (elem,data)
    {
    	if(!$.isEmpty(elem) && !$.isEmpty(data))
    	{
    		$(elem)[0].reset();
    		$(elem).autofill(data);
    		form.render(); //更新全部
    	}
    },
    getUrlPra:function(name){
			var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		  var r = window.location.search.substr(1).match(reg);
		  if(r!=null)return  unescape(r[2]); return null;
		},
    //弹出窗口
    open : function(_title,_width,_height,_url,_end,isMaximize){
    	if($.isEmpty(_width))
  		{
  			_width = "700px";
  		}
  		if($.isEmpty(_height))
  		{
  			_height = "400px";
  		}
  		if(parseInt(_width.replace(/[^0-9]/ig,"")) > $(window.top.document).width()){
  			_width = $(window.top.document).width()+"px";
  		}
  		if(parseInt(_height.replace(/[^0-9]/ig,"")) > $(window.top.document).height()){
  			_height = $(window.top.document).height()+"px";
  		}
  		
    	var index = top.layer.open({
			  type: 2,
			  title:_title,
			  area: [_width, _height],
			  fixed: true, //不固定
			  scrollbar: true,
			  maxmin: true,
			  content: _url,
			  end: _end
  		});
    	if(isMaximize == "1"){
    		layer.full(index);
    	}
    },
    /**
     * 获取token信息
     */
    getToken : function ()
    {
    	var _csrf_code=$('meta[name="_csrf_code"]').attr("content");
      var _csrf_name=$('meta[name="_csrf_name"]').attr("content");
      var token = {};
      token[_csrf_name] = _csrf_code;
      return token;
    },
    /**
     * 按钮事件绑定
     */
    buttionEvent : function (fsType,getDatagrid)
    {
    	var bottion = "";
    	if(fsType == "tree"){//操作树
    		bottion = $("button.fsTree");
    	}else{//默认操作table
    		bottion = $("button:not(.fsTree)");
    	}
    	bottion.on("click",function(event){
				var _this = $(this);
				var _function = _this.attr("function");
				var _funcNo = _this.attr("funcNo");
				var _tableId = _this.attr("tableId");
				switch(_function){
					case "refresh" :
						var obj = getDatagrid(_tableId);
		    		if(!$.isEmpty(obj)){
		    			//刷新
	    		    obj.refresh();
		    		}
					  break;
					case "submit" :
						//提交
		    		//单选判断 //多选判断
		    		if("1" == _this.attr("isSelect") || "1" == _this.attr("isMutiDml"))
		    		{
		    			//获取选中的数据
			    		var data = getDatagrid(_tableId).getCheckData();
			    		if(data.length == 0)
			    		{
			    			fsCommon.warnMsg("请选择需要操作的数据！");
			    			return;
			    		}
			    		if("1" == _this.attr("isSelect") && data.length > 1)
			    		{
			    			fsCommon.warnMsg("请选择一行数据！");
			    			return;
			    		}
		    		}
		    		
		    		function submitForm(){
		    			var url = _this.attr("url");//请求url
	            if($.isEmpty(_funcNo) && $.isEmpty(url)){
	            	fsCommon.warnMsg("功能号或请求地址为空！");
	              return;
	            }
	            if($.isEmpty(url)){
	              url = "/fsbus/" + _funcNo;
	            }
	    				//获取参数
	    				var inputs = _this.attr("inputs");
			    		var param = {};//参数
			    		if(!$.isEmpty(inputs))
			    		{
			    			//获取选中的数据
				    		var data = getDatagrid(_tableId).getCheckData();
				    		param = fsCommon.getParamByInputs(inputs,data);
			    		}
	    				//请求数据
			    		fsCommon.invoke(url,param,function(data)
							{
			        	if(data[statusName] == "0")
			        	{
			        		fsCommon.setRefreshTable("1");
			        		if(!$.isEmpty(getDatagrid(_tableId))){
			        			//刷新
			        			getDatagrid(_tableId).refresh();
			        		}
			        		
			        		fsCommon.successMsg('操作成功!');
			        	}
			        	else
			        	{
			        		//提示错误消息
			        		fsCommon.errorMsg(data[msgName]);
			        	}
							});
		    		}
		    		
		    		
		    		if("1" == _this.attr("isConfirm"))
		    		{
		    			var confirmMsg = _this.attr("confirmMsg");
		    			if($.isEmpty(confirmMsg))
		    			{
		    				confirmMsg="是否确定操作选中的数据?";
		    			}
		    			
		    			fsCommon.confirm("提示", confirmMsg, function(index)
		    			{
		    				layer.close(index);
		    				submitForm();
		    			});
		    		}else{
		    			submitForm();
		    		}
					  break;
					case "close" :
						//关闭
		    		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		    		parent.layer.close(index);
					  break;
					case "openNewWindow" :
						//关闭

		    			var windowUrl = _this.attr("windowUrl"),//请求url
		    			windowWidth = _this.attr("windowWidth"),
		    			windowHeight = _this.attr("windowHeight"),
		    			windowTitle = _this.attr("windowTitle");
		    			fsCommon.open(windowTitle,windowWidth,windowHeight,windowUrl,function(){});
					  break;
					case "query" :
						//查询
		    		var obj = getDatagrid(_tableId);
		    		if(!$.isEmpty(obj)){
		    			var formData = $(this).parentsUntil('form').parent().getFormData();
		    			obj.query(formData);
		    		}
					  break;
					case "top" :

		    		var _url = _this.attr("topUrl");
		    		if($.isEmpty(_url))
		    		{
		    			fsCommon.warnMsg("url地址为空！");
		    			return false;
		    		}
		    		
		    		if("1" == _this.attr("isSelect"))
		    		{
		    			//获取选中的数据
			    		var data = getDatagrid(_tableId).getCheckData();
			    		if(data.length == 0)
			    		{
			    			fsCommon.warnMsg("请选择需要操作的数据！");
			    			return;
			    		}
			    		if(data.length > 1)
			    		{
			    			fsCommon.warnMsg("请选择一行数据！");
			    			return;
			    		}
		    		}
		    		
		    		
		    		var inputs = _this.attr("inputs");
		    		
		    		if(!$.isEmpty(inputs))
		    		{
		    			//获取选中的数据
			    		var data = getDatagrid(_tableId).getCheckData();
			    		_url = fsCommon.getUrlByInputs(_url,inputs,data[0]);
			    		
			    		//处理数据缓存
		          if(loadDataType == "1"){
		          	var uuid = $.uuid();
		          	_url += "&_fsUuid="+uuid;
		          	//缓存选中的数据
		          	$.setSessionStorage(uuid,JSON.stringify(data[0]));
		          }
		    		}
		    		
		    		//弹出的方式
		        var _mode = _this.attr("topMode");
		        if(!$.isEmpty(_mode)){
		        	if(_url.indexOf('?') == -1)
		  				{
		  					_url +="?";
		  				}else{
		  					_url +="&";
		  				}
		        	_url += "_mode="+_mode;
		        }
		    		
		    		var _title = _this.attr("topTitle");
		    		var _width = _this.attr("topWidth");
		    		var _height = _this.attr("topHeight");
		    		
		    		var isMaximize = _this.attr("isMaximize");
		    		
	  			  fsCommon.open(_title,_width,_height,_url,function(){
		    			if(fsCommon.isRefreshTable()){
						  	getDatagrid(_tableId).refresh();
						  }
	    			},isMaximize);
						break;
					case "upload" :

		    		var _title = "上传附件";
		    		var _width = "400px";
		    		var _height = "280px";
		    		var _url = $.result(fsConfig,"global.uploadHtmlUrl","/plugins/frame/views/upload.html");
		    		
		    		var inputs = _this.attr("inputs");
		    		
		    		if(!$.isEmpty(inputs))
		    		{
			    		 _url = fsCommon.getUrlByInputs(_url,inputs,null);
		    		}
		    		
		    		var fileParam = {};
		    		if(!$.isEmpty(_this.attr("fileAccept"))){
		    			fileParam["fileAccept"] = _this.attr("fileAccept");
		    		}
		    		if(!$.isEmpty(_this.attr("fileExts"))){
		    			fileParam["fileExts"] = _this.attr("fileExts");
		    		}
		    		if(!$.isEmpty(_this.attr("fileSize"))){
		    			fileParam["fileSize"] = _this.attr("fileSize");
		    		}
		    		
		    		if(!$.isEmpty(fileParam)){
		    			if(_url.indexOf('?') == -1)
		    			{
		    				_url +="?";
		    			}else {
		    				_url +="&";
		    			}
		    			_url += "fileParam="+ escape(JSON.stringify(fileParam));
		    		}
		    		
		    		fsCommon.open(_title,_width,_height,_url,function(){
		    			var uploadFilePath = top.$('meta[name="uploadFilePath"]').attr("content");
					  	
					  	if(!$.isEmpty(uploadFilePath)){
					  		if(!$.isEmpty(_this.attr("fileElem"))){
					  			$(_this.attr("fileElem")).val(uploadFilePath);
					  		}
					  	}
	    			});
		    		
					  break;
					case "addRow" :
						getDatagrid(_tableId).addRow();
					  break;
					case "save" :
						
						var groupId = _this.attr("groupId");//分组id
						
						if($.isEmpty(groupId)){
							fsCommon.warnMsg("未配置分组id!");
							return;
						}
						var fsFormData = {};//form表单数据
						var isFsForm = false;//是否有form表单
						var fsTableData = [];//数据表格数据
						var isFsTable = false;//是否有table表格
						
						var isFsVerifyForm = true;
						
						$("table.fsDatagrid,form").each(function(index,elem){
							var _groupId = $(this).attr("groupId");
							if(!$.isEmpty(_groupId) && groupId == _groupId){
								if("FORM" == elem.tagName.toUpperCase()){
									var isVerify=form.verifyForm($(this));
									if(isVerify != false){//验证通过
										//获取form表单数据
										var formData = $(this).getFormData();
										$.extend(fsFormData,formData);
										isFsForm = true;
									}else{
										isFsVerifyForm = false;
										return false;
									}
								}else if("TABLE" == elem.tagName.toUpperCase()){
									var data = getDatagrid(elem.id).getData();
									$.extend(fsTableData,data);
									isFsTable = true;
								}
							}
						});
						
						if(!isFsVerifyForm){
							return;
						}
						
						function submitFormSave(){
		    			var url = _this.attr("url");//请求url
	            
	            if($.isEmpty(_funcNo) && $.isEmpty(url)){
	            	fsCommon.warnMsg("功能号或请求地址为空！");
	              return;
	            }
	            if($.isEmpty(url)){
	              url = "/fsbus/" + _funcNo;
	            }
	    				//获取参数
	    				var inputs = _this.attr("inputs");
			    		var param = {};//参数
							
			    		if(!$.isEmpty(inputs))
			    		{
				    		param = fsCommon.getParamByInputs(inputs);
			    		}
			    		if(isFsForm){
								param["fsFormData"] = encodeURIComponent(JSON.stringify(fsFormData));
							}
							if(isFsTable){
								param["fsTableData"] = encodeURIComponent(JSON.stringify(fsTableData));
							}
	    				
	    				//请求数据
			    		fsCommon.invoke(url,param,function(data)
							{
			        	if(data[statusName] == "0")
			        	{
			        		fsCommon.setRefreshTable("1");
			        		fsCommon.successMsg('操作成功!');
			        		
			        		//是否自动关闭，默认是
			        		if(_this.attr("isClose") != "0"){
			        			parent.layer.close(parent.layer.getFrameIndex(window.name));
			        		}
			        	}
			        	else
			        	{
			        		//提示错误消息
			        		fsCommon.errorMsg(data[msgName]);
			        	}
							});
		    		}
		    		
		    		if("1" == _this.attr("isConfirm"))
		    		{
		    			var confirmMsg = _this.attr("confirmMsg");
		    			if($.isEmpty(confirmMsg))
		    			{
		    				confirmMsg="是否确定操作选中的数据?";
		    			}
		    			
		    			fsCommon.confirm("提示", confirmMsg, function(index)
		    			{
		    				layer.close(index);
		    				submitFormSave();
		    			});
		    		}else{
		    			submitFormSave();
		    		}
					  break;
					default:;
				}
    	});
    },
    /**获取参数对象**/
    getParamByInputs : function(inputs,data){
    	var param = {};//参数
    	if(!$.isEmpty(inputs)){
				var inputArr = inputs.split(',');
				$.each(inputArr,function(i,value) {
					var paramArr = value.split(':',2);
					if(!$.isEmpty(paramArr[0]))
					{
						//获取参数值，如果值为空，获取datagrid选中行数据
						var _vaule = paramArr[1];
						
						if($.isEmpty(_vaule))
						{
							//多结果集,分割
							var newValue = "";
							if(!$.isEmpty(data)){
								//如果多选，获取多选数据
								$(data).each(function(index,dom)
								{
									if(!$.isEmpty(newValue))
									{
										newValue += "+";
									}
									var __value = dom[paramArr[0]];
									if($.isEmpty(__value)){
										__value="";
									}
									newValue += __value;
								});
							}
							
				    		_vaule = newValue;
						}else if($.startsWith(_vaule,"$")){
						    var xxxx = _vaule.substring(1);
						    //多结果集,分割
				            var newValue = "";
				            if(!$.isEmpty(data)){
				            	//如果多选，获取多选数据
					            $(data).each(function(index,dom)
					            {
					              if(!$.isEmpty(newValue))
					              {
					                newValue += "+";
					              }
					              var __value = dom[xxxx];
												if($.isEmpty(__value)){
													__value="";
												}
					              newValue += __value;
					            });
				            }
			            	_vaule = newValue;
				        }else if($.startsWith(_vaule,"#")){
				            _vaule = $(_vaule).val();
				        }
						if($.isEmpty(_vaule)){
							_vaule="";
						}
						param[paramArr[0]] = _vaule;
					}
				});
			}
    	return param;
    },
    /**获取参数对象**/
    getParamByInputsAndKey : function(inputs,data,key){
    	var param = {};//参数
    	if(!$.isEmpty(inputs)){
				var inputArr = inputs.split(',');
				$.each(inputArr,function(i,value) {
					var paramArr = value.split(':',2);
					if(!$.isEmpty(paramArr[0]))
					{
						//获取参数值，如果值为空，获取datagrid选中行数据
						var _vaule = paramArr[1];
						
						if($.isEmpty(_vaule))
						{
							//多结果集,分割
							var newValue = "";
							if(!$.isEmpty(data)){
								//如果多选，获取多选数据
								$(data).each(function(index,dom)
								{
									if(!$.isEmpty(newValue))
									{
										newValue += "+";
									}
									var __value = dom[key];
									if($.isEmpty(__value)){
										__value="";
									}
									newValue += __value;
								});
							}
							
				    		_vaule = newValue;
						}else if($.startsWith(_vaule,"$")){
					    var xxxx = key;
					    //多结果集,分割
	            var newValue = "";
	            if(!$.isEmpty(data)){
	            	//如果多选，获取多选数据
		            $(data).each(function(index,dom)
		            {
		              if(!$.isEmpty(newValue))
		              {
		                newValue += "+";
		              }
		              var __value = dom[xxxx];
									if($.isEmpty(__value)){
										__value="";
									}
		              newValue += __value;
		            });
	            }
	            _vaule = newValue;
	          }else if($.startsWith(_vaule,"#")){
	            _vaule = $(_vaule).val();
	          }
						if($.isEmpty(_vaule)){
							_vaule="";
						}
						param[paramArr[0]] = _vaule;
					}
				});
			}
    	return param;
    },
    /**返回url组装参数**/
    getUrlByInputs : function(_url,inputs,data){
			if(!$.isEmpty(inputs))
			{
	      var urlStr = "";
				var inputArr = inputs.split(',');
	      $.each(inputArr,function(i,value) {
					var paramArr = value.split(':',2);
					if(!$.isEmpty(paramArr[0]))
					{
						if(!$.isEmpty(urlStr))
						{
							urlStr += '&';
						}
						
						//获取参数值，如果值为空，获取datagrid选中行数据
						var _vaule = paramArr[1];
						if($.isEmpty(_vaule))//如果值为空或者值是#/$开头   $取参数，#取选择器
  					{
			    		_vaule = data[paramArr[0]];
  					}else if($.startsWith(_vaule,"$")){
  						_vaule = data[_vaule.substring(1)];
  					}else if($.startsWith(_vaule,"#")){
  						_vaule = $(_vaule).val();
  					}
					
						if(!$.isEmpty(_vaule)){
							urlStr += paramArr[0] + "=" + _vaule;
						}
					}
	      });
				if(_url.indexOf('?') == -1)
				{
					_url +="?";
				}
				_url += urlStr;
			}
			return _url;
    },
    /**返回Id组装参数**/
    getIdByInputs : function(inputs,data){
			if(!$.isEmpty(inputs))
			{
	      var urlStr = "";
				var inputArr = inputs.split(',');
	      $.each(inputArr,function(i,value) {
					var paramArr = value.split(':',2);
					if(!$.isEmpty(paramArr[0]))
					{
						if(!$.isEmpty(urlStr))
						{
							urlStr += '&';
						}
						
						//获取参数值，如果值为空，获取datagrid选中行数据
						var _vaule = paramArr[1];
						if($.isEmpty(_vaule))//如果值为空或者值是#/$开头   $取参数，#取选择器
  					{
			    		_vaule = data[paramArr[0]];
  					}else if($.startsWith(_vaule,"$")){
  						_vaule = data[_vaule.substring(1)];
  					}else if($.startsWith(_vaule,"#")){
  						_vaule = $(_vaule).val();
  					}
					
						if(!$.isEmpty(_vaule)){
							urlStr +=  _vaule;
						}
					}
	      });
				 
			}
			return urlStr;
    },
    /**退出*/
    logOut: function (title, text, url, type, dataType, data, callback) {
        parent.layer.confirm(text, {
          title: title,
          resize: false,
          btn: ['确定退出系统', '不，我点错了！'],
          btnAlign: 'c',
          icon: 3
        }, function () {
          location.href = url
        }, function () {
           
        })
    }
  };
  exports('fsCommon', fsCommon);
})



