<!DOCTYPE html>

<html>

<head>
    <title>Confirmation Page</title>
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
        <h1>Confirmation Page</h1>

        <img src="../images/Scroll.png" class="scroll">
        <p class="description confirm"> Congratulations, you are logged in!
        </p>
    </div>
</div>
<?php
header('refresh:5; url=accountInfo.php');
?>
</body>
</html>