
function AccountRegister()
{
	var currentObject = this;
	
	this.registerAccount = function()
	{
		$('#emailExistAlertID').hide();
		$('#emailRegAlertID').hide();
		var accountRegisterTO = new AccountRegisterTO();
		accountRegisterTO.companyName =  $("#company").val();
		/*accountRegisterTO.companyPOC =  $("#companyPoc").val();*/
		accountRegisterTO.firstName =  $("#firstName").val();
		accountRegisterTO.lastName =  $("#lastName").val();
		accountRegisterTO.password =  $("#password").val();
		accountRegisterTO.email =  $("#emailAddress").val();
		
		var callbackFunction = $.Callbacks('once');
		callbackFunction.add(currentObject.registerAccountSuccessHandler);
		var httpService = new HttpAjaxServices();
		httpService.registerAccount(accountRegisterTO,callbackFunction,false);
	};
	
	this.registerAccountSuccessHandler = function(successJson)
	{
		if(successJson.booleanOutput)
		{
			$('#emailRegAlertID').show();
			document.getElementById("signupForm").reset();
			$('#fieldsRequireID').hide();
			$('#signupForm').hide();
		}else {
			
			$('#emailExistAlertID').show();
		}
		
	};
}