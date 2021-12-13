<#assign title = "DogeShop | Main page"/>

<#include "blocks/header.ftl">

<div class="d-flex flex-row flex-wrap justify-content-around">
    <#list skinDtos as skinDto>
        <div class="border border-2 p-4 rounded mb-3" style="min-width: 260px; max-width: 260px; min-height: 360px; max-height: 360px;">
            <div class="w-100 h-100 d-flex flex-column justify-content-between text-center align-items-center">
                <img class="border border-2 rounded" height="150px" width="150px" src="<@spring.url skinDto.pictureUrl />" alt="">
                <div style=" -webkit-line-clamp: 3; -webkit-box-orient: vertical; display: -webkit-box; overflow: hidden; text-overflow: ellipsis; font-weight: 600;">
                    ${skinDto.description}
                </div>
                    <div class="w-100">
                        <#if authed>
                            <#if cartSkins?seq_contains(skinDto)>
								    <button type="button" class="buybtn w-100 btn btn-success border border-2" data-id="${skinDto.id}" disabled="disabled">✔</button>
								<#else>
								    <button type="button" class="buybtn w-100 btn btn-light border border-2" data-id="${skinDto.id}"><@spring.message code='skinDto.addtocart'/></button>
							</#if>
                        </#if>
                    </div>
            </div>
        </div>
    </#list>

</div>

<script>
	$(document).ready(function(){
    $(".buybtn").on('click', function(e){
			let btn = $(this);
            btn.addClass('disabled').attr('disabled', 'disabled');
			let id = $(this).attr('data-id');
            $.ajax({
                url:'/api/cart/add?skinId='+id,
                type:'post',
                success:function(response){
                    btn.addClass('btn-success').removeClass('btn-light').text('✔');
                    console.log(response)
                },
                error: function (err) {
                    btn.removeClass('disabled').removeAttr('disabled');
                }
            });
    });
});
</script>

<#include "blocks/footer.ftl">