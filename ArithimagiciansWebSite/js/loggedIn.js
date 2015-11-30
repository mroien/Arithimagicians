/**
 * Created by mroien on 11/15/15.
 */

var accuracy = sessionStorage.getItem('accuracy'),
    operations = sessionStorage.getItem('operation'),
    target = sessionStorage.getItem('target'),
    number = sessionStorage.getItem('numberOfTries'),
    maxLevel = sessionStorage.getItem('maxLevel'),
    fname = sessionStorage.getItem('firstname'),
    total = sessionStorage.getItem('total'),
    ccType = sessionStorage.getItem('ccType'),
    total_check = sessionStorage.getItem('total'),
    XPBONUS_check = sessionStorage.getItem('xp_bonus'),
    DMGBOUNUS_check = sessionStorage.getItem('damage'),
    LOOTBONUS_check = sessionStorage.getItem('loot'),
    HPFULLREFRESH_check = sessionStorage.getItem('health'),
    HPREGEN_check = sessionStorage.getItem('regeneration');



$(document).ready(function(){

    if(local_username === null) {
        window.location.href = '../main/login.html'
    } else {
        console.log("You are logged in")
    }

    //Prompts for Delete page

    $('.del_yes').on('click', function(){
        var _delete = prompt("Are you sure you want to Delete your account? Type YES to confirm");
        _delete.toUpperCase();
        if(_delete === 'YES'){
            $.ajax({
                url: 'http://52.32.43.132:8080/delete?',
                type: 'POST',
                data: 'userID=' + local_userID,
                crossDomain: true,
                success: function(response) {
                    sessionStorage.clear();
                    window.location.href = '../main/index.html'
                }
            })
        }
    });

    // Clicking the No button on download page and opt out page

    $('.del_no, .opt_no').on('click', function(){
        window.location.href = 'accountInfo.html';
    });

    // Quantity on Purchase page

    var xp_bonus_sub = 0, damage_sub = 0, loot_sub = 0, health_sub = 0, regeneration_sub = 0;

    $('.xp_bonus').on('change', function () {
        sessionStorage.setItem('xp_bonus', this.value);
        var xp_bonus = sessionStorage.getItem('xp_bonus');
        xp_bonus_sub = (xp_bonus * .99).toFixed(2);
        sessionStorage.setItem('xp_bonus_sub', xp_bonus_sub);
        $('.xp_bonus_sub').html('$' + xp_bonus_sub)
    });

    $('.damage').on('change', function () {
        sessionStorage.setItem('damage', this.value);
        var damage = sessionStorage.getItem('damage');
        damage_sub = (damage * 1.49).toFixed(2);
        sessionStorage.setItem('damage_sub', damage_sub);
        $('.damage_sub').html('$' + damage_sub)
    });

    $('.loot').on('change', function () {
        sessionStorage.setItem('loot', this.value);
        var loot = sessionStorage.getItem('loot');
        loot_sub = (loot * .99).toFixed(2);
        sessionStorage.setItem('loot_sub', loot_sub);
        $('.loot_sub').html('$' + loot_sub)
    });

    $('.health').on('change', function () {
        sessionStorage.setItem('health', this.value);
        var health = sessionStorage.getItem('health');
        health_sub = (health * 1.99).toFixed(2);
        sessionStorage.setItem('health_sub', health_sub);
        $('.health_sub').html('$' + health_sub)
    });

    $('.regeneration').on('change', function () {
        sessionStorage.setItem('regeneration', this.value);
        var regeneration = sessionStorage.getItem('regeneration');
        regeneration_sub = (regeneration * 2.99).toFixed(2);
        sessionStorage.setItem('regeneration_sub', regeneration_sub);
        $('.regeneration_sub').html('$' + regeneration_sub);
    });
    $('.xp_bonus, .damage, .loot, .health, .regeneration').on('change', function(){
        var total = (parseFloat(xp_bonus_sub) + parseFloat(damage_sub) + parseFloat(loot_sub) + parseFloat(health_sub) +
        parseFloat(regeneration_sub)).toFixed(2);
        sessionStorage.setItem('total', total);
        $('.total').html('$' + total);
    });

    // Redirect page from purchase power up to CC page
    $('.confirm').on('click', function() {
        window.location.href = 'paymentInfo.html';
    });

    // Stats Page
   $('#accuracy').html(accuracy);
   $('#operations').html(operations);
    $('#number').html(number);
    $('#maxLevel').html(maxLevel);
    $('#target').html(target);

    // Setting up Payment Stub
    $('#fname').html(fname);
    $('#total').html(total);
    $('.ccType').html(ccType);

    // Opt Out Page

    $('.opt_yes').on('click', function(){
        var _opt = prompt("Are you sure you want to opt out of the leaderboard? Type YES to confirm");
        _opt.toUpperCase();
        if(_opt === 'YES'){
            $.ajax({
                url: 'http://52.32.43.132:8080/optOut?',
                type: 'POST',
                data: 'userID=' + local_userID,
                crossDomain: true,
                success: function(response) {
                    alert(local_username + ' you have opted out of being shown in the Leaderboard');
                    window.location.href = 'accountInfo.html'
                }
            })
        }
    });
});



