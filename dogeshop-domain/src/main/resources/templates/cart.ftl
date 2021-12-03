<#assign title = "DogeShop | Cart">

<#include "blocks/header.ftl">

<div>
    <div class="row">
        <div class="col-md-6 d-flex">
            <span class="my-auto"><@spring.message code='cart.balance'/> ${balance} <@spring.message code='cart.currency'/></span>
        </div>
        <div class="col-md-6 d-flex flex-row-reverse">
            <a href="/payment" class="btn btn-light border" style="max-width: 200px;" href=""><@spring.message code='cart.deposit'/></a>
        </div>
    </div>
    <div class="row mt-4">
        <div class="text-center col-12">
            <h2><@spring.message code='cart.title'/></h2>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th><@spring.message code='cart.skin'/></th>
                    <th><@spring.message code='cart.price'/></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Педигри</td>
                    <td>40 хрывень</td>
                </tr>
                <tr class="border-top">
                    <td class="text-end pt-5 fw-bold"><@spring.message code='cart.total'/></td>
                    <td class="pt-5">40 хрывень</td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="d-flex flex-row justify-content-around mt-3">
        <button data-bs-toggle="modal" data-bs-target="#successModal" class="btn btn-light rounded border"><@spring.message code='cart.buy'/></button>
        <button class="btn btn-light rounded border"><@spring.message code='cart.clear'/></button>
    </div>
</div>


<div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content" style="background-color: #59E12F;">
            <div class="modal-header">
                <h5 class="modal-title" id="successModalLabel"><@spring.message code='cart.notification.title'/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body text-center">
                <h2><@spring.message code='cart.notification.success.text'/></h2>
            </div>
        </div>
    </div>
</div>

<#include "blocks/footer.ftl">