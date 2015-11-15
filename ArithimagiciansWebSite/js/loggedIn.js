/**
 * Created by mroien on 11/15/15.
 */

var storage = localStorage.getItem('username');

if(storage === null) {
    alert('Not logged in')
} else {
    alert("You are logged in")
}
