<#assign title = "Корзина">
<#assign authed = true>

<#include "blocks/header.ftl">

<div>
    <div class="row">
        <div class="col-md-6 d-flex">
            <span class="my-auto">Баланс: 0 рублей</span>
        </div>
        <div class="col-md-6 d-flex flex-row-reverse">
            <a class="btn btn-light border" style="max-width: 200px;" href="">Пополнить баланс</a>
        </div>
    </div>
    <div class="row mt-4">
        <div class="text-center col-12">
            <h2>Корзина</h2>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>Чо купишь</th>
                    <th>Цена</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Педигри</td>
                    <td>40 хрывень</td>
                </tr>
                <tr></tr>
                <tr>
                    <td class="text-end">Итог</td>
                    <td>40 хрывень</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<#include "blocks/footer.ftl">