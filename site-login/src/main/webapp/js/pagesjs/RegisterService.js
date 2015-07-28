function RegisterService()
{
	var currentObject = this;
	this.resultSet = [];
	this.allCerts = [];
	this.editingSystemID = 0;
	this.directEndPoint = "";
	this.selectedFile = "";
	this.UPLOAD_FILE = APP_CONTEXT + "uploadCert?directEndPoint=";
	
	this.validateService = function()
	{
		$('#directEmailExistAlertID').hide();
		var callbackFunction = $.Callbacks('once');
		callbackFunction.add(currentObject.validateServiceSuccessHandler);
		var httpService = new HttpAjaxServices();
		httpService.validateDirectSystem($("#directEmail").val(), callbackFunction, false);
	};
	
	this.validateServiceSuccessHandler = function(successJson)
	{
		if(successJson)
		{
			currentObject.registerService();
			
		}else 
		{
			$('#directEmailExistAlertID').show();
		}
	};
	
	this.registerService = function()
	{
		var callbackFunction = $.Callbacks('once');
		callbackFunction.add(currentObject.registerServiceSuccessHandler);
		var httpService = new HttpAjaxServices();
		httpService.registerDirectSystem(currentObject.readValues(), callbackFunction, false,true);
	};
	
	this.readValues = function()
	{
		var registerServiceTO = new RegisterServiceTO();
		registerServiceTO.cehrtLabel =  $("#cehrtLabel").val();
		registerServiceTO.organizationName =  $("#orgName").val();
		registerServiceTO.directEmailAddress =  $("#directEmail").val();
		registerServiceTO.pointOfContact =  $("#pocEmail").val();
		registerServiceTO.pocFirstName =  $("#pocFirstName").val();
		registerServiceTO.pocLastName =  $("#pocLastName").val();
		registerServiceTO.timezone =  $("#timezone").val();
		registerServiceTO.directTrustMembership =  $("#registerForm input[type='radio']:checked").val();
		registerServiceTO.availFromDate =  $("#availFromDate").val();
		registerServiceTO.availToDate =  $("#availToDate").val();
		registerServiceTO.id = currentObject.editingSystemID;
		registerServiceTO.userEmailAddress = MODEL.userEmail;
		return registerServiceTO;
	};
	
	this.updateService = function()
	{
		var callbackFunction = $.Callbacks('once');
		callbackFunction.add(currentObject.updateServiceSuccessHandler);
		var httpService = new HttpAjaxServices();
		httpService.updateDirectSystem(currentObject.readValues(), callbackFunction, false);
	};
	
	this.registerServiceSuccessHandler = function(successJson)
	{
			$("#vendorReg").show();
			$('#registrationModal').modal('hide');
			$('#DirectSystemRegAlertID').show();
			registerService.readUserDirectSystems();
	};
	
	this.updateServiceSuccessHandler = function(successJson)
	{
		if(successJson)
		{
			$("#vendorReg").show();
			$('#registrationModal').modal('hide');
			$('#updateSystemAlertID').show();
			registerService.readUserDirectSystems();
		}
	};
	
	this.readAllDirectSystems = function()
	{
		var callbackFunction = $.Callbacks('once');
		callbackFunction.add(currentObject.readAllDirectSystemsSuccessHandler);
		var httpService = new HttpAjaxServices();
		httpService.readAllDirectSystem(callbackFunction, false,"");
	};
	
	this.readAllDirectSystemsSuccessHandler = function(successJson)
	{
		var resultArray = successJson;
		currentObject.resultSet = resultArray;
		var rows = "";
		 $(resultArray).each(function(){
		   rows += 	"<tr><td>"+this.cehrtLabel+"</td>"
			+	"<td>"+this.organizationName+"</td>"
			+	"<td>"+this.directEmailAddress+"</td>"
			+	"<td>"+this.pocFirstName + " " + this.pocLastName + " (" +this.pointOfContact +")</td>"
			+	"<td>"+this.availFromDate + " to " +this.availToDate +"</td>"
			+	"<td style='text-align: center;'>"+this.directTrustMembership+"</td>"
			+   "<td style='text-align: center;'>" +
					"<a href='#'  style='text-decoration: none;' onclick = registerService.onInteropAttachClick(this)> " +
							"<span class='glyphicon glyphicon-paperclip'></span> <span hidden='true'>t</span></a></td>"
			+ "</tr>";
		 });
		 
		 $("#tableBody").append(rows);
	};
	
	this.onInteropAttachClick = function(object)
	{
		currentObject.directEndPoint = $(object).closest('tr').find('td:eq(2)').text();
		currentObject.readAllInteropCerts(currentObject.directEndPoint);
	};
	
	this.readAllInteropCerts = function(directEndPoint)
	{
		var callbackFunction = $.Callbacks('once');
		callbackFunction.add(currentObject.interopCertsSuccessHandler);
		var httpService = new HttpAjaxServices();
		httpService.readAllCerts(callbackFunction, false,directEndPoint);
	};
	
	this.interopCertsSuccessHandler = function(successJson)
	{
		$("#certTableBodyId").empty();
		var resultArray = successJson.resultSet.results;
		currentObject.allCerts = resultArray;
		var rows = "";
		 $(resultArray).each(function(){
		   rows += 	"<tr><td><a href='#'  style='text-decoration: none;' onclick = registerService.downloadFile(this) >" +this.certFile + "</a></td>"
		   +	"<td>"+currentObject.convertDateToLocal(this.uploadedTimeStamp) + "</td>"
			+ "</tr>";
		 });
		 
		 $("#certTableBodyId").append(rows);
		 $('#interopCertModel').modal('show');
	};
	
	this.readUserDirectSystems = function()
	{
		var callbackFunction = $.Callbacks('once');
		callbackFunction.add(currentObject.readUserDirectSystemsSuccessHandler);
		var httpService = new HttpAjaxServices();
		httpService.readAllDirectSystem(callbackFunction, false,MODEL.userEmail);
	};
	
	this.readUserDirectSystemsSuccessHandler = function(successJson)
	{
		$("#userDirectSysTableBody").empty();
		var resultArray = successJson;
		currentObject.resultSet = resultArray;
		var rows = "";
		 $(resultArray).each(function(){
		   rows += 	"<tr><td><a href='#'  style='text-decoration: none;' onclick =registerService.onEditClick(this)> " +
		   			"<span class='glyphicon glyphicon-edit'></span> <span hidden='true'>t</span></a>&nbsp;&nbsp;"+this.cehrtLabel+"</td>"
			+	"<td>"+this.organizationName+"</td>"
			+	"<td>"+this.directEmailAddress+"</td>"
			+	"<td>"+this.pocFirstName + " " + this.pocLastName + " (" +this.pointOfContact +")</td>"
			+	"<td>"+this.availFromDate + " to " +this.availToDate +"</td>"
			+	"<td style='text-align: center;'>"+this.directTrustMembership+"</td>"
			+   "<td style='text-align: center;'><a href='#'  style='text-decoration: none;' onclick = registerService.onAttachClick(this)> " +
					"<span class='glyphicon glyphicon-paperclip'></span><span hidden='true'>t</span></a></td>"
			+ "</tr>";
		 });
		 
		 $("#userDirectSysTableBody").append(rows);
	};
	
	this.onAttachClick = function(object)
	{
		currentObject.directEndPoint = $(object).closest('tr').find('td:eq(2)').text();
		currentObject.uploadBinding(currentObject.directEndPoint);
		$("#anchoruploadform").parsley().reset();
		$('#progressText').html("");
		$('#anchoruploadform').trigger('reset');
		$('#anchoruploadfiles').empty();
		currentObject.readAllCerts(currentObject.directEndPoint);
	};
	
	this.readAllCerts = function(directEndPoint)
	{
		var callbackFunction = $.Callbacks('once');
		callbackFunction.add(currentObject.readallCertsSuccessHandler);
		var httpService = new HttpAjaxServices();
		httpService.readAllCerts(callbackFunction, false,directEndPoint);
	};
	
	this.readallCertsSuccessHandler = function(successJson)
	{
		$("#certAnchorsTableBodyId").empty();
		var resultArray = successJson.resultSet.results;
		currentObject.allCerts = resultArray;
		var rows = "";
		 $(resultArray).each(function(){
		   rows += 	"<tr><td><a href='#'  style='text-decoration: none;' onclick = registerService.downloadFile(this) >" +this.certFile + "</a></td>"
		   +	"<td>"+currentObject.convertDateToLocal(this.uploadedTimeStamp)+"</td>"
			+   "<td><a href='#'  style='text-decoration: none;' onclick = registerService.deleteConfirm(this)> " +
					"<span class='glyphicon glyphicon-trash'></span><span hidden='true'>t</span></a></td>"
			+ "</tr>";
		 });
		 
		 $("#certAnchorsTableBodyId").append(rows);
		 $('#uploadModel').modal('show');
	};
	
	this.downloadFile = function(object)
	{
		var fileName = $(object).closest('tr').find('td:eq(0)').text();
		var httpService = new HttpAjaxServices();
		$(currentObject.allCerts).each(function(){
			if(this.certFile == fileName)
			{
				var obj = {};
				obj.key = "filePath";
				obj.value = this.absolutePath;
				var dataArray = [];
				dataArray.push(obj);
				httpService.downloadFile(dataArray);
				return;
			}
		});
		
	};
	
	this.deleteConfirm = function(object)
	{
		$('#DeleteConfirm').modal('show');
		currentObject.selectedFile = $(object).closest('tr').find('td:eq(0)').text();
	};
	
	this.deleteCert = function()
	{
		$('#DeleteConfirm').modal('hide');
		var fileName = currentObject.selectedFile;
		var callbackFunction = $.Callbacks('once');
		callbackFunction.add(currentObject.deleteCertsSuccessHandler);
		var httpService = new HttpAjaxServices();
		$(currentObject.allCerts).each(function(){
		  if(this.certFile == fileName)
		  {
			 httpService.deleteCerts(callbackFunction, false, this.absolutePath);
			 return;
		  }
		});
	};
	
	this.deleteCertsSuccessHandler = function(successJson)
	{
		if(successJson.booleanOutput)
		{
			currentObject.readAllCerts(currentObject.directEndPoint);
		}
	};
	
	this.onEditClick = function onEditClick(object)
	{
		cleanUp();
		$("#updateServiceButton").show();
		$("#submitButton").hide();
		var selectedDirectEmail = $(object).closest('tr').find('td:eq(2)').text();
		$(currentObject.resultSet).each(function(){
			if(this.directEmailAddress == selectedDirectEmail)
			{
				$("#cehrtLabel").val(this.cehrtLabel);
				currentObject.editingSystemID = this.id;
				$("#orgName").val(this.organizationName);
				$("#directEmail").val(this.directEmailAddress);
				$("#directEmail").attr("disabled", "disabled");
				$("#pocEmail").val(this.pointOfContact);
				$("#pocFirstName").val(this.pocFirstName);
				$("#pocLastName").val(this.pocLastName);
				var text = this.timezone;
				$('#timezone').val(text);
				if(this.directTrustMembership == 'Yes')
				{
					$("#option_yes").attr('checked', 'checked');	
				}else
				{  
					$("#option_no").attr('checked', 'checked');
				}
				$("#availFromDate").val(this.availFromDate);
				$("#availToDate").val(this.availToDate);
				
				
			   return;
			}
		});
	};
	
	this.convertDateToLocal = function(date)
	{
        var givenDate = new Date(date);

        if(givenDate == 'NaN') return;

        // format the date 
        return $.format.date(givenDate, 'MM/dd/yyyy hh:mm:ss a');

	};
	
	this.uploadBinding = function(directEndPoint)
	{
		// Change this to the location of your server-side upload handler:
		var URL = currentObject.UPLOAD_FILE + directEndPoint;
		//$('#anchoruploadprogress').hide();
		$('#anchoruploadfile').fileupload(
		{
			url : URL,
			dataType : 'json',
			autoUpload : false,
			type : 'POST',
			contenttype : false,
			replaceFileInput : false,
			beforeSend: function(xhr){
		        xhr.setRequestHeader("X-Auth-Token", sessionStorage.authToken);
		    },
			done : function(e, data) {
				
				$('#anchoruploadform').trigger('reset');
				$('#anchoruploadfiles').empty();
				$('#progressText').html("Cert uploaded successfully");
				$('#anchoruploadsubmit').unbind("click");
				currentObject.readAllCerts(directEndPoint);
			},
			progressall : function(e, data) {
			
			}
		}).on('fileuploadadd',function(e, data) {
			 $('#anchoruploadsubmit').unbind("click");
			 $('#anchoruploadfiles').empty();
			 
			 data.context = $('<div/>').appendTo('#anchoruploadfiles');
			 $.each(data.files, function(index, file) {
				var node = $('<p/>').append($('<span/>').text(file.name));
								node.appendTo(data.context);
			});
		    
			 $('#anchoruploadsubmit').click(function(e) {
				 if($("#anchoruploadform").parsley().validate()){
					   $('#anchoruploadprogress').show();
					   data.submit();
				}
			});
		}).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');
			$('#anchoruploadfile').bind('fileuploaddrop', function(e, data) {
					e.preventDefault();
			}).bind('fileuploaddragover', function(e) {
				e.preventDefault();
		});
		$('#anchoruploadfile-btn').bind('click', function(e, data) {
			$('#progressText').html("");
			$('#anchoruploadform').trigger('reset');
			$('#anchoruploadfiles').empty();
		});
	};
	
};


