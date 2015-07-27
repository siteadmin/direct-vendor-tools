<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>Direct Transport Standard Testing Services</title>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

<link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="vendor/bootstrap/css/bootstrap-theme.css" />
<link rel="stylesheet" href="css/custom.css" />
<link rel="stylesheet" href="css/jquery.fileupload.css" />
<link rel="stylesheet" href="css/parsley.css" />

<!-- <link rel="stylesheet" href="css/jquery.fileupload-noscript.css" /> -->

<script type="text/javascript" src="vendor/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="js/json2.js" type="text/javascript"></script>
<script src="js/jquery.dateFormat.js" type="text/javascript"></script>
<script src="js/vendor/jquery.ui.widget.js"></script>
<script src="js/jquery.iframe-transport.js"></script>
<script src="js/jquery.fileupload.js" type="text/javascript"></script>
<script src="js/jquery.filestyle.js" type="text/javascript"></script>
<script src="js/parsley.js" type="text/javascript"></script>
<script>
	var APP_CONTEXT = "${pageContext.request.contextPath}/";
</script>

<script type="text/javascript" src="js/pagesjs/AccountRegister.js"></script>
<script type="text/javascript" src="js/to/AccountRegisterTO.js"></script>
<script type="text/javascript" src="js/pagesjs/RegisterService.js"></script>
<script type="text/javascript" src="js/to/RegisterServiceTO.js"></script>
<script type="text/javascript" src="js/pagesjs/Login.js"></script>
<script type="text/javascript" src="js/to/UserLoginTO.js"></script>
<script type="text/javascript" src="js/util/Constants.js"></script>
<script type="text/javascript" src="js/util/Utility.js"></script>
<script type="text/javascript" src="js/util/HttpAjaxServices.js"></script>
<script type="text/javascript" src="js/to/Model.js"></script>

<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<script>
  
  		$(function(){
  			
  			window.ParsleyValidator.addValidator('currentdateval',function(value,requirement){
  	    		var now = new Date((new Date()).setHours(0, 0, 0, 0));
  	            var date = new Date(value);
  	          	var givenDate = new Date(date.getTime() + date.getTimezoneOffset()*60000);
  	            return givenDate >= now;
  			},32).addMessage('en','currentdateval','This field should be greater than or equal to current date');
  			
  			
  			window.ParsleyValidator.addValidator('notequal',function(value,requirement){
  				return value != $( requirement ).val();
  			},32).addMessage('en','notequal','Direct service email and POC email should not be same.');
  			
  			window.ParsleyValidator.addValidator('daterangeval',function(value,requirement){
  				var requirementVal = $( requirement ).val();
  				if(requirementVal !=null && requirementVal!='')
  				{
  					var date = new Date(value);
  	            	var date1 = new Date($( requirement ).val());
  	            	var toDate = new Date(date.getTime() + date.getTimezoneOffset()*60000);
  	            	var fromDate = new Date(date1.getTime() + date1.getTimezoneOffset()*60000);
  	            	return toDate > fromDate;
  				}else
  				{
  				    return true;	
  				}
  			},32).addMessage('en','daterangeval','Available To Date should be greater than Available From Date.');
  			
  			window.ParsleyValidator.addValidator('fromdaterangeval',function(value,requirement){
  				var requirementVal = $( requirement ).val();
  				if(requirementVal !=null && requirementVal!='')
  				{
  	    			var date = new Date(value);
  	            	var date1 = new Date($( requirement ).val());
  	            	var toDate = new Date(date1.getTime() + date.getTimezoneOffset()*60000);
  	            	var fromDate = new Date(date.getTime() + date1.getTimezoneOffset()*60000);
  	            	return toDate > fromDate;
  				}else
  				{
  				   return true;	
  				}
  			},32).addMessage('en','fromdaterangeval','Available From Date should be lesser than Available To Date.');
  			
  			window.ParsleyValidator.addValidator('trustfiletypes',function(value){
  				var ext=value.split('.').pop().toLowerCase();
  				
  				var istrue = false;
  				if  (ext === 'cer'){
  					istrue = true;
  				} else if (ext === 'crt') {
  					istrue = true;
  				} else if (ext === 'der') {
  					istrue = true;
  				} else if (ext === 'pem') {
  					istrue = true;
  				} else if (ext === 'cert') {
  					istrue = true;
  				}
  				
  				return istrue;
  			},32).addMessage('en','trustfiletypes','The selected certificate file must be a binary or Base64 encoded file (.cer, .crt, .der, or .pem).');
  			
  			// parsley Validator to validate the file size
  			window.ParsleyValidator.addValidator('anchormaxsize',function(value,requirement){
  				var file_size=$('#anchoruploadfile')[0].files[0];
  				return file_size.size < requirement*1024*1024;
  			},32).addMessage('en','anchormaxsize','The uploaded file size exceeds the maximum file size of 3 MB.');
  			
  			loadHeaderPage();
  			setInteroperabilityServicesPage();
  			$('#contactUs').click(function(){
  			    $(location).attr('href', 'mailto:admin@sitenv.org');
  			});
  		});
  		
  		function setRegisterServicePage(){
  			
  			if(MODEL.userLoggedIn == 0)
  			{
  				$("#rightNavbarID").show();
  			}
			$.get('pages/RegisterService.html',setRegisterServicePageContent);
  		}
  		
  		function setRegisterServicePageContent(data){
  			$('ul.navbar-nav li').removeClass('active');
  			$("#vendorRegLIId").addClass('active');
			$("#mainPageContent").html(data);
  		}
  		
  		function setInteroperabilityServicesPage(){
  				
  			if(MODEL.userLoggedIn == 0)
  			{
  				$("#rightNavbarID").hide();
  			}
  		
			$.get('pages/InteroperabilityServices.html',setInteroperabilityServicesPageContent);
  		}
  		
  		function setInteroperabilityServicesPageContent(data){

  			$('ul.navbar-nav li').removeClass('active');
  			$("#interopLIId").addClass('active');
			$("#mainPageContent").html(data);
  		}
  		
  		function openLoginPage(){
  			$.get('pages/LoginPage.html',showLoginPage);
  		}
  		function showLoginPage(data){
  			$("#loginModel").html(data);
  			$("#loginModel").modal('show');
  		}
  		
  		function openSignUpPage(){
  			$.get('pages/AccountRegister.html',showLoginPage);
  		}
  		
  		function showSignUpPage(data){
  			$("#signupModel").html(data);
  			$("#signupModel").modal('show');
  		}
  		
  		function loadHeaderPage()
  		{
  			$.get('pages/Header.html',loadHeaderPageContent);
  		}
  		
  		function loadHeaderPageContent(data)
  		{
  			$("#headerId").html(data);
  		}
  		
  		function onlogout(){
  			$("#logoutId").hide();
  			$("#rightNavbarID").show();
  			MODEL.userLoggedIn = 0;
  			MODEL.userEmail = "";
  			setRegisterServicePage();
  		}
 
	</script>
