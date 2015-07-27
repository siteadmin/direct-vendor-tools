
function AccountRegister()
{
	var currentObject = this;
	
	this.checkUserName = function()
	{
		$('#emailExistAlertID').hide();
		$('#emailRegAlertID').hide();
		var callbackFunction = $.Callbacks('once');
		callbackFunction.add(currentObject.checkUserNameSuccessHandler);
		var httpService = new HttpAjaxServices();
		httpService.checkUsername($("#emailAddress").val(), callbackFunction, false);
	};
	
	this.checkUserNameSuccessHandler = function(successJson)
	{
		if(successJson)
		{
			currentObject.registerAccount();
		}else
		{
			$('#emailExistAlertID').show();
		}
	};
	
	
	this.registerAccount = function()
	{
		var accountRegisterTO = new AccountRegisterTO();
		accountRegisterTO.companyName =  $("#company").val();
		/*accountRegisterTO.companyPOC =  $("#companyPoc").val();*/
		accountRegisterTO.firstName =  $("#firstName").val();
		accountRegisterTO.lastName =  $("#lastName").val();
		accountRegisterTO.password =  $("#password").val();
		accountRegisterTO.username =  $("#emailAddress").val();
		
		var callbackFunction = $.Callbacks('once');
		callbackFunction.add(currentObject.registerAccountSuccessHandler);
		var httpService = new HttpAjaxServices();
		httpService.registerAccount(accountRegisterTO,callbackFunction,false);
	};
	
	this.registerAccountSuccessHandler = function(successJson)
	{
			$('#emailRegAlertID').show();
			document.getElementById("signupForm").reset();
	};
}