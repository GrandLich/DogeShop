<#assign title = "DogeShop | Skin">

<#include "blocks/header.ftl">

<div class="row">
    <div class="col-md-4 text-center align-items-center">
        <div>
            <img height="200px" src="${skin.pictureUrl}" alt="">
        </div>
    </div>

    <div class="col-md-8">
        <div class="text-left col-16">
            <h2>${skin.name}</h2>
            <p style="margin-bottom: 30px;">${skin.description}</p>
            <p><b><@spring.message code='skin.priceLabel'/> ${skin.price} RUB</b></p>
            <button class="btn btn-primary d-block items-end self-end" onclick="back()"><@spring.message code='skin.backbtn'/></button>
        </div>
    </div>

</div>

<#include "blocks/footer.ftl">

<script>

    function back() {
        window.location.replace('/');
    }

$(document).ready(function(){
});

</script>