</head>

<body>
	<header id="headerId">
	</header>
	<nav class="navbar navbar-default navbar-static-top"
		style="margin-bottom: 0px;">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#navbar" aria-expanded="true" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			</div>
			<div id="navbar" class="navbar-collapse collapse in"
				aria-expanded="true">
				<ul class="nav navbar-nav">
				<li>
						<a href="http://sitenv.org/" style="text-decoration: none;">Home</a></li>
				 <li  id="interopLIId"><a href="javascript:setInteroperabilityServicesPage()" style="text-decoration: none;" >Direct Services</a></li>
				<li id="vendorRegLIId"><a href="javascript:setRegisterServicePage()" style="text-decoration: none;" >Developer Registration</a></li>
					
					
					
				</ul>
				
				<ul class="nav navbar-nav navbar-right" id="rightNavbarID">
					<li id="signupID"><a href="javascript:openSignUpPage()" style="text-decoration: none;" >Sign Up </a></li>
					<li id="loginID"><a href="javascript:openLoginPage()" style="text-decoration: none;" >Login</a></li>
				</ul>
				
				<ul class="nav navbar-nav navbar-right" id="logoutId" hidden="true">
					<li id="logoutLI"><a href="#" onclick="onlogout()" style="text-decoration: none;" >Logout</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<div id="mainPageContent" aria-live="assertive"></div>
	<footer class="panel-footer" style="padding-bottom: 0px;">
		<div class="container" role="contentinfo">
			<div class="row">
				<div>
					<p>
						This project was funded by a contract from the <a
							href="http://www.healthit.gov" >Office of the
							National Coordinator for Health Information Technology (ONC)</a>
					</p>
					<p>
						<a href="http://www.hhs.gov/Privacy.html" >Privacy
							Policy</a> | <a href="http://www.hhs.gov/Disclaimer.html" 
							>Disclaimer</a> | <a href="#" id="contactUs" 
							>Contact US </a>
					</p>
				</div>
			</div>
		</div>
	</footer>

	<div id="loginModel" class="modal fade" tabindex="-1"></div>
	
	<div id="signupModel" class="modal fade" tabindex="-1"></div>
</body>
</html>
