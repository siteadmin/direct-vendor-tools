function HttpAjaxServices()
{
	var currentObject = this;
	
	this.REGISTER_ACCOUNT = APP_CONTEXT+"usersignup";
	this.CHECK_EMAIL = APP_CONTEXT+"checkUserName?username=";
	this.LOGIN_AUTH = APP_CONTEXT+"userlogin";
	this.VALIDATE_DIRECT_SYS = APP_CONTEXT + "validatedirecttransporttestingservice?directEmailAddress=";
	this.REGISTER_DIRECT_SYSTEM = APP_CONTEXT+"directtransporttestingservice";
	this.UPDATE_DIRECT_SYSTEM = APP_CONTEXT+"updatedirecttransporttestingservice";
	this.DELETE_DIRECT_SYSTEM = APP_CONTEXT+"deletedirecttransporttestingservice";
	this.READ_ALL_DIRECT_SYSTEM = APP_CONTEXT+"directtransporttestingservice";
	this.READ_ALL_CERTS = APP_CONTEXT + "readAllCerts?directEndPoint=";
	this.DOWNLOAD_CERT = APP_CONTEXT+"downloadCert";
	this.DELETE_CERT = APP_CONTEXT+"deleteCert?filePath=";
	
	
	this.checkUsername = function(username,callback,freezeScreen,screenFreezeMessage)
	{
		var utility = new Utility();
		if(freezeScreen)
		{
			if(utility.isEmptyString(screenFreezeMessage))
			   screenFreezeMessage =  "Processing. Please wait...";
			   UTILITY.screenFreeze(screenFreezeMessage);
		}
		$.ajax({type:"GET",
			url: currentObject.CHECK_EMAIL+username,
			cache: false,
			datatype : CONSTANTS.DATA_TYP_JSON,
			contentType: CONSTANTS.CONTENT_TYP_JSON,
			success:function (successJson, textStatus, oHTTP){
				callback.fire(successJson); 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown)
			{
				alert("Error Calling Service","Error");
			}
		});
	};
	
	this.registerAccount = function(accountRegisterTO,callback,freezeScreen,screenFreezeMessage)
	{
		var utility = new Utility();
		if(freezeScreen)
		{
			if(utility.isEmptyString(screenFreezeMessage))
			   screenFreezeMessage =  "Processing. Please wait...";
			   UTILITY.screenFreeze(screenFreezeMessage);
		}
		$.ajax({type:"POST",
			data:JSON.stringify(accountRegisterTO),
			url: currentObject.REGISTER_ACCOUNT,
			cache: false,
			datatype : CONSTANTS.DATA_TYP_JSON,
			contentType: CONSTANTS.CONTENT_TYP_JSON,
			success:function (successJson, textStatus, oHTTP){
				callback.fire(successJson); 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown)
			{
				alert("Error Calling Service","Error");
			}
		});
	};
	
	this.login = function(userLoginTO,callback,freezeScreen,screenFreezeMessage)
	{
		var utility = new Utility();
		if(freezeScreen)
		{
			if(utility.isEmptyString(screenFreezeMessage))
			   screenFreezeMessage =  "Processing. Please wait...";
			   UTILITY.screenFreeze(screenFreezeMessage);
		}
		$.ajax({type:"POST",
			data:JSON.stringify(userLoginTO),
			url: currentObject.LOGIN_AUTH,
			cache: false,
			datatype : CONSTANTS.DATA_TYP_JSON,
			contentType: CONSTANTS.CONTENT_TYP_JSON,
			success:function (successJson, textStatus, oHTTP){
				callback.fire(successJson); 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown)
			{
				if(errorThrown == "Unauthorized")
				{
					callback.fire("");	
				}else
				{
					alert("Error Calling Service","Error");	
				}
				
			}
		});
	};
	
	this.validateDirectSystem = function(directEmailAddress,callback,freezeScreen,register)
	{
		var utility = new Utility();
		if(freezeScreen)
		{
			if(utility.isEmptyString(screenFreezeMessage))
			   screenFreezeMessage =  "Processing. Please wait...";
			   UTILITY.screenFreeze(screenFreezeMessage);
		}
		
		$.ajax({type:"GET",
			url: currentObject.VALIDATE_DIRECT_SYS+directEmailAddress,
			cache: false,
			datatype : CONSTANTS.DATA_TYP_JSON,
			contentType: CONSTANTS.CONTENT_TYP_JSON,
			beforeSend: function(xhr){
		        xhr.setRequestHeader("X-Auth-Token", sessionStorage.authToken);
		    },
			success:function (successJson, textStatus, oHTTP){
				callback.fire(successJson); 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown)
			{
				alert("Error Calling Service","Error");
			}
		});
	};
	
	this.registerDirectSystem = function(registerServiceTO,callback,freezeScreen,register)
	{
		var URL = "";
		var utility = new Utility();
		/*var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");*/
		if(freezeScreen)
		{
			if(utility.isEmptyString(screenFreezeMessage))
			   screenFreezeMessage =  "Processing. Please wait...";
			   UTILITY.screenFreeze(screenFreezeMessage);
		}
		
		if(register)
		{
		  URL = currentObject.REGISTER_DIRECT_SYSTEM;	
		}else
		{
			URL = currentObject.UPDATE_DIRECT_SYSTEM;
		}
		$.ajax({type:"POST",
			data:JSON.stringify(registerServiceTO),
			url: URL,
			cache: false,
			datatype : CONSTANTS.DATA_TYP_JSON,
			contentType: CONSTANTS.CONTENT_TYP_JSON,
			beforeSend: function(xhr){
		        xhr.setRequestHeader("X-Auth-Token", sessionStorage.authToken);
		    },
			success:function (successJson, textStatus, oHTTP){
				callback.fire(successJson); 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown)
			{
				alert("Error Calling Service","Error");
			}
		});
	};
	
	
	this.updateDirectSystem = function(registerServiceTO,callback,freezeScreen)
	{
		var utility = new Utility();
		if(freezeScreen)
		{
			if(utility.isEmptyString(screenFreezeMessage))
			   screenFreezeMessage =  "Processing. Please wait...";
			   UTILITY.screenFreeze(screenFreezeMessage);
		}
		
		$.ajax({type:"PUT",
			data:JSON.stringify(registerServiceTO),
			url: currentObject.UPDATE_DIRECT_SYSTEM,
			cache: false,
			datatype : CONSTANTS.DATA_TYP_JSON,
			contentType: CONSTANTS.CONTENT_TYP_JSON,
			beforeSend: function(xhr){
		        xhr.setRequestHeader("X-Auth-Token", sessionStorage.authToken);
		    },
			success:function (successJson, textStatus, oHTTP){
				callback.fire(successJson); 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown)
			{
				alert("Error Calling Service","Error");
			}
		});
	};
	
	this.deleteDirectSystem = function(registerServiceTO,callback,freezeScreen)
	{
		var utility = new Utility();
		if(freezeScreen)
		{
			if(utility.isEmptyString(screenFreezeMessage))
			   screenFreezeMessage =  "Processing. Please wait...";
			   UTILITY.screenFreeze(screenFreezeMessage);
		}
		
		$.ajax({type:"DELETE",
			data:JSON.stringify(registerServiceTO),
			url: currentObject.DELETE_DIRECT_SYSTEM,
			cache: false,
			datatype : CONSTANTS.DATA_TYP_JSON,
			contentType: CONSTANTS.CONTENT_TYP_JSON,
			beforeSend: function(xhr){
		        xhr.setRequestHeader("X-Auth-Token", sessionStorage.authToken);
		    },
			success:function (successJson, textStatus, oHTTP){
				callback.fire(successJson); 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown)
			{
				alert("Error Calling Service","Error");
			}
		});
	};
	
	
	this.readAllDirectSystem = function(callback,freezeScreen,userEmail)
	{
		var utility = new Utility();
		var URL = "";
		if(!utility.isEmptyString(userEmail))
		{
			URL = currentObject.READ_ALL_DIRECT_SYSTEM + "?useremail=" + userEmail;
		}else 
		{
			URL = currentObject.READ_ALL_DIRECT_SYSTEM;
		}
			
		if(freezeScreen)
		{
			if(utility.isEmptyString(screenFreezeMessage))
			   screenFreezeMessage =  "Processing. Please wait...";
			   UTILITY.screenFreeze(screenFreezeMessage);
		}
		$.ajax({type:"GET",
			url: URL,
			cache: false,
			datatype : CONSTANTS.DATA_TYP_JSON,
			contentType: CONSTANTS.CONTENT_TYP_JSON,
			success:function (successJson, textStatus, oHTTP){
				callback.fire(successJson); 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown)
			{
				alert("Error Calling Service","Error");
			}
		});
	};
	
	
	this.readAllCerts = function(callback,freezeScreen,directEndPoint)
	{
		var utility = new Utility();
		if(freezeScreen)
		{
			if(utility.isEmptyString(screenFreezeMessage))
			   screenFreezeMessage =  "Processing. Please wait...";
			   UTILITY.screenFreeze(screenFreezeMessage);
		}
		$.ajax({type:"GET",
			url: currentObject.READ_ALL_CERTS + directEndPoint,
			cache: false,
			datatype : CONSTANTS.DATA_TYP_JSON,
			contentType: CONSTANTS.CONTENT_TYP_JSON,
			success:function (successJson, textStatus, oHTTP){
			if(parseInt(successJson.returnCode)!=0)
			{
				alert(successJson.error.errorMessage,"Error");
				return;
			}
			else
				callback.fire(successJson); 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown)
			{
				alert("Error Calling Service","Error");
			}
		});
	};
	
	this.deleteCerts = function(callback,freezeScreen,filePath)
	{
		var utility = new Utility();
		if(freezeScreen)
		{
			if(utility.isEmptyString(screenFreezeMessage))
			   screenFreezeMessage =  "Processing. Please wait...";
			   UTILITY.screenFreeze(screenFreezeMessage);
		}
		$.ajax({type:"GET",
			url: currentObject.DELETE_CERT+filePath,
			cache: false,
			datatype : CONSTANTS.DATA_TYP_JSON,
			contentType: CONSTANTS.CONTENT_TYP_JSON,
			beforeSend: function(xhr){
		        xhr.setRequestHeader("X-Auth-Token", sessionStorage.authToken);
		    },
			success:function (successJson, textStatus, oHTTP){
			if(parseInt(successJson.returnCode)!=0)
			{
				alert(successJson.error.errorMessage,"Error");
				return;
			}
			else
				callback.fire(successJson); 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown)
			{
				alert("Error Calling Service","Error");
			}
		});
	};
	
	this.downloadFile = function(dataArray)
	{
		$.download(currentObject.DOWNLOAD_CERT,'GET',dataArray);
	};
}
