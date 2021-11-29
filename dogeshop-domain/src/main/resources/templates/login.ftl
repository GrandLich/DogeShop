<#assign title = "Аутентификация">
<#assign authed = false>

<#include "blocks/header.ftl">

<div class="p-2 row">
    <div class="col-md-6 offset-md-3">
        <form action="" method="post" id="login_form">
            <div class="mb-3">
                <label for="username" class="col-form-label">Имя пользователя: <span class="text-danger">*</span></label>
                <input type="text" class="form-control" id="username" name="username" required>
            </div>
            <div class="mb-3">
                <label for="password" class="col-form-label">Пароль: <span class="text-danger">*</span></label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="col-12 text-center">
                <button class="btn btn-light border rounded" type="submit" id="submit_login">Войти</button>
            </div>
        </form>
    </div>
</div>

<#include "blocks/footer.ftl">

<script>

$(document).ready(function(){
    $("#login_form").on('submit', function(e){
		e.preventDefault();
        let username = $("#username").val().trim();
        let password = $("#password").val().trim();
        let data = {
            username: username,
            password: password
        }
        if( username.length !== 0 && password.length !== 0 ){
            $('#submit_login').addClass('disabled').attr('disabled', 'disabled');
            $.ajax({
                url:'/api/auth/login',
                type:'post',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data),
                success:function(response){
                    $('#submit_login').removeClass('disabled').removeAttr('disabled');
                    console.log(response)
                    if(response === 1){
                        window.location = "/lk";
                    }else{
						alert("Invalid username and password!");
                    }
                },
                error: function (err) {
                    $('#submit_login').removeClass('disabled').removeAttr('disabled');
                    console.log(err)
                }
            });
        }
    });
});

</script>