<#assign title = "DogeShop | Main page"/>

<#include "blocks/header.ftl">

<div class="d-flex flex-row flex-wrap justify-content-around">
    <#list 1..25 as x>
        <div class="border border-2 p-4 rounded mb-3" style="min-width: 260px; max-width: 260px; min-height: 360px; max-height: 360px;">
            <div class="w-100 h-100 d-flex flex-column justify-content-between text-center align-items-center">
                <img class="border border-2 rounded" height="150px" width="150px" src="<@spring.url '/img/doge.png'/>" alt="">
                <div style=" -webkit-line-clamp: 3; -webkit-box-orient: vertical; display: -webkit-box; overflow: hidden; text-overflow: ellipsis; font-weight: 600;">
                    <@spring.message code='skin.default_description'/>
                </div>
                    <div class="w-100">
                        <#if authed>
                            <button class="w-100 btn btn-light border border-2">В корзину</button>
                        </#if>
                    </div>
            </div>
        </div>
    </#list>

</div>

<#include "blocks/footer.ftl">