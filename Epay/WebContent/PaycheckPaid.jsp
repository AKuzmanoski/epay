<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Paid Paycheck Overview</title>
<style type="text/css">

.body{
   margin-left:100px;
   margin-top:50px;
   height:320px;
   width:600px;
}
label{
  display:block;
  width:200px;
  margin-left:5px;
  margin-right:5px;
  float:left;
}
input{
  width:270px;
  margin-left:5px;
  margin-right:5px;
  float:left;
}
.left, .right{
 padding-left:5px;
 padding-right:5px;
 align:center;
 height:350px;
 background-color:pink;
 font:black;
 width:280px;
 float:left

}
p,h3{
text-align:right;
margin-right:10px;
}

</style>
</head>
<body>
	<div class="content">
		<form action="PaycheckServlet" method="post">
		 <div class="body">
		  <div class='left'>
		    <br/><br/>
		    <h3> Applicant</h3>
			<label>Applicant name</label>
			<input type=text name="applicantName" readonly>
			<label>Applicant address</label>
			<input type=text name="applicantAddress" readonly>
			<label>Applicant bank</label>
			<input type=text name="applicantBank" readonly>
			<label>Applicant account</label>
			<input type=text name="applicantAccount" readonly>
			<label>Applicant social security</label>
			<input type=text name="applicantSS" readonly>
			<label>Description</label>
			<input type=text name="description"readonly>
		  </div>
		  <div class='right'>	    
		    <p>Public revenue</p>
		    <h3> Receiver</h3>
			<label>Receiver name</label>
			<input type=text name="receiverName" readonly>
			<label>Receiver bank</label>
			<input type=text name="receiverBank" readonly>
			<label>Receiver account</label>
			<input type=text name="receiverAccount" readonly>
			<label>Amount</label>
			<input type=text name="amount"readonly>
			<br/><br/>
			<label>PP50</label>
		  </div>
		  <br/> 
		</div>
		
		</form>
	</div>
</body>
</html>
</head>
<body>

</body>
</html>