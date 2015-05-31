<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Registration</title>
	<link type="text/css" rel="stylesheet" href="Registration.css"></link>
	<link type="text/css" rel="stylesheet" href="../jQuery/jquery-ui.min.css"></link>
	
	
	<script type="text/javascript" src="../jQuery/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../jQuery/jquery-ui.min.js"></script>
	<script type="text/javascript" src="Registration.js"></script>	
	</head>
	<body>
		<div id="content" class="content">
			<h3>Sign Up</h3>
			<form action="../RegistrationServlet" name="registration" id="registration" method="post">
				<div id="personalInfo">
					<table>
						<tr>
							<td>Username</td>
							<td>
								<input type="text" id="username" name="username" placeholder="Your unique username" class="required" />
								<img id="usernameErr" class="err" src="Images/error.png" title="This field should not be empty or taken by another user." />
							</td>
						</tr>
						<tr>
							<td>Password</td>
							<td><input type="password" id="password" name="password" class="required" />
							<img id="passwordErr" class="err" src="Images/error.png" title="This field is required." />
							</td>
						</tr>
						<tr>
							<td>Confirm Password</td>
							<td><input type="password" id="confirm" />
							<img id="confirmErr" class="err" src="Images/error.png" title="This field is required and should have the same content as previous one." />
							</td>
						</tr>
						<tr>
							<td></td>
							<td><input type="radio" id="individual" name="isIndividual" value="individual" />
								Individual <input type="radio" id="firm" name="isIndividual" value="firm" />
								Firm</td>
						</tr>
					</table>
					<hr />
					<div id="individualForm" class="Forms">
						<table>
							<tr>
								<td>First Name</td>
								<td><input type="text" id="firstname" name="firstname" placeholder="Your First Name" class="required" />
								<img id="firstnameErr" class="err" src="Images/error.png" title="This field is required." />
								</td>
							</tr>
							<tr>
								<td>Last Name</td>
								<td><input type="text" id="lastname" name="lastname" placeholder="Your Last Name" class="required" />
								<img id="lastnameErr" class="err" src="Images/error.png" title="This field is required." />
								</td>
							</tr>
							<tr>
								<td>Social Security</td>
								<td><input type="text" id="socialsecurity"
									name="socialsecurity" placeholder="Your unique Social Security Identifier" class="required" />
									<img id="socialsecurityErr" class="err" src="Images/error.png" title="This field is required." />
									</td>
							</tr>
							<tr>
								<td>Date Of Birth</td>
								<td><input type="text" id="dateofbirth" name="dateofbirth" class="date" placeholder="Choose your Date of Birth" />
								<img id="dateofbirthErr" class="err" src="Images/error.png" title="Please use standard date format (mm/dd/yyyy) or choose from dropdown chooser." />
								</td>
							</tr>
						</table>
					</div>
					<div id="firmForm" class="Forms">
						<table>
							<tr>
								<td>Company Name</td>
								<td><input type="text" id="companyname" name="companyname" placeholder="Official Company Name" class="required" />
								<img id="companynameErr" class="err" src="Images/error.png" title="This field is required." /></td>
							</tr>
						</table>
					</div>
					<hr />
					<table>
						<tr>
							<td>Email</td>
							<td><input type="text" id="email" name="email" placeholder="An active Email address" />
							<img id="emailErr" class="err" src="Images/error.png" title="This field is required and the standard email format is required" /></td>
						</tr>
						<tr>
							<td>Contact Number</td>
							<td><input type="text" id="contactnumber" name="contactnumber" placeholder="An active phone number" /></td>
						</tr>
						<tr>
							<td>Country</td>
							<td><select id="country" name="country">
									<option value="Macedonia">Macedonia</option>
									<option value="Albania">Albania</option>
									<option value="Serbia">Serbia</option>
									<option value="Montenegro">Montenegro</option>
									<option value="Croatia">Croatia</option>
									<option value="Bulgaria">Bulgaria</option>
							</select></td>
						</tr>
						<tr>
							<td>City</td>
							<td><select id="city" name="city">
									<option value="Gostivar">Gostivar</option>
									<option value="Skopje">Skopje</option>
									<option value="Bitola">Bitola</option>
									<option value="Kocina">Kocani</option>
									<option value="Gevgelija">Gevgelija</option>
									<option value="Tetovo">Tetovo</option>
							</select></td>
						</tr>
						<tr>
							<td>Address</td>
							<td><input type="text" id="address" name="address" placeholder="Your current address" /></td>
						</tr>
					</table>
					<hr />
					<table>
						<tr>
							<td></td>
							<td class="navButtons" id="navFirst"><input type="button" id="next" value="Next" /></td>
						</tr>
					</table>
				</div>
				<div id="accountInfo">
					<table>
						<tr>
							<td>Bank</td>
							<td>
								<select id="bank" name="bank" >
									<option>Stopanska Banka AD</option>
									<option>Tutunska Banka AD</option>
									<option>Ohridska Banka</option>
									<option>Halk Bank</option>
									<option>Sparkase Bank</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Account Number</td>
							<td><input type="text" id="accountnumber" name="accountnumber" placeholder="Your unique Bank Account Number" />
							<img id="accountnumberErr" class="err" src="Images/error.png" title="This field is required." /></td>
						</tr>
						<tr>
							<td>Date From</td>
							<td><input type="text" id="datefrom" name="datefrom" class="date" placeholder="Date of Issue" />
							<img id="socialsecurityErr" class="err" src="Images/error.png" title="This field is required. Please use standard date format (mm/dd/yyyy) or choose from dropdown chooser." /></td>
						</tr>
						<tr>
							<td>Date From</td>
							<td><input type="text" id="dateto" name="dateto" class="date" placeholder="Date of Expiry" />
							<img id="socialsecurityErr" class="err" src="Images/error.png" title="This field is required. Please use standard date format (mm/dd/yyyy) or choose from dropdown chooser." /></td>
						</tr>
					</table>
					<hr />
					<table>
						<tr>
							<td></td>
							<td class="navButtons" id="navFirst">
								<input type="button" name="back" id="back" value="Back" />
								<input type="submit" name="sub" id="sub" value="Submit" />
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</body>
</html>