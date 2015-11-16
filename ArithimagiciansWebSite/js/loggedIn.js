/**
 * Created by mroien on 11/15/15.
 */

var storage = localStorage.getItem('username');

if(storage === null) {
    console.log('Not logged in');
    //window.location.href = '../main/login.html'
} else {
    console.log("You are logged in")
}


