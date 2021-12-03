<#assign title = "DogeShop | Sign up">

<#include "blocks/header.ftl">

<div class="p-2 row">
    <div class="col-md-6 offset-md-3">
        <form id="reg_form">
			<div id="registerFail" class="alert alert-danger text-center d-none">
				<@spring.message code='register.fail'/>
			</div>
            <div class="mb-3">
                <label for="username" class="col-form-label"><@spring.message code='register.username'/><span class="text-danger">*</span></label>
                <input type="username" class="form-control" id="username" name="username" required>
            </div>
            <div class="mb-3">
                <label for="password" class="col-form-label"><@spring.message code='register.password'/><span class="text-danger">*</span></label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="col-12 text-center">
                <button class="btn btn-light border rounded" type="submit" id="submit_btn"><@spring.message code='register.button'/></button>
            </div>
        </form>
    </div>
</div>

<#include "blocks/footer.ftl">

<script>
$(document).ready(function(){
    $("#reg_form").on('submit', function(e){
		e.preventDefault();
		$('#registerFail').addClass('d-none');
        let username = $("#username").val().trim();
        let password = $("#password").val().trim();
        let data = {
            name: username,
            password: password
        }
        if( username.length !== 0 && password.length !== 0 ){
            $('#submit_btn').addClass('disabled').attr('disabled', 'disabled');
            $.ajax({
                url:'/api/account/create',
                type:'post',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data),
                success:function(data){
                    $('#submit_btn').removeClass('disabled').removeAttr('disabled');
                    console.log(data)
                    window.location = "/lk";
                },
                error: function (err) {
                    $('#submit_btn').removeClass('disabled').removeAttr('disabled');
                    console.log(err)
					if(err.status === 409) {
						$('#registerFail').removeClass('d-none');
					}
                }
            });
        }
    });
});

</script>