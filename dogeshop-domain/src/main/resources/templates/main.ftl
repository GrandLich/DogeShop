<#assign title = "DogeShop | Main page"/>

<#include "blocks/header.ftl">

<div class="d-flex flex-row flex-wrap justify-content-around">
    <#list skins as skin>
        <div class="border border-2 p-4 rounded mb-3" style="min-width: 260px; max-width: 260px; min-height: 360px; max-height: 360px;">
            <div class="w-100 h-100 d-flex flex-column justify-content-between text-center align-items-center">
                <img class="border border-2 rounded" height="150px" width="150px" src="<@spring.url skin.pictureUrl />" alt="">
                <div style=" -webkit-line-clamp: 3; -webkit-box-orient: vertical; display: -webkit-box; overflow: hidden; text-overflow: ellipsis; font-weight: 600;">
                    ${skin.description}
                </div>
                    <div class="w-100">
                        <#if authed>
                            <button type="button" class="buybtn w-100 btn btn-light border border-2" data-id="${skin.id}"><@spring.message code='skin.addtocart'/></button>
                        </#if>
                    </div>
            </div>
        </div>
    </#list>

</div>

<script>
	$(document).ready(function(){
    $(".buybtn").on('click', function(e){
            $(this).addClass('disabled').attr('disabled', 'disabled');
			let id = $(this).attr('data-id');
            $.ajax({
                url:'/api/auth/login',
                type:'post',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data),
                success:function(response){
                    $('#submit_login').removeClass('disabled').removeAttr('disabled');
                    console.log(response)
					if(response.accountName != null) {
                        window.location = "/lk";
                    }else {
						$('#registerFail').removeClass('d-none');
                    }
                },
                error: function (err) {
                    $('#submit_login').removeClass('disabled').removeAttr('disabled');
                    $('#registerFail').removeClass('d-none');
                }
            });
    });
});
</script>

<#include "blocks/footer.ftl">