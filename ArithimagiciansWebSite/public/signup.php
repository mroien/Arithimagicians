<!DOCTYPE html>
<?php
require('leftNav.php');
?>
<html>

<head>
    <title>Sign Up</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/main.css">
</head>
<body>
<div class="container">
    <div class="row">
        <h1>Sign Up Page</h1>

        <img src="../images/Scroll.png" class="scroll">
        <form action="confirmation.php" method="post" id="signUpForm" class="col-xs-8 col-xl-10">

            <fieldset>
                <label for="firstName">First Name:*</label>
                <input type="text" name="firstName" id="firstName" placeholder="First Name" autofocus pattern="[\w]{1,20}" required/><br>
                <label for="lastName">Last Name:*</label>
                <input type="text" name="lastName" id="lastName" placeholder="Last Name" pattern="[\w- ]{1,20}" required/><br>
                <label for="street">Street:*</label>
                <input type="text" name="street" id="street" placeholder="Street" pattern="[\w\D]{1,50}" required/><br>
                <label for="city">City:*</label>
                <input type="text" name="city" id="city" placeholder="City"pattern="[\w\D]{1,50}" required/><br>
                <label for="state">State*</label>
                <select id="state">
                    <option selected="selected">Select a state</option>
                    <option value="AL">Alabama</option>
                    <option value="AK">Alaska</option>
                    <option value="AZ">Arizona</option>
                    <option value="AR">Arkansas</option>
                    <option value="CA">California</option>
                    <option value="CO">Colorado</option>
                    <option value="CT">Connecticut</option>
                    <option value="DE">Delaware</option>
                    <option value="DC">District Of Columbia</option>
                    <option value="FL">Florida</option>
                    <option value="GA">Georgia</option>
                    <option value="HI">Hawaii</option>
                    <option value="ID">Idaho</option>
                    <option value="IL">Illinois</option>
                    <option value="IN">Indiana</option>
                    <option value="IA">Iowa</option>
                    <option value="KS">Kansas</option>
                    <option value="KY">Kentucky</option>
                    <option value="LA">Louisiana</option>
                    <option value="ME">Maine</option>
                    <option value="MD">Maryland</option>
                    <option value="MA">Massachusetts</option>
                    <option value="MI">Michigan</option>
                    <option value="MN">Minnesota</option>
                    <option value="MS">Mississippi</option>
                    <option value="MO">Missouri</option>
                    <option value="MT">Montana</option>
                    <option value="NE">Nebraska</option>
                    <option value="NV">Nevada</option>
                    <option value="NH">New Hampshire</option>
                    <option value="NJ">New Jersey</option>
                    <option value="NM">New Mexico</option>
                    <option value="NY">New York</option>
                    <option value="NC">North Carolina</option>
                    <option value="ND">North Dakota</option>
                    <option value="OH">Ohio</option>
                    <option value="OK">Oklahoma</option>
                    <option value="OR">Oregon</option>
                    <option value="PA">Pennsylvania</option>
                    <option value="RI">Rhode Island</option>
                    <option value="SC">South Carolina</option>
                    <option value="SD">South Dakota</option>
                    <option value="TN">Tennessee</option>
                    <option value="TX">Texas</option>
                    <option value="UT">Utah</option>
                    <option value="VT">Vermont</option>
                    <option value="VA">Virginia</option>
                    <option value="WA">Washington</option>
                    <option value="WV">West Virginia</option>
                    <option value="WI">Wisconsin</option>
                    <option value="WY">Wyoming</option>
                </select><br>
                <label for="zip">Zip Code:*</label>
                <input type="number" name="zip" id="zip" placeholder="Zip Code" pattern="[\D-]{5,10}" required/><br>
                <label for="email">Email Address:*</label>
                <input type="email" name="email" id="email" placeholder="Email Address" pattern="[\w\D]{5,50}" required/><br>
                <label for="username">Username:*</label>
                <input type="text" name="username" id="username" placeholder="Username" pattern="[\w\D]{1,10}" required/><br>
                <label for="password1">Password:*</label>
                <input type="password" name="password1" id="password1" placeholder="Password" onchange="form.password2.pattern = this.value" pattern="[\w\D]{6,15}" required/><br>
                <label for="password2">Confirm Password:*</label>
                <input type="password" name="password2" id="password2" placeholder="Confirm Password" pattern="[\w\D]{6,15}" required/><br>
                <input type="submit" name="submit" class="bottomButton"/>
                <input type="reset" name="reset" class="bottomButton">
            </fieldset>
        </form>
    </div>
</div>

</body>
</html>