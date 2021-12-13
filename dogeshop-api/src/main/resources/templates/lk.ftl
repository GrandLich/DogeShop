<#assign title = "DogeShop | Cabinet">

<#include "blocks/header.ftl">

<div class="row">
    <div class="col-md-4 text-center align-items-center">
        <div>
            <img height="150px" src="<@spring.url '/img/profile.png'/>" alt="">
        </div>
        <div>
            <span class="fs-5 d-block" style="font-weight: 600;">${username}</span>
            <span class="fs-5" style="font-weight: 600;"><@spring.message code='lk.status'/></span>
        </div>
        <div class="mt-4">
            <span style="font-weight: 600;"><@spring.message code='lk.balance'/> ${balance} <@spring.message code='lk.currency'/></span>
        </div>
        <div class="mt-4 col-md-6 offset-md-3 d-flex flex-column">
            <a class="btn btn-light border" href="/payment"><@spring.message code='lk.addpayment'/></a>
            <a class="btn btn-light border mt-2" href="#" id="logout_btn"><@spring.message code='lk.quit'/></a>
        </div>
    </div>

    <div class="col-md-8">
        <div class="text-center col-12">
            <h2><@spring.message code='lk.history.title'/></h2>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th><@spring.message code='lk.history.skinDto'/></th>
                    <th><@spring.message code='lk.history.date'/></th>
                    <th><@spring.message code='lk.history.price'/></th>
                </tr>
            </thead>
            <tbody>
                <#list orders as order>
                <tr>
                    <td>${order.skinName}</td>
                    <td>${order.date?datetime.xs}</td>
                    <td>${order.price} RUB</td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>

</div>

<#include "blocks/footer.ftl">

<script>

$(document).ready(function(){
    $("#logout_btn").on('click', function(e){
		e.preventDefault();
            $.ajax({
                url:'/api/auth/logout',
                type:'post',
                success:function(response) {
                    window.location = "/";
                },
                error: function (err) {
                    console.log(err)
                }
            });
    });
});

</script>