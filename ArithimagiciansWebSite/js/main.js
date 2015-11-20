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
    }

    //Clear localStorage and sign the user out

    $('.logout').on('click', function(){
        localStorage.clear();
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

    $('#phone')
        .keydown(function (e) {
            var key = e.charCode || e.keyCode || 0;
            $phone = $(this);

            // Auto-format- do not expose the mask as the user begins to type
            if (key !== 8 && key !== 9) {
                if ($phone.val().length === 4) {
                    $phone.val($phone.val() + ')');
                }
                if ($phone.val().length === 5) {
                    $phone.val($phone.val() + ' ');
                }
                if ($phone.val().length === 9) {
                    $phone.val($phone.val() + '-');
                }
            }

            // Allow numeric (and tab, backspace, delete) keys only
            return (key == 8 ||
            key == 9 ||
            key == 46 ||
            (key >= 48 && key <= 57) ||
            (key >= 96 && key <= 105));
        })

        .bind('focus click', function () {
            $phone = $(this);

            if ($phone.val().length === 0) {
                $phone.val('(');
            }
            else {
                var val = $phone.val();
                $phone.val('').val(val); // Ensure cursor remains at the end
            }
        })

        .blur(function () {
            $phone = $(this);

            if ($phone.val() === '(') {
                $phone.val('');
            }
        });
});





