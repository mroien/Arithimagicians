<!DOCTYPE html>
<?php
require('leftNav.php');

?>
<html>

<head>
    <title>LogIn</title>
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
        <h1>LogIn</h1>

        <img src="../images/Scroll.png" class="scroll">
        <form method="post" action="confirmation.php" id="login">
            <fieldset>
                <label for="username">
                    Username:
                </label>
                <input type="text" id="username" name="username" placeholder="Username" autofocus required pattern="[\w\D]{5,15}"><br>
                <label for="password">
                    Password:
                </label>
                <input type="password" id="password" name="username" placeholder="Password" required pattern="[\w\D]{5,15}"><br>
                <input type="submit" class="bottomButton" name="submit">
                <input type="reset" name="reset" class="bottomButton">
            </fieldset>
        </form>
    </div>
</div>

</body>
</html>