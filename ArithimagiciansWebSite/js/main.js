/**
 * Created by mroien on 11/14/15.
 */

var logout = '<br><a href="#" class="logout">Logout</a>',
    local_username = localStorage.getItem('username'),
    local_userID = localStorage.getItem('userID');

$(document).ready(function(){

    //On page load
    //Check if user is logged in

    if(local_username !== null) {
        $('.signedIn')
            .text("Welcome, " + localStorage.getItem('username'))
            .append(logout);
        $('a:contains("Sign Up")').addClass('disabled');
        $('a:contains("Login")').addClass('disabled');
    }

    //Clear localStorage and sign the user out

    $('.logout').on('click', function(){
        localStorage.clear();
        sessionStorage.clear();
        location.reload(true);
        window.location.href = '../main/index.html';
    });

    //Remove and show stuff on download page

    $('.download').click(function () {
        $('.progress, .cancel').removeClass('hide');
    });
    $('.cancel').click(function(){
        $('.progress, .cancel').addClass('hide');
    });
    
});





