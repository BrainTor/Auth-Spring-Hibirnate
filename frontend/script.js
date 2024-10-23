import CONFIG from './config.js'
let fetch_url = `http${CONFIG.is_ssl?'s':''}://${CONFIG.BACKURL||CONFIG.IP+':'+CONFIG.PORT}/api/`

$('.message a').click(function(){
    $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
 });
$('form').submit(function(e){
    e.preventDefault()
})
 $('#reg').click(async ()=>{
    await fetch(fetch_url+'user/reg', {
        method:'POST', 
        headers:{
            'Content-Type':'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body:JSON.stringify({login: $("#login_reg").val(), password:$("#password_reg").val(), email: $("#email_reg").val()})
    }).then((res)=>{
        alert(res.json())
        // if(res.status == 200)
        //     window.location.href = 'main.html'
    })    
    
 })

 document.body.onload = async ()=>{
    await fetch(fetch_url+'user/test', {
        method:'POST', 
        headers:{
            "Authorization": `Bearer ${localStorage.getItem("token")}`,
            'Content-Type':'application/json'
        }
       
    })
 }

 $('#log').click(async ()=>{
    await fetch(fetch_url+'user/login', {
        method:'POST', 
        headers:{
            'Content-Type':'application/json'
        },
        body:JSON.stringify({login: $("#login_log").val(), password:$("#password_log").val() })
    }).then((res)=>{
        return res.text()
        // if(res.status == 200)
        //     window.location.href = 'main.html'
    }).then((res) => {
        localStorage.setItem('token', res)
    })

 })