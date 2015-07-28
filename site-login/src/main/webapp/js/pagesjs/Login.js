function Login()
{
	var currentObject = this;
	
	this.userLogin = function()
	{
		var userLoginTO = new UserLoginTO();
		userLoginTO.username =  $("#userName").val();
		userLoginTO.password =  $("#password").val();
		
		var callbackFunction = $.Callbacks('once');
		callbackFunction.add(currentObject.loginSuccessHandler);
		var httpService = new HttpAjaxServices();
		httpService.login(userLoginTO,callbackFunction,false);
	};
	
	this.loginSuccessHandler = function(successJson)
	{
		if(successJson.userAuthenticated == 'true')
	     {
	    	 $("#closeBtn").trigger("click");
	    	 setRegisterServicePage();
	     }else if (successJson.userAuthenticated == 'false')
	     {
	    	 alert("OOPS wrong credentials");
	     }
	};
}