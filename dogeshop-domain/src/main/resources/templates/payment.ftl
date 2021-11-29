<#assign title = "Аутентификация">

<#include "blocks/header.ftl">

<div class="p-2 row">
    <div class="col-md-6 offset-md-3">
        <form action="" method="post" id="paymentForm">
            <div id="success" class="alert alert-success text-center d-none">
                Баланс успешно пополнен
            </div>
            <div class="mb-3">
                <label for="balance" class="col-form-label">Количество: <span class="text-danger">*</span></label>
                <input type="text" class="form-control" id="balance" name="balance" required>
            </div>
            <div class="col-12 text-center">
                <button class="btn btn-light border rounded" type="submit" id="submit_btn">Пополнить баланс</button>
            </div>
        </form>
    </div>
</div>

<#include "blocks/footer.ftl">

<script>

$(document).ready(function(){
    $("#paymentForm").on('submit', function(e){
		e.preventDefault();
		$('#success').addClass('d-none');
        let balance = $("#balance").val().trim();
        let data = {
            balance: balance
        }
        if(balance.length !== 0){
            $('#submit_btn').addClass('disabled').attr('disabled', 'disabled');
            $.ajax({
                url:'/api/account/deposit',
                type:'post',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data),
                success:function(response){
                    $('#submit_btn').removeClass('disabled').removeAttr('disabled');
                    console.log(response)
                },
                error: function (err) {
                    $('#submit_btn').removeClass('disabled').removeAttr('disabled');
                    $('#success').removeClass('d-none').text('Баланс успешно пополнен');
                    console.log(err)
                }
            });
        }
    });
});

</script>