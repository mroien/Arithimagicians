/**
 * Created by mroien on 11/15/15.
 */

var storage = localStorage.getItem('username');



$(document).ready(function(){

    if(storage === null) {
        console.log('Not logged in');
        //window.location.href = '../main/login.html'
    } else {
        console.log("You are logged in")
    }

    //Prompts for Delete page

    $('.yes').on('click', function(){
        var _delete = prompt("Are you sure you want to Delete your account? Type YES to confirm");
        _delete.toUpperCase();
        if(_delete === 'YES'){
            $.ajax({
                url: 'http://52.32.43.132:8080/delete?',
                type: 'POST',
                data: 'userID=' + local_userID,
                crossDomain: true,
                success: function(response) {
                    localStorage.clear();
                    window.location.href = '../main/index.html'
                }
            })
        }
    });

    // Clicking the No button on download page

    $('.no').on('click', function(){
        window.location.href = 'accountInfo.html';
    });

    // Quantity on Purchase page

    $('.xp_bonus').on('change', function () {
        sessionStorage.setItem('xp_bonus', this.value);
        xp_bonus = sessionStorage.getItem('xp_bonus');
        xp_bonus_sub = (xp_bonus * .99).toFixed(2);
        $('.xp_bonus_sub').html(xp_bonus_sub)
    });

    $('.damage').on('change', function () {
        sessionStorage.setItem('damage', this.value);
        damage = sessionStorage.getItem('damage');
        damage_sub = (damage * 1.49).toFixed(2);
        $('.damage_sub').html(damage_sub)
    });

    $('.loot').on('change', function () {
        sessionStorage.setItem('loot', this.value);
        loot = sessionStorage.getItem('loot');
        loot_sub = (loot * .99).toFixed(2);
        $('.loot_sub').html(loot_sub)
    });

    $('.health').on('change', function () {
        sessionStorage.setItem('health', this.value);
        health = sessionStorage.getItem('health');
        health_sub = (health * 1.99).toFixed(2);
        $('.health_sub').html(health_sub)
    });

    $('.regeneration').on('change', function () {
        sessionStorage.setItem('regeneration', this.value);
        regeneration = sessionStorage.getItem('regeneration');
        regeneration_sub = (regeneration * 2.99).toFixed(2);
        $('.regeneration_sub').html(regeneration_sub)
    });

    console.log("test " + parseInt(window.regeneration_sub))



});



