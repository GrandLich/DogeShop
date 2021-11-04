<#assign title = "Личный кабинет">
<#assign authed = true>

<#include "blocks/header.ftl">

<div class="row">
    <div class="col-md-4 text-center align-items-center">
        <div>
            <img height="150px" src="<@spring.url '/img/profile.png'/>" alt="">
        </div>
        <div>
            <span class="fs-5" style="font-weight: 600;">Злой покупатель</span>
        </div>
        <div class="mt-4">
            <span style="font-weight: 600;">Баланс: 0 рублей</span>
        </div>
        <div class="mt-4 col-md-6 offset-md-3 d-flex flex-column">
            <a class="btn btn-light border" href="#">Пополнить баланс</a>
            <a class="btn btn-light border mt-2" href="#">Выйти</a>
        </div>
    </div>

    <div class="col-md-8">
        <div class="text-center col-12">
            <h2>История покупок</h2>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>Чо купил</th>
                    <th>Дата</th>
                    <th>Цена</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Педигри</td>
                    <td>3.11.2021</td>
                    <td>40 хрыфень</td>
                </tr>
            </tbody>
        </table>
    </div>

</div>

<#include "blocks/footer.ftl">